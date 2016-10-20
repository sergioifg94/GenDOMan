package parse.impl

import tree.{LiteralNode, TemplateExpression, TemplateNode}

import scala.util.parsing.combinator.RegexParsers

object TemplateParser extends RegexParsers {

  override val skipWhitespace = false

  private def openTemplate = """\{\{""".r
  private def closeTemplate = """\}\}""".r
  private def template = """[^\{\}]+""".r
  private def content = """[^\{\}]+""".r

  def apply(expression: String) = parseAll(parseExpression, expression) match {
    case Success(result, _) => Some(result)
    case _ => None
  }

  private def parseExpression : Parser[List[TemplateExpression]] = rep1(parseNode)

  private def parseTemplateValue = openTemplate ~ template ~ closeTemplate ^^ {
    case (_ ~ template ~ _) => TemplateNode(template)
  }

  private def parseText = content ^^ LiteralNode

  private def parseNode : Parser[TemplateExpression] =  parseTemplateValue | parseText

}
