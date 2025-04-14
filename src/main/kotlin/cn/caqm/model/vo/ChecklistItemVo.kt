package cn.caqm.model.vo

import cn.caqm.model.entity.DataItem
import cn.caqm.model.entity.Finding
import cn.caqm.model.entity.User

class ChecklistItemVo(
    var id: Long? = null,
    /**
     * 9:ChecklistItem
     */
    var type: Int? = null,
    /**
     * 检查单项相关的属性:
     */
    var checklistItemName: String? = null,
    var checklistItemContent: String? = null,
    var checklistItemFiles: List<DataItem>? = null,
    var checklistItemRemark: String? = null,
    var checklistNote: String? = null,
    var auditors: List<User>? = null,
    /**
     * 如果这项没过就会有一个不符合项
     */
    var finding: Finding? = null,
    /*
     * 0:符合
     * 1：文文不符
     * 2：文实不符
     * 3：不适用
     * */
    var status: Int? = null,
    /**
     * 审计相关的附件
     */
    var checklistItemsFiles: List<DataItem>? = null,
)
