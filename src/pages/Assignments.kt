package pages

import org.w3c.dom.HTMLDivElement
import source
import userId
import kotlin.browser.document
import kotlin.browser.window

fun handleAssignments() {
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