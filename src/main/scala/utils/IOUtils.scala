package utils

import org.json4s._
import org.json4s.jackson.JsonMethods._
import static.JSONStructure._
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

object IOUtils {

  implicit val formats = DefaultFormats

  case class JSON_outError(status: String) extends JSON_out
  def makeError(task: String, comment:String = "") = {
    JSON_outError(s"$task : ${if (comment.isEmpty) "ERROR" else comment}")
  }

  def readJSONfromFile(path: String): JSON_in = {
    val json = parse(scala.io.Source.fromFile(path).mkString)
    json.extract[JSON_in]
  }

  def writeJSONtoFile(path: String, jsonResponses: List[JSON_out]): Unit = {
    import org.json4s._
    import org.json4s.jackson.Serialization
    import org.json4s.jackson.Serialization.write
    implicit val formats = Serialization.formats(NoTypeHints)

//    val result = StringBuilder.newBuilder
//    result.append("[")
//    for (response <- jsonResponses) {
//      result.append(write(response))
//      result.append(",")
//    }
//    result.append("]")
//    Files.write(Paths.get(path), result.mkString.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path), write(jsonResponses).mkString.getBytes(StandardCharsets.UTF_8))
  }
}
