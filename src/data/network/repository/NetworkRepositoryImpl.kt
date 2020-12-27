package data.network.repository

import data.model.*
import data.network.source.NetworkDatasource

class NetworkRepositoryImpl(private val source: NetworkDatasource) : NetworkRepository {

    override fun getUserById(id: Int) {
        source.getUserById(id) {
          //  return@getUserById it
        }
    }

    override fun getLessonsByUserId(id: Int, callback: (Array<Lesson>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getSubjectInfoForUser(id: Int, callback: (SubjectInfo?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getEvaluationsForUser(id: Int, callback: (Array<Evaluation>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getStudentsEvaluationsForTeacher(id: Int, callback: (Array<Array<Evaluation>>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getLessonById(id: Int, callback: (Lesson?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getTeacherLessons(id: Int, callback: (Array<Lesson>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getTeacherSubject(id: Int, callback: (Array<Subject>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun isTeacher(id: Int, callback: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun auth(email: String, pass: String, callback: (User?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun createLessonByTeacher(id: Int, title: String, description: String, date: Int, checkFile: String, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun createSubjectByTeacher(id: Int, title: String, type: String, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateMark(userId: Int, lessonId: Int, file: String, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateProfilePhoto(id: Int, photo: String, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun registration(name: String, surname: String, email: String, password: String, birthday: Int, isTeacher: Boolean, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }
}