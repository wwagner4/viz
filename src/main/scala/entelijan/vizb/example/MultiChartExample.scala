package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.{Buildable, LineChartBuilder, MultiChartBuilder}

object MultiChartExample extends App {

  case class S (frequency: Double, sequence: Seq[XY])

  val builders = {
    def createBuilder(sequence: S) = {
      val f = "%.2f".format(sequence.frequency)
      LineChartBuilder("dummy")
        .title(s"Frequency: $f")
        .yRange(-1.2, 1.2)
        .data(sequence.sequence)
    }

    def createSequence(f: Double): Seq[XY] = (0 to 200)
      .map(_ / 120.0)
      .map(x => XY(x, math.sin(x * f)))

    (10 to 18)
      .map(_ / 1.0)
      .map(f => S(f, createSequence(f)))
      .map(createBuilder)
  }

  MultiChartBuilder("vizb_multichart")
    .title("Multichart Example")
    .buildables(builders)
    .fontFactor(0.7)
    .create()
}
