package $pckg;format="lower,package"$

import scala.scalajs.js
import scala.scalajs.js.JSON

@js.native
trait Firebase extends js.Object {
    
    def firestore : Firestore = js.native

    //var _components                                         = js.Object
}
object Firebase {
  def get(): Firebase = {
    js.Dynamic.global.firebase.asInstanceOf[Firebase]
  }
}

@js.native
trait Firestore extends js.Object {

}