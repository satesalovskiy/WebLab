package pages

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLHyperlinkElementUtils
import source
import userId
import kotlin.browser.document

fun handleTasks() {
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