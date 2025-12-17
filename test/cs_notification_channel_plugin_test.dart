import 'package:flutter_test/flutter_test.dart';
import 'package:cs_notification_channel_plugin/cs_notification_channel_plugin.dart';
import 'package:cs_notification_channel_plugin/cs_notification_channel_plugin_platform_interface.dart';
import 'package:cs_notification_channel_plugin/cs_notification_channel_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCsNotificationChannelPluginPlatform
    with MockPlatformInterfaceMixin
    implements CsNotificationChannelPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final CsNotificationChannelPluginPlatform initialPlatform = CsNotificationChannelPluginPlatform.instance;

  test('$MethodChannelCsNotificationChannelPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCsNotificationChannelPlugin>());
  });

  test('getPlatformVersion', () async {
    CsNotificationChannelPlugin csNotificationChannelPlugin = CsNotificationChannelPlugin();
    MockCsNotificationChannelPluginPlatform fakePlatform = MockCsNotificationChannelPluginPlatform();
    CsNotificationChannelPluginPlatform.instance = fakePlatform;

    expect(await csNotificationChannelPlugin.getPlatformVersion(), '42');
  });
}
