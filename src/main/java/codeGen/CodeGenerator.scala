package codeGen

import tree.HtmlNode

trait CodeGenerator {

  def generateCode(nodes: Iterable[HtmlNode], parent: Option[HtmlNode]) : String

}
