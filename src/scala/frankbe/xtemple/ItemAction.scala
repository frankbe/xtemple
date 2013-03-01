package frankbe.xtemple

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 10:46
 */
sealed trait ItemAction

object ItemAction {
  object Copy extends ItemAction
  object Transform extends ItemAction
  object Suppress extends ItemAction
}


