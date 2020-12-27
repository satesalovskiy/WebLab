import model.User
import network.source.NetworkDatasource
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window


lateinit var localName: String
lateinit var localSurname: String
lateinit var localDate: String
lateinit var localEmail: String
lateinit var localPassword: String
lateinit var source: NetworkDatasource

var userId: Int = 0
var assigId: Int = 0

fun main() {

    source = NetworkDatasource()

    var path = window.location.href
    console.log(path)
    if (path.contains("input.html")) {
        startLogin()
    } else if (path.contains("register.html")) {
        startRegister()
    } else if (path.contains("home.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        console.log("in home for student ${userId}")
        console.log((document.getElementById("home_with_id") as HTMLHyperlinkElementUtils).href)

        initHrefs()


    } else if (path.contains("student_profile.html")) {
        console.log("in student profile")
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleProfile()

    } else if (path.contains("student_-progress.html")) {
        console.log("in progress")
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleProgress()
    } else if (path.contains("student_-assignments.html")) {
        console.log("in assignments")
        userId = window.location.search.substringAfter("=").toInt()
        initHrefs()
        handleAssignments()
    } else if (path.contains("one_student_task.html")) {
        console.log("in task =${window.location.search}")
        handleTask()

    } else if (path.contains("home_teacher.html")) {
        console.log("in teacher home")
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()
    } else if (path.contains("tasks.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()
        handleTasks()
    } else if (path.contains("academic_performance.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        initHrefsTeacher()

        source.getStudentsEvaluationsForTeacher(userId) {
            it?.let {
                console.log(it.size)
                it.forEach { eval ->
                    console.log("${eval.user}, ${eval.mark}")
                }
            }
        }
    }
}

private fun handleTasks() {
    val list = document.getElementById("list_of_tasks") as HTMLDivElement
    source.getTeacherLessons(userId) {
        it?.let {
            it.forEach {

                list.innerHTML +=
                        """
                                <div id="${it.id}" class="u-align-left u-container-style u-list-item u-radius-7 u-repeater-item u-shape-round u-white u-list-item-1"
                 data-href="edit_one_-task.html" data-page-id="86096506">
                <div class="u-container-layout u-similar-container u-container-layout-1">
                    <h4 class="u-text u-text-default u-text-1">${it.title}</h4>
                    <p class="u-text u-text-default u-text-2">${it.description}</p>
                </div>
            </div>
                                """


            }

            it.forEach { lesson ->
                val c = document.getElementById(lesson.id.toString()) as HTMLDivElement
                c.addEventListener("click", {

                    console.log("clicked ${lesson.id}")
                    //window.open("one_student_task.html?u_id=3&a_id=${lesson.id}")
                })
            }

        }
    }
}

private fun handleTask() {
    val splits = window.location.search.split("&")
    splits.forEach {
        if (it.contains("u_id")) {
            userId = it.substringAfter("=").toInt()
        }
        if (it.contains("a_id")) {
            assigId = it.substringAfter("=").toInt()
        }
    }

    val text = document.getElementById("assig_title_descr") as HTMLHeadingElement
    source.getLessonsByUserId(userId) {
        it?.let {
            it.forEach {
                if (it.id == assigId) {
                    text.textContent = "${it.title}. \n ${it.description}"
                }
            }
        }
    }
}

private fun initHrefsTeacher() {
    (document.getElementById("home_with_id") as HTMLHyperlinkElementUtils).href = "home_teacher.html?u_id=$userId"
    (document.getElementById("assig_with_id") as HTMLHyperlinkElementUtils).href = "tasks.html?u_id=$userId"
    (document.getElementById("eval_with_id") as HTMLHyperlinkElementUtils).href = "academic_performance.html?u_id=$userId"

    (document.getElementById("home_with_id1") as HTMLHyperlinkElementUtils).href = "home_teacher.html?u_id=$userId"
    (document.getElementById("assig_with_id1") as HTMLHyperlinkElementUtils).href = "tasks.html?u_id=$userId"
    (document.getElementById("eval_with_id1") as HTMLHyperlinkElementUtils).href = "academic_performance.html?u_id=$userId"
}

private fun initHrefs() {
    (document.getElementById("home_with_id") as HTMLHyperlinkElementUtils).href = "home.html?u_id=$userId"
    (document.getElementById("profile_with_id") as HTMLHyperlinkElementUtils).href = "student_profile.html?u_id=$userId"
    (document.getElementById("assig_with_id") as HTMLHyperlinkElementUtils).href = "student_-assignments.html?u_id=$userId"
    (document.getElementById("eval_with_id") as HTMLHyperlinkElementUtils).href = "student_-progress.html?u_id=$userId"

    (document.getElementById("home_with_id1") as HTMLHyperlinkElementUtils).href = "home.html?u_id=$userId"
    (document.getElementById("profile_with_id1") as HTMLHyperlinkElementUtils).href = "student_profile.html?u_id=$userId"
    (document.getElementById("assig_with_id1") as HTMLHyperlinkElementUtils).href = "student_-assignments.html?u_id=$userId"
    (document.getElementById("eval_with_id1") as HTMLHyperlinkElementUtils).href = "student_-progress.html?u_id=$userId"
}


private fun handleProgress() {
    val table = document.getElementById("whole_table") as HTMLTableSectionElement
    source.getEvaluationsForUser(userId) {

        it?.let {
            it.forEach {
                console.log("mark ${it.mark}")
                console.log("lesson ${it.lesson}")

                if (it.mark == null) {
                    table.innerHTML += """
              <tr style="height: 74px;">
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black u-table-cell-19">${it.id}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.lesson}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-custom-color-2">Нет решения</td>
              </tr>
            """
                } else {
                    table.innerHTML += """
              <tr style="height: 74px;">
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black u-table-cell-19">${it.id}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.lesson}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.mark}</td>
              </tr>
            """
                }
            }

        }
    }
}

private fun handleAssignments() {
    val list = document.getElementById("assig_list") as HTMLDivElement

    source.getLessonsByUserId(userId) {
        it?.let {
            it.forEach {
                console.log("kek ${it.title}")

                list.innerHTML += """
             <div id = "${it.id.toString()}" class="u-align-left u-container-style u-list-item u-radius-7 u-repeater-item u-shape-round u-video-cover u-white u-list-item-6"
                 data-href="one_student_task.html" data-page-id="85109178">
                <div class="u-container-layout u-similar-container u-container-layout-6">
                    <h4 class="u-text u-text-default u-text-11">${it.title}</h4>
                    <p class="u-text u-text-default u-text-12">${it.description}</p>
                </div>
            </div>

            """
            }

            it.forEach { lesson ->
                val c = document.getElementById(lesson.id.toString()) as HTMLDivElement
                c.addEventListener("click", {

                    window.open("one_student_task.html?u_id=3&a_id=${lesson.id}")
                })
            }

        }
    }
}

private fun handleProfile() {
    val changeProfileImage = document.getElementById("change_profile_image") as HTMLInputElement
    changeProfileImage.addEventListener("change", {
        console.log("changed")
        document.getElementById("profile_image")?.setAttribute("src", "https://img.mvideo.ru/Pdb/50135799b1.jpg")
    })

    val name = document.getElementById("profile_s_name") as HTMLHeadingElement
    val email = document.getElementById("profile_s_email") as HTMLHeadingElement
    val courses = document.getElementById("profile_s_courses") as HTMLHeadingElement
    val progress = document.getElementById("profile_s_progress") as HTMLHeadingElement

    //name.textContent =

    source.getUserById(userId) {
        it?.let {
            name.textContent = it.name
            email.textContent = it.email
        }
    }
}


private fun startLogin() {
    val loginButton = document.getElementById("get_input_button") as HTMLButtonElement


    source.getUserById(3){
        it?.let {
            console.log("1 = ${it.email} ${it.password}")
        }

        source.getUserById(1){
            it?.let {
                console.log("2 = ${it.email} ${it.password}")
            }
        }
    }

    loginButton!!.addEventListener("click", {
        val email = document.getElementById("input_email") as HTMLInputElement
        val password = document.getElementById("input_password") as HTMLInputElement

        source.auth(email.value, password.value){
            if(it == null){
                window.alert("No such user")
            } else{
                val id = it.id

                source.isTeacher(id) {
                    if (it) {
                        window.open("home_teacher.html?u_id=$id")
                    } else {
                        window.open("home.html?u_id=$id")
                    }
                }
            }
        }


    })
}

private fun startRegister() {
    console.log("in register")
    val registerButton = document.getElementById("register_button") as HTMLButtonElement
    registerButton!!.addEventListener("click", {
        val name = document.getElementById("register_name") as HTMLInputElement
        val surname = document.getElementById("register_surname") as HTMLInputElement
        val date = document.getElementById("register_date") as HTMLInputElement
        val email = document.getElementById("register_email") as HTMLInputElement
        val password = document.getElementById("register_password") as HTMLInputElement
        //window.alert("${name.value}, ${surname.name}, ${date.value}, ${email.value}, ${password.value}")

        if (tryToRegister(name.value, surname.value, email.value, date.value, password.value)) {

            localName = name.value
            localSurname = name.value
            localDate = date.value
            localEmail = email.value
            localPassword = password.value
            window.open("student_profile.html")

        }
    })
}

fun tryToRegister(name: String, surname: String, email: String, date: String, password: String): Boolean {
    //
    return true
}
