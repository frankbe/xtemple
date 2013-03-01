package frankbe.xtemple

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 16:24
 */
object ParamUtils {
  private val paramPattern = """\$\{([A-Za-z0-9_]+)\}""".r

  def replaceAllParams(text: String, getParam: String=>Option[String]): String = {
    paramPattern.replaceAllIn(text, {matcher =>
      require(matcher.groupCount == 1, "regex error - unexpected group count")
      val paramName = matcher.group(1)
      println("matcher.matched: " + paramName)
      getParam(paramName).getOrElse("-")// matcher.source.toString)
    })
  }

}
