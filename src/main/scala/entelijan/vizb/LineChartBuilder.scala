package entelijan.vizb

import entelijan.viz.Viz
import entelijan.viz.Viz.{DataRow, Diagram, Range, XY}
import entelijan.vizb.MultiChartBuilder.Builder


sealed trait LineStile

object LineStile {

  case class Solid(size: Double = 1.0) extends LineStile

  case class Dashed(size: Double = 1.0) extends LineStile

}


object LineChartBuilder {

  class Builder extends AbstractBuilder[Builder] with Buildable {

    private var _xLabel = Option.empty[String]
    private var _yLabel = Option.empty[String]
    private var _xRange = Option.empty[Range]
    private var _yRange = Option.empty[Range]
    private var _datas: Seq[Creatable] = Seq.empty[Creatable]

    def data(data: Seq[XY]): Builder = {
      this._datas = Seq(DataRowBuilder().data(data).build())
      this
    }

    def dataRows(data: Seq[Creatable]): Builder = {
      this._datas = data
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

    def build(): Creatable = {
      if (_datas.isEmpty) throw new IllegalArgumentException("data must be defined")

      val dataRows: Seq[DataRow[XY]] = _datas.map {
        case Creatable.DataRowXy(dataRow) => dataRow
        case _ => throw new IllegalArgumentException("Only datarows allowed")
      }

      val dia = Diagram[XY](
        id = normalizeId(this._id),
        title = _title,
        dataRows = dataRows,
        xLabel = _xLabel,
        yLabel = _yLabel,
        xRange = _xRange,
        yRange = _yRange,
        fontFactor = _fontScale,
        lineFactor = _lineScale,
        width = _width,
        height = _height
      )
      Creatable.DiagramXy(dia)
    }
  }

  def apply(): Builder = new Builder()

  def apply(_id: String): Builder = new Builder().id(_id)
}
