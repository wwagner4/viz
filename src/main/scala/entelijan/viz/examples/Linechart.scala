package entelijan.viz.examples

import entelijan.viz.Viz._
import entelijan.viz.{VizCreator, VizCreators}

object Linechart extends App {

  val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])

  val dats = (-10 to 10).map(i => XY(i, math.exp(i * 0.1)))

  val dia = Diagram[XY](
    id = "ex1",
    title = "Example 1",
    yRange = Some(Range(Some(-10.0),Some(10.0))),
    dataRows = List(DataRow(data = dats)),
  )

  c.createDiagram(dia)
}
