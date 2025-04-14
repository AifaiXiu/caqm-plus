package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.entity.Checklist
import cn.caqm.model.vo.ChecklistVo
import cn.caqm.repo.ChecklistRepo
import cn.caqm.repo.DataItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checklists")
class ChecklistController {
    @Autowired
    private lateinit var checklistRepo: ChecklistRepo

    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    // 分页查询所有检查单
    @GetMapping
    fun getAllChecklists(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<ChecklistVo>> {
        val pageable = PageRequest.of(page, size)
        val checklists = checklistRepo.findAll(pageable)
        val checklistVos =
            checklists.content.map { checklist ->
                val dept = checklist.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val files = checklist.filesIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                val checklistItems = checklist.checklistItemsIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                ChecklistVo(
                    id = checklist.id,
                    name = checklist.name,
                    dept = dept,
                    status = checklist.status,
                    remark = checklist.remark,
                    files = files,
                    checklistItems = checklistItems,
                )
            }
        val checklistVoPage = PageImpl(checklistVos, pageable, checklists.totalElements)
        return Result.success(checklistVoPage)
    }

    // 新增检查单
    @PostMapping
    fun createChecklist(
        @RequestBody checklist: Checklist,
    ): Result<Checklist> {
        val createdChecklist = checklistRepo.save(checklist)
        return Result.success(createdChecklist)
    }

    // 更新检查单信息
    @PutMapping
    fun updateChecklist(
        @RequestBody checklist: Checklist,
    ): Result<Checklist> {
        val updatedChecklist = checklistRepo.save(checklist)
        return Result.success(updatedChecklist)
    }

    // 根据ID字符串查询检查单
    @PostMapping("/by-ids")
    fun getChecklistsByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<ChecklistVo>> {
        val ids = idsRequest.ids?.split(",")?.mapNotNull { it.toLongOrNull() }
        val checklists = ids?.let { checklistRepo.findAllByIdIn(it) }
        val checklistVos =
            checklists?.map { checklist ->
                val dept = checklist.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                val files = checklist.filesIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                val checklistItems = checklist.checklistItemsIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                ChecklistVo(
                    id = checklist.id,
                    name = checklist.name,
                    dept = dept,
                    status = checklist.status,
                    remark = checklist.remark,
                    files = files,
                    checklistItems = checklistItems,
                )
            }
        return Result.success(checklistVos)
    }

    @DeleteMapping("/{id}")
    fun deleteFile(
        @PathVariable id: Long,
    ): Result<Void> {
        checklistRepo.deleteById(id)
        return Result.success(null, "检查单删除成功")
    }

    @GetMapping("/{id}")
    fun getChecklistById(
        @PathVariable id: Long,
    ): Result<ChecklistVo?> {
        val checklist = checklistRepo.findById(id).orElse(null)
        return if (checklist != null) {
            val dept = checklist.deptId?.let { dataItemRepo.findById(it).orElse(null) }
            val files = checklist.filesIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
            val checklistItems = checklist.checklistItemsIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
            val checklistVo =
                ChecklistVo(
                    id = checklist.id,
                    name = checklist.name,
                    dept = dept,
                    status = checklist.status,
                    remark = checklist.remark,
                    files = files,
                    checklistItems = checklistItems,
                )
            Result.success(checklistVo)
        } else {
            Result.failure("检查单未找到", 404)
        }
    }
}
