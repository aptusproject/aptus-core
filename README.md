# Aptus

"Aptus" is latin for suitable, appropriate, fitting. It is a utility library to help smooth certain pain points of the Java/Scala ecosystem.

It is currenly used as part of [Gallia](https://github.com/galliaproject/gallia-core/blob/master/README.md#210129170214), a Scala library for data manipulation.

## SBT
`libraryDependencies += "io.github.aptusproject" %% "aptus-core" % "0.1.0"`

The library is available for both scala 2.12 and 2.13

<a name="210121153149"></a>
**aptus-core** dependency graph:<br/><br/>
<div style="text-align:center"><img src="./dependencies.png" alt="core dependency graph"></div>

## Examples

```scala
import aptus._

"hello".p               // prints: "hello"
"hello".p.toUpperCase.p // prints: "hello", then "HELLO"

3.str     .p // prints "3"
3.toString.p // prints "3"

// ---------------------------------------------------------------------------
"hello".assert (_.startsWith("h"))                    .toUpperCase.p // prints "HELLO"
"hello".assert (_.startsWith("h"), x => s"value=${x}").toUpperCase.p // prints "HELLO"    
"hello".require(_.startsWith("h"))                    .toUpperCase.p // prints "HELLO"   
//"hello".assert (_.startsWith("H"))                    .toUpperCase.p // throws AssertionError
//"hello".assert (_.startsWith("H"), x => s"value=${x}").toUpperCase.p // throws AssertionError: assertion failed: value=hello

// ---------------------------------------------------------------------------
"hello". append(" you!") .p // prints "hello you!"
"hello".prepend("well, ").p // prints "well, hello"
"hello".colon  ("human") .p // prints "hello:human"
"hello".tab    ("human") .p // prints "hello<TAB>human"
"hello".newline("human") .p // prints "hello<new-line>human"
"hello".quote            .p // prints "\"hello\""
// .. many more String operations

// ---------------------------------------------------------------------------
"bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase).p // prints "bonjour" (unchanged)
"hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase).p // prints "HELLO"

3.pipeIf(_ % 2 == 0)(_ + 1).p // prints 3 (unchanged)
4.pipeIf(_ % 2 == 0)(_ + 1).p // prints 5

val suffixOpt = Some("?")
"hello".pipeOpt(suffixOpt)(suffix => _ + suffix).p // prints "hello?"
"hello".pipeOpt(None)     (suffix => _ + suffix).p // prints "hello" (unchanged)

"hello"  .as.noneIf(_.size > 5).p // prints Some("hello")
"bonjour".as.noneIf(_.size > 5).p // prints None

"hello"  .as.someIf(_.size <= 5).p // prints Some("hello")
"bonjour".as.someIf(_.size <= 5).p // prints None

// ---------------------------------------------------------------------------
// "force" disambiguator
//   .get is polysemic in the stdlib, sometimes "attempting" (returns Option[T]), sometimes "forcing" (returns T)
//   aptus' .force better conveys semantics
val myOption = Some("foo")

myOption.get  .p // prints "foo" -> stdlib way, forcing 
myOption.force.p // prints "foo"

val myMap = Map("bar" -> "foo")
myMap.get  ("bar").p // prints Some("foo") -> stdlib way, attempting
myMap.force("bar").p // prints "foo"
```

