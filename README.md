# viz
Visualisation tool for scala

To use it in your project follow these steps:
* have gnuplot installed http://www.gnuplot.info/
* have sbt installed on your computer https://www.scala-sbt.org/
* have git installed on your computer https://git-scm.com/
* clone this repository to your computer 'clone https://github.com/wwagner4/viz.git'
* inside the viz directory execute 'sbt publishLocal'. Eventually adapt the scala version inside the build.sbt file according to the version used in your project.
* add viz as a dependency to your project. 'libraryDependencies += "net.entelijan" %% "viz" % "0.2-SNAPSHOT"'

## TODO
All new Features will only be implemented for the buildr interface which
resides in entelijan.vizb


- Allow logaritmc scaling 