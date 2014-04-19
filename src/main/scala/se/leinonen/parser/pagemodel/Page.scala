package se.leinonen.parser.pagemodel

import org.jsoup.nodes.{Element, Document}
import scala.collection.JavaConverters._
import se.leinonen.parser.ErowidUrl
import se.leinonen.parser.UrlType.UrlType
import se.leinonen.parser.UrlType
import org.jsoup.Jsoup

/**
 * Generic Erowid Page Parser. Handles all generic page stuff.
 * Created by leinonen on 2014-04-18.
 */
class Page(url: String, document: Document) {

  val pageUrl = url
  val doc = document

  def title = doc.title()

  def parseText(query: String): String = {
    val x = for (e <- doc.select(query).iterator().asScala) yield Jsoup.parse(e.html()).text()
    x.mkString("\n")
  }

  def parseImage(query: String): String = {
    val x = for (e <- doc.select(query).iterator().asScala) yield e.attr("src")
    x.mkString
  }

  def parseList(query: String): List[String] = {
    val x = for (e <- doc.select(query).iterator().asScala) yield Jsoup.parse(e.html()).text()
    x.toList
  }

  def links: List[ErowidUrl] = {
    def getType(url: String): UrlType = {
      if (url.endsWith("basics.shtml")) {
        UrlType.Basics
      } else if (url.endsWith("effects.shtml")) {
        UrlType.Effects
      } else if (url.endsWith(".shtml")) {
        UrlType.Drug
      } else {
        UrlType.Unknown
      }
    }

    // This is not the way to do it! Duplicates!
    //val hrefs = for (e <- doc.select("a").iterator().asScala) yield e.attr("href")

    lazy val hrefs : List[String] = doc.select("a").iterator().asScala.collect {
      case s: Element => s.attr("href")
    }.toList.distinct.sorted

    val urls = for (url <- hrefs.filter(x => isValidUrlPattern(x))) yield {
      new ErowidUrl(getCorrectBaseFor(url) + url, getType(url))
    }
    urls
  }

  def drugLinks:List[ErowidUrl] = links.filter(u => (notBlackListed(u.url) && u.typ == UrlType.Drug))

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

  private def getCorrectBaseFor(url:String) : String = {
    if (isRelative(url)) pageUrl.substring(0, pageUrl.lastIndexOf("/") + 1) 
    else "http://www.erowid.org"
  }

  private def isRelative(url: String): Boolean = !url.startsWith("/") && url.endsWith(".shtml")

  private def isValidUrlPattern(url: String): Boolean = {
    isRelative(url) ||
      url.startsWith("/plants") || url.startsWith("/herbs") || url.startsWith("/chemicals") || url.startsWith("/smarts")
  }

  override def toString() = title

}
