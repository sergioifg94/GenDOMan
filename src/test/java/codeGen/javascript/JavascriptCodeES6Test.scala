package codeGen.javascript

import junit.framework.TestCase
import org.junit.Assert._
import tree.{LiteralNode, TemplateNode}

class JavascriptCodeES6Test extends TestCase {

  def testStringTemplate(): Unit = {
    val literal = LiteralNode("foo ")
    val template = TemplateNode("bar")

    val code = new JavascriptCodeES6()
      .stringTemplate(Array(literal, template))

    assertEquals("`foo ${bar}`", code)
  }

}
