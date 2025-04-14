@file:Suppress("ktlint:standard:no-wildcard-imports")

package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.entity.User
import cn.caqm.model.vo.UserVo
import cn.caqm.repo.DataItemRepo
import cn.caqm.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var dataItemRepo: DataItemRepo

    // 分页查询所有用户
    @GetMapping
    fun getAllUsers(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<UserVo>> {
        val pageable = PageRequest.of(page, size)
        val users = userRepo.findAll(pageable)
        val userVos =
            users.content.map { user ->
                val department = user.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                UserVo(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    status = user.status,
                    passwd = user.passwd,
                    department = department,
                )
            }
        val userVoPage = PageImpl(userVos, pageable, users.totalElements)
        return Result.success(userVoPage)
    }

    // 根据ID查询用户
    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable id: Long,
    ): Result<UserVo?> {
        val user = userRepo.findById(id).orElse(null)
        return if (user != null) {
            val department = user.deptId?.let { dataItemRepo.findById(it).orElse(null) }
            val userVo =
                UserVo(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    status = user.status,
                    passwd = user.passwd,
                    department = department,
                )
            Result.success(userVo)
        } else {
            Result.failure("用户未找到", 404)
        }
    }

    // 创建新用户
    @PostMapping
    fun createUser(
        @RequestBody user: User,
    ): Result<User> {
        val createdUser = userRepo.save(user)
        return Result.success(createdUser)
    }

    // 更新用户信息
    @PutMapping
    fun updateUser(
        @RequestBody user: User,
    ): Result<User> {
        val updatedUser = userRepo.save(user)
        return Result.success(updatedUser)
    }

    // 删除用户
    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable id: Long,
    ): Result<Void> {
        userRepo.deleteById(id)
        return Result.success(null, "用户删除成功")
    }

    @PostMapping("/by-ids")
    fun getUsersByIds(
        @RequestBody idsRequest: IdsRequest,
    ): Result<List<UserVo>> {
        val ids = idsRequest.ids?.split(",")?.mapNotNull { it.toLongOrNull() }
        val users = ids?.let { userRepo.findAllByIdIn(it) }
        val userVos =
            users?.map { user ->
                val department = user.deptId?.let { dataItemRepo.findById(it).orElse(null) }
                UserVo(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    status = user.status,
                    passwd = user.passwd,
                    department = department,
                )
            }
        return Result.success(userVos)
    }
}

// 定义请求体类
data class IdsRequest(
    val ids: String? = null,
)
