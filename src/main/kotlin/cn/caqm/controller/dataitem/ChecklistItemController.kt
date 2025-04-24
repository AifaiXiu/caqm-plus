package cn.caqm.controller.dataitem

import cn.caqm.common.Result
import cn.caqm.model.entity.DataItem
import cn.caqm.model.vo.ChecklistItemVo
import cn.caqm.repo.DataItemRepo
import cn.caqm.repo.FindingRepo
import cn.caqm.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checklistItems")
class ChecklistItemController {
    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var findingRepo: FindingRepo

    // 只查询所有的检查单项的内容
    @GetMapping
    fun getAllChecklistItems(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<ChecklistItemVo>> {
        val pageable = PageRequest.of(page, size)
        val dataItems = dataItemRepo.findAllByTypeIn(listOf(9), pageable)
        val checklistItemVos =
            dataItems.content.map { item ->
                val checklistItemFiles =
                    item.checklistItemFilesIds
                        ?.split(
                            ",",
                        )?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                val auditors = item.auditorsIds?.split(",")?.mapNotNull { userRepo.findById(it.toLong()).orElse(null) }
                val finding = item.findingId?.let { findingRepo.findById(it).orElse(null) }
                ChecklistItemVo(
                    id = item.id,
                    type = item.type,
                    checklistItemName = item.checklistItemName,
                    checklistItemContent = item.checklistItemContent,
                    checklistItemFiles = checklistItemFiles,
                    checklistItemRemark = item.checklistItemRemark,
                    checklistNote = item.checklistNote,
                    auditors = auditors,
                    finding = finding,
                    status = item.status,
                    checklistItemsFiles = checklistItemFiles,
                )
            }
        val checklistItemVoPage = PageImpl(checklistItemVos, pageable, dataItems.totalElements)
        return Result.success(checklistItemVoPage)
    }

    @PostMapping("/detail")
    fun updateChecklistItem(
        @RequestBody checklistItemDetail: ChecklistItemDetail,
    ): Result<DataItem> {
        val id = checklistItemDetail.id
        return try {
            val checklistItemRemark = checklistItemDetail.checklistItemRemark
            val status = checklistItemDetail.status

            val optionalItem = dataItemRepo.findById(id)
            if (optionalItem.isPresent) {
                val item = optionalItem.get()

                // 更新所需字段
                if (checklistItemRemark != null) {
                    item.checklistItemRemark = checklistItemRemark
                }
                if (status != null) {
                    item.status = status
                }

                // 保存更新后的 DataItem
                val updatedItem = dataItemRepo.save(item)
                Result.success(updatedItem, "修改成功")
            } else {
                Result.failure("未找到 ID 为 $id 的检查单项")
            }
        } catch (e: Exception) {
            Result.failure("更新检查单项失败: ${e.message}")
        }
    }

    @GetMapping("/{id}")
    fun getChecklistItemById(
        @PathVariable id: Long,
    ): Result<ChecklistItemVo?> {
        val item = dataItemRepo.findById(id).orElse(null)
        return if (item != null) {
            val checklistItemFiles = item.checklistItemFilesIds?.split(",")?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
            val auditors = item.auditorsIds?.split(",")?.mapNotNull { userRepo.findById(it.toLong()).orElse(null) }
            val finding = item.findingId?.let { findingRepo.findById(it).orElse(null) }
            val checklistItemVo =
                ChecklistItemVo(
                    id = item.id,
                    type = item.type,
                    checklistItemName = item.checklistItemName,
                    checklistItemContent = item.checklistItemContent,
                    checklistItemFiles = checklistItemFiles,
                    checklistItemRemark = item.checklistItemRemark,
                    checklistNote = item.checklistNote,
                    auditors = auditors,
                    finding = finding,
                    status = item.status,
                    checklistItemsFiles = checklistItemFiles,
                )
            Result.success(checklistItemVo)
        } else {
            Result.failure("检查单项未找到", 404)
        }
    }

    // 新增检查单项
    @PostMapping
    fun createDataItem(
        @RequestBody dataItem: DataItem,
    ): Result<DataItem> {
        val createdDataItem = dataItemRepo.save(dataItem)
        return Result.success(createdDataItem)
    }

    @DeleteMapping("/{id}")
    fun deleteDataItem(
        @PathVariable id: Long,
    ): Result<Void> {
        dataItemRepo.deleteById(id)
        return Result.success(null, "检查单项删除成功")
    }

    @PostMapping("/by-ids")
    fun getChecklistItemsByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<ChecklistItemVo>> {
        val ids = idsRequest.ids?.split(",")?.mapNotNull { it.toLongOrNull() }
        ids!!.forEach { println(it) }
        val items = ids?.let { dataItemRepo.findAllByIdIn(it) }
        val checklistItemVos =
            items?.map { item ->
                val checklistItemFiles =
                    item.checklistItemFilesIds
                        ?.split(
                            ",",
                        )?.mapNotNull { dataItemRepo.findById(it.toLong()).orElse(null) }
                val auditors = item.auditorsIds?.split(",")?.mapNotNull { userRepo.findById(it.toLong()).orElse(null) }
                val finding = item.findingId?.let { findingRepo.findById(it).orElse(null) }
                ChecklistItemVo(
                    id = item.id,
                    type = item.type,
                    checklistItemName = item.checklistItemName,
                    checklistItemContent = item.checklistItemContent,
                    checklistItemFiles = checklistItemFiles,
                    checklistItemRemark = item.checklistItemRemark,
                    checklistNote = item.checklistNote,
                    auditors = auditors,
                    finding = finding,
                    status = item.status,
                    checklistItemsFiles = checklistItemFiles,
                )
            }

        return Result.success(checklistItemVos)
    }
}

data class ChecklistItemDetail(
    var id: Long,
    var checklistItemRemark: String,
    var status: Int,
)
