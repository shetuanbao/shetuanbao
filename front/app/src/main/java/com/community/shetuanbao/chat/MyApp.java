package com.community.shetuanbao.chat;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.community.shetuanbao.utils.RongCloudEvent;
import io.rong.imkit.RongIM;

public class MyApp extends Application {

  @RequiresApi(api = Build.VERSION_CODES.DONUT)
  @Override
  public void onCreate() {
    super.onCreate();
    if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
            "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
/***
 * 对融云进行初始化
 */
      RongIM.init(this);
      if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
        RongCloudEvent.init(this);

      }

    }
  }


  /**
   * 得到当前进程的名字
   *
   * @param context
   * @return 进程名字的字符串
   */
  @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
  public static String getCurProcessName(Context context) {

    int pid = android.os.Process.myPid();

    ActivityManager activityManager = (ActivityManager) context
            .getSystemService(Context.ACTIVITY_SERVICE);

    for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
            .getRunningAppProcesses()) {

      if (appProcess.pid == pid) {
        return appProcess.processName;
      }
    }
    return null;
  }
}