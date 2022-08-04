package com.example.skindemo.skin

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import kotlin.jvm.Volatile
import com.example.skindemo.utils.ReflectUtil
import java.util.*

class SkinManager private constructor(private val mContext: Application) : Observable() {

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

        fun getInstance(): SkinManager? {
            return instance
        }
    }

    init {
        //资源管理类 用于从 app/皮肤 中加载资源
        SkinResources.init(mContext)
        //注册Activity生命周期,并设置被观察者
        val skinActivityLifecycle = ApplicationActivityLifecycle(this)
        mContext.registerActivityLifecycleCallbacks(skinActivityLifecycle)
    }


    fun loadSkin(skinPath: String) {
        if (TextUtils.isEmpty(skinPath)) {
            SkinResources.getInstance()?.reset()
        } else {
            val appSources = mContext.resources
            //反射创建AssetManager 与 Resource
            val assetManager: AssetManager = AssetManager::class.java.newInstance()

            val addAssetPathMethod = ReflectUtil.findMethod(assetManager, "addAssetPath", String::class.java)
//            val addAssetPathMethod = assetManager.javaClass.getMethod( "addAssetPath", String::class.java)
            addAssetPathMethod.invoke(assetManager, skinPath)

            val packageManager = mContext.packageManager
            //用于获取 APK 安装包文件信息
            val info : PackageInfo? = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
            val packageName = info?.packageName

            val skinResource = Resources(assetManager, appSources.displayMetrics, appSources.configuration)
            SkinResources.getInstance()?.applySkin(skinResource, packageName!!)
        }
        setChanged()
        notifyObservers(null)
    }
}