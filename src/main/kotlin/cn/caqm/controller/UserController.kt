@file:Suppress("ktlint:standard:no-wildcard-imports")

package cn.caqm.controller

import cn.caqm.common.Result
import cn.caqm.model.entity.User
import cn.caqm.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private lateinit var userRepo: UserRepo

    // 分页查询所有用户
    @GetMapping
    fun getAllUsers(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Result<Page<User>> {
        val users = userRepo.findAll(PageRequest.of(page, size))
        return Result.success(users)
    }

    // 根据ID查询用户
    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable id: Long,
    ): Result<User?> {
        val user = userRepo.findById(id).orElse(null)
        return if (user != null) {
            Result.success(user)
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
}
