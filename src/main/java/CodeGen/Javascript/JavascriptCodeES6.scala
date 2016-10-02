package CodeGen.Javascript

class JavascriptCodeES6 extends JavascriptCode {

  override def function(parameters: Traversable[String], body: String) = {
    writeParameters(parameters) +
      s""" => {
         |  $body
         |}
       """.stripMargin
  }



}
