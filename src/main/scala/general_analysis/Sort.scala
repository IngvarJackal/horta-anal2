package general_analysis

import static._
import static.JSONStructure._
import utils.IOUtils

object Sort {

  case class JSON_outSort(status: String, response: Map[String, Int]) extends JSON_out

  private def makeSortedJSON(data: Map[String, Int], task:String):JSON_out = {
    JSON_outSort(s"$task : ${Response.successResponse}", data)
  }

  private def isInt(s: String):Boolean = {
    try {
      s.toInt
      true
    } catch {
      case e:Exception =>
      false 
    }
  }

  private def sorting(users:Array[String], msgLengths: Array[Int], bound:Int):Map[String, Int] = {
    val counts = scala.collection.mutable.Map[String, Int]()
    for ((usr, len) <- (users zip msgLengths)) {
      counts(usr) = counts.getOrElse(usr, 0) + len
    }

    val sortedList = counts.toList.sortBy({_._2}).reverse
    val restrictedList = sortedList.slice(0, if (bound > 0) bound else sortedList.length)
    val sortedCounts = collection.immutable.ListMap[String, Int](restrictedList:_*)
    sortedCounts.toMap
  }

  def freqSort(data:List[Message_in], args: Array[String], task:String): JSON_out = {

    def addUsers(res: Array[String], x: Message_in): Array[String] = res :+ x.user
    def addLengths(res: Array[Int], x: Message_in): Array[Int] = res :+ 1

    def compute(messages:List[Message_in], bound:Int = 0): JSON_out = {
      val users = messages.foldLeft(Array[String]())(addUsers)
      val msgLengths = messages.foldLeft(Array[Int]())(addLengths)
      val counts = sorting(users, msgLengths, bound)
      makeSortedJSON(counts.toMap, task)
    }

    args match {
      case x if x.length == 0 =>
        compute(data)
      case x if x.length == 1 && isInt(x(0)) =>
        compute(data, x(0).toInt)
      case _ =>
        IOUtils.makeError(task, Response.wrongArgsError)
    }
  }

  def lenSort(data:List[Message_in], args: Array[String], task:String): JSON_out = {
    def addUsers(res: Array[String], x: Message_in): Array[String] = res :+ x.user
    def addLengths(res: Array[Int], x: Message_in): Array[Int] = res :+ x.message.length

    def compute(messages:List[Message_in], bound:Int = 0): JSON_out = {
      val users = messages.foldLeft(Array[String]())(addUsers)
      val msgLengths = messages.foldLeft(Array[Int]())(addLengths)
      val counts = sorting(users, msgLengths, bound)
      val a = makeSortedJSON(counts.toMap, task)
      a
    }

    args match {
      case x if x.length == 0 =>
        compute(data)
      case x if x.length == 1 && isInt(x(0)) =>
        compute(data, x(0).toInt)
      case _ =>
        IOUtils.makeError(task, Response.wrongArgsError)
    }
  }
}
