package cn.caqm.model.dto

import java.time.LocalDateTime

data class FindingMeasureDto(
    var id: Long? = null,
    var measureDutyManId: Long? = null,
    var finisherId: Long? = null,
    var targetDate: LocalDateTime? = null,
    var finishDate: LocalDateTime? = null,
    var details: String? = null,
)
