package entelijan.viz.examples

import java.nio.file.Path

import entelijan.viz.Viz.{DataRow, Diagram, XYZ}
import entelijan.viz.{VizCreator, VizCreators}

object Lines3dChart extends App {
  val crea: VizCreator[XYZ] = VizCreators.gnuplot(
    clazz = classOf[XYZ])

  def rows = {
    for (i <- -10 to 10) yield
      DataRow(data = for (j <- 1 to 40) yield 
        XYZ(i, j, math.exp(i * j * 0.01)))
  }


  println(rows.mkString("\n"))

  val dia = Diagram[XYZ](
    id = "ex4",
    title = "Example 4",
    xyPlaneAt = Some(0),
    dataRows =rows,
  )

  crea.createDiagram(dia)

}
