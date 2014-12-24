import java.text.SimpleDateFormat

import general_analysis.Sort.JSON_outSort
import org.scalatest.FlatSpec
import static.JSONStructure.{JSON_in, Message_in}
import utils.IOUtils._

class TestIO extends FlatSpec {
  final val dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  val fileIn = "src/test/resources/in.json"
  val fileOut = "src/test/resources/resp.json"

  "A readJSONfromFile" should "parse JSON from file" in {
    assert(readJSONfromFile(fileIn) ===
      JSON_in(List("freq_sort 1", "len_sort 50", "ururu", "freq_sort", "len_mean xcvxc"),
        List(Message_in("Ingvar","hi!",dateFormatter.parse("2014/12/24 00:00:00")),
             Message_in("Ingvar","hello?",dateFormatter.parse("2014/12/24 00:00:01")),
             Message_in("Ingvar","is anyone there?",dateFormatter.parse("2014/12/24 00:00:02")),
             Message_in("ForNeVeR","101000101010101110",dateFormatter.parse("2014/12/24 00:00:03")))))
  }

  "A writeJSONtoFile" should "serialize case class to JSON" in {
    writeJSONtoFile(fileOut, List(JSON_outSort("freq_sort 1 : task successfully processed",Map("Ingvar" -> 3)),
                                  JSON_outSort("len_sort 50 : task successfully processed",Map("Ingvar" -> 25, "ForNeVeR" -> 18)),
                                  JSON_outError("ururu : there is no such command"),
                                  JSON_outSort("freq_sort : task successfully processed",Map("Ingvar" -> 3, "ForNeVeR" -> 1)),
                                  JSON_outError("len_mean xcvxc : there is no such command")))
    assert(scala.io.Source.fromFile(fileOut).mkString === "[{\"status\":\"freq_sort 1 : task successfully processed\",\"response\":{\"Ingvar\":3}},{\"status\":\"len_sort 50 : task successfully processed\",\"response\":{\"Ingvar\":25,\"ForNeVeR\":18}},{\"status\":\"ururu : there is no such command\"},{\"status\":\"freq_sort : task successfully processed\",\"response\":{\"Ingvar\":3,\"ForNeVeR\":1}},{\"status\":\"len_mean xcvxc : there is no such command\"}]")
  }
}
