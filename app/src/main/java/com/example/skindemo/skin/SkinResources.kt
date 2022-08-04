package com.example.skindemo.skin

import android.app.Application
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils

class SkinResources private constructor(private val mContext: Application) {
    companion object {
        @Volatile
       private var instance: SkinResources? = null

        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinManager::class.java) {
                    if (instance == null) {
                        instance = SkinResources(application)
                    }
                }
            }
        }

        @JvmStatic
        fun getInstance() : SkinResources? {
            return instance
        }
    }


    private var mSkinResources: Resources? = null
    private var mAppResources: Resources = mContext.resources
    private var mSkinPkgName: String? = ""
    private var isDefaultSkin: Boolean = true

    fun reset() {
        mSkinResources = null
        mSkinPkgName = null
        isDefaultSkin = true
    }

    fun applySkin(resources: Resources, pkgName: String) {
        mSkinResources = resources
        mSkinPkgName = pkgName
        isDefaultSkin = mSkinResources == null || TextUtils.isEmpty(pkgName) //是否使用默认皮肤
    }

    fun getColorStateList(resId: Int): ColorStateList? {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId)
        }
        return mSkinResources?.getColorStateList(skinId)
    }

    fun getBackground(resId: Int): Any? {
        val resourceTypeName = mAppResources.getResourceTypeName(resId)
        //background可以设置颜色 和 drawable 所以这里判断一下
        if (TextUtils.equals(resourceTypeName, "color")) {
            return getColor(resId)
        } else {
            // drawable
            return getDrawable(resId)
        }
    }

    fun getColor(resId: Int): Int? {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId)
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getColor(resId)
        }
        return mSkinResources?.getColor(skinId)
    }

    /**
     * 1.通过 app的resource 获取id 对应的 资源名 与 资源类型
     * 2.找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
     */
    fun getDrawable(resId: Int): Drawable? {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId)
        }
        //查找皮肤包资源id，没有找到就return app中的drawable
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getDrawable(resId)
        }
        return mSkinResources?.getDrawable(skinId)
    }

    /**
     * 查找皮肤包资源id
     * 根据app中的资源id，获取资源名字和类型。然后到皮肤包中查找资源id
     *
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    private fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        val resName = mAppResources.getResourceEntryName(resId)
        val resType = mAppResources.getResourceTypeName(resId)
        return mSkinResources?.getIdentifier(resName, resType, mSkinPkgName) ?: 0
    }
}