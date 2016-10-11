package parse

import tree.HtmlNode

/**
  * Component that parses the input and produces the tree
  */
trait HtmlParser {

  /**
    * Parse the HTML
    * @param input HTML string
    * @return Nodes of the first level of the tree. Nothing if it fails
    */
  def parseHtml(input: String) : Option[Iterable[HtmlNode]]

}
