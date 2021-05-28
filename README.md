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

"bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase) // "bonjour" (unchanged)
"hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase) // "HELLO"

3.pipeIf(_ % 2 == 0)(_ + 1) // 3 (unchanged)
4.pipeIf(_ % 2 == 0)(_ + 1) // 5

val suffixOpt = Some("?")
"hello".pipeOpt(suffixOpt)(suffix => _ + suffix) // "hello?"
"hello".pipeOpt(None)     (suffix => _ + suffix) // "hello" (unchanged)

"hello"  .as.noneIf(_.size > 5) // Some("hello")
"bonjour".as.noneIf(_.size > 5) // None

"hello"  .as.someIf(_.size <= 5) // Some("hello")
"bonjour".as.someIf(_.size <= 5) // None
```

