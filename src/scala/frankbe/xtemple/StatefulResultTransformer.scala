package frankbe.xtemple

import java.io._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 25.02.13
 * Time: 16:06
 */
trait StatefulResultTransformer[S, T] {

  def transform(source: S, getParam: String => Option[String], target: T)

}
