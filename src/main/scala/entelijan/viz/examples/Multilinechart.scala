package entelijan.viz.examples

import entelijan.viz.Viz.{DataRow, Diagram, XY}
import entelijan.viz.{VizCreator, VizCreators}

object Multilinechart {

  def run():Unit = {
    val dataRows: Seq[DataRow[XY]] = {

      def dataRow(fact: Double) =
        for (i <- -10 to 10) yield {
          XY(i, math.exp(i * fact))
        }

      for (f <- 1 to 10) yield {
        DataRow(data = dataRow(f * 0.01))
      }
    }

    val dia = Diagram[XY](
      id = "ex2",
      title = "Example 2",
      dataRows = dataRows,
    )

    val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
    creator.createDiagram(dia)

  }
  

}
