# dyn-module tasks

Quick dump of current tasks for Dyn module


```
	io:
		both:
			t241204140907 - to/from data classes (case classes)
			t241129144728 - support converstion to/from case class
			t241024183349 - windows path don't start with '/'
			t241129144706 - support typical streams of data in scala (to/from): fs2, zio, ...
		in:
			t241030132858 - offer explicit ones (tsv, json, ...) here?			
		out:
			t241024153830 - write rdbms
			t241024175310 - actually use csv lib to write csv/tsv...
		json:
			json array:
				t241022104721 - stream JSON array .readFileContent()
				t241030113645 - override def formatCompact: String = asList.formatCompact- allow streaming here as well
			t241022151803 - injection - json stuff
			t241024153842 - use ujson		
			! t241203145950 - FIXME: def toDouble(value: Any): Double = value match {
		  	t241205093939 - simplify - _root_.gallia.gson.customJsonFormatters +
	ops:
		all:
			t241205112955 - do offer a Path+Ren, eg foo |> bar ~> BAR		
		Dyn:
			t241205125816 - all the generates: generusion and generussion
			t242041122561 - add the .using for consistency (need intermediate) : def transformString  (key: Key)(f: String => NakedValue) --> not using using.
			t241204111122 - more such ops: flips, square, ...
			! t241127142124 - FIXME: transformIfType - what about Seq[Int] - 
			building:
				t241203213714 - turn maps to Dyn automatically?
		Mult
			both:
				t241203151505	def doubles          : Seq[Double] = ??? // exoMap(_.double(_.soleKey)).toList
				t241203213031 - all the missing mult accessors -- missing => see codegen
				t241203161832 - split as .force.{one, distinct, ...}
				t241203162005 - more: partition, scan, splitAt, ...
			Dyns:
				t241120102540: def build(values: Iterable[Dyn]): Dyns = Dyns(values.toList) // TODO consider offering Vector version too? ()
				t241203101540 - join/cogroup/pivot/sort... 
			Dynz:				
				t241205113436 - switch to CloseabledIterator						
				t241128141346 - valuesIterator.take(1).pipe(const) /* TODO:  - close if closeabled iterator... */
				t241128123352 - groupBy for iterator (needs external sort)
				t241203101550 - join/cogroup/... --> needs external sort
				t241204105908 - offer Iterator/List alteratives (for Seq_.data)
	errors:
		t241029154956 - error msgs - else if (s.startsWith("[")) ???
	gallia:
		t241130165320 - refactor code borrowed from gallia
		t241024175302 - clean up code reuse from Gallia		
	gen:
		t241127134106 - macros for boilerplate (eg wrapped ops)
	inferring:
		t241129144211 - support String|Int like unions
	tests
		t241031141912 - DynOutputTableStringsTests - more complex cases (!= keys, ...) - 
```

