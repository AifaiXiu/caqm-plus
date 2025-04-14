package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.entity.Finding
import cn.caqm.model.vo.FindingVo
import cn.caqm.repo.AuditRepo
import cn.caqm.repo.DataItemRepo
import cn.caqm.repo.FindingRepo
import cn.caqm.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/findings")
class FindingController {
    @Autowired
    private lateinit var findingRepo: FindingRepo

    @Autowired
    private lateinit var auditRepo: AuditRepo

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    // 分页查询所有发现
    @GetMapping
    fun getAllFindings(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<FindingVo>> {
        val pageable = PageRequest.of(page, size)
        val findings = findingRepo.findAll(pageable)
        val findingVos =
            findings.content.map { finding ->
                val audit = finding.auditId?.let { auditRepo.findById(it).orElse(null) }
                val closeUser = finding.closeUserId?.let { userRepo.findById(it).orElse(null) }
                val findingType = finding.findingTypeId?.let { dataItemRepo.findById(it).orElse(null) }
                val process = finding.processId?.let { dataItemRepo.findById(it).orElse(null) }
                val airport = finding.airportId?.let { dataItemRepo.findById(it).orElse(null) }
                val dept = finding.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val measureDutyMan = finding.measureDutyManId?.let { userRepo.findById(it).orElse(null) }
                val finisher = finding.finisherId?.let { userRepo.findById(it).orElse(null) }
                val rootAnalyze = finding.rootAnalyzeId?.let { dataItemRepo.findById(it).orElse(null) }
                val relatedDept = finding.relatedDeptId?.let { dataItemRepo.findById(it).orElse(null) }
                val evaluateDutyMan = finding.evaluateDutyManId?.let { userRepo.findById(it).orElse(null) }
                FindingVo(
                    id = finding.id,
                    audit = audit,
                    closeUser = closeUser,
                    findingType = findingType,
                    process = process,
                    airport = airport,
                    dept = dept,
                    measureDutyMan = measureDutyMan,
                    finisher = finisher,
                    rootAnalyze = rootAnalyze,
                    relatedDept = relatedDept,
                    evaluateDutyMan = evaluateDutyMan,
                    status = finding.status,
                    targetCloseTime = finding.targetCloseTime,
                    evaluateResult = finding.evaluateResult,
                    targetDate = finding.targetDate,
                    finishDate = finding.finishDate,
                    details = finding.details,
                    measureType = finding.measureType,
                    possibility = finding.possibility,
                    severity = finding.severity,
                    riskValue = finding.riskValue,
                    isSecure = finding.isSecure,
                    eventDescribe = finding.eventDescribe,
                    executeDate = finding.executeDate,
                )
            }
        val findingVoPage = PageImpl(findingVos, pageable, findings.totalElements)
        return Result.success(findingVoPage)
    }

    // 新增发现
    @PostMapping
    fun createFinding(
        @RequestBody finding: Finding,
    ): Result<Finding> {
        val createdFinding = findingRepo.save(finding)
        return Result.success(createdFinding)
    }

    // 更新发现信息
    @PutMapping
    fun updateFinding(
        @RequestBody finding: Finding,
    ): Result<Finding> {
        val updatedFinding = findingRepo.save(finding)
        return Result.success(updatedFinding)
    }

    // 删除发现
    @DeleteMapping("/{id}")
    fun deleteFinding(
        @PathVariable id: Long,
    ): Result<Void> {
        findingRepo.deleteById(id)
        return Result.success(null, "发现删除成功")
    }

    // 根据ID字符串查询发现
    @PostMapping("/by-ids")
    fun getFindingsByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<FindingVo>> {
        val ids = idsRequest.ids?.split(",")?.mapNotNull { it.toLongOrNull() }
        val findings = ids?.let { findingRepo.findAllByIdIn(it) }
        val findingVos =
            findings?.map { finding ->
                val audit = finding.auditId?.let { auditRepo.findById(it).orElse(null) }
                val closeUser = finding.closeUserId?.let { userRepo.findById(it).orElse(null) }
                val findingType = finding.findingTypeId?.let { dataItemRepo.findById(it).orElse(null) }
                val process = finding.processId?.let { dataItemRepo.findById(it).orElse(null) }
                val airport = finding.airportId?.let { dataItemRepo.findById(it).orElse(null) }
                val dept = finding.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val measureDutyMan = finding.measureDutyManId?.let { userRepo.findById(it).orElse(null) }
                val finisher = finding.finisherId?.let { userRepo.findById(it).orElse(null) }
                val rootAnalyze = finding.rootAnalyzeId?.let { dataItemRepo.findById(it).orElse(null) }
                val relatedDept = finding.relatedDeptId?.let { dataItemRepo.findById(it).orElse(null) }
                val evaluateDutyMan = finding.evaluateDutyManId?.let { userRepo.findById(it).orElse(null) }
                FindingVo(
                    id = finding.id,
                    audit = audit,
                    closeUser = closeUser,
                    findingType = findingType,
                    process = process,
                    airport = airport,
                    dept = dept,
                    measureDutyMan = measureDutyMan,
                    finisher = finisher,
                    rootAnalyze = rootAnalyze,
                    relatedDept = relatedDept,
                    evaluateDutyMan = evaluateDutyMan,
                    status = finding.status,
                    targetCloseTime = finding.targetCloseTime,
                    evaluateResult = finding.evaluateResult,
                    targetDate = finding.targetDate,
                    finishDate = finding.finishDate,
                    details = finding.details,
                    measureType = finding.measureType,
                    possibility = finding.possibility,
                    severity = finding.severity,
                    riskValue = finding.riskValue,
                    isSecure = finding.isSecure,
                    eventDescribe = finding.eventDescribe,
                    executeDate = finding.executeDate,
                )
            }
        return Result.success(findingVos)
    }

    @GetMapping("/{id}")
    fun getFindingById(
        @PathVariable id: Long,
    ): Result<FindingVo?> {
        val finding = findingRepo.findById(id).orElse(null)
        return if (finding != null) {
            val audit = finding.auditId?.let { auditRepo.findById(it).orElse(null) }
            val closeUser = finding.closeUserId?.let { userRepo.findById(it).orElse(null) }
            val findingType = finding.findingTypeId?.let { dataItemRepo.findById(it).orElse(null) }
            val process = finding.processId?.let { dataItemRepo.findById(it).orElse(null) }
            val airport = finding.airportId?.let { dataItemRepo.findById(it).orElse(null) }
            val dept = finding.deptId?.let { dataItemRepo.findById(it).orElse(null) }
            val measureDutyMan = finding.measureDutyManId?.let { userRepo.findById(it).orElse(null) }
            val finisher = finding.finisherId?.let { userRepo.findById(it).orElse(null) }
            val rootAnalyze = finding.rootAnalyzeId?.let { dataItemRepo.findById(it).orElse(null) }
            val relatedDept = finding.relatedDeptId?.let { dataItemRepo.findById(it).orElse(null) }
            val evaluateDutyMan = finding.evaluateDutyManId?.let { userRepo.findById(it).orElse(null) }
            val findingVo =
                FindingVo(
                    id = finding.id,
                    audit = audit,
                    closeUser = closeUser,
                    findingType = findingType,
                    process = process,
                    airport = airport,
                    dept = dept,
                    measureDutyMan = measureDutyMan,
                    finisher = finisher,
                    rootAnalyze = rootAnalyze,
                    relatedDept = relatedDept,
                    evaluateDutyMan = evaluateDutyMan,
                    status = finding.status,
                    targetCloseTime = finding.targetCloseTime,
                    evaluateResult = finding.evaluateResult,
                    targetDate = finding.targetDate,
                    finishDate = finding.finishDate,
                    details = finding.details,
                    measureType = finding.measureType,
                    possibility = finding.possibility,
                    severity = finding.severity,
                    riskValue = finding.riskValue,
                    isSecure = finding.isSecure,
                    eventDescribe = finding.eventDescribe,
                    executeDate = finding.executeDate,
                )
            Result.success(findingVo)
        } else {
            Result.failure("发现未找到", 404)
        }
    }
}
