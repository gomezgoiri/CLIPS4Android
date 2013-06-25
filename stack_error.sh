#! /bin/bash


adb logcat | ndk-stack -sym ./ClipsAndroid/obj/local/armeabi # > "/tmp/ndk_stack.log"
