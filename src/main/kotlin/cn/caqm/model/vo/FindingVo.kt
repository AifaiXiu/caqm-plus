package cn.caqm.model.vo

import cn.caqm.model.entity.Audit
import cn.caqm.model.entity.DataItem
import cn.caqm.model.entity.User
import java.time.LocalDateTime

class FindingVo(
    var id: Long? = null,
    var audit: Audit? = null,
    /*
     * 1:开启
     * 0:关闭
     * */
    var status: Int? = null,
    var process: DataItem? = null,
    var airport: DataItem? = null,
    var closeUser: User? = null,
    var targetCloseTime: LocalDateTime? = null,
    var dept: DataItem? = null,
    var findingType: DataItem? = null,
    /**
     * 详细内容
     */
    var evaluateResult: String? = null,
    /**
     * 下面是和措施有关的
     */
    var measureDutyMan: User? = null,
    var finisher: User? = null,
    var targetDate: LocalDateTime? = null,
    var finishDate: LocalDateTime? = null,
    var details: String? = null,
    /*
     * 0:跟进措施
     * 1：纠正措施
     * */
    var measureType: Int? = null,
    /**
     *风险分析评估相关的:
     */
    var rootAnalyze: DataItem? = null,
    var relatedDept: DataItem? = null,
    var evaluateDutyMan: User? = null,
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
