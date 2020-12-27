package network.source

import ext.addPathParam
import ext.setPath
import model.Evaluation
import model.Lesson
import model.Subject
import model.User
import network.response.BaseResponse
import org.w3c.files.File
import org.w3c.xhr.XMLHttpRequest
import utils.*

class NetworkDatasource {

    fun getUserById(id: Int, callback: (User?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/profile").addPathParam(id))
        getResponse(callback)
        xmlHttp.send()
    }

    fun getLessonsByUserId(id: Int, callback: (Array<Lesson>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/lesson").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun getEvaluationsForUser(id: Int, callback: (Array<Evaluation>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("user/evaluation").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
    }

    fun getStudentsEvaluationsForTeacher(id: Int, callback: (Array<Evaluation>?) -> Unit) {
        xmlHttp.open(GET, BASE_URL.setPath("teacher/evaluation").addPathParam(id))
        getResponseList(callback)
        xmlHttp.send()
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
/*
    fun createLessonByTeacher(id: Int, title: String, description: String, date: Long, checkFile: File, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("teacher/create/lesson"))
        getResponse(callback)
        xmlHttp.send(JSON.stringify(createLessonJson(id, title, description, date, checkFile)))
    }

    fun createSubjectByTeacher(id: Int, title: String, type: String, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("teacher/create/subject"))
        getResponse(callback)
        xmlHttp.send(JSON.stringify(createSubjectJson(id, title, type)))
    }

    fun updateMark(userId: Int, lessonId: Int, file: File, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/update/mark"))
        getResponse(callback)
        xmlHttp.send(JSON.stringify(updateMarkJson(userId, lessonId, file)))
    }

    fun updateProfilePhoto(id: Int, photo: File, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/update/photo"))
        getResponse(callback)
        xmlHttp.send(JSON.stringify(updateProfilePhotoJson(id, photo)))
    }

    fun registration(name: String, surname: String, email: String, password: String, birthday: Long, isTeacher: Boolean, callback: (String) -> Unit) {
        xmlHttp.open(POST, BASE_URL.setPath("user/create"))
        getResponse(callback)
        xmlHttp.send(JSON.stringify(createUserJson(name, surname, email, password, birthday, isTeacher)))
    }*/

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

    companion object {
        private val xmlHttp = XMLHttpRequest()
        private const val GET = "GET"
        private const val POST = "POST"
        const val BASE_URL = "https://vf1x0u.deta.dev/"
    }

}