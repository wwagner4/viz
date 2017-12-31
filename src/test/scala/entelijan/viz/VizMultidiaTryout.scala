package entelijan.viz

object VizMultidiaTryout extends App {

  private val dir = DefaultDirectories("viz-tryout")

  implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY](scriptDir= dir.scriptDir, imageDir = dir.imageDir)

  val yr = Some(Viz.Range(Some(0), Some(5)))

  val mdia = Viz.MultiDiagram[Viz.XY](
    "vizMultidiaTryout01",
    2,
    title = Some("Multidia 01"),
    diagrams = Seq(
      Viz.Diagram[Viz.XY](
        "a",
        "Diagram A",
        yRange = yr,
        dataRows = Seq(
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 2),
              Viz.XY(2, 1),
              Viz.XY(3, 1),
              Viz.XY(4, 2),
            )
          ),
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 3),
              Viz.XY(2, 2),
              Viz.XY(3, 3),
              Viz.XY(4, 2),
            )
          ),
        )
      ),
      Viz.Diagram[Viz.XY](
        "b",
        "Diagram B",
        yRange = yr,
        dataRows = Seq(
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 1),
              Viz.XY(2, 1.1),
              Viz.XY(3, 1.2),
              Viz.XY(4, 1.4),
            )
          ),
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 3),
              Viz.XY(2, 1),
              Viz.XY(3, 1),
              Viz.XY(4, 2),
            )
          ),
        )
      ),
      Viz.Diagram[Viz.XY](
        "c",
        "Diagram C",
        yRange = yr,
        dataRows = Seq(
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 1),
              Viz.XY(2, 1.1),
              Viz.XY(3, 1.4),
              Viz.XY(4, 1.8),
            )
          ),
          Viz.DataRow[Viz.XY](
            data = Seq(
              Viz.XY(1, 2),
              Viz.XY(2, 3),
              Viz.XY(3, 2),
              Viz.XY(4, 2.1),
            )
          ),
        )
      ),
    )
  )

  Viz.createDiagram(mdia)

}
