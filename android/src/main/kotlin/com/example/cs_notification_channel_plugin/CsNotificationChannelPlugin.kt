package com.example.cs_notification_channel_plugin
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
/** CsNotificationChannelPlugin */
class CsNotificationChannelPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "cs_notification_channel_plugin")
    channel.setMethodCallHandler(this)
  }
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "getPlatformVersion" -> {
        result.success("Android ${android.os.Build.VERSION.RELEASE}")
      }
      "createNotificationChannel" -> {
        createNotificationChannel(call, result)
      }
      else -> {
        result.notImplemented()
      }
    }
  }
  private fun createNotificationChannel(call: MethodCall, result: Result) {
    try {
      // Notification channels are only needed on Android O (API 26) and above
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = call.argument<String>("channelId")
        val channelName = call.argument<String>("channelName")
        val channelDescription = call.argument<String>("channelDescription")
        val importance = call.argument<Int>("importance") ?: NotificationManager.IMPORTANCE_DEFAULT
        // Validate required parameters
        if (channelId == null || channelName == null) {
          result.error("INVALID_ARGUMENTS", "channelId and channelName are required", null)
          return
        }
        // Create the notification channel
        val channel = NotificationChannel(channelId, channelName, importance).apply {
          description = channelDescription ?: ""
        }
        // Register the channel with the system
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        
        result.success(true)
      } else {
        // For devices below Android O, notification channels are not needed
        result.success(false)
      }
    } catch (e: Exception) {
      result.error("CHANNEL_ERROR", "Failed to create notification channel: ${e.message}", null)
    }
  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}