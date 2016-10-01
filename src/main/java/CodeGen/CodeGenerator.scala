package CodeGen

import Trees.HtmlNode

trait CodeGenerator {

  def generateCode(nodes: Iterable[HtmlNode], parent: Option[HtmlNode]) : String

}
