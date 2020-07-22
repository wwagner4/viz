package entelijan.viz.creators

import java.io.{Closeable, File, PrintWriter}
import java.util.Locale

import entelijan.viz.Viz.{DataDim_1D, DataDim_2D, DataDim_3D, DataRow, Dia, Diagram, LegendPlacement_LEFT, LegendPlacement_RIGHT, Lineable, MultiDiagram, Range}

import entelijan.viz.{Viz, VizCreator}

/**
  * An implementation for data visualisation using gnuplot
  *
  * @param scriptDir Directory in which gnuplot scripts are created
  */
case class VizCreatorGnuplot[T <: Lineable](scriptDir: File, imageDir: File, execute: Boolean) extends VizCreator[T] {

  def create(dia: Dia[T], script: String): Unit = {

    val id = dia.id
    val filename = s"$id.gp"
    val f = new File(scriptDir, filename)
    use(new PrintWriter(f))(pw => pw.print(script))
    println(s"wrote diagram '$id' to $f")

    if (execute) {
      exec(f, scriptDir)
    }

    def exec(script: File, workdir: File): Unit = {
      import scala.sys.process._
      val cmd = s"gnuplot ${script.getName}"
      val result = Process(cmd, cwd = workdir).!
      if (result != 0) {
        println(s"executed -> '$cmd' -> ERROR '$result'")
      } else {
        println(s"executed -> '$cmd'")
      }
    }

    def use[U <: Closeable](closeable: U)(f: U => Unit): Unit = {
      try {
        f(closeable)
      }
      finally {
        closeable.close()
      }
    }


  }

  def createMultiDiagramInit(mdia: MultiDiagram[T]): String = {
    val titleString = if (mdia.title.isDefined) s"title '${mdia.title.get}'" else ""
    val outfileName = s"${mdia.id}.png"
    val outfile = new File(imageDir, outfileName)
    s"""
       |set terminal pngcairo dashed enhanced size ${mdia.imgWidth}, ${mdia.imgHeight}
       |set output '${outfile.getAbsolutePath}'
       |set multiplot layout ${mdia.rows}, ${mdia.columns} $titleString
       |""".stripMargin
  }

  def createMultiDiagramClose(mdia: MultiDiagram[T], script: String): String = {
    script +
      s"""
         |unset multiplot
         |""".stripMargin
  }

  def createDiagramInit(dia: Diagram[T]): String = {
    val outfileName = s"${dia.id}.png"
    val outfile = new File(imageDir, outfileName)
    s"""
       |set terminal pngcairo dashed enhanced size ${dia.imgWidth}, ${dia.imgHeight}
       |set output '${outfile.getAbsolutePath}'
       |""".stripMargin
  }

  def createDiagramData(dia: Diagram[T], diaIndex: Int, script: String): String = {

    val loc = Locale.ENGLISH

    def values(values: Seq[T]) = values.map {
      lin => lin.line(formatNumber)
    }.mkString("\n")

    def formatNumber(n: Number): String = n match {
      case a: java.lang.Byte => "%d" formatLocal(loc, a.longValue())
      case a: java.lang.Integer => "%d" formatLocal(loc, a.longValue())
      case a: java.lang.Long => "%d" formatLocal(loc, a.longValue())
      case a: Any => "%f" formatLocal(loc, a.doubleValue())
    }

    def data(dataRows: Seq[DataRow[T]]): String = dataRows.zipWithIndex.map {
      case (dr, i) => s"""
                         |${datablockName(dia, diaIndex, i)} << EOD
                         |${values(dr.data)}
                         |EOD
                         |""".stripMargin.trim
    }.mkString("\n")

    script + "\n" + data(dia.dataRows)
  }

  def datablockName(dia: Dia[T], diaIndex: Int, dataIndex: Int): String = s"$$data_${dia.id}_${diaIndex}_$dataIndex"

  def createDiagramCommands(dia: Diagram[T], diaIndex: Int, script: String): String = {

    def formatNumber(n: Number): String = "" + n

    def formatRange(r: Range): String = {
      val from = formatRangeValue(r.from)
      val to = formatRangeValue(r.to)
      s"[$from:$to]"
    }

    def formatRangeValue(v: Option[Number]): String = if (v.isDefined) formatNumber(v.get) else "*"

    def mapStyle(style: Viz.Style): String = style match {
      case Viz.Style_POINTS => "points"
      case Viz.Style_POINTS(size) => s"points ps $size"
      case Viz.Style_LINES => "lines"
      case Viz.Style_LINES(size) => s"lines lw $size"
      case Viz.Style_LINESDASHED => "lines dashtype 2"
      case Viz.Style_LINESDASHED(size) => s"lines lw $size dashtype 2"
      case Viz.Style_DOTS => "dots"
      case Viz.Style_LINESPOINTS => "linespoints"
      case Viz.Style_BOXPLOT => "boxplot"
    }

    def series(dataRows: Seq[DataRow[T]]) = dataRows.zipWithIndex.map {
      case (dr, i) =>
        def title: String = {
          if (dr.name.isDefined) "title '" + dr.name.get + "'"
          else "notitle"
        }

        def dim(nr: Int) = dr.dataDim match {
          case DataDim_1D => s"($nr):1"
          case DataDim_2D => "1:2"
          case DataDim_3D => "1:2:3"
        }

        val style = mapStyle(dr.style)
        s"""${datablockName(dia, diaIndex, i)} using ${dim(i + 1)} $title with $style"""
    }.mkString(", \\\n")


    def xLabel: String = if (dia.xLabel.isDefined) s"""set xlabel "${dia.xLabel.get}"""" else ""

    def yLabel: String = if (dia.yLabel.isDefined) s"""set ylabel "${dia.yLabel.get}"""" else ""

    def zLabel: String = if (dia.zLabel.isDefined) s"""set zlabel "${dia.zLabel.get}"""" else ""

    def xZeroAxis: String = if (dia.xZeroAxis) s"""set xzeroaxis""" else ""

    def yZeroAxis: String = if (dia.yZeroAxis) s"""set yzeroaxis""" else ""

    def zZeroAxis: String = if (dia.zZeroAxis) s"""set zzeroaxis""" else ""

    def xRange: String = if (dia.xRange.isDefined) s"""set xrange ${formatRange(dia.xRange.get)}""" else ""

    def yRange: String = if (dia.yRange.isDefined) s"""set yrange ${formatRange(dia.yRange.get)}""" else ""

    def zRange: String = if (dia.zRange.isDefined) s"""set zrange ${formatRange(dia.zRange.get)}""" else ""

    def legendTitle: String = if (dia.legendTitle.isDefined) s"""title "${dia.legendTitle.get}""" else ""

    def plotCmd: String =
      dia.dataDim match {
        case DataDim_1D => "plot"
        case DataDim_2D => "plot"
        case DataDim_3D => "splot"
      }

    def settings3D: String =
      dia.dataDim match {
        case DataDim_1D => ""
        case DataDim_2D => ""
        case DataDim_3D =>
          s"""
             |set dgrid3d ${dia.xyGrid},${dia.xyGrid}
             |set hidden3d
             |""".stripMargin
      }

    val lp = dia.legendPlacement match {
      case LegendPlacement_LEFT => "left"
      case LegendPlacement_RIGHT => "right"
    }

    def isBoxplot(dataRows: Seq[Viz.DataRow[T]]): Boolean = {
      if (dataRows.exists(_.style == Viz.Style_BOXPLOT)) {
        require(dataRows.exists(_.style == Viz.Style_BOXPLOT), "If any datarow has style BOXPLOT all rows must be of style BOXPLOT")
        true
      } else {
        false
      }
    }

    def descr[U <: Lineable](dr: DataRow[U], idx: Int): String = {
      if (dr.name.isDefined) dr.name.get
      else "" + idx
    }

    def xtics(dataRows: Seq[Viz.DataRow[T]]): String =
      dataRows.zipWithIndex.map { case (dr, idx) =>
        s"'${descr(dr, idx)}' ${idx + 1}"
      }.mkString(", ")

    def settings: String =
      if (isBoxplot(dia.dataRows))
        s"""
           |unset key
           |set style data boxplot
           |set xtics (${xtics(dia.dataRows)})
           |#set border 2
           |set xtics nomirror
           |set ytics nomirror
        """.stripMargin
      else
        s"""
           |set key inside $lp top vertical Right noreverse enhanced autotitle $legendTitle
        """.stripMargin

    script +
      s"""
         |$settings
         |set title "${dia.title}"
         |$xLabel
         |$yLabel
         |$zLabel
         |$xZeroAxis
         |$yZeroAxis
         |$zZeroAxis
         |$xRange
         |$yRange
         |$zRange
         |$settings3D
         |$plotCmd \\
         |${series(dia.dataRows)}
         |""".stripMargin

  }


}