package cn.caqm.model.vo

import cn.caqm.model.entity.DataItem

class UserVo(
    var id: Long? = null,
    var username: String? = null,
    var email: String? = null,
    var status: Int? = null,
    var passwd: String? = null,
    var department: DataItem? = null,
)
