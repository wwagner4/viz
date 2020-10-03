package entelijan.viz.examples

import entelijan.viz.Viz.{DataRow, Diagram, MultiDiagram, XY}
import entelijan.viz.{VizCreator, VizCreators}

object Multiplot {

  def run(): Unit = {

    val rows: Seq[Seq[XY]] = {
      def row(fact: Double) =
        for (i <- -10 to 10) yield {
          XY(i, math.exp(i * fact))
        }

      for (f <- 1 to 9) yield {
        row(f * 0.01)
      }
    }

    val multidiagram = {
      val dias = for ((d, i) <- rows.zipWithIndex) yield {
        val nr = i + 1
        Diagram[XY](
          id = s"dia$i",
          title = s"Diagram $nr",
          dataRows = Seq(DataRow(data = d)),
        )
      }

      MultiDiagram(id = "ex4", columns = 3, fontFactor = 0.7, title = Some("Example 4"), diagrams = dias)
    }

    val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
    c.createMultiDiagram(multidiagram)

  }

}
