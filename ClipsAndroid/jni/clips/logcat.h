#include <android/log.h>
#define  LOG_TAG    "CLIPS native code"
#define  aprintf(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
// Or...
//#define printf(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
// It should be never define before including stdio!
// That's too difficult as I don't understand the code and its dependencies so well