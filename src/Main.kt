import network.source.NetworkDatasource
import org.w3c.dom.*
import pages.*
import kotlin.browser.document
import kotlin.browser.window

lateinit var source: NetworkDatasource

var userId: Int = 0
var assigId: Int = 0

fun main() {

    source = NetworkDatasource()

    val path = window.location.href
    if (path.contains("input.html")) {
        startLogin()
    } else if (path.contains("register.html")) {
        startRegister()
    } else if (path.contains("home.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
    } else if (path.contains("student_profile.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleProfile()
    } else if (path.contains("student_-progress.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleProgress()
    } else if (path.contains("student_-assignments.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleAssignments()
    } else if (path.contains("one_student_task.html")) {
        handleTask()
    } else if (path.contains("home_teacher.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()
    } else if (path.contains("tasks.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()
        handleTasks()
    } else if (path.contains("academic_performance.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()
        handlePerformance()
    } else if (path.contains("create_one_-task.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        handleTaskCreation()
    }
}

fun initHrefsTeacher() {
    (document.getElementById("home_with_id") as HTMLHyperlinkElementUtils).href = "home_teacher.html?u_id=$userId"
    (document.getElementById("assig_with_id") as HTMLHyperlinkElementUtils).href = "tasks.html?u_id=$userId"
    (document.getElementById("eval_with_id") as HTMLHyperlinkElementUtils).href = "academic_performance.html?u_id=$userId"

    (document.getElementById("home_with_id1") as HTMLHyperlinkElementUtils).href = "home_teacher.html?u_id=$userId"
    (document.getElementById("assig_with_id1") as HTMLHyperlinkElementUtils).href = "tasks.html?u_id=$userId"
    (document.getElementById("eval_with_id1") as HTMLHyperlinkElementUtils).href = "academic_performance.html?u_id=$userId"
}

fun initHrefs() {
    (document.getElementById("home_with_id") as HTMLHyperlinkElementUtils).href = "home.html?u_id=$userId"
    (document.getElementById("profile_with_id") as HTMLHyperlinkElementUtils).href = "student_profile.html?u_id=$userId"
    (document.getElementById("assig_with_id") as HTMLHyperlinkElementUtils).href = "student_-assignments.html?u_id=$userId"
    (document.getElementById("eval_with_id") as HTMLHyperlinkElementUtils).href = "student_-progress.html?u_id=$userId"

    (document.getElementById("home_with_id1") as HTMLHyperlinkElementUtils).href = "home.html?u_id=$userId"
    (document.getElementById("profile_with_id1") as HTMLHyperlinkElementUtils).href = "student_profile.html?u_id=$userId"
    (document.getElementById("assig_with_id1") as HTMLHyperlinkElementUtils).href = "student_-assignments.html?u_id=$userId"
    (document.getElementById("eval_with_id1") as HTMLHyperlinkElementUtils).href = "student_-progress.html?u_id=$userId"
}

