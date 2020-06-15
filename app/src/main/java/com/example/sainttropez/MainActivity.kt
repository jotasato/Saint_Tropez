package com.example.sainttropez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //HTMLファイル内でjavascriptを使用しているので、有効にしている
        webView.settings.javaScriptEnabled = true
        //loadUrlメソッドに表示したいHTMLのパスを渡す。指定したHTMLをWebViewに表示する
        webView.loadUrl("file:///android_asset/html/index.html")
    }
}