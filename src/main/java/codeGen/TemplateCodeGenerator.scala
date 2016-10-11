package codeGen
import codeGen.template.TemplateWriter
import tree.HtmlNode

import scala.io.Source

/**
  * Generate the code within a template
  */
class TemplateCodeGenerator(val codeGenerator: CodeGenerator, val templateWriter: TemplateWriter,
                            val templateFilename: String) extends CodeGenerator {

  // Read the contents of the template
  lazy val template = Source.fromFile(templateFilename).mkString

  override def generateCode(nodes: Iterable[HtmlNode], parent: Option[HtmlNode] = None): String =
    templateWriter.write(template, codeGenerator.generateCode(nodes, parent))

}
