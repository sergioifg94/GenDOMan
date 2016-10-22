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
  override def stringTemplate(expression: Iterable[TemplateExpression]): String = expression match {
    // If it's only a literal, write is as an string
    case (LiteralNode(literal)::Nil) => s""""$literal""""
    // If it's an expression, write the expression
    case (TemplateNode(value)::Nil) => s"$value"

    // If it's a template, use string interpolation
    case expr => expr.foldLeft("`") {
      (code, node) => node match {
        case LiteralNode(literal) => code + literal
        case TemplateNode(value) => code + "${" + value + "}"
      }
    } + "`"
  }

}
