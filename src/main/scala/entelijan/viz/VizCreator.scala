package entelijan.viz

import entelijan.viz.Viz.{Diagram, Lineable, MultiDiagram}

/**
  * Interface for actual data visualisation
  */
trait VizCreator[T <: Lineable] {

  def createDiagram(dia: Diagram[T]): Unit

  def createMultiDiagram(mdia: MultiDiagram[T]): Unit

}


