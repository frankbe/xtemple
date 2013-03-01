package frankbe.xtemple

import java.io.{ByteArrayOutputStream, ByteArrayInputStream, OutputStream}
import java.util.zip.{ZipFile, ZipEntry}
import xml.XML
import org.apache.commons.io.{Charsets, IOUtils}

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
}

class DocxTransformer extends ZipFileTransformer {
  import DocxTransformer._

  protected def createEntryTransformer(sourceFile: ZipFile) = new ZipEntryTransformer(sourceFile) {
    override protected def innerTransform(sourceEntry: ZipEntry, getParam: String => Option[String], outputStream: OutputStream) {
      if (isTransformableXmlEntry(sourceEntry)) {
        println("detected " + sourceEntry.getName + "...")
        val inputString = IOUtils.toString(sourceFile.getInputStream(sourceEntry), Charsets.UTF_8)
        val outputString = new TextTransformer().transform(inputString, getParam)
        IOUtils.write(outputString, outputStream, Charsets.UTF_8)
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
