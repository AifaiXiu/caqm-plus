package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.entity.Audit
import cn.caqm.model.vo.AuditVo
import cn.caqm.repo.AuditRepo
import cn.caqm.repo.ChecklistRepo
import cn.caqm.repo.DataItemRepo
import cn.caqm.repo.FindingRepo
import cn.caqm.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/audits")
class AuditController {
    @Autowired
    private lateinit var auditRepo: AuditRepo

    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var checklistRepo: ChecklistRepo

    @Autowired
    private lateinit var findingRepo: FindingRepo

    // 分页查询所有审计
    @GetMapping
    fun getAllAudits(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<AuditVo>> {
        val pageable = PageRequest.of(page, size)
        val audits = auditRepo.findAll(pageable)
        val auditVos =
            audits.content.map { audit ->
                val dept = audit.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val airport = audit.airportId?.let { dataItemRepo.findById(it).orElse(null) }
                val process = audit.processId?.let { dataItemRepo.findById(it).orElse(null) }
                val mainAuditor = audit.mainAuditorId?.let { userRepo.findById(it).orElse(null) }
                val otherAuditors =
                    audit.otherAuditorsId
                        ?.takeIf { it.isNotBlank() }
                        ?.split(",")
                        ?.mapNotNull { it.trim().toLongOrNull() }
                        ?.mapNotNull { userRepo.findById(it).orElse(null) }
                        ?.takeIf { it.isNotEmpty() }
                val auditMethod = audit.auditMethodId?.let { dataItemRepo.findById(it).orElse(null) }
                val checklists = audit.checklistsIds?.split(",")?.mapNotNull { checklistRepo.findById(it.toLong()).orElse(null) }
                val findings = audit.findingsIds?.split(",")?.mapNotNull { findingRepo.findById(it.toLong()).orElse(null) }
                AuditVo(
                    id = audit.id,
                    name = audit.name,
                    dept = dept,
                    plannedStartDate = audit.plannedStartDate,
                    plannedEndDate = audit.plannedEndDate,
                    realStartDate = audit.realStartDate,
                    realEndDate = audit.realEndDate,
                    airport = airport,
                    process = process,
                    mainAuditor = mainAuditor,
                    otherAuditors = otherAuditors,
                    auditMethodId = auditMethod,
                    status = audit.status,
                    closeUser = audit.closeUserId?.let { userRepo.findById(it).orElse(null) },
                    remark = audit.remark,
                    checklists = checklists,
                    findings = findings,
                    summary = audit.summary,
                )
            }
        val auditVoPage = PageImpl(auditVos, pageable, audits.totalElements)
        return Result.success(auditVoPage)
    }

    @GetMapping("/{id}")
    fun getAuditById(
        @PathVariable id: Long,
    ): Result<AuditVo?> {
        val audit = auditRepo.findById(id).orElse(null)
        return if (audit != null) {
            val dept = audit.deptId?.let { dataItemRepo.findById(it).orElse(null) }
            val airport = audit.airportId?.let { dataItemRepo.findById(it).orElse(null) }
            val process = audit.processId?.let { dataItemRepo.findById(it).orElse(null) }
            val mainAuditor = audit.mainAuditorId?.let { userRepo.findById(it).orElse(null) }
            val otherAuditors =
                audit.otherAuditorsId
                    ?.takeIf { it.isNotBlank() }
                    ?.split(",")
                    ?.mapNotNull { it.trim().toLongOrNull() }
                    ?.mapNotNull { userRepo.findById(it).orElse(null) }
                    ?.takeIf { it.isNotEmpty() }
            val auditMethod = audit.auditMethodId?.let { dataItemRepo.findById(it).orElse(null) }
            val checklists = audit.checklistsIds?.split(",")?.mapNotNull { checklistRepo.findById(it.toLong()).orElse(null) }
            val findings = audit.findingsIds?.split(",")?.mapNotNull { findingRepo.findById(it.toLong()).orElse(null) }
            val auditVo =
                AuditVo(
                    id = audit.id,
                    name = audit.name,
                    dept = dept,
                    plannedStartDate = audit.plannedStartDate,
                    plannedEndDate = audit.plannedEndDate,
                    realStartDate = audit.realStartDate,
                    realEndDate = audit.realEndDate,
                    airport = airport,
                    process = process,
                    mainAuditor = mainAuditor,
                    otherAuditors = otherAuditors,
                    auditMethodId = auditMethod,
                    status = audit.status,
                    closeUser = audit.closeUserId?.let { userRepo.findById(it).orElse(null) },
                    remark = audit.remark,
                    checklists = checklists,
                    findings = findings,
                    summary = audit.summary,
                )
            Result.success(auditVo)
        } else {
            Result.failure("审计未找到", 404)
        }
    }

    @PostMapping("/by-ids")
    fun getAuditsByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<AuditVo>> {
        val ids = idsRequest.ids?.split(",")?.mapNotNull { it.toLongOrNull() }
        val audits = ids?.let { auditRepo.findAllByIdIn(it) }
        val auditVos =
            audits?.map { audit ->
                val dept = audit.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val airport = audit.airportId?.let { dataItemRepo.findById(it).orElse(null) }
                val process = audit.processId?.let { dataItemRepo.findById(it).orElse(null) }
                val mainAuditor = audit.mainAuditorId?.let { userRepo.findById(it).orElse(null) }
                val otherAuditors =
                    audit.otherAuditorsId
                        ?.takeIf { it.isNotBlank() }
                        ?.split(",")
                        ?.mapNotNull { it.trim().toLongOrNull() }
                        ?.mapNotNull { userRepo.findById(it).orElse(null) }
                        ?.takeIf { it.isNotEmpty() }
                val auditMethod = audit.auditMethodId?.let { dataItemRepo.findById(it).orElse(null) }
                val checklists = audit.checklistsIds?.split(",")?.mapNotNull { checklistRepo.findById(it.toLong()).orElse(null) }
                val findings = audit.findingsIds?.split(",")?.mapNotNull { findingRepo.findById(it.toLong()).orElse(null) }
                AuditVo(
                    id = audit.id,
                    name = audit.name,
                    dept = dept,
                    plannedStartDate = audit.plannedStartDate,
                    plannedEndDate = audit.plannedEndDate,
                    realStartDate = audit.realStartDate,
                    realEndDate = audit.realEndDate,
                    airport = airport,
                    process = process,
                    mainAuditor = mainAuditor,
                    otherAuditors = otherAuditors,
                    auditMethodId = auditMethod,
                    status = audit.status,
                    closeUser = audit.closeUserId?.let { userRepo.findById(it).orElse(null) },
                    remark = audit.remark,
                    checklists = checklists,
                    findings = findings,
                    summary = audit.summary,
                )
            }
        return Result.success(auditVos)
    }

    // 新增审计
    @PostMapping
    fun createAudit(
        @RequestBody audit: Audit,
    ): Result<Audit> {
        val createdAudit = auditRepo.save(audit)
        return Result.success(createdAudit)
    }

    // 更新审计信息
    @PutMapping
    fun updateAudit(
        @RequestBody audit: Audit,
    ): Result<Audit> {
        val updatedAudit = auditRepo.save(audit)
        return Result.success(updatedAudit)
    }

    // 删除审计
    @DeleteMapping("/{id}")
    fun deleteAudit(
        @PathVariable id: Long,
    ): Result<Void> {
        auditRepo.deleteById(id)
        return Result.success(null, "审计删除成功")
    }
}
