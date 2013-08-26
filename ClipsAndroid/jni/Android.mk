LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := clips
LOCAL_LDLIBS := -llog
FILE_LIST := $(wildcard $(LOCAL_PATH)/clips/*.c)
LOCAL_SRC_FILES := $(FILE_LIST:$(LOCAL_PATH)/%=%)
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/clips
LOCAL_EXPORT_CFLAGS := -g -O3 -pipe -pedantic -std=gnu99 -fno-strict-aliasing -DIO_FUNCTIONS=0
# http://stackoverflow.com/questions/5313536/whats-the-difference-between-gnu99-and-c99-clang
LOCAL_EXPORT_LDFLAGS = -lm
LOCAL_EXPORT_LDLIBS := -llog
#include $(BUILD_STATIC_LIBRARY)
include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE := clipsdroid
LOCAL_SHARED_LIBRARIES := clips
LOCAL_SRC_FILES := eu_deustotech_clips_Environment.c
include $(BUILD_SHARED_LIBRARY)