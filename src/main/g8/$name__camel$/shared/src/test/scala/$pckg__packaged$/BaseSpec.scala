package $pckg;format="lower,package"$

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

abstract class BaseSpec extends AnyWordSpecLike with Matchers with BeforeAndAfterAll
