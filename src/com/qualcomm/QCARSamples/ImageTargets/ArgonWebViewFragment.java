package com.qualcomm.QCARSamples.ImageTargets;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("NewApi")
public class ArgonWebViewFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_argonwebview, container,
				false);

		// String currentUrl = "http://threejs.org/examples/css3d_periodictable.html";
		// String currentUrl = "http://davidwalsh.name/demo/css-cube.php";
		// String currentUrl = "http://54.200.170.80:9000/";
		String currentUrl = "http://threejs.org/examples/#webgl_geometry_colors";
		// set up web view
		WebView mWebView = (WebView) v.findViewById(R.id.webPage);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setBackgroundColor(Color.TRANSPARENT);
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.loadUrl(currentUrl);

		return v;
	}
}
