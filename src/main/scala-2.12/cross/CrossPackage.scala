import scala.collection.mutable.{MutableList, ListBuffer}

// ===========================================================================
package object cross { // for 2.12
          type MutList[T]                         = MutableList[T]
          val  MutList                            = MutableList
  @inline def  mutList[T](x: MutList[T]): List[T] = x.result.toList
  
  // ---------------------------------------------------------------------------
  type SeqView[T] = scala.collection.SeqView[T, scala.collection.Seq[_]]

  // ---------------------------------------------------------------------------
  def immutableSeqToListBuffer[T](x: collection.Seq[T]): ListBuffer[T] = x.to[ListBuffer] // [] instead of ()
}

// ===========================================================================
