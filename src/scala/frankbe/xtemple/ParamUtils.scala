package frankbe.xtemple

import java.io.{Writer, Reader}
import com.github.mustachejava.{MustacheFactory, DefaultMustacheFactory}

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 16:24
 */
object ParamUtils {
  private val paramPattern = """\$\{([A-Za-z0-9_]+)\}""".r


  def simpleReplace(reader: Reader, writer: Writer, getParam: String=>Option[String]) {
    val source = Utils.readAll(reader)
    val target = paramPattern.replaceAllIn(source, {matcher =>
      require(matcher.groupCount == 1, "regex error - unexpected group count")
      val paramName = matcher.group(1)
      println("matcher.matched: " + paramName)
      getParam(paramName).getOrElse("-")// matcher.source.toString)
    })
    //println("source.length: " + source.length)
    //println("target.length: " + target.length)
    writer.write(target)
  }

  def mustacheReplace(factory: MustacheFactory, reader: Reader, writer: Writer, scope: AnyRef) {
    val mustache = factory.compile(reader, "main")
    mustache.execute(writer, scope)
  }

}
