package io.github.openai4s.docugenie

import scala.tools.nsc
import scala.tools.nsc.{Global, Phase}
import scala.tools.nsc.plugins.{Plugin, PluginComponent}

class DivideByZero(val global: Global) extends Plugin {
  import global._

  val name = "divbyzero"
  val description = "checks for division by zero"
  val components = List[PluginComponent](Component)

  private object Component extends PluginComponent {
    val global: DivideByZero.this.global.type = DivideByZero.this.global
    val runsAfter = List[String]("refchecks")
    val phaseName = DivideByZero.this.name
    def newPhase(_prev: Phase) = new DivByZeroPhase(_prev)
    class DivByZeroPhase(prev: Phase) extends StdPhase(prev) {
      override def name = DivideByZero.this.name
      def apply(unit: CompilationUnit): Unit = {
        for ( tree @ Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0)))) <- unit.body
              if rcvr.tpe <:< definitions.IntClass.tpe)
        {
          global.reporter.error(tree.pos, "definitely division by zero")
        }
      }
    }
  }
}