package frankbe.xtemple

import java.util.zip.{ZipOutputStream, ZipEntry, ZipFile}
import collection.JavaConversions.enumerationAsScalaIterator
import Utils._
import java.io._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 10:27
 */
abstract class ZipFileTransformer extends StatefulResultTransformer[ZipFile, ZipOutputStream] {

  protected def createEntryTransformer(source: ZipFile)(fn: RewriteContent): ZipEntryTransformer

  def transform(source: ZipFile, target: ZipOutputStream)(fn: RewriteContent) {
    val et = createEntryTransformer(source)(fn)
    for (item: ZipEntry <- enumerationAsScalaIterator(source.entries)) {
      et.transform(item, target)(fn)
    }
  }

  def transform(source: File, target: File)(fn: RewriteContent) {
    require(source.exists, "the source file " + source + " does not exist")
    require(source.isFile, "the source file " + source + " is not a file")
    using(new ZipOutputStream( new FileOutputStream(target))) { zipOut =>
      transform(new ZipFile(source), zipOut)(fn)
    }
  }

}
