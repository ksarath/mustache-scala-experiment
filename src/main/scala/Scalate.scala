
object Scalate {

  import org.fusesource.scalate._

  var format = "mustache"

  lazy val scalateEngine = {
    val engine = new TemplateEngine
    //engine.combinedClassPath = true
    engine
  }

  def apply(template: String) = Template(template)

  case class Template(name: String) {
    def render(args: Map[String, Any]) = {
      ScalateContent{
        scalateEngine.layout(name, args)
      }
    }
  }

  case class ScalateContent(val cont: String)
}
