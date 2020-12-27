package data.network.source

import ext.addPathParam
import ext.setPath
import data.model.*
import data.network.response.BaseResponse
import org.w3c.xhr.XMLHttpRequest
import utils.*

class NetworkDatasource {

    fun getUserById(id: Int, callback: (User?) -> Unit) {
        val xhr = XMLHttpRequest()
        xhr.open(GET, BASE_URL.setPath("user/profile").addPathParam(id))
        xhr.onload = {
            if (xhr.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<BaseResponse<User>>(xhr.responseText).__values__)
            }

        }
        xhr.send()
    }

    fun getLessonsByUserId(id: Int, callback: (Array<Lesson>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/lesson").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun getSubjectInfoForUser(id: Int, callback: (SubjectInfo?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/subject").addPathParam(id))
        xmlHttp.onload = {
            if (xmlHttp.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<SubjectInfo>(xmlHttp.responseText))
            }
        }
        xmlHttp.send()
    }

    fun getEvaluationsForUser(id: Int, callback: (Array<Evaluation>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/evaluation").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun getStudentsEvaluationsForTeacher(id: Int, callback: (Array<Array<Evaluation>>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("teacher/evaluation").addPathParam(id))
        xmlHttp.onload = {
            if (xmlHttp.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<Array<Array<BaseResponse<Evaluation>>>>(xmlHttp.responseText).map { it.map { it.__values__ }.toTypedArray() }.toTypedArray())
            }

        }
        xmlHttp.send()
    }

    fun getLessonById(id: Int, callback: (Lesson?) -> Unit) {
        val xhr = XMLHttpRequest()
        xhr.open("GET", BASE_URL.setPath("teacher/lesson_id").addPathParam(id))
        xhr.onload = {
            if (xhr.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<BaseResponse<Lesson>>(xhr.responseText).__values__)
            }

        }
        xhr.send()
    }

    fun getTeacherLessons(id: Int, callback: (Array<Lesson>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("teacher/lesson").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun getTeacherSubject(id: Int, callback: (Array<Subject>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("teacher/subject").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun isTeacher(id: Int, callback: (Boolean) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("teacher/is_teacher").addPathParam(id))
        xmlHttp.onload = {
            callback.invoke(xmlHttp.responseText.toBoolean())
        }
        xmlHttp.send()
    }

    fun auth(email: String, pass: String, callback: (User?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/authorisation").addPathParam(email).addPathParam(pass))
        getResponse(callback)
        xmlHttp.send()
    }

    fun createLessonByTeacher(id: Int, title: String, description: String, date: Int, checkFile: String, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("teacher/create/lesson"))
        getResponseAfterPost(callback)
        xmlHttp.send(JSON.stringify(createLessonJson(id, title, description, date, checkFile)))
    }

    fun createSubjectByTeacher(id: Int, title: String, type: String, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("teacher/create/subject"))
        getResponseAfterPost(callback)
        xmlHttp.send(JSON.stringify(createSubjectJson(id, title, type)))
    }

    fun updateMark(userId: Int, lessonId: Int, file: String, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/update/mark"))
        getResponseAfterPost(callback)
        xmlHttp.send(JSON.stringify(updateMarkJson(userId, lessonId, file)))
    }

    fun updateProfilePhoto(id: Int, photo: String, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/update/photo"))
        xmlHttp.setRequestHeader("Content-type", "application/json")
        getResponseAfterPost(callback)
        xmlHttp.send(JSON.stringify(updateProfilePhotoJson(id, photo)))
    }

    fun registration(name: String, surname: String, email: String, password: String, birthday: Int, isTeacher: Boolean, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/create/"))
        xmlHttp.setRequestHeader("content-type", "application/json;charset=UTF-8")
        getResponseAfterPost(callback)
        xmlHttp.send(JSON.stringify(createUserJson(name, surname, email, password, birthday, isTeacher)))
    }

    fun getLessonInfo(userId: Int, lessonId: Int, callback: (LessonInfo?) -> Unit) {
        getLessonById(lessonId) { lesson ->
            getUserById(userId) { user ->
                callback.invoke(LessonInfo(userName = user?.name, userSurname = user?.surname, lessonTitle = lesson?.title))
            }
        }
    }

    private fun <T> getResponse(callback: (T?) -> Unit) {
        xmlHttp.onload = {
            if (xmlHttp.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<BaseResponse<T>>(xmlHttp.responseText).__values__)
            }

        }
    }

    private fun <T> getResponseList(callback: (Array<T>?) -> Unit) {
        xmlHttp.onload = {
            if (xmlHttp.response == "null") {
                callback.invoke(null)
            } else {
                callback.invoke(JSON.parse<Array<BaseResponse<T>>>(xmlHttp.responseText).map(BaseResponse<T>::__values__).toTypedArray())
            }

        }
    }

    private fun getResponseAfterPost(callback: (String) -> Unit) {
        xmlHttp.onload = {
            if (xmlHttp.status == 200.toShort()) {
                callback.invoke(xmlHttp.statusText)
            } else {
                callback.invoke(ERROR)
            }
        }
    }

    companion object {
        private val xmlHttp = XMLHttpRequest()
        private const val GET = "GET"
        private const val POST = "POST"
        const val BASE_URL = "https://f5nns8.deta.dev/"
        const val ERROR = "error"
    }

}