package entelijan.vizb

import java.util.UUID

import entelijan.viz.{Viz, VizCreator, VizCreators}
import entelijan.viz.Viz.XY

sealed trait Creatable

trait Buildable {
  def build(): Creatable
}

object Creatable {

  case class DiagramXy(diagram: Viz.Diagram[XY]) extends Creatable

  case class MultidiagramXy(multiDiagram: Viz.MultiDiagram[XY]) extends Creatable

}

object Creator {

  def create(buildable: Buildable): Unit = {
    buildable.build() match {
      case c: Creatable.DiagramXy=>
        val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
        creator.createDiagram(c.diagram)
      case c: Creatable.MultidiagramXy=>
        val creator: VizCreator[XY] = VizCreators.gnuplot(clazz = classOf[XY])
        creator.createMultiDiagram(c.multiDiagram)
    }
  }

}

abstract class AbstractBuilder[T <: AbstractBuilder[T]] extends Buildable  {

  protected var _id: String = UUID.randomUUID().toString
  protected var _title = "undefined Titel"

  protected def normalizeId(id: String): String = {
    id.replace("-", "_")
  }

  def title(title: String): T = {
    this._title = title
    this.asInstanceOf[T]
  }

  def id(id: String): T = {
    this._id = id
    this.asInstanceOf[T]
  }

  def create(): Unit = {
    Creator.create(this)
  }

}