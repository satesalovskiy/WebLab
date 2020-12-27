package pages

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import source
import kotlin.browser.document
import kotlin.browser.window

fun startLogin() {
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