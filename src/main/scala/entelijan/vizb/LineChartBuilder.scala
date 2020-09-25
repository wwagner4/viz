package entelijan.vizb

import entelijan.viz.Viz.{DataRow, Diagram, Range, XY}
import entelijan.viz.{VizCreator, VizCreators}


object LineChartBuilder {

  class Builder(val id: String) {

    private var _data: Option[Seq[XY]] = None
    private var _title = "undefined Titel"
    private var _xLabel = Option.empty[String]
    private var _yLabel = Option.empty[String]
    private var _xRange = Option.empty[Range]
    private var _yRange = Option.empty[Range]

    def data(data: Seq[XY]): Builder = {
      this._data = Some(data)
      this
    }

    def title(title: String): Builder = {
      this._title = title
      this
    }

    def xLabel(label: String): Builder = {
      this._xLabel = Some(label)
      this
    }

    def yLabel(label: String): Builder = {
      this._yLabel = Some(label)
      this
    }

    def xRange(min: Double, max: Double): Builder = {
      this._xRange = Some(Range(Some(min), Some(max)))
      this
    }

    def xRangeMin(min: Double): Builder = {
      this._xRange = Some(Range(Some(min), None))
      this
    }

    def xRangeMax(max: Double): Builder = {
      this._xRange = Some(Range(None, Some(max)))
      this
    }

    def yRange(min: Double, max: Double): Builder = {
      this._yRange = Some(Range(Some(min), Some(max)))
      this
    }

    def yRangeMin(min: Double): Builder = {
      this._yRange = Some(Range(Some(min), None))
      this
    }

    def yRangeMax(max: Double): Builder = {
      this._yRange = Some(Range(None, Some(max)))
      this
    }

    def build(): Unit = {
      if (_data.isEmpty) throw new IllegalArgumentException("data must be defined")

      val dia = Diagram[XY](
        id = this.id,
        title = _title,
        dataRows = Seq(DataRow(data = _data.get)),
        xLabel = _xLabel,
        yLabel = _yLabel,
        xRange = _xRange,
        yRange = _yRange
      )

      val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
      creator.createDiagram(dia)

    }
  }


  def apply(id: String): Builder = new Builder(id)
}
