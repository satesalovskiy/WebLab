package utils

import org.w3c.files.File
import kotlin.js.json

fun createUserJson(name: String, surname: String, email: String, password: String, birthday: Int, isTeacher: Boolean) = json(
        "name" to name,
        "surname" to surname,
        "email" to email,
        "password" to password,
        "birthday" to birthday,
        "is_teacher" to isTeacher
)

fun createLessonJson(id: Int, title: String, description: String, date: Int, checkFile: String) = json(
        "user_id" to id,
        "title" to title,
        "description" to description,
        "date" to date,
        "check_file" to checkFile
)

fun createSubjectJson(id: Int, title: String, type: String) = json(
        "user_id" to id,
        "title" to title,
        "type_checking" to type
)

fun updateMarkJson(userId: Int, lessonId: Int, file: File) = json(
        "user_id" to userId,
        "lesson_id" to lessonId,
        "file" to file
)

fun updateProfilePhotoJson(id: Int, photo: File) = json(
        "user_id" to id,
        "photo" to photo
)
