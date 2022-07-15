package com.example.skindemo.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.skindemo.utils.ReflectUtil

class ApplicationActivityLifecycle : Application.ActivityLifecycleCallbacks {


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory， 修改 mFactorySet 标签为false
        val layoutInflater = activity.layoutInflater
        val factorySetField = ReflectUtil.findField(layoutInflater, "mFactorySet")
        factorySetField.isAccessible = true
        factorySetField.set(layoutInflater, false)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}