package codeGen.javascript

import tree.{LiteralNode, TemplateExpression, TemplateNode}

class JavascriptCodeES5 extends JavascriptCode {

  override def function(parameters: Traversable[String], body: String): String = {
    "function" + writeParameters(parameters) +
      s""" {
         |  $body
         |}
       """.stripMargin
  }

  override def isReservedSpecific(word: String): Boolean = false

  /**
    * Writes a string by concatenation
    * @param expression Template expression
    * @return
    */
  override def stringTemplate(expression: Iterable[TemplateExpression]): String = {
    expression.foldLeft("") {
      (code, node) => code + (node match {
        case LiteralNode(literal) => s""""${escapeCharacters(literal)}" + """
        case TemplateNode(value) => s"""$value + """
      })
    }.init.init.init
  }

  private def escapeCharacters(input: String) = input.replaceAllLiterally("\"", "\\\"")
}
