package codeGen.template

import junit.framework.TestCase
import org.junit.Assert._

class SimpleTemplateWriterTest extends TestCase {

  def testTemplateWriter(): Unit = {
    val templateWriter = new SimpleTemplateWriter
    val codeTemplate = "${code}"
    def template(code: String) =
      s"""
        |<script>
        | ${code}
        |</script>
      """.stripMargin
    val contents = """alert("Hello World!")"""

    val result = templateWriter.write(template(codeTemplate), contents)
    assertEquals(template(contents), result)
  }

}
