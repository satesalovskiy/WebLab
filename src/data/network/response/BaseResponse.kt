package data.network.response

data class BaseResponse<T>(
        val __profile__: Any?,
        val __values__:T
)