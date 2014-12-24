package static

object JSONStructure {
  case class JSON_in(tasks: List[String], messages:List[Message_in])
  case class Message_in(user: String, message: String, date: java.util.Date)
  abstract class JSON_out {val status: String}
}
