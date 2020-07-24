package entelijan.viz.examples

import java.nio.file.Path

import entelijan.viz.Viz.{DataRow, Diagram, XYZ}
import entelijan.viz.{VizCreator, VizCreators}

object Boxes3dChart extends App {
  val outDir = Path.of("target", "scripts")
  val crea: VizCreator[XYZ] = VizCreators.gnuplot(
    scriptDir = outDir.toFile, 
    imageDir=outDir.toFile, 
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
