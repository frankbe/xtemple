package frankbe.xtemple

import java.io._
import java.util.zip.{ZipFile, ZipEntry}
import java.nio.charset.Charset
import util.parsing.input.StreamReader


/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 25.02.13
 * Time: 16:49
 */
object DocxTransformer extends ZipFileTransformer {

  private val docxContentXmlEntryPattern = "word/.+\\.xml".r
  //val nodeTransformer = new WordMLNodeTransformer
  val UTF_8 = Charset.forName("UTF-8")

  def isTransformableXmlEntry(entry: ZipEntry) = docxContentXmlEntryPattern.findFirstIn(entry.getName).isDefined

  protected def createEntryTransformer(sourceFile: ZipFile)(fn: RewriteContent) = new ZipEntryTransformer(sourceFile) {
    override protected def copy(sourceEntry: ZipEntry, outputStream: OutputStream) {
      if (isTransformableXmlEntry(sourceEntry)) {
        println("detected " + sourceEntry.getName + "...")
        val reader = new BufferedReader(new InputStreamReader(sourceFile.getInputStream(sourceEntry)))
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


/*

        val inputString = Utils.readAll(sourceFile.getInputStream(sourceEntry))
        //val inputString = IOUtils.toString(sourceFile.getInputStream(sourceEntry), UTF_8)
        val outputString = new TextTransformer().transform(inputString)(fn)
        outputStream.write(outputString.getBytes(Charset.forName("UTF-8")))
        //IOUtils.copy(sourceFile.getInputStream(sourceEntry), outputStream)
        // val xmlDoc = XML.load(sourceFile.getInputStream(sourceEntry))
        //Utils.stream(new ByteArrayInputStream(xmlDoc.toString.getBytes()), outputStream)
        //val targetNode = nodeTransformer.transform(xmlDoc, getParam)
        //IOUtils.write(xmlDoc.toString(), outputStream)
        //super.innerTransform(sourceEntry, getParam, outputStream)
*/