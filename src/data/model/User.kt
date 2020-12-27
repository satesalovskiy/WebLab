package data.model

data class User(
        val id: Int,
        val name: String,
        val surname: String,
        val birthday: Long,
        val email: String,
        val password: String,
        val avatar: String?,
        val is_teacher: Boolean
)