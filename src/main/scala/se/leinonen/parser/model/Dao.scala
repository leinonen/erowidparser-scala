package se.leinonen.parser.model

import com.avaje.ebean.{Query, Ebean}


/**
 * Dao for a given Entity bean type.
 * Created by leinonen on 2014-04-18.
 * As described at http://www.avaje.org/topic-137.html
 */
abstract class Dao[T](cls: Class[T]) {

  private val ebeanServer = Ebean.getServer(null)

  def find(id: Any): T = ebeanServer.find(cls, id)

  def find: Query[T] = ebeanServer.find(cls)

  def ref(id: Any): T = ebeanServer.getReference(cls, id)

  def save(o: Any): Unit = ebeanServer.save(o)

  def delete(o: Any): Unit = ebeanServer.delete(o)
}
