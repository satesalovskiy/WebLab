package utils

import org.w3c.files.File

fun createUserJson(name: String, surname: String, email: String, password: String, birthday: Long, isTeacher: Boolean) = "{\n" +
        "  \"name\": $name,\n" +
        "  \"surname\": $surname,\n" +
        "  \"email\": $email,\n" +
        "  \"password\": $password,\n" +
        "  \"birthday\": $birthday,\n" +
        "  \"is_teacher\": $isTeacher\n" +
        "}"

fun createLessonJson(id: Int, title: String, description: String, date: Long, checkFile: File) = "{\n" +
        "  \"user_id\": $id,\n" +
        "  \"title\": $title,\n" +
        "  \"description\": $description,\n" +
        "  \"date\": $date,\n" +
        "  \"check_file\": $checkFile\n" +
        "}"

fun createSubjectJson(id: Int, title: String, type: String) = "{\n" +
        "  \"user_id\": $id,\n" +
        "  \"title\": $title,\n" +
        "  \"type_checking\": $type\n" +
        "}"

fun updateMarkJson(userId: Int, lessonId: Int, file: File) = "{\n" +
        "  \"user_id\": $userId,\n" +
        "  \"lesson_id\": $lessonId,\n" +
        "  \"file\": $file\n" +
        "}"

fun updateProfilePhotoJson(id:Int,photo:File) = "{\n" +
        "  \"user_id\": $id,\n" +
        "  \"photo\": $photo\n" +
        "}"