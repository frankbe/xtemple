package frankbe.xtemple

import org.scalatest.FunSuite
import java.io._

class TestSuite extends FunSuite {

  val mapping = Map("SUBJECT"->"duck", "OBJECT"->"worm")

  test("test WordML node processing") {      pending
    val inXml = scala.xml.XML.loadFile("res/document.xml")
    val outXml = new WordMLNodeTransformer().transform(inXml, mapping.get(_))
    assert((inXml \\ "t").size === (outXml \\ "t").size)
  }

  def processTemplate(reader: Reader, writer: Writer) {
    val source = Utils.readAll(reader)
    val target = ParamUtils.replaceAllParams(source, mapping.get(_))
    writer.write(target)
  }

  test("simple document processing") {
    val template = new File("res/test.docx").ensuring(_.exists)
    val outfile = new File("out.docx").ensuring(f => !f.exists() || f.delete())
    Transformer.transform(template, mapping.get(_), outfile)
    assert(outfile.exists && outfile.length > 0, "no target file")
  }

}

