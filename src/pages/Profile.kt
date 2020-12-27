package pages

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader
import org.w3c.files.get
import source
import userId
import kotlin.browser.document
import kotlin.browser.window

fun handleProfile() {
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

            source.getSubjectInfoForUser(userId) {
                it?.let { info ->
                    courses.textContent = "Курс: ${info.subject_title}"
                    progress.textContent = "Прогресс: ${info.count_passed_lessons}/${info.count_lessons}"
                }
            }

        }
    }
}