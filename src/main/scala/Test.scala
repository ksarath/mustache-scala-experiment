import com.github.mustachejava.DefaultMustacheFactory

import scala.collection.JavaConverters._

case class Feature(description: String)

case class Item(name: String, price: Double, features: List[Feature])

case class ItemJava(name: String, price: Double, features: java.util.List[Feature])

object Test extends App {

  private def toJava(s: Any): Any = s match {
    case map: Map[_, _] => map.map { case (k, v) => (k, toJava(v)) }.asJava
    case seq: Seq[_] => seq.map(toJava).asJava
    case item: Item => ItemJava(item.name, item.price, item.features.asJava)
    case _ => s
  }

  val items = List(
    Item("Item 1", 19.99, List(Feature("New!"), Feature("Awesome!"))),
    Item("Item 2", 29.99, List(Feature("Old."), Feature("Ugly.")))
  )

  import java.io.StringWriter

  val writer = new StringWriter
  val mf = new DefaultMustacheFactory()
  val mustache = mf.compile("email-template.mustache")
  val result = mustache.execute(writer, toJava(Map("items" -> items)))
  println(result.toString)

  val result2 = Scalate("email-template.mustache").render(Map("items" -> items))
  println(result2)
}
