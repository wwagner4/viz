package entelijan.viz

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
    id = "vizTryout01",
    title = "Tryout 01",
    dataRows = Seq(dataRow)
  )

  Viz.createDiagram(dia)
}

