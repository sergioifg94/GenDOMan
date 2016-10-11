package codeGen.template

class SimpleTemplateWriter extends TemplateWriter {

  val REGEX = """\$\{code\}"""

  /**
    * Writes a string into a template by regex substitution
    *
    * @param template Template
    * @param contents Contents to be written in the template
    */
  override def write(template: String, contents: String): String = template.replaceAll(REGEX, contents)

}
