-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keepnames class * implements java.io.Serializable { *; }
-dontwarn com.google.android.gms.common.GoogleApiAvailability
-dontwarn com.google.android.gms.location.FusedLocationProviderClient
-dontwarn com.google.android.gms.location.LocationCallback
-dontwarn com.google.android.gms.location.LocationRequest
-dontwarn com.google.android.gms.location.LocationResult
-dontwarn com.google.android.gms.location.LocationServices
-dontwarn com.google.android.gms.tasks.OnCanceledListener
-dontwarn com.google.android.gms.tasks.OnFailureListener
-dontwarn com.google.android.gms.tasks.OnSuccessListener
-dontwarn com.google.android.gms.tasks.RuntimeExecutionException
-dontwarn com.google.android.gms.tasks.Task
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
-dontwarn org.slf4j.impl.StaticLoggerBinder

# TODO fix serializable rename