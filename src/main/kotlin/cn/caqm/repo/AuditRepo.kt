package cn.caqm.repo

import cn.caqm.model.entity.Audit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface AuditRepo : JpaRepository<Audit, Long>
