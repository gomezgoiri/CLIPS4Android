// Inspired by http://manski.net/2012/05/logging-from-c-on-android/
#include <android/log.h>
#define  LOG_TAG    "CLIPS native code"
#define  aprintf(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

// Or as suggested by http://stackoverflow.com/a/10275473
//#define printf(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
// Problem: It should never be defined before including stdio!
// That's too difficult as I don't fully understand CLIPS code and its dependencies.