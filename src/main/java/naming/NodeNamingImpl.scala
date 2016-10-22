package naming
import codeGen.javascript.JavascriptCode
import tree.{HtmlNode, NodeElement, NodeRepeat, NodeText}

class NodeNamingImpl(val js: JavascriptCode) extends NodeNaming {

  // Index for the text nodes variable name
  var textNodesIndex = 0

  // Index for the nodes without ID
  var notIdNodesIndex = 0

  // Map of existing variable names with the number of occurrences
  var existingElements = Map[String, Int]()

  override def nameNode(node: HtmlNode): HtmlNode = node match {
    // Naming the text nodes
    case NodeText(_, text) =>
      val result = NodeText(Some(s"text$textNodesIndex"), text)
      textNodesIndex = textNodesIndex + 1
      result


    // Naming the element nodes
    case NodeElement(_, tag, children, attributes) =>
      // If the node doesn't have an ID or the ID is a reserved keyword, give it a default variable name
      if (!attributes.contains("id") || js.isReserved(attributes("id"))) {
        notIdNodesIndex = notIdNodesIndex + 1
        val result = NodeElement(Some(s"node${notIdNodesIndex - 1}"), tag, nameNodes(children), attributes)
        return result
      }

      val id = attributes("id")

      // If there's already an element with the ID value, add it an increasing number
      if (existingElements.contains(id)) {
        val result = NodeElement(Some(s"$id${existingElements(id)}"), tag, nameNodes(children), attributes)
        existingElements = existingElements + (id -> (existingElements(id) + 1))
        return result
      }

      // If it's the firs occurrence of that name, assign it to the node, and update the map
      existingElements + id -> 0
      NodeElement(Some(s"$id"), tag, nameNodes(children), attributes)


    // Visit the subtree of the foreach node
    case NodeRepeat(repeater, variable, repeated) => NodeRepeat(repeater, variable, nameNode(repeated))

    // Just return the node
    case default => default
  }

}
