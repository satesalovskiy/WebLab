package pages

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLTableSectionElement
import source
import userId
import kotlin.browser.document

fun handlePerformance() {
    val table = document.getElementById("whole_table") as HTMLTableSectionElement

    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"


    source.getStudentsEvaluationsForTeacher(userId) {
        it?.let {
            console.log(it.size)
            it.forEach { lesson ->
                lesson.forEach { one ->

                    source.getLessonInfo(one.user, one.lesson){info->
                        console.log(info?.userName)

                        table.innerHTML +=
                                """
                                  <tr style="height: 75px;">
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-7">${info?.lessonTitle}</td>
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-8">${info?.userName} ${info?.userSurname}</td>
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-9">${one.mark}</td>
                </tr>
                                """

                    }


                    //console.log("user=${user.name},lesson=${one.lesson}, mark=${one.mark}")
                }
            }
        }

        progressV.style.visibility = "hidden"

    }


}