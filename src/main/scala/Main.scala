import se.leinonen.parser.ErowidParser
import se.leinonen.parser.model.Drug
import se.leinonen.parser.pagemodel.{EffectsPage, BasicsPage, DrugPage, Page}

/**
 * Created by leinonen on 2014-04-18.
 */
object Main extends App {
  val parser: ErowidParser = new ErowidParser
  val erowid: Page = parser parse "http://www.erowid.org/general/big_chart.shtml"

  erowid.drugLinks.foreach {
    drugUrl =>
      val drugPage = (parser parse drugUrl).asInstanceOf[DrugPage]

      val drug: Drug = drugPage.toDrug
      Drug.save(drug)
      println("Saved " + drug.name + " to database")

      drugPage.basicsLinks.foreach {
        basicsUrl =>
          val basicsPage = (parser parse basicsUrl).asInstanceOf[BasicsPage]
          println(basicsPage.title)
          // val basics: Basics = basicsPage.toBasics
          // Basics.save(basics)
          // drug.basics = basics
          // Drug.save(drug) // Update drug with basics info
          // println("Added basics info")
      }

      // Process effects
      drugPage.effectsLinks.foreach {
        effectsUrl =>
          val effectsPage = (parser parse effectsUrl).asInstanceOf[EffectsPage]
          println(effectsPage.title)
          // val effects: Effects = effectsPage.toEffects
          // Effects.save(effects)
          // drug.effects = effects
          // Drug.save(drug) // Update drug with effects info
          // println("Added effects info")
      }

      println
  }
}
