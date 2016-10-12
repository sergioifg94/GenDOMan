package naming

import tree.HtmlNode

/**
  * Traverses the tree and assigns different variable names to the nodes
  */
trait NodeNaming {

  def nameNodes(nodes: Iterable[HtmlNode]) : Iterable[HtmlNode] =
    nodes.map(nameNode)

  protected def nameNode(node: HtmlNode) : HtmlNode

}
