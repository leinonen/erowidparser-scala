package se.leinonen.parser.pagemodel

import org.jsoup.nodes.Document
import se.leinonen.parser.model.Drug
import se.leinonen.parser.{ErowidUrl, UrlType}

/**
 * Created by leinonen on 2014-04-18.
 */
class DrugPage(url:String, doc:Document) extends Page(url, doc){

  def basicsLinks:List[ErowidUrl] = links.filter(_.typ == UrlType.Basics).toList
  def effectsLinks:List[ErowidUrl] = links.filter(_.typ == UrlType.Effects).toList

  def substanceName:String = parseText("div.ts-substance-name")
  def chemicalName:String = parseText("div.sum-chem-name")
  def description:String = parseText("div.sum-description")
  def effectsClassfication:String = parseText("div.sum-effects")
  def commonName:String = parseText("div.sum-common-name")

  def toDrug: Drug = {
    val drug: Drug = new Drug
    drug.name = substanceName
    drug.description = description
    drug.chemicalName = chemicalName
    drug.url = url
    drug.imageUrl = parseImage("div.summary-card-topic-image img")
    drug
  }
}
