import CodeGen.CodeGeneratorImpl
import Naming.NodeNamingImpl
import Parse.Impl.HtmlParserImpl

object Main {

  def main(args: Array[String]): Unit = {

    val parser = new HtmlParserImpl
    val codeGen = new CodeGeneratorImpl
    val naming = new NodeNamingImpl

    val tree = parser.parseHtml("""<div id="mydiv" style="background-color: #ccc;"><p data-repeat="[1,2,3,4]">Hello World</p></div>""")

    tree match {
      case Some(justTree) => println(codeGen.generateCode(naming.nameNodes(justTree)))
      case _ => println("Failed parsing input")
    }
  }

}
