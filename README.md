xtemple
=======

a simple template engine for docx and odt files (written in scala)

Features
--------
- replace parameters in Open Office (odt) and MS Word (docx) files
- uses mustache as the default internal template processor
- replaceable template processor

Quickstart
----------
in scala repl...

    scala> import frankbe.xtemple._
    import frankbe.xtemple._

    scala> transformDocx("res/sample1-templ.docx", "target/sample1.docx")(Map("animal"->"duck","food"->"worm"))

this example takes the template file res/sample1-templ.docx with the text

    //TEXT

and create an output file target/sample1.docx with the text

   //TEXT

for further samples, take a look at the unit tests

Recommendations
----------------
I recommend to create the template file allways with Open/Libre Office Write, even if you create docx templates.
Thats because MS Word creates sometimes unpredictable xml markup.


