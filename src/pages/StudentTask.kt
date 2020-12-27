package pages

import assigId
import initHrefs
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader
import org.w3c.files.get
import source
import userId
import kotlin.browser.document
import kotlin.browser.window

fun handleTask() {
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