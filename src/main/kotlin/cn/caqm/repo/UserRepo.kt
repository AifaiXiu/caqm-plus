package cn.caqm.repo

import cn.caqm.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface UserRepo : JpaRepository<User, Long> {
    fun findAllByIdIn(ids: List<Long>): List<User>

    fun findByUsername(username: String): User?
}
