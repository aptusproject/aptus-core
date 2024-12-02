package aptus
package min

import apttraits._

// ===========================================================================
/** 230927172554 - have your prototyping package object extend this trait to save imports */
trait AptusMinimal
    extends AptusBooleanShorthands
       with AptusDummyImplicitShorthand
       with AptusNullShorthands
       with AptusChaining
       with AptusMinExtensions
       with AptusMinimalAliases

// ===========================================================================
