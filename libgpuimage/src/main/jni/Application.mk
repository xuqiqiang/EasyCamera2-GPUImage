
APP_ABI := armeabi-v7a arm64-v8a x86 
# armeabi & mips are deprecated
#APP_ABI :=  armeabi-v7a

APP_PLATFORM := android-16

#APP_STL := gnustl_static
APP_STL := c++_static

#APP_CPPFLAGS := -frtti -fexceptions
#APP_CPPFLAGS := -fpermissive
APP_CPPFLAGS := -frtti -std=gnu++11

APP_OPTIM := release