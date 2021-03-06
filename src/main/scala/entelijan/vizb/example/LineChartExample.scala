package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.LineChartBuilder

object LineChartExample {

  def run(): Unit = {
    val data = for (i <- 0 to 100) yield {
      val x = i / 40.0
      XY(x, math.sin(x) * 2)
    }

    LineChartBuilder("vizb_linechart")
      .title("Linechart Example")
      .xySeq(data)
      .xLabel("horizontal")
      .yLabel("vertical")
      .yRangeMax(2.5)
      .fontScale(1.2)
      .lineScale(0.5)
      .create()
  }

}
