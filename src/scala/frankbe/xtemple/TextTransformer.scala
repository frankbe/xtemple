package frankbe.xtemple

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 19:02
 */
class TextTransformer  extends StatelessResultTransformer[String, String]{
  def transform(source: String, getParam: (String) => Option[String]) = {
    ParamUtils.replaceAllParams(source, getParam)
  }
}
