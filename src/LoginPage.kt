import model.User
import network.source.NetworkDatasource
import org.w3c.dom.*
import org.w3c.files.FileReader
import org.w3c.files.get
import kotlin.browser.document
import kotlin.browser.window

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

        handlePerformance()
    } else if (path.contains("create_one_-task.html")) {
        userId = window.location.search.substringAfter("=").toInt()
        handleTaskCreation()
    }
}

private fun handleTaskCreation() {
    val title = document.getElementById("title_enter") as HTMLInputElement
    val descr = document.getElementById("descr_enter") as HTMLInputElement

    val confirm = document.getElementById("confirm_button") as HTMLButtonElement
    confirm.addEventListener("click", {
        if (title.value.isNotBlank() && descr.value.isNotBlank()) {
            source.createLessonByTeacher(userId, title.value, descr.value, 121212, "nothing") {
                window.open("tasks.html?u_id=$userId")
            }
        }
    })
}

private fun handlePerformance() {
    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"
    source.getStudentsEvaluationsForTeacher(userId) {
        it?.let {
            console.log(it.size)
            it.forEach { eval ->
                eval.forEach { one ->
                    console.log("user=${one.user},lesson=${one.lesson}, mark=${one.mark}")
                }
            }
        }

        progressV.style.visibility = "hidden"

    }
}

private fun handleTasks() {
    val list = document.getElementById("list_of_tasks") as HTMLDivElement
    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"
    val createButton = document.getElementById("create_task_button") as HTMLHyperlinkElementUtils
    createButton.href = "create_one_-task.html?u_id=${userId}"

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

        progressV.style.visibility = "hidden"
    }
}

private fun handleTask() {
    val progressV = document.getElementById("progress") as HTMLDivElement

    val splits = window.location.search.split("&")
    splits.forEach {
        if (it.contains("u_id")) {
            userId = it.substringAfter("=").toInt()
        }
        if (it.contains("a_id")) {
            assigId = it.substringAfter("=").toInt()
        }
    }

    initHrefs()

    var result = ""

    val input1 = document.getElementById("file-input1") as HTMLInputElement
    input1.addEventListener("change", {
        console.log("${input1.files?.length}")
        val input1File = input1.files?.get(0)!!
        val fr = FileReader()
        fr.readAsBinaryString(input1File)
        fr.onload = {
            console.log(it.target)
            result = fr.result as String
            it
        }

    })

    val input2 = document.getElementById("file-input2") as HTMLInputElement
    input2.addEventListener("change", {
        console.log("${input2.files?.length}")

        result = "решение"

    })

    val button = document.getElementById("send_answer") as HTMLButtonElement
    button.addEventListener("click", {
        progressV.style.visibility = "visible"
        source.updateMark(userId, assigId, result) {
            progressV.style.visibility = "hidden"
            window.open("student_-assignments.html?u_id=$userId")

        }

    })


    val text = document.getElementById("assig_title_descr") as HTMLHeadingElement

    progressV.style.visibility = "visible"
    source.getLessonsByUserId(userId) {
        it?.let {
            it.forEach {
                if (it.id == assigId) {
                    text.textContent = "${it.title}. \n ${it.description}"
                }
            }
        }

        progressV.style.visibility = "hidden"
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
    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"
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

        progressV.style.visibility = "hidden"
    }
}

private fun handleAssignments() {
    val list = document.getElementById("assig_list") as HTMLDivElement
    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"


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
        progressV.style.visibility = "hidden"

    }
}

private fun handleProfile() {
    val changeProfileImage = document.getElementById("change_profile_image") as HTMLInputElement
    val progressV = document.getElementById("progress") as HTMLDivElement
    val avatar = document.getElementById("profile_image")
    progressV.style.visibility = "visible"

    changeProfileImage.addEventListener("change", {
        console.log("changed")
        val file = changeProfileImage.files?.get(0)!!
        val fr = FileReader()
        fr.readAsBinaryString(file)
        fr.onload = {
            console.log(it.target)
            avatar?.setAttribute("src", "data:image/gif;base64,${window.btoa(fr.result as String)}")
            source.updateProfilePhoto(userId, fr.result as String) { response ->
                console.log(response)
            }
        }
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
            progressV.style.visibility = "hidden"
            it.let { user ->
                user.avatar?.let { binaryPhoto ->
                    avatar?.setAttribute("src", "data:image/gif;base64,${window.btoa(binaryPhoto)}")
                }
            }

        }
    }
}


private fun startLogin() {
    val loginButton = document.getElementById("get_input_button") as HTMLButtonElement
    val progress = document.getElementById("progress") as HTMLDivElement



    source.getUserById(3) {
        it?.let {
            console.log("1 = ${it.email} ${it.password}")
        }

        source.getUserById(1) {
            it?.let {
                console.log("2 = ${it.email} ${it.password}")
            }
        }
    }



    loginButton!!.addEventListener("click", {

        progress.style.visibility = "visible"

        val email = document.getElementById("input_email") as HTMLInputElement
        val password = document.getElementById("input_password") as HTMLInputElement

        source.auth(email.value, password.value) {
            if (it == null) {
                window.alert("No such user")

                progress.style.visibility = "hidden"
            } else {
                val id = it.id

                source.isTeacher(id) {
                    progress.style.visibility = "hidden"
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
    val name = document.getElementById("register_name") as HTMLInputElement
    val surname = document.getElementById("register_surname") as HTMLInputElement
    val date = document.getElementById("register_date") as HTMLInputElement
    val email = document.getElementById("register_email") as HTMLInputElement
    val password = document.getElementById("register_password") as HTMLInputElement
    val registerButton = document.getElementById("register_button") as HTMLButtonElement
    val teacher = name.value.contains("teacher")

    registerButton!!.addEventListener("click", {
        source.registration(name.value, surname.value, email.value, password.value, 1000, teacher) {
            source.auth(email.value, password.value) {
                if (it == null) {
                    window.alert("No such user")
                } else {
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
        }
    })
}

fun tryToRegister(name: String, surname: String, email: String, date: String, password: String): Boolean {
    //
    return true
}
