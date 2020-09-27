package entelijan.vizb

import java.util.UUID

import entelijan.viz.{Viz, VizCreator, VizCreators}
import entelijan.viz.Viz.{DataRow, XY}

sealed trait Creatable

trait Buildable {
  def build(): Creatable
}

object Creatable {

  case class DiagramXy(diagram: Viz.Diagram[XY]) extends Creatable

  case class MultidiagramXy(multiDiagram: Viz.MultiDiagram[XY]) extends Creatable

  case class DataRowXy(dataRow: Viz.DataRow[XY]) extends Creatable

}

object Creator {

  def create(buildable: Buildable): Unit = {
    buildable.build() match {
      case c: Creatable.DiagramXy =>
        val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
        creator.createDiagram(c.diagram)
      case c: Creatable.MultidiagramXy =>
        val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
        creator.createMultiDiagram(c.multiDiagram)
    }
  }

}

abstract class AbstractBuilder[T <: AbstractBuilder[T]] extends Buildable {

  protected var _id: String = UUID.randomUUID().toString
  protected var _title = "undefined Titel"
  protected var _fontScale = 1.0
  protected var _lineScale = 1.0
  protected var _width = 1200
  protected var _height = 900

  protected def normalizeId(id: String): String = {
    id.replace("-", "_").replace(" ", "_")
  }

  def title(title: String): T = {
    this._title = title
    this.asInstanceOf[T]
  }

  def id(id: String): T = {
    this._id = id
    this.asInstanceOf[T]
  }

  def width(width: Int): T = {
    this._width = width
    this.asInstanceOf[T]
  }

  def height(height: Int): T = {
    this._height = height
    this.asInstanceOf[T]
  }

  def size(width: Int, height: Int): T = {
    this._width = width
    this._height = height
    this.asInstanceOf[T]
  }

  def fontScale(fontScale: Double): T = {
    this._fontScale = fontScale
    this.asInstanceOf[T]
  }

  def lineScale(lineScale: Double): T = {
    this._lineScale = lineScale
    this.asInstanceOf[T]
  }

  def create(): Unit = {
    Creator.create(this)
  }

}

object DataRowBuilder {

  class Builder extends Buildable {
    private var _data: Seq[XY] = Seq.empty[XY]
    private var _name: Option[String] = Option.empty[String]
    private var _lineStyle: LineStile = LineStile.Solid()

    def data(data: Seq[XY]): Builder = {
      this._data = data
      this
    }

    def name(name: String): Builder = {
      this._name = Some(name)
      this
    }

    def lineStyle(lineStile: LineStile): Builder = {
      this._lineStyle = lineStile
      this
    }

    def create(): Unit = {
      Creator.create(this)
    }

    override def build(): Creatable = {
      if (_data.isEmpty) throw new IllegalArgumentException("At least one data value must be defined")
      val style = _lineStyle match {
        case LineStile.Solid(size) => Viz.Style_LINES(size)
        case LineStile.Dashed(size) => Viz.Style_LINESDASHED(size)
      }
      val dataRow = DataRow[XY](name = _name, data = _data, style = style)
      Creatable.DataRowXy(dataRow)
    }
  }

  def apply(): Builder = new Builder()
}


