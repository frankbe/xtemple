package frankbe.xtemple

import java.io._
import java.util.zip.{ZipFile, ZipEntry}
import util.matching.Regex


/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 25.02.13
 * Time: 16:49
 */

trait DocumentTransformer extends ZipFileTransformer {
  val fittingEntryPattern: Regex
  override protected def isTransformableEntry(entry: ZipEntry) =
    fittingEntryPattern.findFirstIn(entry.getName).isDefined
}

object DocxTransformer extends DocumentTransformer {
  val fittingEntryPattern: Regex = "word/document.xml".r          //"word/.+\.xml"    //TODO check
}

object OdtTransformer extends DocumentTransformer {
  val fittingEntryPattern: Regex = "content.xml".r          //TODO check
}

