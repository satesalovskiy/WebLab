import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document

fun main() {
    val loginButton = document.getElementById("get_input_button") as HTMLButtonElement


    loginButton!!.addEventListener("click", {

        val email = document.getElementById("input_email") as HTMLInputElement
        val password = document.getElementById("input_password") as HTMLInputElement


        if (tryToLogin(email.value.toString(), password.value.toString())){


        }



        console.log(email.value, password.value)
    })


}

fun tryToLogin(email: String, password: String): Boolean{

    // request to db
    return true
}