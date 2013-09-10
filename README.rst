CLIPS4Android
==============

*CLIPS4Android* is a project created to adapt `CLIPS <http://clipsrules.sourceforge.net/>`_ to Android (I know, too obvious).


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


 
Debugging CLIPS on Android
--------------------------

The cleanest way to show messages from a native library in Android is by simply redirecting its output (see `Redirecting stdout`_).
However, this method has not always work for me (sorry, I don't know the cause).
That's why I forced CLIPS to show all its messages using Logcat (see `Logcat from native code`_).


Redirecting stdout
******************

CLIPS may throw *system exits*.
As a result, the Android process using CLIPS can unexpectedly crash without giving any useful information.
To show CLIPS' original error messages, redirect NDK's standard output to *LogCat* using the following commands:

.. code-block:: bash

  $ adb shell stop
  $ adb shell setprop log.redirect-stdio true
  $ adb shell start


Logcat from native code
***********************

Sometimes the previous property does not work (I don't know why yet).
Since `messages can be directly logged on Logcat <http://stackoverflow.com/questions/10274920/how-to-get-printf-messgaes-written-in-ndk-application/10275209#10275209>`_, 
we replaced the printf's with these *Logcat*'s logs in the `issue 2 <https://github.com/gomezgoiri/CLIPSonAndroid/issues/2>`_ .

To that end, we have done an `utility macro <https://github.com/gomezgoiri/CLIPSonAndroid/blob/master/ClipsAndroid/jni/clips/logcat.h>`_.
Common usage:

.. code-block:: c
  
  #include "logcat.h"
  
  ...
  
  aprintf("This is an standard message\n");


License
-------

CLIPS's source files remain licensed in the *public domain*.

The rest of the parts of this project will be licensed also as *public domain*  unless the contrary is stated.


Acknowledgements
----------------

 * The development of the *ClipsAndroid* library was possible thanks to the work done in the `CLIPSJNI project <http://clipsrules.sourceforge.net/CLIPSJNIBeta.html>`_.
 * This project is supported or has been supported by the `THOFU R&D project <http://www.thofu.es/>`_.
