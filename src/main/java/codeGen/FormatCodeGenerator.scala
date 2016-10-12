package codeGen
import tree.HtmlNode

class FormatCodeGenerator(val codeGenerator: CodeGenerator) extends CodeGenerator {

  override def generateCode(nodes: Iterable[HtmlNode], parent: Option[HtmlNode]): String =
    formatCode(codeGenerator.generateCode(nodes, parent))

  /**
    * Removes empty lines
    * @param code Original code
    * @return
    */
  private def formatCode(code: String) = code.lines.filter(_.trim() != "").foldLeft("") {
    (code, line) => code + "\n" + line
  }

}
