import se.leinonen.parser.ErowidParser

import se.leinonen.parser.pagemodel.{DrugPage, Page}

/**
 * Created by leinonen on 2014-04-18.
 */
object Main extends App {

  val parser: ErowidParser = new ErowidParser

  //val drugUrl = new ErowidUrl("http://www.erowid.org/chemicals/dmt/dmt.shtml", UrlType.Drug)
  //val drugPage = (parser parse drugUrl).asInstanceOf[DrugPage]
  //drugPage.links.foreach(url => println(url.url))

  val root: Page = parser parse "http://www.erowid.org/general/big_chart.shtml"

  root.drugLinks.foreach {
    drugUrl =>
      val drugPage = (parser parse drugUrl).asInstanceOf[DrugPage]

      println("title: " + drugPage.title)
      //println("chemical name: " + drugPage.chemicalName)
      //println("substance name: " + drugPage.substanceName)
      println("effects: " + drugPage.effectsClassfication)
      //println("common name: " + drugPage.commonName)
      println("description: " + drugPage.description)

      // Process basics
      drugPage.basicsLinks.foreach {
        basicsUrl =>
          println("Basics url: " + basicsUrl.url)
      }

      // Process effects
      drugPage.effectsLinks.foreach {
        effectsUrl =>
          println("Effects url: " + effectsUrl.url)
      }

      println
  }

}
