package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.LineChartBuilder

object LineChartExample extends App {

  val data = for (i <- 0 to 100) yield {
    val x = i / 40.0
    XY(x, math.sin(x) * 2)
  }

  LineChartBuilder("vizb_linechart")
    .title("Linechart Example")
    .data(data)
    .xLabel("horiontal")
    .yLabel("vertical")
    .yRangeMax(2.5)
    .create()

}