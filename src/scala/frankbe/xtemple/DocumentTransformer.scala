package frankbe.xtemple

import java.io._
import java.util.zip.{ZipFile, ZipEntry}
import java.nio.charset.Charset
import util.parsing.input.StreamReader
import util.matching.Regex


/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 25.02.13
 * Time: 16:49
 */
object DocumentTransformer {
  def forDocx() = new DocumentTransformer("word/document.xml".r)         //"word/.+\.xml"    //TODO check
  def forOdt() = new DocumentTransformer("content.xml".r)                //TODO check
  def forFileName(name: String) = {
    if (name.toLowerCase().endsWith(".docx")) forDocx()
    else if (name.toLowerCase().endsWith(".odt")) forOdt()
    else throw new IllegalArgumentException("unknown file type")
  }

}

class DocumentTransformer(fittingEntryPattern: Regex) extends ZipFileTransformer {

  val UTF_8 = Charset.forName("UTF-8")

  def isTransformableXmlEntry(entry: ZipEntry) = fittingEntryPattern.findFirstIn(entry.getName).isDefined

  protected def createEntryTransformer(sourceFile: ZipFile)(fn: RewriteContent) = new ZipEntryTransformer(sourceFile) {
    override protected def copy(sourceEntry: ZipEntry, outputStream: OutputStream) {
      if (isTransformableXmlEntry(sourceEntry)) {
        println("detected " + sourceEntry.getName + "...")
        val reader = new BufferedReader(new InputStreamReader(sourceFile.getInputStream(sourceEntry))) {
          override def close() {} // Stream will be closed at once at the end of the zip file
        }
        val writer = new BufferedWriter(new OutputStreamWriter(outputStream))
        fn(reader, writer)
        writer.flush()
        //writer.close(); reader.close()
      } else {
        super.copy(sourceEntry, outputStream)
      }
    }
  }

}

