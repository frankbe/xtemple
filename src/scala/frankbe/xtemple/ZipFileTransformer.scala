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

  protected def createEntryTransformer(source: ZipFile): ZipEntryTransformer

  def transform(source: ZipFile, getParam: String => Option[String], target: ZipOutputStream) {
    val et = createEntryTransformer(source)
    for (item: ZipEntry <- enumerationAsScalaIterator(source.entries)) {
      et.transform(item, getParam, target)
    }
  }

  def transform(source: File, getParam: String => Option[String], target: File) {
    require(source.exists, "the source file " + source + " does not exist")
    require(source.isFile, "the source file " + source + " is not a file")
    using(new ZipOutputStream( new FileOutputStream(target))) { zipOut =>
      transform(new ZipFile(source), getParam, zipOut)
    }
  }
}
