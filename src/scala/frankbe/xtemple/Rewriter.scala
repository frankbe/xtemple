package frankbe.xtemple

import com.github.mustachejava.{MustacheFactory, DefaultMustacheFactory}
import collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 16:24
 */
object Rewriter {

  private lazy val paramPattern = """\{\{([A-Za-z0-9_]+)\}\}""".r

  def apply(getParam: String=>Option[String]): RewriteContent = (reader, writer) => {
    val source = Utils.readAll(reader)
    val target = paramPattern.replaceAllIn(source, {matcher =>
      require(matcher.groupCount == 1, "regex error - unexpected group count")
      val paramName = matcher.group(1)
      //println("matcher.matched: " + paramName)
      getParam(paramName).getOrElse("-")// matcher.source.toString)
    })
    //println("source.length: " + source.length)
    //println("target.length: " + target.length)
    writer.write(target)
  }

  private lazy val defaultMustacheFactory = new DefaultMustacheFactory()

  def apply(scope: Any, factory: MustacheFactory = defaultMustacheFactory): RewriteContent = (reader, writer) => {
  // convert to java types, because of missing scala support in the mustache java library
    // TODO refactor ... overwrite DefaultMustacheFactory
    def adjustScopeObj(scopeObj: Any) = scopeObj match {
      case m: Map[_,_] => mapAsJavaMap(m)
      case s: Seq[_] => seqAsJavaList(s)
      case x => x
    }
    val mustache = factory.compile(reader, "main")
    mustache.execute(writer, adjustScopeObj(scope))
  }

}
