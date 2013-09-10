This Android application performs some semantic reasoning:
 * It loads two files with the rules needed to perform [RDFS](http://www.w3.org/TR/2002/WD-rdf-mt-20020429/) and [OWL2](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoning (*note that previously you must copy them in the 'files/' directory of the external storage*).
 * It loads the [Wine ontology](http://krono.act.uji.es/Links/ontologies/wine.owl/at_download/file) from the web.
 * It creates an individual to check basic reasoning.
 * It saves all the inferred triples in a file.
 
 
The CLIPS' format serializer/parser can be [checked here](https://github.com/gomezgoiri/rio-clp).