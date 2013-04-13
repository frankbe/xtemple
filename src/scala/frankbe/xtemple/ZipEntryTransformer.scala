package frankbe.xtemple

import java.util.zip.{ZipFile, ZipOutputStream, ZipEntry}
import java.io._

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 14:45
 */
class ZipEntryTransformer(inputFile: ZipFile) extends StatefulResultTransformer[ZipEntry, ZipOutputStream] {

  protected def skipEntry(entry: ZipEntry) = false
  protected def skipTransformation(entry: ZipEntry) = false

  private def internalTransform(inputStream: InputStream, outputStream: OutputStream)(fn: RewriteContent) {
    //println("detected " + sourceEntry.getName + "...")
    val reader = new BufferedReader(new InputStreamReader(inputStream)) {
      // Mustache java closes the stream always while processing, which causes an error,
      // but the Stream will be closed anyway at the end of the zip file, so...
      override def close() {}
    }
    val writer = new BufferedWriter(new OutputStreamWriter(outputStream))
    fn(reader, writer)
    writer.flush()
  }

  def transform(sourceEntry: ZipEntry, target: ZipOutputStream)(fn: RewriteContent) {
    if (!skipEntry(sourceEntry)) {
      val te = new ZipEntry(sourceEntry.getName)
      val sourceStream = inputFile.getInputStream(sourceEntry)
      target.putNextEntry(te)
      if (!skipTransformation(sourceEntry)) {
        internalTransform(sourceStream, target)(fn)
      }
      Utils.copy(sourceStream, target)
      target.closeEntry()
    }
  }

}
