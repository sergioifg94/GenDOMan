package Utils

import Trees.HtmlNode

class Nodes(val nodes: Iterable[HtmlNode]) {

  def foldAppend(f: HtmlNode => String) =
    nodes.foldLeft("") {
      (code, node) => code + f(node)
    }
}

object Nodes {

  implicit def toNodes(src: Iterable[HtmlNode]) : Nodes = new Nodes(src)

}

