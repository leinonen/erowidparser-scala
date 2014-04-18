import se.leinonen.parser.model.Drug
import se.leinonen.parser.{UrlType, ErowidParser}

import se.leinonen.parser.pagemodel.{DrugPage, Page}

/**
 * Created by leinonen on 2014-04-18.
 */
object Main extends App {

  val drug = Drug.find(1)

  val parser: ErowidParser = new ErowidParser

  val root: Page = parser parse "http://www.erowid.org/general/big_chart.shtml"

  root.links.filter(_.typ == UrlType.Drug).foreach {
    drugUrl =>
      val drugPage = (parser parse drugUrl).asInstanceOf[DrugPage]

      println("title: " + drugPage.title)
      println("chemical name: " + drugPage.chemicalName)
      println("substance name: " + drugPage.substanceName)
      println("effects: " + drugPage.effectsClassfication)
      println("common name: " + drugPage.commonName)
      println("description: " + drugPage.description)

      // Process basics
      drugPage.links.filter(_.typ == UrlType.Basics).foreach {
        basicsUrl =>
          println("Basics url: " + basicsUrl.url)
      }

      // Process effects
      drugPage.links.filter(_.typ == UrlType.Effects).foreach {
        effectsUrl =>
          println("Effects url: " + effectsUrl.url)
      }

      println
  }

}
