package entelijan.viz

/**
  * Interface for data visualisation
  */
object Viz {

  sealed trait LegendPlacement

  case object LegendPlacement_LEFT extends LegendPlacement

  case object LegendPlacement_RIGHT extends LegendPlacement

  sealed trait Style

  case object Style_LINES extends Style

  case class Style_LINES(size: Double) extends Style

  case object Style_LINESDASHED extends Style

  case class Style_LINESDASHED(size: Double) extends Style

  case object Style_POINTS extends Style

  case class Style_POINTS(size: Double) extends Style

  case object Style_DOTS extends Style

  case object Style_LINESPOINTS extends Style

  case object Style_BOXPLOT extends Style

  sealed trait DataDim

  case object DataDim_1D extends DataDim

  case object DataDim_2D extends DataDim

  case object DataDim_3D extends DataDim

  trait Lineable {
    def line(f: Number => String): String

    def dataDim: DataDim
  }

  case class X(
                x: Number
              ) extends Lineable {
    def line(f: Number => String): String = f(x)

    def dataDim: DataDim = DataDim_1D
  }

  case class XY(
                 x: Number,
                 y: Number
               ) extends Lineable {
    def line(f: Number => String): String = {
      val sx = f(x)
      val sy = f(y)
      sx + " " + sy
    }

    def dataDim: DataDim = DataDim_2D
  }

  case class XYZ(
                  x: Number,
                  y: Number,
                  z: Number
                ) extends Lineable {
    def line(f: Number => String): String = {
      val sx = f(x)
      val sy = f(y)
      val sz = f(z)
      sx + " " + sy + " " + sz
    }

    def dataDim: DataDim = DataDim_3D
  }

  case class DataRow[T <: Lineable](
                                     name: Option[String] = None,
                                     style: Style = Style_LINES,
                                     data: Seq[T] = Seq.empty
                                   ) {
    def dataDim: DataDim = {
      if (data.isEmpty) throw new IllegalStateException("Cannot determine dimension because list of data is empty")
      else data(0).dataDim
    }
  }

  sealed trait Dia[T <: Lineable] {
    def id: String

    def dataDim: DataDim
  }

  case class Diagram[T <: Lineable](
                                     id: String,
                                     title: String,
                                     imgWidth: Int = 800,
                                     imgHeight: Int = 600,
                                     xLabel: Option[String] = None,
                                     yLabel: Option[String] = None,
                                     zLabel: Option[String] = None,
                                     xRange: Option[Range] = None,
                                     yRange: Option[Range] = None,
                                     zRange: Option[Range] = None,
                                     xZeroAxis: Boolean = false,
                                     yZeroAxis: Boolean = false,
                                     zZeroAxis: Boolean = false,
                                     xyGrid: Option[Int] = None,
                                     xyHidden: Boolean = false,
                                     xyPlaneAt: Option[Int] = None,
                                     legendPlacement: LegendPlacement = LegendPlacement_LEFT,
                                     legendTitle: Option[String] = None,
                                     dataRows: Seq[DataRow[T]] = Seq.empty
                                   ) extends Dia[T] {
    def dataDim: DataDim =
      if (dataRows.isEmpty) throw new IllegalStateException("Cannot determine data dimension because no data are defined")
      else dataRows(0).dataDim
  }

  case class MultiDiagram[T <: Lineable](
                                          id: String,
                                          columns: Int,
                                          title: Option[String] = None,
                                          imgWidth: Int = 800,
                                          imgHeight: Int = 600,
                                          diagrams: Seq[Diagram[T]]
                                        ) extends Dia[T] {
    def rows: Int = math.ceil(diagrams.size.toDouble / columns).toInt

    def dataDim: DataDim =
      if (diagrams.isEmpty) throw new IllegalStateException("Cannot determine data dimension because no diagram is defined")
      else diagrams(0).dataDim
  }

  case class Range(
                    from: Option[Number],
                    to: Option[Number]
                  )

  def createDiagram[T <: Lineable](dia: Dia[T])(implicit creator: VizCreator[T]): Unit = {
    dia match {
      case d: Diagram[T] => creator.createDiagram(d)
      case md: MultiDiagram[T] => creator.createMultiDiagram(md)
    }
  }
}


