package data.network.repository

import data.model.*
import data.network.response.BaseResponse
import data.network.source.NetworkDatasource
import ext.addPathParam
import ext.setPath
import org.w3c.xhr.XMLHttpRequest
import utils.*

interface NetworkRepository {

    fun getUserById(id: Int)

    fun getLessonsByUserId(id: Int, callback: (Array<Lesson>?) -> Unit)

    fun getSubjectInfoForUser(id: Int, callback: (SubjectInfo?) -> Unit)

    fun getEvaluationsForUser(id: Int, callback: (Array<Evaluation>?) -> Unit)

    fun getStudentsEvaluationsForTeacher(id: Int, callback: (Array<Array<Evaluation>>?) -> Unit)

    fun getLessonById(id: Int, callback: (Lesson?) -> Unit)

    fun getTeacherLessons(id: Int, callback: (Array<Lesson>?) -> Unit)

    fun getTeacherSubject(id: Int, callback: (Array<Subject>?) -> Unit)

    fun isTeacher(id: Int, callback: (Boolean) -> Unit)

    fun auth(email: String, pass: String, callback: (User?) -> Unit)

    fun createLessonByTeacher(id: Int, title: String, description: String, date: Int, checkFile: String, callback: (String) -> Unit)

    fun createSubjectByTeacher(id: Int, title: String, type: String, callback: (String) -> Unit)

    fun updateMark(userId: Int, lessonId: Int, file: String, callback: (String) -> Unit)

    fun updateProfilePhoto(id: Int, photo: String, callback: (String) -> Unit)

    fun registration(name: String, surname: String, email: String, password: String, birthday: Int, isTeacher: Boolean, callback: (String) -> Unit)

}