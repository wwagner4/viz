package entelijan.viz

import entelijan.vizb.example.{LineChartExample, LineChartExampleDataRows, MultiChartExample}
import org.rogach.scallop.{ScallopConf, ScallopOption}

object Run {

  private case class Action(
                             id: String,
                             desc: String,
                             action: () => Unit
                           )

  private val actions = Seq(
    Action("lc", "Plots a linechart with one data row", () => LineChartExample.run()),
    Action("lcm", "Plots a linechart with multiple data rows", () => LineChartExampleDataRows.run()),
    Action("mc", "Plots a multichart", () => MultiChartExample.run()),
  )
  private lazy val actionMap: Map[String, Action] = actions.map(a => (a.id, a)).toMap
  private lazy val actionsDesc: String = actions.map(a => s"\t${a.id} : ${a.desc}").mkString("\n")

  private class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val action: ScallopOption[String] = trailArg[String](descr = s"\n$actionsDesc", required = false)     
    verify()
  }

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args.toIndexedSeq)
    val id = conf.action.getOrElse("-")
    actionMap.get(id) match {
      case Some(value) =>
        println(s"-------------------------------------------------------------------------------------")
        println(s"Running '${value.id}'")
        println(s"${value.desc}")
        println(s"-------------------------------------------------------------------------------------")
        value.action()
      case None => println(s"Error: Unknown action: '$id'")
    }
  }



}
