package frankbe.xtemple

import java.io.{OutputStream, InputStream}
import java.nio.charset.Charset

/**
 * Created with IntelliJ IDEA.
 * User: frankbe
 * Date: 26.02.13
 * Time: 09:18
 */
object Utils {

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

  def streamToString(is: java.io.InputStream, charset: String = "UTF-8") = {
    val s = new java.util.Scanner(is, charset).useDelimiter("\\A")
    if (s.hasNext()) s.next() else ""
  }

  def copy(input: InputStream, output: OutputStream, bufferSize: Int = 8192): Long = {
    val buffer = new Array[Byte](bufferSize)
    val EOF = -1
    var count: Long = 0
    var n: Int = 0
    while (EOF != ({ n = input.read(buffer); n})) {
      output.write(buffer, 0, n)
      count += n
    }
    count
  }

}
