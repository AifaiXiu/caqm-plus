package cn.caqm.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "finding")
data class Finding(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
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
    /**
     * 下面是和措施有关的
     */
    var measureDutyManId: Long? = null,
    var finisherId: Long? = null,
    var targetDate: LocalDateTime? = null,
    var finishDate: LocalDateTime? = null,
    var details: String? = null,
    /*
     * 0:跟进措施
     * 1：纠正措施:现在只保留一个纠正措施
     * */
    var measureType: Int? = null,
    /**
     *风险分析评估相关的:
     */
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
