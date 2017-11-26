package entelijan.viz

import java.io.File
import java.nio.file.{Files, Paths}

object DefaultDirectories {

  private lazy val home = Paths.get(System.getProperty("user.home"), "viz")

  def scriptDir: File = {
    val d = home.resolve("scripts")
    if (!Files.exists(d)) Files.createDirectories(d)
    d.toFile
  }

  def imageDir: File = {
    val d = home.resolve("images")
    if (!Files.exists(d)) Files.createDirectories(d)
    d.toFile
  }


}
