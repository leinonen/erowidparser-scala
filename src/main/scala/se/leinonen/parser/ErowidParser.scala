package se.leinonen.parser

import se.leinonen.parser.pagemodel.{EffectsPage, BasicsPage, DrugPage, Page}
import org.jsoup.nodes.Document
import org.jsoup.Jsoup

/**
 * Created by leinonen on 2014-04-18.
 */
class ErowidParser {

  def parse(url: String): Page = parse(new ErowidUrl(url, UrlType.Unknown))

  def parse(url:ErowidUrl): Page = {
    println("parse " + url.url)
    val doc: Document = fetchDocument(url.url)
    if (doc != null){
      url.typ match {
        case UrlType.Drug => new DrugPage(url.url, doc)
        case UrlType.Basics => new BasicsPage(url.url, doc)
        case UrlType.Effects => new EffectsPage(url.url, doc)
        case _ => new Page(url.url, doc)
      }
    } else null
  }

  private def fetchDocument(url: String): Document = {
    var doc: Document = null
    try {
      doc = Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get()
    } catch {
      case _: Throwable => println("Error")
    }
    doc
  }
}
