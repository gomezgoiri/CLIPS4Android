This Android application reasons over semantic content.
 1. It loads two files with the rules needed to perform [RDFS](http://www.w3.org/TR/2002/WD-rdf-mt-20020429/) and [OWL2](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoning
   * *Note that if they don't exist in your external storage, they will be automatically created.*.
 1. It loads the [Wine ontology](http://krono.act.uji.es/Links/ontologies/wine.owl/at_download/file) from the web.
 1. It creates an individual to check basic reasoning.
 1. It saves all the inferred triples in a file.
   * * Warning: [Starting in Android 4.2, devices can support multiple users](http://source.android.com/devices/tech/storage/#multi-user-external-storage). As a consequence, you won't see any file if you mount the external storage folder.  However, you must be able to check and get it using [DDMS](http://developer.android.com/tools/debugging/ddms.html)'s file explorer (in my case, the folder was located under /mnt/shell/emulated/0) *
 
 
This application uses a parser and a serializer for/to a CLIPS-based format which can be [found here](https://github.com/gomezgoiri/rio-clp).
