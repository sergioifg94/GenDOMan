package Naming

import CodeGen.Javascript.{JavascriptCodeES5, JavascriptCodeES6}
import Trees.NodeElement
import junit.framework.TestCase
import org.junit.Assert._

class NodeNamingTests extends TestCase {

  /**
    * Test that no repeated variable names are assigned
    */
  def testNoRepeatedNames(): Unit = {
    val tree = Range(0, 10).map(_ => NodeElement(None, "div", List(), Map()))

    val naming = new NodeNamingImpl(new JavascriptCodeES6)

    val namedTree = naming.nameNodes(tree)

    val nameSets = namedTree.foldLeft(Set[String]()) {
      (set, node) => node match {
        case NodeElement(Some(name), _, _, _) => set + name
      }
    }

    assertEquals(nameSets.size, namedTree.size)
  }

  /**
    * Test that when a node has an attribute ID, that value will be assigned as variable name
    */
  def testIdCreatesName(): Unit = {
    val nodeName = "nodeName"
    val node = Seq(NodeElement(None, "div", Seq(), Map[String, String]("id" -> nodeName)))
    val naming = new NodeNamingImpl(new JavascriptCodeES6)
    val namedTree = naming.nameNodes(node)

    namedTree match {
      case (NodeElement(Some(givenName), _, _, _)::_) => assertEquals(nodeName, givenName)
    }
  }

  /**
    * Test that no reserved keywords will be used as variable names
    */
  def testIdReserved(): Unit = {
    val keywords = Seq("var", "function")
    val nodes = keywords.map(keyword => NodeElement(None, "div", Seq(), Map[String, String]("id" -> keyword)))
    val naming = new NodeNamingImpl(new JavascriptCodeES5)

    val namedTree = naming.nameNodes(nodes).toSeq

    keywords.indices.foreach(i => {
      namedTree(i) match {
        case NodeElement(Some(name), _, _, _) => assertNotEquals(name, keywords(i))
      }
    })
  }



}
