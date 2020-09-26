package entelijan.vizb

import entelijan.viz.Viz.{Diagram, MultiDiagram, XY}


object MultiChartBuilder {

  class Builder(override val _id: String) extends AbstractBuilder[Builder](_id) with Buildable {

    def toDiagramXy(buildable: Buildable): Diagram[XY] = {
      buildable.build() match {
        case Creatable.DiagramXy(diagram) => diagram
        case _ => throw new IllegalArgumentException("Diagram must be XY")
      }
    }

    private var _columns: Int = 3

    private var _buildables = Seq.empty[Buildable]

    private var _fontFactor = 1.0

    def columns(columns: Int): Builder = {
      this._columns = columns
      this
    }

    def fontFactor(fontFactor: Double): Builder = {
      this._fontFactor = fontFactor
      this
    }

    def buildables(buildables: Seq[Buildable]): Builder = {
      this._buildables = buildables
      this
    }

    def build(): Creatable = {
      if (_buildables.isEmpty) throw new IllegalArgumentException("MultiChart needs at least one diagram")
      val multiDiagram = MultiDiagram[XY](
        id = this._id,
        columns = this._columns,
        title = Some(_title),
        diagrams = _buildables.map(toDiagramXy),
        fontFactor = _fontFactor
      )
      Creatable.MultidiagramXy(multiDiagram)
    }

  }


  def apply(_id: String): Builder = new Builder(_id)


}
