package entelijan.viz

import entelijan.viz.Viz.{Dia, Diagram, Lineable, MultiDiagram}

/**
  * Interface for actual data visualisation
  */
trait VizCreator[T <: Lineable] {

  def createDiagram(dia: Diagram[T]): Unit = {
    val script1 = createDiagramInit(dia)
    val script2 = createDiagramData(dia, 0, script1)
    val script3 = createDiagramCommands(dia, 0, script2)
    create(dia, script3)
  }

  def createMultiDiagram(mdia: MultiDiagram[T]): Unit = {
    val script1 = createMultiDiagramInit(mdia)
    val dias: Seq[(Diagram[T], Int)] = mdia.diagrams.reverse.zipWithIndex
    val script2 = dias.foldRight(script1) { (diaAndIdx, script) => createDiagramData(diaAndIdx._1, diaAndIdx._2, script) }
    val script3 = dias.foldRight(script2) { (diaAndIdx, script) => createDiagramCommands(diaAndIdx._1, diaAndIdx._2, script) }
    val script4 = createMultiDiagramClose(mdia, script3)
    create(mdia, script4)
  }

  def createDiagramInit(dia: Diagram[T]): String

  def createDiagramData(dia: Diagram[T], diaIndex: Int, script: String): String

  def createDiagramCommands(dia: Diagram[T], diaIndex: Int, script: String): String

  def createMultiDiagramInit(mdia: MultiDiagram[T]): String

  def createMultiDiagramClose(mdia: MultiDiagram[T], script: String): String

  def create(dia: Dia[T], script: String): Unit

}


