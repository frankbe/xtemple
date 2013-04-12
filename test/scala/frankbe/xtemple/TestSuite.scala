package frankbe.xtemple

import org.scalatest.FunSuite
import java.io._
import collection.JavaConversions._
import com.github.mustachejava.DefaultMustacheFactory

object TestSuite {

  private def mkOutputDir() = {
    val outputDir = new java.io.File("./target")
    outputDir.mkdir()
    outputDir
  }

  case class Item(name: String, count: Int, price: Double)

}


class TestSuite extends FunSuite {
  import TestSuite._

  test("simple replacer") {
    val mapping = Map("SUBJECT"->"duck", "OBJECT"->"worm")
    val template = new File("res/template-simple.docx").ensuring(_.exists)
    val outfile = new File(mkOutputDir, "simple.docx").ensuring(f => !f.exists() || f.delete())
    DocumentTransformer.forDocx().transform(template, outfile) {(reader, writer) =>
      ParamUtils.simpleReplace(reader, writer, mapping.get(_))
    }
    assert(outfile.exists && outfile.length > 0, "no target file")
  }

  test("mustache replacer") {   //pending //TODO repair
    def test(fileName: String) {
      val order = mapAsJavaMap(Map("orderNo"->"123", 
        "items"->seqAsJavaList(List(Item("book", 1, 22.5), Item("cat food", 2, 2.5), Item("camera", 1, 100)))
      )) 
      val template = new File("res/template-" + fileName).ensuring(_.exists)
      val outfile = new File(mkOutputDir, fileName).ensuring(f => !f.exists() || f.delete())
      val mf = new DefaultMustacheFactory();
      DocumentTransformer.forFileName(fileName).transform(template, outfile) {(reader, writer) =>
        ParamUtils.mustacheReplace(mf, reader, writer, order)
      }
      assert(outfile.exists && outfile.length > 0, "no target file")
    }
    test("sample2.docx")
    test("sample2.odt")
  }


}

