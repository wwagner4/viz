package entelijan.viz.examples

import entelijan.viz.Viz._
import entelijan.viz.{VizCreator, VizCreators}

object Barchart extends App {

  val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])

  val dats = (-10 to 10).map(i => XY(i, math.exp(i * 0.1)))

  val dia = Diagram[XY](
    id = "ex5",
    title = "Example 5",
    dataRows = List(DataRow(data = dats, style = Style_BOXES)),
  )

  c.createDiagram(dia)
}
