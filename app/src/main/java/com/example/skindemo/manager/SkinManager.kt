package com.example.skindemo.manager

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import kotlin.jvm.Volatile
import com.example.skindemo.manager.SkinManager
import com.example.skindemo.utils.ReflectUtil

class SkinManager private constructor(private val mContext: Application) {

    companion object {

        @Volatile
        private var instance: SkinManager? = null

        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinManager::class.java) {
                    if (instance == null) {
                        instance = SkinManager(application)
                    }
                }
            }
        }
    }
    init {
        //注册Activity生命周期,并设置被观察者
        val skinActivityLifecycle = ApplicationActivityLifecycle()
        mContext.registerActivityLifecycleCallbacks(skinActivityLifecycle)
    }


    fun loadSkin(skinPath: String) {
        val appSources = mContext.resources


        //反射创建AssetManager 与 Resource
        val assetManager = AssetManager::class.java.newInstance()

        val addAssetPathMethod = ReflectUtil.findMethod(assetManager, "addAssetPath", String.javaClass)
        addAssetPathMethod.invoke(assetManager, skinPath)

        var skinResource: Resources = Resources(assetManager, appSources.displayMetrics, appSources.configuration)

        val packageManager = mContext.packageManager
        //用于获取 APK 安装包文件信息
        val info = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
        val packageName = info?.packageName




    }


}