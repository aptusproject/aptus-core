import scala.collection.mutable.{ArrayDeque, ListBuffer}

// ===========================================================================
package object cross { // for 3.x
  /**/    type MutList[T]                         = ArrayDeque[T]
  /**/    val  MutList                            = ArrayDeque
  @inline def  mutList[T](x: MutList[T]): List[T] = x.toList // no more ".result" (MutableList)
  
  // ---------------------------------------------------------------------------
  type SeqView[T] = scala.collection.SeqView[T] // no more second type parameter: [T, Seq[_]]

  // ---------------------------------------------------------------------------
  def immutableSeqToListBuffer[T](x: collection.Seq[T]): ListBuffer[T] = x.to(ListBuffer) // () instead of []
}

// ===========================================================================
