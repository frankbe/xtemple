package frankbe

import java.io.{File, Reader, Writer}
import scala.language.implicitConversions


/**
 * Created with IntelliJ IDEA.
 * User: bert
 * Date: 11.04.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
package object xtemple {

  type RewriteContent = (Reader, Writer) => Unit

  trait StatelessResultTransformer[S, T] {
    def transform(source: S)(fn: RewriteContent): T
  }

  trait StatefulResultTransformer[S, T] {
    def transform(source: S, target: T)(fn: RewriteContent)
  }

  implicit def str2file(str: String) = new File(str)

  implicit def anyScope2rewriteContent(scopeObj: Any): RewriteContent = Rewriter(scopeObj)

  lazy val docx = new DocxTransformer

  lazy val odt  = new OdtTransformer

}
