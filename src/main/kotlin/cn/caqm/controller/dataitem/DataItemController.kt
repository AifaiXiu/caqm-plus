@file:Suppress("ktlint:standard:no-wildcard-imports")

package cn.caqm.controller.dataitem

import cn.caqm.common.Result
import cn.caqm.model.entity.DataItem
import cn.caqm.repo.DataItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dataitems")
class DataItemController {
    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    // 分页查询
    @GetMapping
    fun getAllDataItems(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<DataItem>> {
        val dataItems = dataItemRepo.findAllByTypeIn(listOf(0, 1, 2, 3, 4, 5, 6, 7), PageRequest.of(page, size))
        return Result.success(dataItems)
    }

    // 新增数据项
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
        val files = ids?.let { dataItemRepo.findAllByIdInAndTypeIn(it, listOf(0, 1, 2, 3, 4, 5, 6, 7)) }
        return Result.success(files)
    }
}
