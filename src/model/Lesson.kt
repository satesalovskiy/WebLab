package model

import org.w3c.files.File

data class Lesson(
        val id: Int,
        val subject: Subject,
        val title: String,
        val description: String,
        val date: Long,
        val check_file: File
)