package Naming

import Trees.HtmlNode

/**
  * Traverses the tree and assigns different variable names to the nodes
  */
trait NodeNaming {

  def nameNodes(nodes: Iterable[HtmlNode]) : Iterable[HtmlNode] =
    nodes.map(nameNode)

  def nameNode(node: HtmlNode) : HtmlNode

}
