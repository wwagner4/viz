package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.{DataRowBuilder, LineChartBuilder, LineStile}

object LineChartExampleDataRows extends App {


  val data = for (f <- 1 to 5) yield {
    val freq =  f * 0.8
    val xy = for (i <- 0 to 100) yield {
      val x = i / 40.0
      XY(x, math.sin(x * freq))
    }
    val name = f"frequency : $freq%5.1f"
    DataRowBuilder()
      .data(xy)
      .name(name)
      .lineStyle(LineStile.Solid())
      .build()
  }

  LineChartBuilder("vizb_linechart_datarows")
    .title("Linechart Example Data Rows")
    .creatables(data)
    .xLabel("horizontal")
    .yLabel("vertical")
    .yRange(-1.5, 1.5)
    .fontScale(1.2)
    .create()

}
