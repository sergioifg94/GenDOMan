package codeGen
import codeGen.javascript.JavascriptCode
import tree._
import util.Nodes.toNodes

class CodeGeneratorImpl(val javascriptCode: JavascriptCode) extends CodeGenerator {

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
           | var $variable = document.createTextNode(${javascriptCode.stringTemplate(text)});
           | ${appendToParent(htmlNode, parent)}
         """.stripMargin

      case NodeComment(comment) =>
        s"""
           |/* $comment */
         """.stripMargin

      case NodeRepeat(repeater, variable, node) =>
        s"""
           |$repeater.forEach(${javascriptCode.function(List(variable), writeNode(parent)(node))}
           |);
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
