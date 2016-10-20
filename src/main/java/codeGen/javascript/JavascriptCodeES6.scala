package codeGen.javascript
import tree.{LiteralNode, TemplateExpression, TemplateNode}

class JavascriptCodeES6 extends JavascriptCode {

  private val reservedWords = Seq("let")

  override def function(parameters: Traversable[String], body: String) = {
    writeParameters(parameters) +
      s""" => {
         |  $body
         |}
       """.stripMargin
  }

  override def isReservedSpecific(word: String): Boolean = reservedWords.contains(word)

  /**
    * Writes a string from a template by string interpolation
    * @param expression
    * @return
    */
  override def stringTemplate(expression: Iterable[TemplateExpression]): String = expression.foldLeft("`") {
    (code, node) => node match {
      case LiteralNode(literal) => code + literal
      case TemplateNode(value) => code + "${" + value + "}"
    }
  } + "`"

}
