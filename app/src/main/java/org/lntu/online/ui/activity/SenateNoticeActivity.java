package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.lntu.online.R;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.ShipUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SenateNoticeActivity extends StatusBarActivity implements Toolbar.OnMenuItemClickListener {

    private static final String URL = "http://60.18.131.133:8090/lntu/pub_message/messagesplitepageopenwindow.jsp?fmodulecode=5100&modulecode=5100&messagefid=5100";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.web_view)
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senate_notice);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.senate_notice);
        toolbar.setOnMenuItemClickListener(this);

        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_in_browser:
                ShipUtils.openInBrowser(this, URL);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
