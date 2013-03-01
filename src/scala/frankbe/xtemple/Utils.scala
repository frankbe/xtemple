package frankbe.xtemple

import java.io.{OutputStream, InputStream}

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

}
