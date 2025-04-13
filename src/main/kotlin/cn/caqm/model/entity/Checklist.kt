package cn.caqm.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "checklist")
data class Checklist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String? = null,
    var deptId: Long? = null,
    var status: Int? = null,
    var remark: String? = null,
    /**
     * 将文件夹id用逗号拼接
     */
    var filesIds: String? = null,
    // 下面是检查单项的内容
    var checklistItemsIds: String? = null,
)
