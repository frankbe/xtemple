package frankbe.xtemple

import java.io.{StringWriter, OutputStream, InputStream}
import java.nio.charset.Charset

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 09:18
 */
object Utils {

  val EOF = -1

  def using[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try { f(closeable) } finally { closeable.close()
  }

  // see http://stackoverflow.com/a/6928784
  def stream(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int = 16384) {
    val buffer = new Array[Byte](bufferSize)
    def doStream(total: Int = 0): Int = {
      val n = inputStream.read(buffer)
      if (n == -1)
        total
      else {
        outputStream.write(buffer, 0, n)
        doStream(total + n)
      }
    }
    doStream()
  }

  def readAll(is: java.io.InputStream, charset: String = "UTF-8"): String = {
    val s = new java.util.Scanner(is, charset).useDelimiter("\\A")
    if (s.hasNext()) s.next() else ""
  }

  def readAll(reader: java.io.Reader, bufferSize: Int = 8192): String = {
    val buffer = new Array[Char](bufferSize)
    val sb = new StringBuilder()
    var n = EOF
    while (EOF != {n = reader.read(buffer); n}) {
      sb.append(n.toChar)
    }
    sb.toString()
  }

  def copy(input: InputStream, output: OutputStream, bufferSize: Int = 8192): Long = {
    val buffer = new Array[Byte](bufferSize)
    var count: Long = 0
    var n: Int = 0
    while (EOF != ({ n = input.read(buffer); n})) {
      output.write(buffer, 0, n)
      count += n
    }
    count
  }

}
