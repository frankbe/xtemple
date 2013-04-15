package frankbe.xtemple

import org.scalatest.FunSuite
import java.io._
import collection.JavaConversions._

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

  private def test(transformer: DocumentTransformer, inFileName: String, outFileName: String, obj: Any) {
      val template = new File("res/" + inFileName).ensuring(_.exists)
      val outfile = new File(mkOutputDir, outFileName).ensuring(f => !f.exists() || f.delete())
      transformer.transform(template, outfile)(obj)
      assert(outfile.exists && outfile.length > 0, "no target file")
    }
 
  test("mustache replacer sample 1") { 
    val mapping = Map("animal"->"duck", "food"->"worm")
    test(docx, "sample1-templ.docx", "sample1.docx", mapping)
    test(odt, "sample1-templ.odt", "sample1.odt", mapping)
  }

  test("mustache replacer sample 2") { 
    val order = mapAsJavaMap(Map("orderNo"->"123", 
        "items"->seqAsJavaList(List(Item("book", 1, 22.5), Item("cat food", 2, 2.5), Item("camera", 1, 100)))
      )) 
    test(docx, "sample2-templ.docx", "sample2.docx", order)
    test(odt, "sample2-templ.odt", "sample2.odt", order)
  }


  test("alternative simple replacer") {
    val mapping = Map("animal"->"duck", "food"->"worm")
    val template = new File("res/sample1-templ.docx").ensuring(_.exists)
    val outfile = new File(mkOutputDir, "sample1-alternative.docx").ensuring(f => !f.exists() || f.delete())
    docx.transform(template, outfile)(Rewriter(mapping.get(_)))
    assert(outfile.exists && outfile.length > 0, "no target file")
  }

 
}

