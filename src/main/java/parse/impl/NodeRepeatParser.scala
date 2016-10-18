package parse.impl

import tree.{HtmlNode, NodeRepeat}

import scala.util.parsing.combinator.RegexParsers

/**
  * Parses the expression of repeated nodes
  */
object NodeRepeatParser extends RegexParsers {

  private def variable: Parser[String] = """[a-zA-Z0-9_\-]+""".r
  private def tokenIn: Parser[String] = """ *in *""".r
  private def repeater: Parser[String] = """.*""".r

  private def expression: Parser[(String, String)] = variable ~ tokenIn ~ repeater ^^ {
    case (variable ~ _ ~ repeater) => (variable, repeater)
  }

  def apply(input: String, repeatedNode: HtmlNode) : Option[NodeRepeat] = parseAll(expression, input.trim()) match {
    case Success((variable, repeater), _) => Some(NodeRepeat(repeater, variable, repeatedNode))
    case _ => None
  }
}
