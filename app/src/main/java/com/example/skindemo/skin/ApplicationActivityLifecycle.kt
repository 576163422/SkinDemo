package com.example.skindemo.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.core.view.LayoutInflaterCompat
import com.example.skindemo.utils.ReflectUtil
import java.util.*

class ApplicationActivityLifecycle(private val observable: Observable) : Application.ActivityLifecycleCallbacks {

    private val mLayoutInflaterFactories = mutableMapOf<Activity, SkinLayoutInflaterFactory>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory， 修改 mFactorySet 标签为false
        val layoutInflater = activity.layoutInflater
        val factorySetField = ReflectUtil.findField(layoutInflater, "mFactorySet")
        factorySetField.isAccessible = true
        factorySetField.set(layoutInflater, false)

        //使用factory2 设置布局加载工程
        val skinLayoutInflaterFactory = SkinLayoutInflaterFactory()
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory)
        mLayoutInflaterFactories.put(activity, skinLayoutInflaterFactory)
        observable.addObserver(skinLayoutInflaterFactory)
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
        mLayoutInflaterFactories.remove(activity)
    }
}