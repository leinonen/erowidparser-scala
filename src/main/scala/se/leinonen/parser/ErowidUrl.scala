package se.leinonen.parser

import se.leinonen.parser.UrlType.UrlType

/**
 * Created by leinonen on 2014-04-18.
 */

object UrlType extends Enumeration {
  type UrlType = Value
  val Drug, Basics, Effects, Unknown = Value
}

class ErowidUrl(urlParam:String) extends Ordered[ErowidUrl] {
  val url = urlParam
  var typ : UrlType = UrlType.Unknown
  def this(urlParam:String, typParam:UrlType) = {
    this(urlParam); typ = typParam
  }

  override def compare(that: ErowidUrl): Int = this.url.compareTo(that.url)
}
