package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.LineChartBuilder

object LineChartLogExample {

  def run(): Unit = {
    val data = for (i <- -100 to 200) yield {
      val x = i / 40.0
      XY(x, 0.01 + math.abs(math.sin(x) * 2))
    }

    LineChartBuilder("vizb_linechart")
      .title("Linechart Example")
      .xySeq(data)
      .xLabel("horizontal")
      .yLabel("vertical")
      .yScalingLog
      .create()
  }

}
