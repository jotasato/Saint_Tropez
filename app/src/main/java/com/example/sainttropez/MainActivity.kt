package com.example.sainttropez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    //メニューを表示させるには、onCreateOptionMenuをオーバーライドして、メニューの初期化を行う。
    //メニューはMenuクラスのインスタンスですが、これはonCreateOptionsMenuメソッドの引数として渡されてきます。
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //onCreateOptionsMenuメソッドの引数であるメニューに、メニュー項目のXMLファイルを設定することで、画面にメニューを表示します。
        //XMLファイルの設定はMenuInflaterクラスを使用する。kotlinではmenuInflaterプロパティが利用可能。
        //onCreateOptionMenuメソッドでは戻り値を返す必要がある。メニューを表示する場合はtrueを返す。
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //onOptionItemSelectedメソッドは、オプションメニューが選択された時に呼ばれます。
    //戻り値でメニュー処理を続行する場合はfalse,続行しない場合はtrueを返す。
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //引数には選択されたメニュー項目を表すMenuItemクラスのインスタンスが渡されるので、
        //itemIdプロパティから選択されたメニュー項目のIDを取得している。
        //IDによりwhen式で処理を分岐し、各メニューがタップされた時の処理を作成している。
        //ここではWebViewに表示するHTMLファイルを変更し、メニュー処理を終了するためにtrueを返している。
        when(item?.itemId) {
            R.id.top -> {
                webView.loadUrl("file:///android_asset/html/index.html")
                return true
            }
            R.id.lunch01 -> {
                webView.loadUrl("file:///android_asset/html/lunch01.html")
                return true
            }
            R.id.lunch02 -> {
                webView.loadUrl("file:///android_asset/html/lunch02.html")
                return true
            }
            R.id.dinner01 -> {
                webView.loadUrl("file:///android_asset/html/dinner01.html")
                return true
            }
            R.id.dinner02 -> {
                webView.loadUrl("file:///android_asset/html/dinner02.html")
                return true
            }
        }
        //親クラスの同名メソッドを実行して値を返している。APIの決まりである。
        //when式で指定している以外のIDが取得された場合に何も返されなくなるのを避けるための記述。
        return super.onOptionsItemSelected(item)
    }

}