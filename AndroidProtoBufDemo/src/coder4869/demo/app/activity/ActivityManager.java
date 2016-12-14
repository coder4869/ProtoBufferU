package coder4869.demo.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class ActivityManager {
	
	private static final String TAG = ActivityManager.class.getName();
    public static final List<Activity> activityList = new ArrayList<Activity>();
    
	public static void addActivity(Activity activity) {
		if (null != activity) {
			activityList.add(activity);
		}
	}
	
	/**
	 * @Description: finish one activity
	 * @param activity
	 */
	public static void finishActivity(Activity activity) {
		if (activity != null) {
			activityList.remove(activity);
			Log.d(TAG, "one activity finished:" + activity);
			activity.finish();
		}
	}
	
    // exit client by closing all Activities
    public static void finishAllActivities() {   
		for (Activity activity : activityList) {
			if (activity != null) {
				activity.finish();
			}
		}
		Log.d(TAG, "all activities finished");
		activityList.clear();
    }
    
    public static void startActivity(Activity activity_one , Class<? extends Activity> target) {
        Intent intent = new Intent();
        intent.setClass(activity_one, target);
        activity_one.startActivity(intent);
    }
    
}
