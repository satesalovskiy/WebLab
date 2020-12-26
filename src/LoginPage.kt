import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window


lateinit var localName: String
lateinit var localSurname: String
lateinit var localDate: String
lateinit var localEmail: String
lateinit var localPassword: String

fun main() {

    var path = window.location.href
    console.log(path)
    if( path.contains("input.html")){
        startLogin()
    }  else if (path.contains("register.html")){
        startRegister()
    }else if (path.contains("home.html")){
        console.log("in home for student")
        val kek = window.location.search.substring(1)
        console.log(kek.toString())
    } else if (path.contains("student_profile.html")){
        console.log("in student profile")
        handleProfile()

    } else if (path.contains("student_-progress.html")){
        console.log("in progress")
        handleProgress()
    } else if (path.contains("student_-assignments.html")){
        console.log("in assignments")
        handleAssignments()
    } else if (path.contains("one_student_task.html")){

    }
}

private fun handleProgress() {
    val table = document.getElementById("whole_table") as HTMLTableSectionElement
    table.innerHTML += """
              <tr style="height: 74px;">
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black u-table-cell-19">10</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">Jopa</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-custom-color-2">Pipa</td>
              </tr>
            """
}

private fun handleAssignments() {
    val list = document.getElementById("assig_list") as HTMLDivElement
    list.innerHTML += """
             <div class="u-align-left u-container-style u-list-item u-radius-7 u-repeater-item u-shape-round u-video-cover u-white u-list-item-6"
                 data-href="one_student_task.html" data-page-id="85109178">
                <div class="u-container-layout u-similar-container u-container-layout-6">
                    <h4 class="u-text u-text-default u-text-11">Jopa</h4>
                    <p class="u-text u-text-default u-text-12">Sample text. Click to select the text box. Click again or
                        double click to start editing the text.&nbsp;Excepteur sint occaecat cupidatat non proident,
                        sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                </div>
            </div>
            
            """

    console.log(list.toString())
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

    name.textContent = "jopa"
}


private fun startLogin() {
    val loginButton = document.getElementById("get_input_button") as HTMLButtonElement


    loginButton!!.addEventListener("click", {
        val email = document.getElementById("input_email") as HTMLInputElement
        val password = document.getElementById("input_password") as HTMLInputElement


        if (tryToLogin(email.value.toString(), password.value.toString())) {

            localEmail = email.toString()
            localPassword = password.toString()

            //window.open("home.html?email=${email.value}&name=Loh")
        }

        console.log(email.value, password.value)
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

fun tryToRegister(name: String, surname: String, email: String, date: String, password: String) : Boolean{
    //
    return true
}

fun tryToLogin(email: String, password: String): Boolean{

    // request to db
    return true
}