package tree

sealed trait TemplateExpression

case class LiteralNode(value: String) extends TemplateExpression

case class TemplateNode(template: String) extends TemplateExpression
