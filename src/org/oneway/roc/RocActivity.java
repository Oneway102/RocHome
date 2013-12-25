package org.oneway.roc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RocActivity extends Activity {
    
    private static String TAG = "RocHome";
    private WebView mMainView;
    
    static String DEFAULT_HOME_URL = "http://app1.test.com/roc/home.html";
    static String DEFAULT_HOME_URL1 = "http://app1.test.com/roc/th1/app.html";
    static String DEFAULT_HOME_URL2 = "http://app1.test.com/roc/th2/app.html";
    //static String METRO_HOME_URL = "http://192.168.5.187/test/bootmetro/roc.html";
    static String METRO_HOME_URL = "http://oneway.test.com/h5dev/roc/roc.html";

    /** Called when the activity is first created.. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mMainView = new WebView(this);
        initWebView();

        setContentView(mMainView);
        
        //mMainView.loadUrl(DEFAULT_HOME_URL2);
        mMainView.loadUrl(METRO_HOME_URL);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String url = mMainView.getUrl();
            if (url.contains("roc.html")) {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    void initWebView() {
        mMainView.getSettings().setJavaScriptEnabled(true);
        
        // For debug only!
        mMainView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //Injects JavaHelperObject as "androidHelper".
        mMainView.addJavascriptInterface(new JavaHelperObject(), "androidHelper");

        mMainView.setWebChromeClient(mWebChromeClient);
        mMainView.setWebViewClient(mWebViewClient);
        mMainView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition, String mimetype,
                    long contentLength) {
            }
        });

    }

    void switchHome(int id) {
        Log.d(TAG, "switchHome: " + id);
        switch(id) {
        case 1:
            // traditional web style
            mMainView.loadUrl(DEFAULT_HOME_URL2);
            break;
        case 2:
            // metro style
            mMainView.loadUrl(METRO_HOME_URL);
            break;
        case 3:
            // android style
            break;
         default:
             break;
        }

    }

    // -------------------------------------------------------------------------
    // WebViewClient implementation for the main WebView
    // -------------------------------------------------------------------------

    private final WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            ;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.w(TAG, " *** onPageFinished *** ");
        }
        
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading === ");
            //Intent intent = new Intent(url);
            
            return true;
        }
        
        @Override
        public void onLoadResource(WebView view, String url) {
        }
        
        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
        }

        @Override
        public void onFormResubmission(WebView view, final Message dontResend,
                                       final Message resend) {
        }
        
        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                boolean isReload) {
        }

        @Override
        public void onReceivedSslError(final WebView view,
                final SslErrorHandler handler, final SslError error) {
        }
        
        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                final HttpAuthHandler handler, final String host,
                final String realm) {
        }
        
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        }
    };

    // -------------------------------------------------------------------------
    // WebChromeClient implementation for the main WebView
    // -------------------------------------------------------------------------

    private final WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onCreateWindow(WebView view, final boolean dialog,
                final boolean userGesture, final Message resultMsg) {
            return false;
        }
        
        @Override
        public void onRequestFocus(WebView view) { 
        }

        @Override
        public void onCloseWindow(WebView window) {
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, final String title) {
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                boolean precomposed) {
        }
        
        @Override
        public void onShowCustomView(View view,
                WebChromeClient.CustomViewCallback callback) {
        }
        
        @Override
        public void onHideCustomView() {
        }
        
        @Override
        public void onExceededDatabaseQuota(String url,
            String databaseIdentifier, long currentQuota, long estimatedSize,
            long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        }

        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        }
        
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                GeolocationPermissions.Callback callback) {
        }
        
        @Override
        public void onGeolocationPermissionsHidePrompt() {
        }
        
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return false;
        }
        
        @Override
        public Bitmap getDefaultVideoPoster() {
            return null;
        }
        
        @Override
        public View getVideoLoadingProgressView() {
            return null;
        }

        /*
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        }*/
        
        @Override
        public void getVisitedHistory(final ValueCallback<String[]> callback) {
        }
    };
    
    /*
     * A Java class to inject into JavaScript.
     */
    final class JavaHelperObject {
        JavaHelperObject() {
            
        }
        
        /* 
         * Note that this is not called in the main UI thread.
         */
        public void launch(String packageName, String uri) {
            //packageName = "com.anroid.calculator";
            Log.d("JS", "Launching " + packageName);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setPackage(packageName);
            if (uri != null || uri.length() > 0) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
            }
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Failed to launch " + packageName);
            }
        }

        public void launch(String packageName) {
            //packageName = "com.anroid.calculator";
            Log.d("JS", "Launching " + packageName);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setPackage(packageName);
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Failed to launch " + packageName);
            }
        }

        public void launch() {
            launch("com.android.calendar", null);
        }

        public void switchHomeStyle(final int id) {
            
            RocActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    RocActivity.this.switchHome(id);                    
                }
            });
        }
    }
}