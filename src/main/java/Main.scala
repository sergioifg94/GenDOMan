import CodeGen.CodeGeneratorImpl
import CodeGen.Javascript.{JavascriptCode, JavascriptCodeES5, JavascriptCodeES6}
import Naming.NodeNamingImpl
import Parse.Impl.HtmlParserImpl

import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    val input = getInputText(getInputFile(args))
    val jsCode = getEcmascript(args)

    // If no input could be open, show a message and finish
    if (input.isEmpty) {
      println(
        """
          |No input file
        """.stripMargin
      )
      return
    }

    // If the ES specification was invalid, show a message and finish
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

    val tree = parser.parseHtml(input.get)

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

      case _ => ;
    }

    Some(new JavascriptCodeES6)
  }

  /**
    * Get the input file from the program arguments
    * @param args Program arguments
    * @return Name of the input
    */
  def getInputFile(args: Array[String]) : Option[String] = {
    val prevIndex = args.indexWhere(_ == "-i")

    if (prevIndex == -1)
      None
    else
      Some(args(prevIndex + 1))
  }

  /**
    * Get the contents of the input file
    * @param file File name
    * @return
    */
  def getInputText(file: Option[String]) : Option[String] = {
    for (
      fileName <- file;
      file = Source.fromFile(fileName).mkString
    ) yield file
  }
}
