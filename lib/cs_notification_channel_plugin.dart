import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';

class CsNotificationChannelPlugin {
  static const MethodChannel _channel = MethodChannel(
    'cs_notification_channel_plugin',
  );

  /// Creates a notification channel for Android 8.0+
  ///
  /// Parameters:
  /// - channelId: Unique identifier for the channel
  /// - channelName: Display name shown in system settings
  /// - channelDescription: Description shown in system settings
  /// - importance: 1=MIN, 2=LOW, 3=DEFAULT, 4=HIGH, 5=MAX
  static Future<bool> createNotificationChannel({
    required String channelId,
    required String channelName,
    String? channelDescription,
    int importance = 2, // IMPORTANCE_LOW
  }) async {
    // Only run on Android
    if (!Platform.isAndroid) {
      return false;
    }
    try {
      final bool result = await _channel
          .invokeMethod('createNotificationChannel', {
            'channelId': channelId,
            'channelName': channelName,
            'channelDescription': channelDescription ?? '',
            'importance': importance,
          });
      return result;
    } catch (e) {
      print('Error creating notification channel: $e');
      return false;
    }
  }
}
