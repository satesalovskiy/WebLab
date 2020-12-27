package pages

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import source
import userId
import kotlin.browser.document
import kotlin.browser.window

fun handleTaskCreation() {
    val title = document.getElementById("title_enter") as HTMLInputElement
    val descr = document.getElementById("descr_enter") as HTMLInputElement
    val progress = document.getElementById("progress") as HTMLDivElement

    val confirm = document.getElementById("confirm_button") as HTMLButtonElement
    confirm.addEventListener("click", {
        progress.style.visibility = "visible"
        if (title.value.isNotBlank() && descr.value.isNotBlank()) {
            source.createLessonByTeacher(userId, title.value, descr.value, 121212, "nothing") {
                progress.style.visibility = "hidden"
                window.open("tasks.html?u_id=$userId")
            }
        }
    })
}