package frankbe.xtemple

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 13:30
 */
trait StatelessResultTransformer[S, T] {

  def transform(source: S, getParam: String => Option[String]): T

}
