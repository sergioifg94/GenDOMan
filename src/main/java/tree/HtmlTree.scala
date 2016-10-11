package tree


sealed trait HtmlNode

/**
  * HTML document node
  * @param tag HTML tag
  * @param children Children of the node
  * @param attributes Map of its attributes
  */
case class NodeElement(variable: Option[String], tag: String, children: Iterable[HtmlNode], attributes: Map[String, String]) extends HtmlNode

/**
  * Text node
  * @param text Contents of the node
  */
case class NodeText(variable: Option[String], text: String) extends HtmlNode

/**
  * Node created in a for loop
  * @param repeater JavaScript reference to the array that spans the nodes
  * @param node Node to repeat
  */
case class NodeRepeat(repeater: String, node: HtmlNode) extends HtmlNode