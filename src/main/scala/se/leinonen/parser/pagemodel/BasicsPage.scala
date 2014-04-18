package se.leinonen.parser.pagemodel

import org.jsoup.nodes.Document

/**
 * Created by leinonen on 2014-04-18.
 */
class BasicsPage(url:String, doc:Document) extends Page(url, doc){

  println("Basics Page Constructor")
}
