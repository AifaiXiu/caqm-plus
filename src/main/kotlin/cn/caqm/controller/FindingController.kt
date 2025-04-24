@file:Suppress("ktlint:standard:no-wildcard-imports")

package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.dto.FindingBasicDto
import cn.caqm.model.dto.FindingEvaluateDto
import cn.caqm.model.dto.FindingMeasureDto
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

//    @PostMapping("/basic")
//    fun updateFindingBasic(
//        @RequestBody findingBasicDto: FindingBasicDto,
//    ) {
//        var id = findingBasicDto.id
//        // 把对应id下的finding的下面字段全部修改
//        var auditId = findingBasicDto.auditId
//        var closeUserId = findingBasicDto.closeUserId
//        var findingTypeId = findingBasicDto.findingTypeId
//        var processId = findingBasicDto.processId
//        var airportId = findingBasicDto.airportId
//        var deptId = findingBasicDto.deptId
//        var targetCloseTime = findingBasicDto.targetCloseTime
//        var evaluateResult = findingBasicDto.evaluateResult
//    }
//
//    @PostMapping("/evaluate")
//    fun updateFindingEvaluate(
//        @RequestBody findingEvaluateDto: FindingEvaluateDto,
//    ) {
//        var id = findingEvaluateDto.id
//        // 把对应id下的finding的下面字段全部修改
//        var rootAnalyzeId = findingEvaluateDto.rootAnalyzeId
//        val relatedDeptId = findingEvaluateDto.relatedDeptId
//        val evaluateDutyManId = findingEvaluateDto.evaluateDutyManId
//        val eventDescribe = findingEvaluateDto.eventDescribe
//        val executeDate = findingEvaluateDto.executeDate
//        var possibility = findingEvaluateDto.possibility
//        var severity = findingEvaluateDto.severity
//        var riskValue = findingEvaluateDto.riskValue
//        var isSecure = findingEvaluateDto.isSecure
//    }
//
//    @PostMapping("/measure")
//    fun updateFindingMeasure(
//        @RequestBody findingMeasureDto: FindingMeasureDto,
//    ) {
//        var id = findingMeasureDto.id
//        // 把对应id下的finding的下面字段全部修改
//        var measureDutyManId = findingMeasureDto.measureDutyManId
//        var finisherId = findingMeasureDto.finisherId
//        var targetDate = findingMeasureDto.targetDate
//        var finishDate = findingMeasureDto.finishDate
//        var details = findingMeasureDto.details
//    }

    @PostMapping("/basic")
    fun updateFindingBasic(
        @RequestBody findingBasicDto: FindingBasicDto,
    ): Result<Finding> {
        val id = findingBasicDto.id ?: return Result.failure("ID不能为空")

        // 查找现有记录
        return try {
            val finding =
                findingRepo
                    .findById(id)
                    .orElseThrow { NoSuchElementException("找不到ID为 $id 的不符合项") }

            // 更新基本信息字段
            finding.apply {
                findingBasicDto.auditId?.let { auditId = it }
                findingBasicDto.closeUserId?.let { closeUserId = it }
                findingBasicDto.findingTypeId?.let { findingTypeId = it }
                findingBasicDto.processId?.let { processId = it }
                findingBasicDto.airportId?.let { airportId = it }
                findingBasicDto.deptId?.let { deptId = it }
                findingBasicDto.targetCloseTime?.let { targetCloseTime = it }
                findingBasicDto.evaluateResult?.let { evaluateResult = it }
                findingBasicDto.status?.let { status = it }
            }

            // 保存更新后的记录
            val updatedFinding = findingRepo.save(finding)
            Result.success(updatedFinding, "基本信息更新成功")
        } catch (e: NoSuchElementException) {
            Result.failure(e.message ?: "找不到记录")
        } catch (e: Exception) {
            Result.failure("更新基本信息失败: ${e.message}")
        }
    }

    @PostMapping("/evaluate")
    fun updateFindingEvaluate(
        @RequestBody findingEvaluateDto: FindingEvaluateDto,
    ): Result<Finding> {
        val id = findingEvaluateDto.id ?: return Result.failure("ID不能为空")

        // 查找现有记录
        return try {
            val finding =
                findingRepo
                    .findById(id)
                    .orElseThrow { NoSuchElementException("找不到ID为 $id 的不符合项") }

            // 更新风险评估相关字段
            finding.apply {
                findingEvaluateDto.rootAnalyzeId?.let { rootAnalyzeId = it }
                findingEvaluateDto.relatedDeptId?.let { relatedDeptId = it }
                findingEvaluateDto.evaluateDutyManId?.let { evaluateDutyManId = it }
                findingEvaluateDto.eventDescribe?.let { eventDescribe = it }
                findingEvaluateDto.executeDate?.let { executeDate = it }
                findingEvaluateDto.possibility?.let { possibility = it }
                findingEvaluateDto.severity?.let { severity = it }
                findingEvaluateDto.riskValue?.let { riskValue = it }
                findingEvaluateDto.isSecure?.let { isSecure = it }
            }

            // 保存更新后的记录
            val updatedFinding = findingRepo.save(finding)
            Result.success(updatedFinding, "风险评估信息更新成功")
        } catch (e: NoSuchElementException) {
            Result.failure(e.message ?: "找不到记录")
        } catch (e: Exception) {
            Result.failure("更新风险评估失败: ${e.message}")
        }
    }

    @PostMapping("/measure")
    fun updateFindingMeasure(
        @RequestBody findingMeasureDto: FindingMeasureDto,
    ): Result<Finding> {
        val id = findingMeasureDto.id ?: return Result.failure("ID不能为空")

        // 查找现有记录
        return try {
            val finding =
                findingRepo
                    .findById(id)
                    .orElseThrow { NoSuchElementException("找不到ID为 $id 的不符合项") }

            // 更新措施相关字段
            finding.apply {
                findingMeasureDto.measureDutyManId?.let { measureDutyManId = it }
                findingMeasureDto.finisherId?.let { finisherId = it }
                findingMeasureDto.targetDate?.let { targetDate = it }
                findingMeasureDto.finishDate?.let { finishDate = it }
                findingMeasureDto.details?.let { details = it }
            }

            // 保存更新后的记录
            val updatedFinding = findingRepo.save(finding)
            Result.success(updatedFinding, "措施信息更新成功")
        } catch (e: NoSuchElementException) {
            Result.failure(e.message ?: "找不到记录")
        } catch (e: Exception) {
            Result.failure("更新措施信息失败: ${e.message}")
        }
    }
}
