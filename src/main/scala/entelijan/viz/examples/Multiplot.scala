package entelijan.viz.examples

import entelijan.viz.Viz.{DataRow, Diagram, MultiDiagram, XY}
import entelijan.viz.{VizCreator, VizCreators}

object Multiplot extends App {

  val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])

  def _dats(fact: Double) =
    for (i <- -10 to 10) yield {
      XY(i, math.exp(i * fact))
    }

  val rows: Seq[Seq[XY]] =
    for (f <- 1 to 9) yield {
      _dats(f * 0.01)
    }

  val dias = for (d <- rows) yield {
    Diagram[XY](
      id = "ex2",
      title = "Example 2",
      dataRows = Seq(DataRow(data = d)),
    )
  }

  val mdia = MultiDiagram(id = "ex4", columns = 3, title = Some("Multidiagram"), diagrams = dias)

  c.createMultiDiagram(mdia)


}
