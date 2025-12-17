package com.centerstone.cs_notification_channel_plugin
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
class CsNotificationChannelPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel: MethodChannel
  private lateinit var context: Context
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "cs_notification_channel_plugin")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
  }
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "createNotificationChannel" -> {
        val channelId = call.argument<String>("channelId") ?: "default"
        val channelName = call.argument<String>("channelName") ?: "Default"
        val description = call.argument<String>("channelDescription") ?: ""
        val importance = call.argument<Int>("importance") ?: NotificationManager.IMPORTANCE_DEFAULT
        
        createChannel(channelId, channelName, description, importance)
        result.success(true)
      }
      else -> {
        result.notImplemented()
      }
    }
  }
  private fun createChannel(channelId: String, channelName: String, description: String, importance: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(channelId, channelName, importance).apply {
        this.description = description
        setShowBadge(false)
        enableVibration(false)
        setSound(null, null)
      }
      
      val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}