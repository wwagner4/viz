package entelijan.viz

object VizTryout extends App {

  private val dir = DefaultDirectories("viz-tryout")

  implicit val creator: VizCreator[Viz.XY] = VizCreators.gnuplot(scriptDir= dir.scriptDir, imageDir = dir.imageDir, clazz=classOf[Viz.XY])

  val data = Seq(
    Viz.XY(1, 2),
    Viz.XY(2, 1.5),
    Viz.XY(3, 1.5),
    Viz.XY(4, -1),
    Viz.XY(5, -1.3),
  )

  val dataRow = Viz.DataRow(
    style = Viz.Style_LINES,
    data = data,
  )

  val dia = Viz.Diagram[Viz.XY](
    id = "vizTryout01",
    title = "Tryout 01",
    yRange = Some(Viz.Range(Some(-2), Some(2))),
    dataRows = Seq(dataRow)
  )

  Viz.createDiagram(dia)
}

