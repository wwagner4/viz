package entelijan.viz

import entelijan.viz

object VizMultidiaTryout extends App {
  
  implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY]()

  val mdia = Viz.MultiDiagram[Viz.XY](
    "vizMultidiaTryout01",
    2,
    title = Some("Multidia 01"),
    diagrams = Seq(
      Viz.Diagram[Viz.XY](
        "a",
        "Diagram A",
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
