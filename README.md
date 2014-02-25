xtemple
=======

A simple template engine for *.docx and *.odt files (written in scala)

Features
--------
- edit the template parameters in Open/Libre Office (for odt files) or MS Word (for docx files) 
- replaceable template processor
- [mustache] is the default template processor

Quickstart
----------
in scala repl:

    scala> import frankbe.xtemple._
    scala> val params = Map("animal"->"duck","food"->"worm")
    scala> docx.transform("res/sample1-templ.docx", "target/sample1.docx")(params)

this example takes the template file *res/sample1-templ.docx* 

    ... the {{animal}} eats a {{food}} ...

and creates an output file *target/sample1.docx* 

    ... the duck eats a worm ...

for further samples, take a look at the unit tests

Recommendations
---------------
You can edit the template files including the placeholders with standard editors (MS Word or Open/Libre Office), but they produce sometimes unparsable xml content. Since docx and odt files are just zip files which contain xml files, I recommend to check the modified content of the template files after editing. For docx look at the zip entry *word/document.xml*. For odt look at *content.xml*.

[mustache]:https://github.com/spullara/mustache.java
