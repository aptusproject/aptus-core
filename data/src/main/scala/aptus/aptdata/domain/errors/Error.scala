package aptus
package aptdata
package domain
package errors

// ===========================================================================
trait Error {
    def id : ErrorId
    val msg: ErrorMsg

    def stateError: Boolean // in theory those should never be seen by user
def name: String = getClass.getSimpleName
    // ---------------------------------------------------------------------------
    final def thro: Nothing =
      s"${id} - ${name} - ${msg}".pipe {
        if (stateError) illegalState   (_)
        else            illegalArgument(_) } }

// ===========================================================================
object Error {

  // ---------------------------------------------------------------------------
  object NoNestedIterators extends ErrorCompanion(id = "E241119155444", stateError = true) // see a241119123645
    case class NoNestedIterators(x: Any)
      extends ErrorData { val companion = NoNestedIterators
        val msg: String = s"TODO:${id}" }

  // ---------------------------------------------------------------------------
//rename
object CanNotForceKey extends ErrorCompanion(id = "E241126104802", stateError = true)
    case class CanNotForceKey(deef: DataEntityErrorFormatter, key: Key)
      extends ErrorData { val companion = CanNotForceKey
        val msg: String =
          s"could not force value for key ${key.name.quote}: ${deef.formatErrorEntity}" }

  // ===========================================================================
  object CantCompareDynz extends ErrorCompanion(id = "E241124211420") with Error with HasErrorId {
    val msg: String = "can't compare Dynz" }

  // ---------------------------------------------------------------------------
  object DuplicateKeys extends ErrorCompanion(id = "E241120111033")
    case class DuplicateKeys(allKeys: Seq[Key], distinctKeys: Seq[Key])
      extends ErrorData { val companion = DuplicateKeys
        val msg: String =
          s"duplicate keys: ${
            allKeys.diff(distinctKeys).map(_.quote).#@@}" }

  // ===========================================================================
  // Mult

  object MoreThanOneElement extends ErrorCompanion(id = "E241128134840")
      case class MoreThanOneElement(x: Any)
        extends ErrorData { val companion = MoreThanOneElement
          val msg: String = s"TODO:${id}" }

    // ---------------------------------------------------------------------------
    object NoElements extends ErrorCompanion(id = "E241128134841")
      case class NoElements(x: Any)
        extends ErrorData { val companion = NoElements
          val msg: String = s"TODO:${id}" }

  // ===========================================================================
  // Common

  object TransformGuaranteeFailure extends ErrorCompanion(id = "E241118160516")
    case class TransformGuaranteeFailure(x: Any)
      extends ErrorData { val companion = TransformGuaranteeFailure
        val msg: String = s"TODO:${id}:guarantee" }

  // ===========================================================================
  object EnsurePresenceError extends ErrorCompanion(id = "E241118144208")
      case class EnsurePresenceError(x: Any)
        extends ErrorData { val companion = EnsurePresenceError
          val msg: String = s"TODO:${id}" }

    // ---------------------------------------------------------------------------
    object EnsureAbsenceError extends ErrorCompanion(id = "E241118144208")
      case class EnsureAbsenceError(x: Any)
        extends ErrorData { val companion = EnsureAbsenceError
          val msg: String = s"TODO:${id}" }

  // ===========================================================================
  object AccessAsSpecificType extends ErrorCompanion(id = "E241122144121")
        with AccessAsSpecificTypeThrowers
      case class AccessAsSpecificType(superType: SuperType, value: NakedValue)
        extends ErrorData { val companion = AccessAsSpecificType
          val msg: String =
            s"target not a ${superType.formatErrorMessageString}: ${value}" }

    // ---------------------------------------------------------------------------
    // TODO: t241205114751 - create combo for those two
    private type DEEF = DataEntityErrorFormatter
    private type TS   = TargetSelector

    // ---------------------------------------------------------------------------
    object TransformSpecificType extends ErrorCompanion(id = "E241122144221") {
        def throwIntegerLike       (deef: DEEF, target: TargetSelector, valew: Valew): Nothing = TransformSpecificType(deef, target, IntegerLike, valew).thro
        def throwRealLike          (deef: DEEF, target: TargetSelector, valew: Valew): Nothing = TransformSpecificType(deef, target, RealLike   , valew).thro

        // ---------------------------------------------------------------------------
        def throwBasicType(deef: DEEF, target: TS, valew: Valew)(basicType: BasicType.Selector): Nothing =
          TransformSpecificType(deef, target, basicType(BasicType), valew).thro }

      // ---------------------------------------------------------------------------
      case class TransformSpecificType(
          deef  : DataEntityErrorFormatter,
          target: TargetSelector,
          superType: SuperType,
          valew: Valew = Valew.build("TODO:241126150248"))
        extends ErrorData { val companion = TransformSpecificType
          val msg: String =
            s"target \"${target.formatDefault}\" not a ${superType.formatErrorMessageString}: " +
              s"${valew.naked.getClass.getSimpleName}\n\t\t${deef.formatErrorEntity}" } }

// ===========================================================================