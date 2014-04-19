package se.leinonen.parser.model

import javax.persistence.{Column, Table, Entity, Id}
import scala.beans.BeanProperty

/**
 * Created by leinonen on 2014-04-19.
 */
@Entity
@Table(name = "drugs")
class Drug {

  @Id
  @BeanProperty
  var id:Int = 0

  @BeanProperty
  var name:String = null

  @BeanProperty
  @Column(unique = true)
  var simpleName:String = null

  @BeanProperty
  @Column(columnDefinition = "TEXT")
  var description:String = null

  @BeanProperty
  var url:String = null

  @BeanProperty
  var chemicalName: String = null

  @BeanProperty
  var commonName: String = null

  @BeanProperty
  var imageUrl: String = null
}

/**
 * Drug Data Access Object.
 */
object Drug extends Dao(classOf[Drug]){

}
