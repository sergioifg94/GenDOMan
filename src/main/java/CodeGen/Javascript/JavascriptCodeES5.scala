package CodeGen.Javascript

class JavascriptCodeES5 extends JavascriptCode {

  override def function(parameters: Traversable[String], body: String): String = {
    "function" + writeParameters(parameters) +
      s""" {
         |  $body
         |}
       """.stripMargin
  }

}
