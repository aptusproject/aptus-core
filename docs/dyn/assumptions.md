# dyn-module assumptions

Quick dump of current assumptions for Dyn module

```
	dyn:
		data:
			dyn:
				DynBuilder:
					! a241120102934 - automatically wraps nested Seq[Dyn] as Dyns
					a241120103837 - automatically flattens optional values
					a241119155444 - nested Dynz/Iterators are forbidden
			mult:
				dyns:
					a241203101826 - Dyns fits in memory
				dynz:
					a241128152444 - does not HAVE to fit in memory (unlike `Dyns`), but more limited in terms of operations
					a241203101839 - use external sort for "big" operations on Dynz
		types:
			valew:
				a241119155118 - Valew of Valew is forbidden (and containers thereof)
			nums:
				a241125101517 - we treat BigInt differently than other int-likes; unlike gallia (for now...)
				a241125114540 - JSON number tax
				a241125115501 - can't increment real numbers (not good use case)
				a241125125730 - if dividing two ints, the result must be integer (or else we error)		
		selectors:
			a241127165900 - selectors: can't have it all at once (eg dynamic + path)
		io
			json:
				a241203170954 - must use pre/post-processing if 'null' matters (TODO: t241203171010 - minimal tool to transform them)
					likewise with the other null-like constructs ([], ...) - see https://github.com/galliaproject/gallia-docs/blob/master/json.md
```
