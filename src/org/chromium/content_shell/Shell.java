// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.content_shell;

import org.chromium.base.CalledByNative;
import org.chromium.base.JNINamespace;
import org.chromium.content.browser.ContentView;
import org.chromium.content.browser.ContentViewRenderView;
import org.chromium.content.browser.LoadUrlParams;
import org.chromium.ui.WindowAndroid;

import com.qualcomm.QCARSamples.ImageTargets.R;
import com.qualcomm.QCARSamples.ImageTargets.R.id;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Container for the various UI components that make up a shell window.
 */
@JNINamespace("content")
public class Shell extends LinearLayout {

	private ContentView mContentView;
	private ContentViewRenderView mContentViewRenderView;
	private WindowAndroid mWindow;

	/**
	 * Constructor for inflating via XML.
	 */
	public Shell(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Set the SurfaceView being renderered to as soon as it is available.
	 */
	public void setContentViewRenderView(
			ContentViewRenderView contentViewRenderView) {
		FrameLayout contentViewHolder = (FrameLayout) findViewById(R.id.contentview_holder);
		if (contentViewRenderView == null) {
			if (mContentViewRenderView != null) {
				contentViewHolder.removeView(mContentViewRenderView);
			}
		} else {
			contentViewHolder.addView(contentViewRenderView,
					new FrameLayout.LayoutParams(
							FrameLayout.LayoutParams.MATCH_PARENT,
							FrameLayout.LayoutParams.MATCH_PARENT));
		}
		mContentViewRenderView = contentViewRenderView;
	}

	/**
	 * @param window
	 *            The owning window for this shell.
	 */
	public void setWindow(WindowAndroid window) {
		mWindow = window;
	}

	/**
	 * Loads an URL. This will perform minimal amounts of sanitizing of the URL
	 * to attempt to make it valid.
	 * 
	 * @param url
	 *            The URL to be loaded by the shell.
	 */
	public void loadUrl(String url) {
		mContentView.loadUrl(new LoadUrlParams(ShellManager.DEFAULT_SHELL_URL));
	}


	@SuppressWarnings("unused")
	@CalledByNative
	private void onUpdateUrl(String url) {
	}

	@SuppressWarnings("unused")
	@CalledByNative
	private void onLoadProgressChanged(double progress) {
	}

	@CalledByNative
	private void toggleFullscreenModeForTab(boolean enterFullscreen) {
	}

	@CalledByNative
	private boolean isFullscreenForTabOrPending() {
		return false;
	}

	@SuppressWarnings("unused")
	@CalledByNative
	private void setIsLoading(boolean loading) {
	}

	/**
	 * Initializes the ContentView based on the native tab contents pointer
	 * passed in.
	 * 
	 * @param nativeTabContents
	 *            The pointer to the native tab contents object.
	 */
	@SuppressWarnings("unused")
	@CalledByNative
	private void initFromNativeTabContents(int nativeTabContents) {
		mContentView = ContentView.newInstance(getContext(), nativeTabContents,
				mWindow);
		((FrameLayout) findViewById(R.id.contentview_holder)).addView(
				mContentView, new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
		mContentView.requestFocus();
		mContentViewRenderView.setCurrentContentView(mContentView);
	}

	/**
	 * @return The {@link ContentView} currently shown by this Shell.
	 */
	public ContentView getContentView() {
		return mContentView;
	}

}
