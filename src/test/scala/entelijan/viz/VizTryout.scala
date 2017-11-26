package entelijan.viz

import java.io.File
import java.nio.file.{Files, Paths}

object VizTryout extends App {

  implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY]()

  val data = Seq(
    Viz.XY(1, 2),
    Viz.XY(2, 4),
    Viz.XY(3, 2.5),
    Viz.XY(4, 5),
    Viz.XY(5, 1),
  )

  val dataRow = Viz.DataRow(
    style = Viz.Style_LINES,
    data = data,
  )

  val dia = Viz.Diagram[Viz.XY](
    id = "tryout01",
    title = "Tryout 01",
    dataRows = Seq(dataRow)
  )

  Viz.createDiagram(dia)
}

