package cn.caqm.repo

import cn.caqm.model.entity.DataItem
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface DataItemRepo : JpaRepository<DataItem, Long> {
    fun findAllByIdInAndTypeIn(
        ids: List<Long>,
        types: List<Int>,
    ): List<DataItem>

    fun findAllByTypeIn(
        types: List<Int>,
        pageable: Pageable,
    ): Page<DataItem>
}
