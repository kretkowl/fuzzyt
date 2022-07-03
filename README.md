# fuzzyt

Library for fuzzy matching of strings in Java. Implementation of Levenshtein distance stolen from [rosetta code](https://rosettacode.org/wiki/Levenshtein_distance#Java).

## Description

Library exposes Levenshtein class which has ability to calculate Levenshtein distance and similarity based on it. Also implements token ratio - i.e. Levenshtein distance
calculated for strings divided to tokens on word boundaries, which are then sorted lexically and joined to form new string.

Main class is *FuzzyIndex*, which allows for quick searching of matches of token ratio in static set. Set elements are calculated on index insertion, query is also transformed
obly once.

## Dependencies

No dependencies, except for recent Java (12+) and JUnit 5 for tests.

