package pages

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLTableSectionElement
import source
import userId
import kotlin.browser.document

fun handleProgress() {
    val table = document.getElementById("whole_table") as HTMLTableSectionElement
    val progressV = document.getElementById("progress") as HTMLDivElement
    progressV.style.visibility = "visible"
    source.getEvaluationsForUser(userId) {
        it?.let {
            it.forEachIndexed { index, it ->
                console.log("mark ${it.mark}")
                console.log("lesson ${it.lesson}")

                if (it.mark == null) {
                    table.innerHTML += """
              <tr style="height: 74px;">
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black u-table-cell-19">${index+1}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.lesson}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-custom-color-2">Нет решения</td>
              </tr>
            """
                } else {
                    table.innerHTML += """
              <tr style="height: 74px;">
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black u-table-cell-19">${index+1}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.lesson}</td>
                <td class="u-align-center u-border-2 u-border-grey-50 u-table-cell u-text-black">${it.mark}</td>
              </tr>
            """
                }
            }

        }

        progressV.style.visibility = "hidden"
    }
}