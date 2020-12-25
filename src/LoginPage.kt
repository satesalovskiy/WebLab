import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window

lateinit var emailP: String
lateinit var passwordP: String

fun main() {

    var path = window.location.href
    if( path.contains("input.html")){
        val loginButton = document.getElementById("get_input_button") as HTMLButtonElement


        loginButton!!.addEventListener("click", {
            val email = document.getElementById("input_email") as HTMLInputElement
            val password = document.getElementById("input_password") as HTMLInputElement


            if (tryToLogin(email.value.toString(), password.value.toString())){

                emailP = email.toString()
                passwordP = password.toString()
                window.open("home.html")
            }

            console.log(email.value, password.value)
        })
    } else if (path.contains("home.html")){
        console.log("in home for student")
    } else if (path.contains("register.html")){
        console.log("in register")
        val registerButton = document.getElementById("register_button") as HTMLButtonElement
        registerButton!!.addEventListener("click", {
            val name = document.getElementById("register_name") as HTMLInputElement
            val surname = document.getElementById("register_surname") as HTMLInputElement
            val date = document.getElementById("register_date") as HTMLInputElement
            val email = document.getElementById("register_email") as HTMLInputElement
            val password = document.getElementById("register_password") as HTMLInputElement
            //window.alert("${name.value}, ${surname.name}, ${date.value}, ${email.value}, ${password.value}")

            if (tryToRegister(name.value, surname.value, email.value, date.value, password.value)){

            }
        })
    }
}

fun tryToRegister(name: String, surname: String, email: String, date: String, password: String) : Boolean{
    //
    return true
}

fun tryToLogin(email: String, password: String): Boolean{

    // request to db
    return true
}