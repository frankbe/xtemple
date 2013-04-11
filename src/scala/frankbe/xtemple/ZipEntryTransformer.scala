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

  protected def copy(source: ZipEntry, outputStream: OutputStream) {
    Utils.copy(inputFile.getInputStream(source), outputStream)
  }

  def transform(source: ZipEntry, target: ZipOutputStream)(fn: RewriteContent) {
    if (!skipEntry(source)) {
      val te = new ZipEntry(source.getName)
      target.putNextEntry(te)
      copy(source, target)
      target.closeEntry()
    }
  }

}
