package com.example.skindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skindemo.databinding.ActivityMainBinding
import com.example.skindemo.skin.SkinManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.change.setOnClickListener {
            val dirPath = cacheDir.absolutePath
            val skinApkDir = "$dirPath/skinapk-debug.apk"
            SkinManager.getInstance()?.loadSkin(skinApkDir)
        }
        binding.reset.setOnClickListener {
            SkinManager.getInstance()?.loadSkin("")
        }
    }
}