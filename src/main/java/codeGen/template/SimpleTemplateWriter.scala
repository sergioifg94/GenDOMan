package codeGen.template

class SimpleTemplateWriter extends TemplateWriter {

  val REPLACE_TOKEN = """{{code}}"""

  /**
    * Writes a string into a template by string substitution
    *
    * @param template Template
    * @param contents Contents to be written in the template
    */
  override def write(template: String, contents: String): String = template.replaceAllLiterally(REPLACE_TOKEN, contents)

}
