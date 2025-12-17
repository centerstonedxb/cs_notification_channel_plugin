import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'cs_notification_channel_plugin_platform_interface.dart';

/// An implementation of [CsNotificationChannelPluginPlatform] that uses method channels.
class MethodChannelCsNotificationChannelPlugin extends CsNotificationChannelPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('cs_notification_channel_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
