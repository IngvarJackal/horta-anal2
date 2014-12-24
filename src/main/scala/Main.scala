import general_analysis.Sort
import static.Response._
import static.JSONStructure.JSON_out
import utils.IOUtils
import static.Commands._

object Main {

  final val fileIn = "src/test/resources/in.json"
  final val fileOut = "src/test/resources/resp.json"

  def main(args: Array[String]): Unit = {
    val json = IOUtils.readJSONfromFile(fileIn)
    var jsonResponses = List[JSON_out]()

    for (task <- json.tasks) {
      val s = task.split(" ")
      val command = s(0)
      val args = s.slice(1, s.length)
      val resp: JSON_out = command match {
        case `freqSort` =>
          Sort.freqSort(json.messages, args, task)
        case `lenSort` =>
          Sort.lenSort(json.messages, args, task)
        case _ =>
          IOUtils.makeError(task, noCommandError)
      }
      jsonResponses = jsonResponses :+ resp
    }
    IOUtils.writeJSONtoFile(fileOut, jsonResponses)
  }
}
