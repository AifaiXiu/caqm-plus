package cn.caqm.model.dto

import java.time.LocalDateTime

data class FindingBasicDto(
    var id: Long? = null,
    var auditId: Long? = null,
    /*
     * 1:开启
     * 0:关闭
     * */
    var status: Int? = null,
    var processId: Long? = null,
    var airportId: Long? = null,
    var closeUserId: Long? = null,
    var targetCloseTime: LocalDateTime? = null,
    var deptId: Long? = null,
    var findingTypeId: Long? = null,
    /**
     * 详细内容
     */
    var evaluateResult: String? = null,
)
