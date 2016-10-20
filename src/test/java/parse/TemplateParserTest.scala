package parse

import junit.framework.TestCase
import org.junit.Assert._
import parse.impl.TemplateParser
import tree.{LiteralNode, TemplateNode}

class TemplateParserTest extends TestCase {

  def testExpressionParse(): Unit = {
    val input = "hello {{foobar}} this is a test"
    val parsed = TemplateParser(input).get

    parsed.head match {
      case LiteralNode(hello) => assertEquals("hello ", hello)
      case _ => fail()
    }

    parsed(1) match {
      case TemplateNode(foobar) => assertEquals("foobar", foobar)
      case _ => fail()
    }

    parsed(2) match {
      case LiteralNode(last) => assertEquals(" this is a test", last)
      case _ => fail()
    }
  }

}
