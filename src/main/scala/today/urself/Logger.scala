package today.urself

import io.circe.{Encoder, Json}
import io.circe.syntax._

import scala.collection.JavaConverters._
import scala.util.Try

object Logger extends App {

  def log[T: Encoder](level: String)(t: T)(implicit line: sourcecode.Line, file: sourcecode.File): Unit = {
    println(Json.obj(
      "level" -> Json.fromString(level),
      "ts" -> Json.fromLong(System.currentTimeMillis()),
      "line" -> Json.fromInt(line.value),
      "file" -> Json.fromString(file.value),
      "value" -> t.asJson
    ))
  }

  implicit val stackTraceElementEncoder = new Encoder[StackTraceElement] {
    override def apply(a: StackTraceElement): Json = {
      Json.obj(
        "class" -> Json.fromString(a.getClassName),
        "method" -> Json.fromString(a.getMethodName),
        "file" -> Json.fromString(a.getFileName),
        "line" -> Json.fromInt(a.getLineNumber)
      )
    }
  }

  implicit val throwableEncoder = new Encoder[Throwable] {
    override def apply(a: Throwable): Json = {
      Json.obj(
        "message" -> Json.fromString(a.getMessage),
        "stacktrace" -> a.getStackTrace.asJson
      )
    }
  }

  info("hello world")
  info(Map("hello" -> 1234))

}
