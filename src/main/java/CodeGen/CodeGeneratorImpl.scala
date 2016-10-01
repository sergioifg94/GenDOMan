package CodeGen
import Trees.{HtmlNode, NodeElement, NodeRepeat, NodeText}
import Utils.Nodes.toNodes

class CodeGeneratorImpl extends CodeGenerator {

  /**
    * Generate the code to create a collection of nodes and append them to their parents
    * @param nodes Nodes to be created
    * @param parent Parent of the nodes
    * @return
    */
  override def generateCode(nodes: Iterable[HtmlNode], parent: Option[HtmlNode] = None): String = {
    nodes.foldAppend(writeNode(parent))
  }

  /**
    * Generate the code to create a single node
    * @param parent Parent of the node
    * @param htmlNode Node to be created
    * @return
    */
  private def writeNode(parent: Option[HtmlNode])(htmlNode: HtmlNode) : String = {
    htmlNode match {
      case NodeElement(Some(variable), tag, children, attributes) =>
        val attributesCode = writeAttributes(htmlNode.asInstanceOf[NodeElement])
        val childrenCode = generateCode(children, Some(htmlNode))

        s"""
             | var $variable = document.createElement("$tag");
             | $attributesCode
             | $childrenCode
             | ${appendToParent(htmlNode, parent)}
           """.stripMargin

      case NodeText(Some(variable), text) =>
        s"""
           | var $variable = document.createTextNode(node);
           | ${appendToParent(htmlNode, parent)}
         """.stripMargin
      case NodeRepeat(repeater, node) =>
        val nodeCode = writeNode(parent)(node)
        s"""
           |$repeater.forEach(function(${getNodeName(node)}) {
           |  $nodeCode
           |}
         """.stripMargin
    }
  }

  private def writeAttributes(node: NodeElement) = {
    node.attributes.foldLeft("")((carry, attr) => {
      attr match {
        case (k , v) => carry +
                       s"""
                         | ${node.variable.get}.setAttribute("$k", "$v");
                       """.stripMargin
      }
    })
  }

  private def getNodeName(node: HtmlNode) = {
    (node match {
      case NodeElement(name, _, _, _) => name
      case NodeText(name, _) => name
    }).get
  }

  private def appendToParent(node: HtmlNode, parent: Option[HtmlNode]) = parent match {
    case Some(justParent) => s"${getNodeName(justParent)}.appendChild(${getNodeName(node)});"
    case _ => ""
  }

}
