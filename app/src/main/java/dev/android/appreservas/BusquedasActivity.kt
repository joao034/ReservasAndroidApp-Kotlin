package dev.android.appreservas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.widget.BaseAdapter
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_busquedas.*

class BusquedasActivity : AppCompatActivity() {

    private val BASE_URL = "https://google.com"
    private val SEARCH_PATH = "/search?q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busquedas)
        navegacion()
    }

    private fun navegacion(){

        //Refresh
        srNavegacion.setOnRefreshListener {
           wvNav.reload()
        }

        //SearchView
        svNavegacion.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //Busqueda especifica el escribir una palabra
            override fun onQueryTextSubmit(cadena: String?): Boolean {
                //entra si no es nulo
                cadena?.let {
                    if(URLUtil.isValidUrl(it)){
                        //URL valida
                        wvNav.loadUrl(it)
                    }else{
                        wvNav.loadUrl("$BASE_URL$SEARCH_PATH$it")
                    }
                }
                return false;

            }

            //Prediccion al escribir palabra
            override fun onQueryTextChange(cadena : String?): Boolean {
                return false;
            }
        })

        //Web View
        wvNav.webChromeClient = object : WebChromeClient(){}

        wvNav.webViewClient = object: WebViewClient(){

            //La aplicacion controla la carga de las URL's
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                svNavegacion.setQuery(url, false)
                srNavegacion.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                srNavegacion.isRefreshing = false
            }
        }

        val settings: WebSettings = wvNav.settings;
        settings.javaScriptEnabled = true
        wvNav.loadUrl(BASE_URL)
    }

    //Retroceder busquedas
    override fun onBackPressed() {
        if(wvNav.canGoBack()){
            wvNav.goBack()
        }else{
            super.onBackPressed()
        }
    }
}