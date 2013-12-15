// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package com.qualcomm.QCARSamples.ImageTargets;

import org.chromium.content.app.LibraryLoader;
import org.chromium.content.browser.BrowserStartupController;
import org.chromium.content.browser.ContentView;
import org.chromium.content.browser.DeviceUtils;
import org.chromium.content.common.CommandLine;
import org.chromium.content.common.ProcessInitException;
import org.chromium.content_shell.Shell;
import org.chromium.content_shell.ShellManager;
import org.chromium.ui.WindowAndroid;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Activity for managing the Content Shell.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ContentShellFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public static final String COMMAND_LINE_FILE = "/data/local/tmp/content-shell-command-line";
	private static final String TAG = "ContentShellActivity";

	private static final String ACTIVE_SHELL_URL_KEY = "activeUrl";
	public static final String COMMAND_LINE_ARGS_KEY = "commandLineArgs";
	private static final String ACTION_START_TRACE = "org.chromium.content_shell.action.PROFILE_START";
	private static final String ACTION_STOP_TRACE = "org.chromium.content_shell.action.PROFILE_STOP";

	private ShellManager mShellManager;
	private WindowAndroid mWindowAndroid;
	private BroadcastReceiver mReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {


		// Initializing the command line must occur before loading the library.
		if (!CommandLine.isInitialized()) {
			CommandLine.initFromFile(COMMAND_LINE_FILE);
		}

		DeviceUtils.addDeviceSpecificUserAgentSwitch(getActivity());
		try {
			LibraryLoader.ensureInitialized();
		} catch (ProcessInitException e) {
			Log.e(TAG, "ContentView initialization failed.", e);
		}

		View v = inflater.inflate(R.layout.content_shell_activity, container,
				false);
		
//		setContentView(R.layout.content_shell_activity);
		mShellManager = (ShellManager) v.findViewById(R.id.shell_container);
		mWindowAndroid = new WindowAndroid(getActivity());
		mWindowAndroid.restoreInstanceState(savedInstanceState);
		mShellManager.setWindow(mWindowAndroid);
		BrowserStartupController.get(getActivity()).startBrowserProcessesAsync(
				new BrowserStartupController.StartupCallback() {
					@Override
					public void onSuccess(boolean alreadyStarted) {
						finishInitialization(savedInstanceState);
					}

					@Override
					public void onFailure() {
						initializationFailed();
					}
				});
		return v;
	}

	private void finishInitialization(Bundle savedInstanceState) {
		String shellUrl = ShellManager.DEFAULT_SHELL_URL;
		mShellManager.launchShell(shellUrl);
	}

	private void initializationFailed() {
		Log.e(TAG, "ContentView initialization failed.");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Shell activeShell = getActiveShell();
		if (activeShell != null) {
			outState.putString(ACTIVE_SHELL_URL_KEY,
					ShellManager.DEFAULT_SHELL_URL);
		}

		mWindowAndroid.saveInstanceState(outState);
	}

	@Override
	public void onPause() {
		ContentView view = getActiveContentView();
		if (view != null)
			view.onActivityPause();

		super.onPause();
		getActivity().unregisterReceiver(mReceiver);
	}
	
	@SuppressLint("NewApi")
	@TargetApi(9)
	@Override
	public void onResume() {
		super.onResume();

		ContentView view = getActiveContentView();
		if (view != null)
			view.onActivityResume();
		IntentFilter intentFilter = new IntentFilter(ACTION_START_TRACE);
		intentFilter.addAction(ACTION_STOP_TRACE);
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				String extra = intent.getStringExtra("file");
			}
		};
		getActivity().registerReceiver(mReceiver, intentFilter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mWindowAndroid.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @return The {@link ShellManager} configured for the activity or null if
	 *         it has not been created yet.
	 */
	public ShellManager getShellManager() {
		return mShellManager;
	}

	/**
	 * @return The currently visible {@link Shell} or null if one is not
	 *         showing.
	 */
	public Shell getActiveShell() {
		return mShellManager != null ? mShellManager.getActiveShell() : null;
	}

	/**
	 * @return The {@link ContentView} owned by the currently visible
	 *         {@link Shell} or null if one is not showing.
	 */
	public ContentView getActiveContentView() {
		Shell shell = getActiveShell();
		return shell != null ? shell.getContentView() : null;
	}
}
