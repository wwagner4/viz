package entelijan.vizb

import entelijan.viz.Viz.{DataRow, Diagram, XY}
import entelijan.viz.{VizCreator, VizCreators}
import entelijan.viz.examples.Linechart.dia


object LineChartBuilder {

  class Builder(val id: String) {

    private var _data: Option[Seq[XY]] = None
    private var _title = "undefined Titel"
    private var _xLabel = Option.empty[String]
    private var _yLabel = Option.empty[String]

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

    def build(): Unit = {
      if (_data.isEmpty) throw new IllegalArgumentException("data must be defined")

      val dia = Diagram[XY](
        id = this.id,
        title = _title,
        dataRows = Seq(DataRow(data = _data.get)),
        xLabel = _xLabel,
        yLabel = _yLabel,
      )

      val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
      creator.createDiagram(dia)

    }
  }


  def apply(id: String): Builder = new Builder(id)
}
