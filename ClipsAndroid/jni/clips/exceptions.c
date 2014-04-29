#include <jni.h>
#include "clips.h"
#include "exceptions.h"

jint throwCLIPSError2(void *theEnv, char *module, int errorID, char *message)
{
    jclass exClass;
    JNIEnv *env;
    
    env = (JNIEnv *) GetEnvironmentContext(theEnv);
    //exClass = (*env)->FindClass( env, "java/lang/NoClassDefFoundError");
    exClass = (*env)->FindClass( env, "eu/deustotech/clips/CLIPSError");
    if (exClass == NULL) {
	//B plan: null pointer ...
	exClass = (*env)->FindClass( env, "java/lang/NoClassDefFoundError");
	return (*env)->ThrowNew( env, exClass, "eu/deustotech/clips/CLIPSError not found" );
    } else {
	// You want a constructor that takes two ints as arguments. The signature for that is "(II)V".
	// Other examples: ()V, "(Ljava/lang/String;)V", "(J)V", "(D)V", "(I)V", "(Ljava/lang/Object;)Z"
      
	jmethodID voidConstructor = (*env)->GetMethodID(env, exClass, "<init>", "(Ljava/lang/String;ILjava/lang/String;)V");
	jstring jstrModule = (*env)->NewStringUTF(env, module);
	jstring jstrMessage = (*env)->NewStringUTF(env, message);
	jobject clipsErr = (*env)->NewObject(env, exClass, voidConstructor, jstrModule, errorID, jstrMessage);
	
	// WARNING: Different methods are used to call a construtor or a normal method.
	// http://stackoverflow.com/questions/5198105/calling-a-java-method-from-c-in-android
	/* jstring jstr = (*env)->NewStringUTF(env, "This string comes from JNI");
	   jmethodID messageMe = (*env)->GetMethodID(env, exClass, "messageMe", "(Ljava/lang/String;)Ljava/lang/String;");
	   jobject result = (*env)->CallObjectMethod(env, clipsErr, messageMe, jstr);*/
	
	return (*env)->Throw( env, clipsErr );
	// If we wanted to use the constructor with a String parameter we could just use:
	// return (*env)->ThrowNew( env, exClass, message );
    }
}

// To avoid importing JNI to compile CLIPS' classes'
// Otherwise, we should make the following changes:
//   + Add "-I$(JAVA_INCLUDE) -I$(JAVA_INCLUDE)/linux" in makefile.linux. Specifically, ".c.o :  gcc (other parameters and flags) -Wno-implicit [HERE] $<".
//   + Make analogous changes in the other makefiles
void throwCLIPSError(void *theEnv, char *module, int errorID, char *message)
{
  throwCLIPSError2(theEnv, module, errorID, message);
}