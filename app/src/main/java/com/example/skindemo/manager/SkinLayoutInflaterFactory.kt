package com.example.skindemo.manager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import java.lang.reflect.Constructor
import java.util.HashMap

class SkinLayoutInflaterFactory : LayoutInflater.Factory2 {

    companion object {
        private val mClassPrefixList = arrayOf("android.widget.", "android.webkit.", "android.app.", "android.view.")

        private val mConstructorSignature = arrayOf(Context::class.java, AttributeSet::class.java)

        private val sConstructorMap = HashMap<String, Constructor<out View?>>()
    }

    private val skinAttribute: SkinAttribute by lazy {
        SkinAttribute()
    }


    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        TODO("Not yet implemented")
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        //换肤就是在需要时候替换 View的属性(src、background等) 所以这里创建 View,从而修改View属性
        var view = createSDKView(context, name, attrs)
        if (view == null) {
            view = createView(context, name, attrs);
        }
        //插入换肤逻辑
        if (null != view) {
            //加载属性
            skinAttribute.look(view, attrs)
        }
        return null
    }


    private fun createSDKView(context: Context, name: String, attrs: AttributeSet): View? {
        //如果包含 . 则不是SDK中的view 可能是自定义view 或者 support库中的View
        if (name.contains(".")) {
            return null
        }
        for (item in mClassPrefixList) {
            val view: View? = createView(context, item + name, attrs)
            if (view != null) {
                return view
            }
        }
        return null
    }

    /**
     * 创建View
     */
    private fun createView(context: Context, name: String, attrs: AttributeSet): View? {
        val constructor: Constructor<out View?>? = findConstructor(context, name)
        return constructor?.newInstance(context, attrs)
    }

    /**
     * 保存View的构造方法
     */
    private fun findConstructor(context: Context, name: String): Constructor<out View?>? {
        var constructor: Constructor<out View?>? = sConstructorMap.get(name)
        if (constructor == null) {
            val clazz: Class<out View?> =
                Class.forName(name, false, context.classLoader).asSubclass(View::class.java)
            constructor = clazz.getConstructor(*mConstructorSignature)
            sConstructorMap.put(name, constructor)
        }
        return constructor
    }
}