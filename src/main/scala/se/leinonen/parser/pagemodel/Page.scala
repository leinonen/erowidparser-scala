package se.leinonen.parser.pagemodel

import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import se.leinonen.parser.ErowidUrl
import se.leinonen.parser.UrlType.UrlType
import se.leinonen.parser.UrlType
import org.jsoup.Jsoup

/**
 * Created by leinonen on 2014-04-18.
 */
class Page(url: String, document: Document) {

  val doc = document

  def title = doc.title()

  def text(query:String) : String = {
    val x = for(e <- doc.select(query).iterator().asScala) yield Jsoup.parse(e.html()).text()
    x.mkString("\n")
  }

  def image(query:String) : String = {
    val x = for(e <- doc.select(query).iterator().asScala) yield e.attr("src")
    x.mkString
  }

  def list(query:String) : List[String] = {
    val x = for(e <- doc.select(query).iterator().asScala) yield Jsoup.parse(e.html()).text()
    x.toList
  }

  def links: Iterator[ErowidUrl] = {
    def getType(url: String): UrlType = {
      if (url.endsWith("basics.shtml")) {
        UrlType.Basics
      } else if (url.endsWith("effects.shtml")) {
        UrlType.Effects
      } else {
        UrlType.Drug
      }
    }
    val links = for (e <- doc.select("a").iterator().asScala) yield e.attr("href")
    val urls = for (url <- links.filter(x => (isValidUrlPattern(x) && notBlackListed(x)))) yield {
      new ErowidUrl("http://www.erowid.org" + url, getType(url))
    }
    urls
  }

  private def notBlackListed(str: String): Boolean = {
    val blacklist = Array("images", "dose", "cultivation1", "writings", "law",
      "journal", "media", "faq", "faqs", "testing",
      "health", "chemistry", "synthesis", "synthesis1", "spirit")
    if (!str.endsWith(".shtml"))
      return false

    for (s <- blacklist) {
      if (str.endsWith(s + ".shtml")) return false
    }
    true
  }

  private def isValidUrlPattern(url: String): Boolean = {
    url.startsWith("/plants") || url.startsWith("/herbs") || url.startsWith("/chemicals") || url.startsWith("/smarts")
  }
}
