package entelijan.vizb

import entelijan.viz.Viz.{DataRow, Diagram, Range, XY, Scaling}


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
    private var _xScaling = Scaling.LIN
    private var _yScaling = Scaling.LIN
    private var _creatables: Seq[Creatable] = Seq.empty[Creatable]

    def xySeq(xySeq: Seq[XY]): Builder = {
      this._creatables = Seq(DataRowBuilder().data(xySeq).build())
      this
    }

    def creatables(data: Seq[Creatable]): Builder = {
      this._creatables = data
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

    def xScalingLog: Builder = {
      this._xScaling = Scaling.LOG
      this
    }

    def yScalingLog: Builder = {
      this._yScaling = Scaling.LOG
      this
    }

    def build(): Creatable = {
      if (_creatables.isEmpty) throw new IllegalArgumentException("data must be defined")

      val dataRows: Seq[DataRow[XY]] = _creatables.map {
        case Creatable.DataRowXy(dataRow) => dataRow
        case _ => throw new IllegalArgumentException("Only data rows allowed")
      }

      val dia = Diagram[XY](
        id = normalizeId(this._id),
        title = _title,
        dataRows = dataRows,
        xLabel = _xLabel,
        yLabel = _yLabel,
        xRange = _xRange,
        yRange = _yRange,
        xScaling = _xScaling,
        yScaling = _yScaling,
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
