package frankbe.xtemple

import collection.Traversable


/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 09:59
 */

trait ListContentTransformer[S, C, T] extends StatefulResultTransformer[S, T] {

  def listSourceContent(source: S): Traversable[C]

  def resolveContentItemAction(sourceContentItem: C): ItemAction = ItemAction.Copy

  def handleItem(source: S, sourceContentItem: C, planedAction: ItemAction, target: T)

  def transform(source: S, getParam: String => Option[String], target: T) {
    for (item <- listSourceContent(source)) {
      handleItem(source, item, resolveContentItemAction(item), target)
    }
  }

}
