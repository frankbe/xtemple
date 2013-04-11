package frankbe.xtemple

import org.scalatest.FunSuite
import java.io._
import collection.JavaConversions

class TestSuite extends FunSuite {

  val mapping = Map("SUBJECT"->"duck", "OBJECT"->"worm")

  /*test("test WordML node processing") {      pending
    val inXml = scala.xml.XML.loadFile("res/document.xml")
    val outXml = new WordMLNodeTransformer().transform(inXml, mapping.get(_))
    assert((inXml \\ "t").size === (outXml \\ "t").size)
  }*/


  test("simple replacer") {
    val template = new File("res/in-simple.docx").ensuring(_.exists)
    val outfile = new File("out-simple.docx").ensuring(f => !f.exists() || f.delete())
    DocxTransformer.transform(template, outfile) {(reader, writer) =>
      ParamUtils.simpleReplace(reader, writer, mapping.get(_))
    }
    assert(outfile.exists && outfile.length > 0, "no target file")
  }

  test("mustache replacer") {   pending //TODO repair
    val template = new File("res/in-mustache.docx").ensuring(_.exists)
    val outfile = new File("out-mustache.docx").ensuring(f => !f.exists() || f.delete())
    DocxTransformer.transform(template, outfile) {(reader, writer) =>
      ParamUtils.mustacheReplace(reader, writer, JavaConversions.mapAsJavaMap(mapping))
    }
    assert(outfile.exists && outfile.length > 0, "no target file")
  }


}

