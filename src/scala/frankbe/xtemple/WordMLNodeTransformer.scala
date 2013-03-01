package frankbe.xtemple

import xml.{Text, Elem, Node}
import xml.transform.{RuleTransformer, RewriteRule}
import scala.Predef._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 13:15
 */


// Does not yes work...
class WordMLNodeTransformer extends StatelessResultTransformer[Node, Node]{

  def transform(source: Node, getParam: String => Option[String]): Node = {
    val textElements = source \\ "t"
    val rewriteTextNodes = new RewriteRule {
      override def transform(n: Node): Seq[Node] = {
        def copyWithNewText(node:Node, newText: String) : Node = node match {
          case e : Elem => e.copy(child = e.child.toSeq.map(x => copyWithNewText(x, newText)))
          case t : Text => new Text(newText)
          case x => x
        }
        if (textElements.contains(n))
          //Array[Node](copyWithNewText(n, ParamUtils.replaceAllParams(n.text, getParam)))
          Array[Node](n)
        else Array[Node](n)
      }
    }
    //new RuleTransformer(new RewriteRule(){}).transform(source).head
    source
  }

}
