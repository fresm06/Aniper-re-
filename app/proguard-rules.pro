# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Hilt
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keepclasseswithmembernames class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Kotlin
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
-dontwarn kotlin.**

# Room
-keep class androidx.room.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Keep model classes
-keep class com.aniper.app.domain.model.** { *; }
-keep class com.aniper.app.data.remote.dto.** { *; }
-keep class com.aniper.app.data.local.entity.** { *; }
