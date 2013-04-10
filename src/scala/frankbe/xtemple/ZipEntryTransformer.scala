package frankbe.xtemple

import java.util.zip.{ZipFile, ZipOutputStream, ZipEntry}
import java.io.OutputStream

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 14:45
 */
abstract class ZipEntryTransformer(inputFile: ZipFile) extends StatefulResultTransformer[ZipEntry, ZipOutputStream] {

  protected def skipEntry(entry: ZipEntry) = false

  protected def innerTransform(source: ZipEntry, getParam: String => Option[String], outputStream: OutputStream) {
    Utils.copy(inputFile.getInputStream(source), outputStream)
    //Utils.stream(inputFile.getInputStream(source), outputStream)
  }

  def transform(source: ZipEntry, getParam: String => Option[String], target: ZipOutputStream) {
    if (!skipEntry(source)) {
      val te = new ZipEntry(source.getName)
      target.putNextEntry(te)
      innerTransform(source, getParam, target)
      target.closeEntry()
    }
  }

}
