package com.t3hh4xx0r.lifelock;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

public class SettingsProvider {

	Context ctx;

	public SettingsProvider(Context ctx) {
		this.ctx = ctx;
	}

	public static int getAppVersionCode(Context c) {
		try {
			PackageInfo packageInfo = c.getPackageManager().getPackageInfo(
					c.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public boolean isFirstLaunchForVersion() {
		boolean isInPrefs = PreferenceManager.getDefaultSharedPreferences(ctx)
				.getBoolean("seen_" + getAppVersionCode(ctx), false);
		if (!isInPrefs) {
			PreferenceManager.getDefaultSharedPreferences(ctx).edit()
					.putBoolean("seen_" + getAppVersionCode(ctx), true).apply();
		}
		return !isInPrefs;
	}

	public boolean isFirstLaunchEver() {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getAll()
				.isEmpty();
	}

	public boolean didSaveUserStats() {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
				"didSave", false);
	}

	public boolean shouldSaveUserStats() {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
				"shouldSave", true);
	}

	public void setShouldSaveUserStats(boolean shouldSave) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit()
				.putBoolean("shouldSave", false).apply();
	}

}
