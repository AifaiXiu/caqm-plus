package cn.caqm.model.vo

class DataItemVo(
    /**
     * 0:Airport
     * 1:AuditMethod
     * 2:Company
     * 3:Dept
     * 4:FindingType
     * 5:Process
     * 6:RiskSource
     * 7:RootAnalyze
     */
    var type: Int? = null,
    /**
     * 数据项的值
     */
    var value: String? = null,
)
