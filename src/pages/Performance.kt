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
                    console.log("user=${one.user},lesson=${one.lesson}, mark=${one.mark}")
                    table.innerHTML +=
                            """
                                  <tr style="height: 75px;">
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-7">${one.lesson}</td>
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-8">${one.user}</td>
                    <td class="u-border-3 u-border-grey-50 u-table-cell u-table-cell-9">${one.mark}</td>
                </tr>
                                """
                }
            }
        }

        progressV.style.visibility = "hidden"

    }


}