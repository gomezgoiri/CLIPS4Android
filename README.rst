CLIPSonAndroid
==============

*CLIPSonAndroid* is a project created to use `CLIPS <http://clipsrules.sourceforge.net/>`_ on Android (I know, too obvious).


The project is organized as follows:

ClipsAndroid
  It is an Android library which is intended to be used by other Android applications. To compile the eclipse project, it is necessary to compile CLIPS for Android first (see `Compiling CLIPS for Android`_.).

Examples
  It contains different Android applications which use *ClipsAndroid*.


Compiling CLIPS for Android
---------------------------

Follow the following steps to compile CLIPS for Android:

 1. Install the `NDK <http://developer.android.com/tools/sdk/ndk/index.html>`_
 2. Go to the *jni* folder
 3. Run *ndk-build* [*]_

 .. [*] Providing you have added it to your *PATH*.


License
-------

CLIPS's source files remain licensed in the *public domain*.

The rest of the parts of this project will be licensed also as *public domain*  unless the contrary is stated.


Acknowledgements
----------------

 * The development of the *ClipsAndroid* library was possible thanks to the work done in the `CLIPSJNI project <http://clipsrules.sourceforge.net/CLIPSJNIBeta.html>`_.
 * This project is supported or has been supported by the `THOFU R&D project <http://www.thofu.es/>`_.