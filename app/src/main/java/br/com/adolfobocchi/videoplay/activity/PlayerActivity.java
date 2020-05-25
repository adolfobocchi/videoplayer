package br.com.adolfobocchi.videoplay.activity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.adolfobocchi.videoplay.R;

public class PlayerActivity  extends AppCompatActivity {

    private VideoView videoView;
    private WebView webview;
    private String urlVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //Configura componentes
        //videoView = findViewById(R.id.playerVideo);
        webview = findViewById(R.id.webview);
        //videoView.setMediaController(new MediaController(this));

        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            urlVideo = bundle.getString("urlVideo");
            //videoView.setVideoURI(Uri.parse(urlVideo));
            //videoView.start();

            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setPluginState(WebSettings.PluginState.ON);
            webview.loadUrl(urlVideo+"?autoplay=1&vq=small" );
            webview.setWebChromeClient(new WebChromeClient());
        }



    }
}
