package frankbe

import java.io.{Reader, Writer}

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

}
