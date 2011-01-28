X12 Parser 
Current Version : V0.7

CHANGES
-------------------------------------------------------------------------------
V0.7
Not compatible with the previous versions.
Earlier class X12 is now X12Simple. Provides the same features as earlier.
Added class Loop and class X12. They represent the X12 in a loop 
hierarchy.
Parser is now an Interface. There are two types of Parsers, an X12SimpleParser
and X12Parser. They return objects of type X12Simple and X12 respectively.
New class Cf has configuration information of how to identify X12 loops.
It is used in combination with the X12Parser.
New examples have been added. 

V0.6
Second version

V0.5
Initial verision