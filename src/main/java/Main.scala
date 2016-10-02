import CodeGen.CodeGeneratorImpl
import CodeGen.Javascript.JavascriptCodeES6
import Naming.NodeNamingImpl
import Parse.Impl.HtmlParserImpl

object Main {

  def main(args: Array[String]): Unit = {
    val jsCode = new JavascriptCodeES6

    val parser = new HtmlParserImpl
    val codeGen = new CodeGeneratorImpl(jsCode)
    val naming = new NodeNamingImpl

    val tree = parser.parseHtml("""<div id="mydiv" style="background-color: #ccc;"><p data-repeat="[1,2,3,4]">Hello World</p></div>""")

    tree match {
      case Some(justTree) => println(codeGen.generateCode(naming.nameNodes(justTree)))
      case _ => println("Failed parsing input")
    }
  }

}
