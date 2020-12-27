package pages

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import source
import kotlin.browser.document
import kotlin.browser.window

fun startRegister() {
    console.log("in register")
    val name = document.getElementById("register_name") as HTMLInputElement
    val surname = document.getElementById("register_surname") as HTMLInputElement
    val date = document.getElementById("register_date") as HTMLInputElement
    val email = document.getElementById("register_email") as HTMLInputElement
    val password = document.getElementById("register_password") as HTMLInputElement
    val registerButton = document.getElementById("register_button") as HTMLButtonElement
    val teacher = name.value.contains("teacher")
    val progress = document.getElementById("progress") as HTMLDivElement

    registerButton!!.addEventListener("click", {
        progress.style.visibility = "visible"
        source.registration(name.value, surname.value, email.value, password.value, 1000, teacher) {
            source.auth(email.value, password.value) {
                if (it == null) {
                    window.alert("No such user")
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
        }
    })
}