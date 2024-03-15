package aptus
package aptutils

// ===========================================================================
object IteratorUtils {

  def groupByPreSortedKey[K, V](itr: Iterator[(K, V)]): Iterator[(K, List[V])] = {
    var previousKeyOpt: Option[K] = None
    
    var currentGroup = cross.MutList[V]()
    
    itr
      .flatMap { current =>          
        val (currentKey, currentValue) = (current._1, current._2)

        val tmp =
          if (previousKeyOpt.exists(_ != currentKey)) { // new key
            val entry = previousKeyOpt.get -> currentGroup.toList
            
            currentGroup = cross.MutList[V]()
           
            Some(entry)             
          }
          else // repeated key
            None
        
        currentGroup += currentValue            
        previousKeyOpt = Some(currentKey)
        
        tmp } ++
      previousKeyOpt.map(_ -> cross.mutList(currentGroup)).iterator }

  // ===========================================================================
  def zipSameSize[A, B](itr: Iterator[A], that: Iterator[B]): Iterator[(A, B)] =
    new collection.AbstractIterator[(A, B)] { // TODO: knownSize
      def    next() = (itr.next() , that.next())
      def hasNext   = (itr.hasNext, that.hasNext) match {
        case (false, false) => false
        case (true , true ) => true
        case (x, y)         => illegalState("not the same size (hasNext/hasNext): ", x, y) } }

  // ===========================================================================
  def logIteratorProgress[A](n: Int, debug: A => String)(iter: Iterator[A]): Iterator[A] = {
    var count = 0
    iter.map { line =>
      count += 1
      if ((count % n) == 0) println(s"\t${count.formatUsLocale}\t${debug(line)}")

      line } } }

// ===========================================================================
