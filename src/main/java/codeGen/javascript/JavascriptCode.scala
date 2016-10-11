package codeGen.javascript

trait JavascriptCode {

  /**
    * Write the code for an anonymous function
    * @param parameters Parameters of the function
    * @param body Body of the function
    * @return JS code
    */
  def function(parameters: Traversable[String], body: String) : String

  /**
    * Check if a word is reserved in the JS specification
    * @param word
    * @return
    */
  protected def isReservedSpecific(word: String) : Boolean

  def isReserved(word: String) = {
    isReservedSpecific(word) || commonReserved.contains(word)
  }

  protected def writeParameters(parameters: Traversable[String]) = {
    parameters match {
      case Nil => "()"
      case _  => parameters.foldLeft("(") {
        (code, param) => code + param + ", "
      }.init.init + ")"
    }
  }

  /**
    * Reserved keywords common in all the JS specifications
    */
  protected lazy val commonReserved = Seq("var", "function").filter(!isReservedSpecific(_))



}
