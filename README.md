xtemple
=======

A simple template engine for *.docx and *.odt files (written in scala)

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

    ... the {{animal}} eats a {{food}} ...

and create an output file target/sample1.docx with the text

    ... the duck eats a worm ...

for further samples, take a look at the unit tests

Recommendations
---------------
You can edit the template files including the placeholders with the standard editors (MS Word or Open/Libre Office), but they produce sometimes unparsable xml content. Since docx and odt files are just zip files which contain xml files, I recoment to check the modified content of the template files after editing. 

