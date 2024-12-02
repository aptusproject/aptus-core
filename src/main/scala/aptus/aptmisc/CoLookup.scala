package aptus
package aptmisc

import util.chaining._

// ===========================================================================
class CoLookup[A, B](
    val lookup1: Map[A, B],
    val lookup2: Map[B, A])

  // ---------------------------------------------------------------------------
  object CoLookup {
    def forceDoubleLookup[Z, A, B](z: CloseabledIterator[Z])(f1: Z => A, f2: Z => B): (Map[A, B], Map[B, A]) =
        z.map { obj => f1(obj) -> f2(obj) }.consumeAll.pipe(_forceDoubleLookup(f1, f2))

      def forceDoubleLookup[Z, A, B](z: Iterator[Z])(f1: Z => A, f2: Z => B): (Map[A, B], Map[B, A]) =
        z.map { obj => f1(obj) -> f2(obj) }.toSeq.pipe(_forceDoubleLookup(f1, f2))

      // ---------------------------------------------------------------------------
      private def _forceDoubleLookup[Z, A, B](f1: Z => A, f2: Z => B)(pairs: Seq[(A, B)]): (Map[A, B], Map[B, A]) = {
        pairs.map(_._1).duplicates.@@
        pairs.map(_._2).duplicates.@@

        (pairs            .force.map,
         pairs.map(_.swap).force.map) }

    // ---------------------------------------------------------------------------
    def forceLookup1[Z, A, B](z: Iterator          [Z])(f1: Z => A, f2: Z => B): Map[A,     B ] = { z.map { obj => f1(obj) -> f2(obj) }.toSeq     .force.map }
    def forceLookup1[Z, A, B](z: CloseabledIterator[Z])(f1: Z => A, f2: Z => B): Map[A,     B ] = { z.map { obj => f1(obj) -> f2(obj) }.consumeAll.force.map }
    def forceLookup2[Z, A, B](z: Iterator          [Z])(f1: Z => A, f2: Z => B): Map[A, Seq[B]] = { z.map { obj => f1(obj) -> f2(obj) }.toSeq     .groupByKey }
    def forceLookup2[Z, A, B](z: CloseabledIterator[Z])(f1: Z => A, f2: Z => B): Map[A, Seq[B]] = { z.map { obj => f1(obj) -> f2(obj) }.consumeAll.groupByKey }}

// ===========================================================================