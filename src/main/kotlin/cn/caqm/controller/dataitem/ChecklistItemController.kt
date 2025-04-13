package cn.caqm.controller.dataitem

import cn.caqm.common.Result
import cn.caqm.model.entity.DataItem
import cn.caqm.repo.DataItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    // 只查询所有的检查单项的内容
    @GetMapping
    fun getChecklistItems(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<DataItem>> {
        // TODO 把type为9的值全部查出来
        val dataItems = dataItemRepo.findAllByTypeIn(listOf(9), PageRequest.of(page, size))
        return Result.success(dataItems)
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
        return Result.success(null, "数据项删除成功")
    }

    @PostMapping("/by-ids")
    fun getFilesByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<DataItem>> {
        println(idsRequest.toString() + "打印输出了")
        val ids = idsRequest.ids?.split(",")?.map { it.toLong() }
        val files = ids?.let { dataItemRepo.findAllByIdInAndTypeIn(it, listOf(9)) }
        return Result.success(files)
    }
}
