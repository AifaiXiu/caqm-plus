package cn.caqm.model.vo

import cn.caqm.model.entity.Checklist
import cn.caqm.model.entity.DataItem
import cn.caqm.model.entity.Finding
import cn.caqm.model.entity.User
import java.time.LocalDateTime

class AuditVo(
    var id: Long? = null,
    var name: String? = null,
    var dept: DataItem? = null,
    val plannedStartDate: LocalDateTime? = null,
    val plannedEndDate: LocalDateTime? = null,
    val realStartDate: LocalDateTime? = null,
    val realEndDate: LocalDateTime? = null,
    var airport: DataItem? = null,
    var process: DataItem? = null,
    var mainAuditor: User? = null,
    var otherAuditors: List<User>? = null,
    var auditMethodId: DataItem? = null,
    /*
     * o：计划
     * 1：执行
     * 2：取消
     * 3：待关闭
     * 4：已关闭
     * */
    var status: Int? = null,
    var closeUser: User? = null,
    var remark: String? = null,
    var checklists: List<Checklist>? = null,
    var findings: List<Finding>? = null,
    var summary: String? = null,
)
