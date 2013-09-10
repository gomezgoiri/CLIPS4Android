This Android application reasons over semantic content:
 1. It loads two files with the rules needed to perform [RDFS](http://www.w3.org/TR/2002/WD-rdf-mt-20020429/) and [OWL2](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoning
   * *Note that previously you must copy them in the* *'files/'* *directory of the external storage*.
 1. It loads the [Wine ontology](http://krono.act.uji.es/Links/ontologies/wine.owl/at_download/file) from the web.
 1. It creates an individual to check basic reasoning.
 1. It saves all the inferred triples in a file.
 
 
This sample application uses a parser and a serializer to an CLIPS-based format which can be [found here](https://github.com/gomezgoiri/rio-clp).
