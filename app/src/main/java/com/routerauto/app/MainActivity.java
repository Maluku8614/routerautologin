package com.routerauto.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private static final String ROUTER_URL = "http://192.168.1.1/";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "masohi86";
    private WebView webView;
    private boolean attempted = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        FrameLayout root = new FrameLayout(this);
        webView = new WebView(this);
        webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        root.addView(webView);
        setContentView(root);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setLoadsImagesAutomatically(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                if (url != null && url.startsWith(ROUTER_URL)) {
                    // Give router scripts a moment to finish building the login form.
                    view.postDelayed(() -> autoLogin(), 350);
                }
            }
        });
        webView.setBackgroundColor(Color.WHITE);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(ROUTER_URL);
    }

    private void autoLogin() {
        if (webView == null) return;
        String js = "(function(){" +
            "var U='" + jsEscape(USERNAME) + "',P='" + jsEscape(PASSWORD) + "';" +
            "function fire(e){try{e.dispatchEvent(new Event('input',{bubbles:true}));e.dispatchEvent(new Event('change',{bubbles:true}));}catch(x){}}" +
            "function score(el,kind){var s=((el.name||'')+' '+(el.id||'')+' '+(el.placeholder||'')+' '+(el.getAttribute('aria-label')||'')).toLowerCase();" +
            "if(kind==='p') return (el.type==='password'||s.indexOf('pass')>=0)?10:0;" +
            "return (s.indexOf('user')>=0||s.indexOf('login')>=0||s.indexOf('account')>=0)?10:(el.type==='text'?2:0);}" +
            "var ins=[].slice.call(document.querySelectorAll('input'));" +
            "var ps=ins.filter(function(e){return score(e,'p')>0;});" +
            "var us=ins.filter(function(e){return score(e,'u')>0;});" +
            "if(!us.length) us=ins.filter(function(e){return e.type==='text'||e.type==='email';});" +
            "if(us.length){us.sort(function(a,b){return score(b,'u')-score(a,'u')});us[0].value=U;fire(us[0]);}" +
            "if(ps.length){ps[0].value=P;fire(ps[0]);}" +
            "var els=[].slice.call(document.querySelectorAll('button,input[type=submit],input[type=button],a'));" +
            "els.sort(function(a,b){var A=((a.innerText||a.value||a.title||'')+' '+(a.id||'')+' '+(a.name||'')).toLowerCase(),B=((b.innerText||b.value||b.title||'')+' '+(b.id||'')+' '+(b.name||'')).toLowerCase();function z(x){return (x.indexOf('login')>=0||x.indexOf('log in')>=0||x.indexOf('sign in')>=0||x.indexOf('masuk')>=0)?10:0}return z(B)-z(A)});" +
            "var b=els.find(function(e){var x=((e.innerText||e.value||e.title||'')+' '+(e.id||'')+' '+(e.name||'')).toLowerCase();return x.indexOf('login')>=0||x.indexOf('log in')>=0||x.indexOf('sign in')>=0||x.indexOf('masuk')>=0;});" +
            "if(b){setTimeout(function(){b.click();},250);}" +
            "})();";
        webView.evaluateJavascript(js, null);
    }

    private static String jsEscape(String s) { return s.replace("\\", "\\\\").replace("'", "\\'"); }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }

    @Override protected void onDestroy() {
        if (webView != null) { webView.stopLoading(); webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
