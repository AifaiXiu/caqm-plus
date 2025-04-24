package cn.caqm.model.dto

import java.time.LocalDateTime

data class FindingEvaluateDto(
    var id: Long? = null,
    var rootAnalyzeId: Long? = null,
    var relatedDeptId: Long? = null,
    var evaluateDutyManId: Long? = null,
    /*
     * 0:高
     * 1:中
     * 2:低
     * */
    var possibility: Int? = null,
    /*
     * 0:高
     * 1:中
     * 2:低
     * */
    var severity: Int? = null,
    /*
     * 0:高
     * 1:中
     * 2:低
     * */
    var riskValue: Int? = null,
    /*
     * 1:是
     * 0:否
     * */
    var isSecure: Int? = null,
    var eventDescribe: String? = null,
    var executeDate: LocalDateTime? = null,
)
