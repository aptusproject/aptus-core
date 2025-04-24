/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package inferring
package table

import scala.collection.mutable

import aptus.{Seq_, String_}

// ===========================================================================
class MutableValuesSubset(keys: Seq[Key], max: Int) {

  private val subsets = mutable.Map[Key, mutable.Set[String]]()
  init()

  // ---------------------------------------------------------------------------
  private def init() = {
    keys
      .foreach { key =>
        subsets += key -> mutable.Set[String]() } }

  // ===========================================================================
  def addValues(key: Key, values: Set[String]): Unit = {
    val _values = subsets(key)

    if (_values.size <= max) {
      subsets += key -> (_values ++= values) } }

  // ---------------------------------------------------------------------------
  private def getValues(key: Key): Seq[String] = subsets(key).toList.sorted

  // ---------------------------------------------------------------------------
  def potentiallyUpdateInfo(key: Key, info: Info): Info =
    if (info.subInfo1.multiple) info
    else {
      val values = subsets(key)

      if (values.size <= 2 &&
          BooleanDetector.isLikelyBoolean(values.toSet))
         info.updateValueType(BasicType._Boolean)
       else
         info }

  // ===========================================================================
  override def toString: String = formatDefault

    def formatDefault: String =
      keys
        .map { key =>
          s"${key.name.quote}: ${getValues(key)}" }
        .joinln }

// ===========================================================================