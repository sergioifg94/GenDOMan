package CodeGen.Javascript

trait JavascriptCode {

  def function(parameters: Traversable[String], body: String) : String

  protected def writeParameters(parameters: Traversable[String]) = {
    parameters match {
      case Nil => "()"
      case _  => parameters.foldLeft("(") {
        (code, param) => code + param + ", "
      }.init.init + ")"
    }
  }
}
