package codeGen.javascript

class JavascriptCodeES6 extends JavascriptCode {

  private val reservedWords = Seq("let")

  override def function(parameters: Traversable[String], body: String) = {
    writeParameters(parameters) +
      s""" => {
         |  $body
         |}
       """.stripMargin
  }

  override def isReservedSpecific(word: String): Boolean = reservedWords.contains(word)
}
