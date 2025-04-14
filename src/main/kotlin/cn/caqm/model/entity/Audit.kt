package cn.caqm.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "audit")
data class Audit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String? = null,
    var deptId: Long? = null,
    val plannedStartDate: LocalDateTime? = null,
    val plannedEndDate: LocalDateTime? = null,
    val realStartDate: LocalDateTime? = null,
    val realEndDate: LocalDateTime? = null,
    var airportId: Long? = null,
    var processId: Long? = null,
    var mainAuditorId: Long? = null,
    var otherAuditorsId: String? = null,
    var auditMethodId: Long? = null,
    /*
     * o：计划
     * 1：执行
     * 2：取消
     * 3：待关闭
     * 4：已关闭
     * */
    var status: Int? = null,
    var closeUserId: Long? = null,
    var remark: String? = null,
    var checklistsIds: String? = null,
    var findingsIds: String? = null,
    var summary: String? = null,
)
