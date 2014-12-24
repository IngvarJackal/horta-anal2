import java.text.SimpleDateFormat

import general_analysis.Sort
import org.scalatest._
import static.JSONStructure.Message_in
import utils.IOUtils.JSON_outError

class TestSort extends FlatSpec {
  final val dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  val message = List[Message_in](Message_in("Ingvar", "I am war, I am pain", dateFormatter.parse("2014/12/24 00:00:00")),
    Message_in("Ingvar", "I am all you've ever slain", dateFormatter.parse("2014/12/24 00:00:01")),
    Message_in("Ingvar", "I am tears in your eyes", dateFormatter.parse("2014/12/24 00:00:02")),
    Message_in("Ingvar", "I am grief, I am lies!", dateFormatter.parse("2014/12/24 00:00:03")),
    Message_in("ForNeVeR", "Dimmu Borgir — Puritania", dateFormatter.parse("2014/12/24 00:00:05")))

  "A freqSort" should "sort users in descending order by number of messages" in {
    assert(Sort.freqSort(message,
      Array[String](),
      "freq_sort") === Sort.JSON_outSort("freq_sort : task successfully processed",
      Map[String, Int]("Ingvar" -> 4,
        "ForNeVeR" -> 1))
    )
  }
  it should "get trimmed answer if specified" in {
    assert(Sort.freqSort(message,
      Array[String]("1"),
      "freq_sort 1") === Sort.JSON_outSort("freq_sort 1 : task successfully processed",
                                           Map[String, Int]("Ingvar" -> 4))
    )
  }
  it should "get untrimmed answer if boundary is negative" in {
    assert(Sort.freqSort(message,
      Array[String]("-2"),
      "freq_sort -2") === Sort.JSON_outSort("freq_sort -2 : task successfully processed",
                                           Map[String, Int]("Ingvar" -> 4,
                                                           "ForNeVeR" -> 1))
    )
  }
  it should "return error if argument isn't integer" in {
    assert(Sort.freqSort(message,
      Array[String]("vcxv"),
      "freq_sort vcxv") === JSON_outError("freq_sort vcxv : args are wrong")
    )
  }


  "A lenSort" should "sort users in descending order by length of messages" in {
    assert(Sort.lenSort(message,
      Array[String](),
      "len_sort") === Sort.JSON_outSort("len_sort : task successfully processed",
                                        Map[String, Int]("Ingvar" -> 90,
                                                         "ForNeVeR" -> 24))
    )
  }
  it should "get trimmed answer if specified" in {
    assert(Sort.lenSort(message,
      Array[String]("1"),
      "len_sort 1") === Sort.JSON_outSort("len_sort 1 : task successfully processed",
                                          Map[String, Int]("Ingvar" -> 90))
    )
  }
  it should "get untrimmed answer if boundary is negative" in {
    assert(Sort.lenSort(message,
      Array[String]("-2"),
      "len_sort -2") === Sort.JSON_outSort("len_sort -2 : task successfully processed",
                                           Map[String, Int]("Ingvar" -> 90,
                                                            "ForNeVeR" -> 24))
    )
  }
  it should "return error if argument isn't integer" in {
    assert(Sort.lenSort(message,
      Array[String]("vcxv"),
      "len_sort vcxv") === JSON_outError("len_sort vcxv : args are wrong")
    )
  }
}
