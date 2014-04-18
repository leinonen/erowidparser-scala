package se.leinonen.parser.pagemodel

import org.jsoup.nodes.Document
import se.leinonen.parser.model.Drug

/**
 * Created by leinonen on 2014-04-18.
 */
class DrugPage(url:String, doc:Document) extends Page(url, doc){

  def substanceName:String = text("div.ts-substance-name")
  def chemicalName:String = text("div.sum-chem-name")
  def description:String = text("div.sum-description")
  def effectsClassfication:String = text("div.sum-effects")
  def commonName:String = text("div.sum-common-name")

  def toDrug: Drug = {
    val drug: Drug = new Drug
    drug.name = substanceName
    drug
  }
}
