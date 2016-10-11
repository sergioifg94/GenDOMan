package parse.impl

import parse.HtmlParser
import tree._
import org.jsoup.Jsoup
import org.jsoup.nodes.{Attributes, Element, Node, TextNode}

import collection.JavaConversions._
import scala.collection.immutable.Map

class HtmlParserImpl extends HtmlParser {

  val FOREACH_NODE_ATTRIBUTE = "data-repeat"

  /**
    * Parse the HTML
    *
    * @param input HTML string
    * @return Nodes of the first level of the tree. Nothing if it fails
    */
  override def parseHtml(input: String) = {
      val doc = Jsoup.parseBodyFragment(input)
      val body = doc.select("body")(0)

      Some(filterNodes(body.childNodes()).map(createNode))
  }

  /**
    * Create the node from the Jsoup element
    * @param element Jsoup element
    * @return Tree node
    */
  private def createNode(element: Node) : HtmlNode = {
    element match {
      // Text node
      case text: TextNode => NodeText(None, text.getWholeText)

      // Element
      case elem: Element  =>
        val node = NodeElement(None, elem.tagName(), filterNodes(elem.childNodes()).map(createNode), getAttributes(element.attributes()))

        if (elem.attributes().hasKey(FOREACH_NODE_ATTRIBUTE))
          NodeRepeat(elem.attr(FOREACH_NODE_ATTRIBUTE), NodeElement(None, node.tag, node.children,
            node.attributes - FOREACH_NODE_ATTRIBUTE)) // Removes the foreach attribute from the node
        else
          node
    }
  }

  /**
    * Get the attriibutes map from the Jsoup class
    * @param attributes Attributes parsed from Jsoup
    * @return Map with the attributes of the node
    */
  private def getAttributes(attributes: Attributes) : Map[String, String] = {
    attributes.foldLeft(Map[String, String]()) {
      (map, attribute) => map + (attribute.getKey -> attribute.getValue)
    }
  }

  /**
    * Filters a collection of nodes. Ignores blank text nodes
    * @param nodes Collection of nodes
    * @return
    */
  private def filterNodes(nodes: Iterable[Node]) = nodes.filter({
    case text: TextNode => !text.isBlank
    case _ => true
  })

}
