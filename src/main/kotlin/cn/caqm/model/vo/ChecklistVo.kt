package cn.caqm.model.vo

import cn.caqm.model.entity.DataItem

class ChecklistVo(
    var id: Long? = null,
    var name: String? = null,
    var dept: DataItem? = null,
    var status: Int? = null,
    var remark: String? = null,
    /**
     * 将文件夹id用逗号拼接
     */
    var files: List<DataItem>? = null,
    // 下面是检查单项的内容
    var checklistItems: List<DataItem>? = null,
)
