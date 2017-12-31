package entelijan.viz

import java.io.File
import java.nio.file.{Files, Paths}

case class DefaultDirectories(baseDirName: String = "viz") {

  private lazy val home = Paths.get(System.getProperty("user.home"), baseDirName)

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
