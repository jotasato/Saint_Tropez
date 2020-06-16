package com.example.sainttropez

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //HTMLファイル内でjavascriptを使用しているので、有効にしている
        webView.settings.javaScriptEnabled = true
        //loadUrlメソッドに表示したいHTMLのパスを渡す。指定したHTMLをWebViewに表示する
        webView.loadUrl("file:///android_asset/html/index.html")
        //ActicityクラスのregisterForContextMenuメソッドを使って、コンテキストメニューを追加したいビューを登録しておく。
        //ここではhtmlを表示しているWebViewを登録している。
        registerForContextMenu(webView)
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

    //コンテキストメニューとして登録されたビューを長押しすると、onCreateContextMenuメソッドがよばれるので、これをオーバーライドする。
    //第1引数は登録されたコンテキストメニュー,vは長押しされたビュー,menuInfoはコン的メニューの作成に関する追加情報

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //menuInflaterプロパティからMenuInflaterクラスのインスタンスを取得し、
        // inflateメソッドによりメニューxmlファイルを、コンテキストメニューに設定
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.sms -> {
                val number = "999-9999-9999"
                //データとして渡すために電話番号からUriオブジェクトを生成している。
                val uri = Uri.parse("sms:$number")
                //intent.ACTION_VIEWを指定してインテントを作成している。
                var intent = Intent(Intent.ACTION_VIEW)
                //電話番号が入っている変数uriをデータとして設定。
                intent.data = uri
                startActivity(intent)
                return true
            }
                R.id.mail -> {
                //⓵では、メールアプリに渡すアドレス、件名、本文を変数に代入し、データとして渡すUriオブジェクトを作成している。
                val email = "nobody@example.com"//⓵
                val subject = "予約問い合わせ"//⓵
                val text = "以下のとおり予約希望します。"//⓵
                val uri = Uri.parse("mailto:")//⓵
                //インテントはアクションとしてIntent.ACTION_SENDTOを指定して作成している。
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = uri

                //⓵で作成した変数を使ってインテントの設定を行っている。宛先メールアドレスはキーにIntent.EXTRA_EMAILを
                //使用しますが、これはString型の配列なので、今回のようにアドレスが1つしかない場合でも、arratOf関数により配列にしておく比喩よyがある。
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))//⓷
                        .putExtra(Intent.EXTRA_SUBJECT, subject)//⓷
                        .putExtra(Intent.EXTRA_TEXT, text)//⓷
                //ここのif式は作成した暗黙インテントを処理できるアプリをユーザーがインストールしていない場合のための処理。
                //処理できるアプリがないと呼び出しは失敗し、このアプリは強制終了してします。
                //アクティビティが確実にインテントを受け取るようにするには、IntentクラスのresolveActivityメソッドを使う。
                //このメソッドの戻り値が、null以外の値であれば、インテントを処理できるアプリが少なくと1つは存在するということなので、安全にstartActicityを呼び出すことができる。
                //引数であるpackageManagerはインストールされているアプリに関する様々な情報を保持しているクラス。このインスタンスは、ActivityクラスのgetPackageManagerメソッドで取得しますが、
                //kotlinではpackageManagerプロパティが利用できる。
                if (intent.resolveActivity(packageManager) != null ) {
                    startActivity(intent)
                }
                return true
            }
                R.id.shere -> {
                    val text = "美味しいレストランを紹介します。"
                    val intent = Intent(Intent.ACTION_SEND)
                    //typeプロパティで、インテントのタイプを設定している。
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, text)
                    //毎回、アプリの選択画面を表示させたい場合は、IntentクラスのクラスメソッドであるcreateChooserを使います。
                    //createChooserで新たなインテントを作成し、それをstartActivityに渡している。
                    val chooser = Intent.createChooser(intent, null)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(chooser)
                    }
                return true
                }
                R.id.browse ->{
                    val url: String = "http://www.yahoo.co.jp/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}