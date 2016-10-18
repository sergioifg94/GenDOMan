package parse

import parse.impl.{HtmlParserImpl, NodeRepeatParser}
import tree.{NodeElement, NodeRepeat}
import junit.framework.TestCase
import org.junit.Assert._

class HtmlParserTest extends TestCase {

  def testSucessParsing() {
    val input = """<div><p></p></div>"""
    val parser = new HtmlParserImpl

    val result = parser.parseHtml(input)

    result match {
      case Some(nodes) =>   ;
      case _ => fail("Failed parsing")
    }

    val div = result.get.head

    div match {
      case NodeElement(_, tag, p, _) =>
        assertEquals(tag, "div")

        p.head match {
          case NodeElement(_, pTag, _, _) =>
            assertEquals(pTag, "p")
        }
    }
  }

  def testAttributesParsing() {
    val classValue="c-div"
    val idValue = "my-div"
    val styleValue = "background-color: #ccc"

    val input =
      s"""<div class="$classValue" id="$idValue" style="$styleValue"></div>"""

    val parser = new HtmlParserImpl
    val result = parser.parseHtml(input).get.head

    result match {
      case NodeElement(_, _, _, attributes) =>
        assertEquals(classValue, attributes("class"))
        assertEquals(idValue, attributes("id"))
        assertEquals(styleValue, attributes("style"))
    }
  }

  def testRepeaterParser(): Unit = {
    val expression = "element in [1, 2, 3, 4, 5]"
    val node = NodeRepeatParser(expression, null)

    node match {
      case Some(NodeRepeat(repeater, variable, _)) =>
        assertEquals("element", variable)
        assertEquals("[1, 2, 3, 4, 5]", repeater)

      case _ => fail("Failed parsing correct expression")
    }
  }

}
