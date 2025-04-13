package cn.caqm.repo

import cn.caqm.model.entity.Checklist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface ChecklistRepo : JpaRepository<Checklist, Long>
