package se.leinonen.parser.model

import javax.persistence.{Table, Entity, Id}

/**
 * Created by leinonen on 2014-04-19.
 */
@Entity
@Table(name = "drugs")
class Drug {

  @Id
  var id:Int = 0
  var name:String = null
}

/**
 * Person Data Access Object.
 */
object Drug extends Dao(classOf[Drug]){

}
