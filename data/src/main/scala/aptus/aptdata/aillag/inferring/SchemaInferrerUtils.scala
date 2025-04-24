package aptus
package aptdata
package aillag
package inferring

import aptus.{Anything_, Seq_, Tuple2_, Option_}
import aptus.aptdata.meta.schema.FldPair

// ===========================================================================
/*private - t250110134800 */object SchemaInferrerUtils {
  class IncompatibleInfoException(pairs: Seq[FldPair]) extends Exception {
    override def getMessage: String = pairs.map(_.formatDefault).section2(pairs.size.str) }

  // ===========================================================================
  implicit class SchemaInferrerCls_(dis: Cls) {

    def combine(that: Cls): Cls = {
      val conflictingPairs: Seq[FldPair] = this.conflictingPairs(that)
      
      if (conflictingPairs.nonEmpty) { // 210802094043
        throw new IncompatibleInfoException(conflictingPairs) }

      dis
        .fields
        .map { existingField =>            
          that
            .field_(existingField.key)
            .map { newField =>                      
              resolve(existingField, newField).force /* else would error at above (see 210802094043) */ }
            .getOrElse(existingField) }
        .pipe(Cls.apply)
        .reviseRequirednessBasedOn(that) /* not in that */
        .addMissingFieldsFrom     (that) /*     in that */ }
    
    // ---------------------------------------------------------------------------  
    private def conflictingPairs(that: Cls): Seq[FldPair] =
      dis
        .fields
        .flatMap { existingField =>            
          that
            .field_(existingField.key)
            .flatMap { newField =>
              resolve(existingField, newField)
                .swap(FldPair(existingField, newField)) } }

    // ===========================================================================
    def reviseRequirednessBasedOn(that: Cls): Cls =
      dis.diffKeys(that)
        .map(dis.field)
        .map(_.toOptional) // because not present in that, therefore not required
        .foldLeft(dis) { (curr, field) =>
          curr.replace(field.key, field.info) }

    // ---------------------------------------------------------------------------
    def addMissingFieldsFrom(that: Cls): Cls =
      that.diffKeys(dis)
        .map(that.field)
        .map(_.toOptional) // because not present in this, therefore not required
        .foldLeft(dis)(_ add _) }

  // ===========================================================================
  private def resolve(existingField: Fld, newField: Fld): Option[Fld] =
      (existingField.nestedClassOpt, newField.nestedClassOpt).toOptionalTuple match {
        case Some((e, n)) => Some(e.combine(n).pipe(existingField.updateSoleNestedClass))
        case None =>
          /**/ if (newField.info.subInfo1 == existingField.info.subInfo1)       Some(existingField)

          // note: not so for multiplicity, as it requires a data change (TODO: t210802090946 - reconsider)
          else if ( existingField.isRequired && !newField.isRequired) Some(existingField.toOptional)
          else if (!existingField.isRequired &&  newField.isRequired) Some(existingField)

          else if (areBoundedNumbers(existingField, newField))        Some(existingField.toDouble)

          else                                                        None }

    // ---------------------------------------------------------------------------
    private def areBoundedNumbers(f1: Fld, f2: Fld): Boolean =
      f1.basicTypeOpt.exists(_.isBoundedNumber) &&
      f2.basicTypeOpt.exists(_.isBoundedNumber) }

// ===========================================================================