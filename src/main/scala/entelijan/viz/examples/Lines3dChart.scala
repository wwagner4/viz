package entelijan.viz.examples

import entelijan.viz.Viz._
import entelijan.viz.{VizCreator, VizCreators}

object Lines3dChart extends App {
  val crea: VizCreator[XYZ] = VizCreators.gnuplot(
    clazz = classOf[XYZ])

  def rows = {
    for (i <- 0 to 50) yield
      DataRow(data = for (j <- 1 to 40) yield XYZ(i, j, i * j), style = Style_BOXES)
  }


  println(rows.mkString("\n"))

  val dia = Diagram[XYZ](
    id = "ex4",
    title = "Example 4",
    xyPlaneAt = Some(0),
    dataRows = rows,
  )

  crea.createDiagram(dia)

}
