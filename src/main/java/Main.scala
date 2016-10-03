import CodeGen.CodeGeneratorImpl
import CodeGen.Javascript.{JavascriptCode, JavascriptCodeES5, JavascriptCodeES6}
import Naming.NodeNamingImpl
import Parse.Impl.HtmlParserImpl

object Main {

  def main(args: Array[String]): Unit = {
    val jsCode = getEcmascript(args)

    if (jsCode.isEmpty) {
      println(
        """Invalid ECMAScript option. Accepted values are:
          | es6
          | es5
        """.stripMargin)
      return
    }


    val parser = new HtmlParserImpl
    val codeGen = new CodeGeneratorImpl(jsCode.get)
    val naming = new NodeNamingImpl

    val tree = parser.parseHtml("""<div id="mydiv" style="background-color: #ccc;"><p data-repeat="[1,2,3,4]">Hello World</p></div>""")

    tree match {
      case Some(justTree) => println(codeGen.generateCode(naming.nameNodes(justTree)))
      case _ => println("Failed parsing input")
    }
  }

  /**
    * Get the JavascriptCode implementation given the program arguments
    * @param args Program arguments
    * @return Implementation of JavascriptCode. If the value is wrong, returns None. If the argument is not passed,
    *         returns the default implementation
    */
  def getEcmascript(args: Array[String]) : Option[JavascriptCode] = {
    val pattern = "--ecmascript=([a-zA-Z0-9]+)".r

    args.foreach {
      case pattern(version) => version match {
        case "es6" => return Some(new JavascriptCodeES6)
        case "es5" => return Some(new JavascriptCodeES5)
        case _     => return None
      }
    }

    Some(new JavascriptCodeES6)
  }

}
