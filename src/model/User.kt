package model

import org.w3c.files.File

data class User(
        val id: Int,
        val name: String,
        val surname: String,
        val birthday: Long,
        val email: String,
        val password: String,
        val avatar: File?,
        val is_teacher: Boolean
)