package com.goldze.mvvmhabit.app;

import android.os.Environment;

import com.goldze.mvvmhabit.BuildConfig;
import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.ui.login.LoginRXActivity;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.log.AndroidLogAdapter;
import me.goldze.mvvmhabit.log.FormatStrategy;
import me.goldze.mvvmhabit.log.KLog;
import me.goldze.mvvmhabit.log.Logger;
import me.goldze.mvvmhabit.log.PrettyFormatStrategy;

/**
 * Created by goldze on 2017/7/16.
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG,false);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .isSaveToFile(true)
                .versionName("_999_")
                .showThreadInfo(false)  // 线程信息
                .fileFolder(Environment.getExternalStorageDirectory().getAbsolutePath() +"/123456/")
                .methodCount(3)         // 方法堆栈数
                .methodOffset(2)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("MVVMHabitTAG")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
                return true;
            }
        });
        //初始化全局异常崩溃
        initCrash();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(LoginRXActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
