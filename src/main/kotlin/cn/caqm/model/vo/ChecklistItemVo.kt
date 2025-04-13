package cn.caqm.model.vo

class ChecklistItemVo(
    /**
     * 9:ChecklistItem
     */
    var type: Int? = null,
    var value: String? = null,
    /**
     * file相关的属性：文件名和文件路径
     */
    var fileName: String? = null,
    var filePath: String? = null,
    /**
     * 检查单项相关的属性:
     */
    var checklistItemName: String? = null,
    var checklistItemContent: String? = null,
    var checklistItemFilesIds: String? = null,
    var checklistItemRemark: String? = null,
    var checklistNote: String? = null,
    var auditorsIds: String? = null,
    /**
     * 如果这项没过就会有一个不符合项
     */
    var findingId: Long? = null,
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
    var checklistItemsFilesIds: String? = null,
)
