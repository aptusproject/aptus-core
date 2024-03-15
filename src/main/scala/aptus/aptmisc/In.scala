package aptus
package aptmisc

// ===========================================================================
private[aptus] final class In[A] private[aptus](private val a: A) {
  def some: Option[A]  = Some(a)
  def seq : Seq   [A]  = Seq (a)
  def list: List  [A]  = List(a)
  def set : Set   [A]  = Set (a)

  // ---------------------------------------------------------------------------
  def left [$Right]: Either[A, $Right] = Left(a)
  def right[$Left ]: Either[$Left, A]  = Right(a)

  // ---------------------------------------------------------------------------
  @inline def noneIf(pred: A => Boolean): Option[A] = if ( pred(a)) None else Some(a)
  @inline def someIf(pred: A => Boolean): Option[A] = if (!pred(a)) None else Some(a)
}

// ===========================================================================
