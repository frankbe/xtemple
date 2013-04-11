package frankbe.xtemple

import java.io.OutputStream
import java.util.zip.{ZipFile, ZipEntry}
import java.nio.charset.Charset



/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 25.02.13
 * Time: 16:49
 */
object DocxTransformer {
  private val docxContentXmlEntryPattern = "word/.+\\.xml".r
  def isTransformableXmlEntry(entry: ZipEntry) = docxContentXmlEntryPattern.findFirstIn(entry.getName).isDefined
  val nodeTransformer = new WordMLNodeTransformer
  val UTF_8 = Charset.forName("UTF-8")
}

class DocxTransformer extends ZipFileTransformer {
  import DocxTransformer._

  protected def createEntryTransformer(sourceFile: ZipFile) = new ZipEntryTransformer(sourceFile) {
    override protected def innerTransform(sourceEntry: ZipEntry, getParam: String => Option[String], outputStream: OutputStream) {
      if (isTransformableXmlEntry(sourceEntry)) {
        println("detected " + sourceEntry.getName + "...")
        val inputString = Utils.readAll(sourceFile.getInputStream(sourceEntry))
        //val inputString = IOUtils.toString(sourceFile.getInputStream(sourceEntry), UTF_8)
        val outputString = new TextTransformer().transform(inputString, getParam)
        outputStream.write(outputString.getBytes(Charset.forName("UTF-8")))
        //IOUtils.copy(sourceFile.getInputStream(sourceEntry), outputStream)
       // val xmlDoc = XML.load(sourceFile.getInputStream(sourceEntry))
        //Utils.stream(new ByteArrayInputStream(xmlDoc.toString.getBytes()), outputStream)
        //val targetNode = nodeTransformer.transform(xmlDoc, getParam)
        //IOUtils.write(xmlDoc.toString(), outputStream)
        //super.innerTransform(sourceEntry, getParam, outputStream)
      } else {
        super.innerTransform(sourceEntry, getParam, outputStream)
      }
    }
  }
}
