package entelijan.vizb.example

import entelijan.viz.Viz.XY
import entelijan.vizb.{LineChartBuilder, MultiChartBuilder}

object MultiChartExample {

  def run(): Unit = {
    case class S(frequency: Double, sequence: Seq[XY])

    val builders = {
      def createBuilder(sequence: S) = {
        val f = "%.2f".format(sequence.frequency)
        LineChartBuilder()
          .title(s"Frequency: $f")
          .yRange(-1.2, 1.2)
          .xySeq(sequence.sequence)
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
      .fontScale(1.5)
      .lineScale(1.1)
      .size(1500, 1400)
      .create()
  }

}
