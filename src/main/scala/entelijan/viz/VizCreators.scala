package entelijan.viz

import java.io.File

import entelijan.viz.Viz.Lineable
import entelijan.viz.creators.VizCreatorGnuplot


object VizCreators {

  def gnuplot[T <: Lineable](
                              scriptDir: File = DefaultDirectories().scriptDir,
                              imageDir: File = DefaultDirectories().imageDir,
                              execute: Boolean = true,
                              clazz: Class[T]): VizCreator[T] = {
    VizCreatorGnuplot[T](scriptDir, imageDir, execute)
  }

}



