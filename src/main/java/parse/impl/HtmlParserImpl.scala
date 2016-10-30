package parse.impl

import parse.HtmlParser
import tree._
import org.jsoup.Jsoup
import org.jsoup.nodes._

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

      mapOption[Node, HtmlNode](filterNodes(body.childNodes()), createNode)
  }

  /**
    * Create the node from the Jsoup element
    * @param element Jsoup element
    * @return Tree node
    */
  private def createNode(element: Node) : Option[HtmlNode] = {
    element match {
      // Text node
      case text: TextNode =>
        TemplateParser(text.text().trim).map(NodeText(None, _))

      // Comment
      case comment: Comment =>
        Some(NodeComment(comment.getData.filterNot(_ == '\n')))

      // Element
      case elem: Element  =>
        // Create the node
        val node = mapOption(filterNodes(elem.childNodes()), createNode)
            .map(NodeElement(None, elem.tagName(), _, getAttributes(element.attributes())))

        // If it's repeated, create the repeated node and return it
        if (elem.attributes().hasKey(FOREACH_NODE_ATTRIBUTE))
          for (n <- node; repeated <- NodeRepeatParser(n.attributes(FOREACH_NODE_ATTRIBUTE), NodeElement(None, n.tag,
            n.children, n.attributes - FOREACH_NODE_ATTRIBUTE))) yield repeated
        // Otherwise, just return the node
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

  /**
    * Implementation of mapM.
    * @param iterable Source list
    * @param f Function to apply to every element of the list
    * @tparam T Type of the original elements
    * @tparam B Type of the elements returned
    * @return
    */
  private def mapOption[T, B](iterable: Iterable[T], f: T => Option[B]) : Option[Iterable[B]] =
    iterable.foldRight(Some(List[B]()): Option[List[B]]) {
      (elem, acc) => for (
        a <- acc;
        e <- f(elem);
        r = a.::(e)
      ) yield r
    }
}
