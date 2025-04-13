package cn.caqm.repo

import cn.caqm.model.entity.Finding
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface FindingRepo : JpaRepository<Finding, Long>
