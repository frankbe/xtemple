package frankbe.xtemple

import java.util.zip.{ZipOutputStream, ZipEntry, ZipFile}
import collection.JavaConversions._
import Utils._
import java.io._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 10:27
 */
abstract class ZipFileTransformer extends StatefulResultTransformer[File, File]  {

  protected def isTransformableEntry(entry: ZipEntry): Boolean

  private def transformEntries(source: ZipFile, target: ZipOutputStream)(fn: RewriteContent) {
    val et = new ZipEntryTransformer(source) {
      override protected def skipTransformation(entry: ZipEntry): Boolean = !isTransformableEntry(entry)
    }
    for (item: ZipEntry <- enumerationAsScalaIterator(source.entries)) {
      et.transform(item, target)(fn)
    }
  }

  def transform(source: File, target: File)(fn: RewriteContent) {
    require(source.exists, "the file " + source + " does not exist")
    require(source.isFile, "the file " + source + " is not a file")
    using(new ZipOutputStream(new FileOutputStream(target))) { zipOut =>
      transformEntries(new ZipFile(source), zipOut)(fn)
    }
  }

}
