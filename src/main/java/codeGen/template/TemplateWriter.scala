package codeGen.template

trait TemplateWriter {

  /**
    * Writes a string into a template
    * @param template Template
    * @param contents Contents to be written in the template
    */
  def write(template: String, contents: String): String

}
