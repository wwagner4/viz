package entelijan.viz

import entelijan.viz.Viz.{Diagram, Lineable, MultiDiagram}

/**
  * Interface for actual data visualisation
  */
trait VizCreator[T <: Lineable] {

  def createDiagram(diagram: Diagram[T]): Unit

  def createMultiDiagram(multidiagram: MultiDiagram[T]): Unit

}


