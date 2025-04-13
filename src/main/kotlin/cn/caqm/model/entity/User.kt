package cn.caqm.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val username: String? = null,
    val email: String? = null,
    var status: Int? = null,
    val passwd: String? = null,
    var deptId: Long? = null,
)
