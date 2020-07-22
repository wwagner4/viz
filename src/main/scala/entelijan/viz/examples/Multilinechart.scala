package entelijan.viz.examples

import entelijan.viz.Viz.{DataRow, Diagram, XY}
import entelijan.viz.{VizCreator, VizCreators}

object Multilinechart extends App {

  val c: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])

  def _dats(fact: Double) =
    for (i <- (-10 to 10)) yield {
      XY(i, math.exp(i * fact))
    }

  val rows =
    for(f <- 1 to 10) yield {
      DataRow(data = _dats(f * 0.01))
    }
    
  
  println(rows.mkString("\n"))

  val dia = Diagram[XY](
    id = "ex2",
    title = "Example 2",
    dataRows = rows,
  )

  c.createDiagram(dia)


}
