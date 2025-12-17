import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'cs_notification_channel_plugin_method_channel.dart';

abstract class CsNotificationChannelPluginPlatform extends PlatformInterface {
  /// Constructs a CsNotificationChannelPluginPlatform.
  CsNotificationChannelPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static CsNotificationChannelPluginPlatform _instance = MethodChannelCsNotificationChannelPlugin();

  /// The default instance of [CsNotificationChannelPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelCsNotificationChannelPlugin].
  static CsNotificationChannelPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [CsNotificationChannelPluginPlatform] when
  /// they register themselves.
  static set instance(CsNotificationChannelPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
