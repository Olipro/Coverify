# Coverify

A tool to convert LLVM profdata exported JSON over to the Cobertura XML format.

## Introduction.

Unsurprisingly, this tool exists for my own selfish purposes. Primarily, I don't particularly care about super-accurate reports when coverage is <100% providing it **never** reports 100% coverage when that's not the case. Consequently, the way in which I process the LLVM input isn't as great as it should be if you wanted a true conversion, but it should be consistent enough that any deviations in coverage should trigger your build system to make you go and have a look at the *original* LLVM HTML reports and see what that is saying.

Most importantly though, if coverage is 100%, this tool absolutely should report it.

## Rationale.

I've found the GCOV based reporting to be... well, shitty? The LLVM "source based" mechanism is wonderfully accurate, irritatingly so in some cases (closing curly brace reporting as uncovered? you actually have a missing case)

### Why Java though?

Java 10 is fairly fresh and has very mature infrastructure for the tedious process of parsing both JSON and XML although it turns out that JAXB is in the midst of being nuked from orbit within the JDK/JRE itself and so it's pulled in as dependencies, although I'm waiting on updates to those so that I stop getting complaints from IntelliJ about module-related stupidity.

## Caveats.

The parsing is not streamed so if you feed it a massive JSON file, either set the heap size appropriately or prepare for Java to OOM - play stupid games, win stupid prizes!

LLVM has a very intricate model when it comes to determining if a line (and indeed, individual parts of a line) are uncovered. Currently I don't care about this and fold this down to a per-line determination that also ignores "regionEntry" segments.

## Building.

You need Java 10 or later. If you dislike this, please feel free to rewrite all the "var" statements.

If you have no experience with Java, go get IntelliJ IDEA and use that to build it, or grab the JAR off the releases tab if you trust me. Otherwise, it's a fairly simple Gradle project.

Feel free to run the unit tests, they should all pass.

## Usage
Firstly, follow LLVM's instructions for generating the JSON (compile, llvm-profdata merge, llvm-cov export).

Secondly, run the JAR with the path to the JSON file as the only argument. XML is spat back at you to stdout, you presumably want to redirect this to a file.

## Submitting Pull Requests.

The code currently has 100% coverage (in terms of classes that actually execute code), if you want to submit something, please ensure you add tests that keep it that way and validate whatever it was you changed.

## License

Copyright(c) 2018 Olipro - https://github.com/Olipro oliver(at-symbol)uptheinter.net

Licensed under the GNU GPLv3 - see LICENSE.md for a copy.

