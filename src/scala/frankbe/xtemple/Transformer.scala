package frankbe.xtemple

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 11:12
 */
object Transformer {

  def transform(source: File, getParam: String => Option[String], target: File) {
    new DocxTransformer().transform(source, getParam, target)
  }
  def transform(sourcePath: String, getParam: String => Option[String], targetPath: String) {
    transform(new File(sourcePath), getParam, new File(targetPath))
  }

}
