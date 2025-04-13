package cn.caqm.common

data class Result<T>(
    val code: Int,
    val msg: String,
    val data: T? = null,
) {
    companion object {
        fun <T> success(
            data: T? = null,
            msg: String = "成功",
        ): Result<T> = Result(1, msg, data)

        fun <T> failure(
            msg: String = "失败",
            code: Int = 0,
        ): Result<T> = Result(code, msg, null)
    }
}
