package io.github.openai4s.docugenie


import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

class DocuGenie(val global: Global) extends Plugin {

  import global._

  val name = "docuGenie"
  val description = "scala documentation generator"
  val components = List[PluginComponent](Component)

  var level = 1000000

  override def init(options: List[String], error: String => Unit): Boolean = {
    for (option <- options) {
      if (option.startsWith("level:")) {
        log("yay! executing docuGenie on file:...")
        level = option.substring("level:".length).toInt
      } else {
        log("oh! command not understood")
      }
    }
    true
  }

  override val optionsHelp: Option[String] = Some(
    "  -P:docuGenie:file:n             set the silliness to level n")

  private object Component extends PluginComponent {
    val global: DocuGenie.this.global.type = DocuGenie.this.global
    val runsAfter = List[String]("refchecks");
    val phaseName = DocuGenie.this.name
    def newPhase(_prev: Phase) = new DocuGeniePhase(_prev)

    class DocuGeniePhase(prev: Phase) extends StdPhase(prev) {
      override def name = DocuGenie.this.name
      def apply(unit: CompilationUnit): Unit = {
        println("Silliness level: " + level)
      }
    }
  }

}