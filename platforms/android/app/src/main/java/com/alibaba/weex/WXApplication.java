package com.alibaba.weex;

import android.app.Application;

import com.alibaba.weex.commons.adapter.ImageAdapter;
import com.alibaba.weex.commons.util.AppConfig;
import com.alibaba.weex.component.MyWebView;
import com.alibaba.weex.pluginmanager.PluginConfig;
import com.alibaba.weex.pluginmanager.PluginManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.devtools.common.LogUtil;

public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initDebugEnvironment(true, "DEBUG_SERVER_HOST");
    WXSDKEngine.addCustomOptions("appName", "WXSample");
    WXSDKEngine.addCustomOptions("appGroup", "WXApp");
    WXSDKEngine.initialize(this,
        new InitConfig.Builder()
            .setImgAdapter(new ImageAdapter())
            .build()
    );

    Fresco.initialize(this);
    AppConfig.init(this);
    PluginConfig.init(this);
    PluginManager.registerComponents(PluginConfig.getComponents());
    PluginManager.registerModules(PluginConfig.getModules());
    initComponent();

  }

  private void initComponent() {
    try {
      WXSDKEngine.registerComponent("mywebview",MyWebView.class);

    } catch (WXException e) {
      LogUtil.e("dicallc引入报错了");
      e.printStackTrace();
    }
  }

  /**
   * @param enable enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
   *               true, you can launch a remote debugger and inspector both.
   *               false, you can  just launch a inspector.
   * @param host   the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
   *               for example "127.0.0.1".
   */
  private void initDebugEnvironment(boolean enable, String host) {
    if (!"DEBUG_SERVER_HOST".equals(host)) {
      WXEnvironment.sRemoteDebugMode = enable;
      WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
    }
  }

}
