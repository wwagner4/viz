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

  def dats =
    for (i <- -2 to 2; j <- 1 to 4) yield {
      XYZ(i, j, math.exp(i * j * 0.1))
    }


  println(dats.mkString("\n"))

  val dia = Diagram[XYZ](
    id = "ex4",
    title = "Example 4",
    dataRows = List(DataRow(data=dats)),
  )

  crea.createDiagram(dia)

}
