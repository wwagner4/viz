package entelijan.viz

import entelijan.vizb.example.{LineChartExample, LineChartExampleDataRows, MultiChartExample}

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
  private lazy val actionsDesc: String = actions.map(a => f" - ${a.id}%-8s${a.desc}").mkString("\n")


  private def run(args: Array[String]): Unit = {
    if (args.length != 1) throw new IllegalArgumentException("require argument 'action' not found'")
    val id = args(0)
    actionMap.get(id) match {
      case Some(value) =>
        println(s"-------------------------------------------------------------------------------------")
        println(s"Running '${value.id}'")
        println(s"${value.desc}")
        println(s"-------------------------------------------------------------------------------------")
        value.action()
      case None => throw new IllegalArgumentException(s"Error: Unknown action: '$id'")
    }
  }


  def main(args: Array[String]): Unit = {
    try {
      run(args)
    } catch {
      case e: Exception =>
        println(s"ERROR: ${e.getMessage}")
        println(s"possible actions:\n$actionsDesc")
    }
  }


}
