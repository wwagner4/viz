package entelijan.viz.examples

import entelijan.viz.Viz._
import entelijan.viz.{VizCreator, VizCreators}

object Barchart extends App {

  val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])

  val dats1 = Seq(
    XY(0, 10),
    XY(1, 11),
    XY(2, 12),
    XY(3, 9),
  )
  val dats2 = Seq(
    XY(0, 8),
    XY(1, 6),
    XY(2, 5),
    XY(3, 1),
  )

  val rows = Seq(
    DataRow(data = dats1, style = Style_BOXES),
    DataRow(data = dats2, style = Style_BOXES),
  )

  val dia = Diagram[XY](
    id = "ex5",
    title = "Example 5",
    yRange = Some(Range(Some(0), None)),
    dataRows = rows,
  )

  c.createDiagram(dia)
}
