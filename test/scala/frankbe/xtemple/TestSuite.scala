package frankbe.xtemple

import org.scalatest.FunSuite
import java.io._
import collection.JavaConversions._
import com.github.mustachejava.DefaultMustacheFactory


class TestSuite extends FunSuite {


  /*test("test WordML node processing") {      pending
    val inXml = scala.xml.XML.loadFile("res/document.xml")
    val outXml = new WordMLNodeTransformer().transform(inXml, mapping.get(_))
    assert((inXml \\ "t").size === (outXml \\ "t").size)
  }*/


  test("simple replacer") {
    val mapping = Map("SUBJECT"->"duck", "OBJECT"->"worm")
    val template = new File("res/in-simple.docx").ensuring(_.exists)
    val outfile = new File("out-simple.docx").ensuring(f => !f.exists() || f.delete())
    DocxTransformer.transform(template, outfile) {(reader, writer) =>
      ParamUtils.simpleReplace(reader, writer, mapping.get(_))
    }
    assert(outfile.exists && outfile.length > 0, "no target file")
  }


  case class Item(name: String, count: Int, price: Double)

  test("mustache replacer") {   //pending //TODO repair
    val order = mapAsJavaMap(Map("orderNo"->"123", 
      "items"->seqAsJavaList(List(Item("book", 1, 22.5), Item("cat food", 2, 2.5), Item("camera", 1, 100)))
    )) 
    val template = new File("res/in-mustache.docx").ensuring(_.exists)
    val outfile = new File("out-mustache.docx").ensuring(f => !f.exists() || f.delete())
    val mf = new DefaultMustacheFactory();
    DocxTransformer.transform(template, outfile) {(reader, writer) =>
      ParamUtils.mustacheReplace(mf, reader, writer, order)
    }
    assert(outfile.exists && outfile.length > 0, "no target file")
  }


}

