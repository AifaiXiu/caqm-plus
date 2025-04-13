package cn.caqm.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "dataitem")
data class DataItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    /**
     * 0:Airport
     * 1:AuditMethod
     * 2:Company
     * 3:Dept
     * 4:FindingType
     * 5:Process
     * 6:RiskSource
     * 7:RootAnalyze
     * 8:File
     * 9:ChecklistItem
     */
    var type: Int? = null,
    /**
     * dataItem相关属性：数据项的值
     */
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
