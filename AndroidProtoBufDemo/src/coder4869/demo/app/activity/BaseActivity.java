package coder4869.demo.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	private Toast toast = null;
    protected Activity activity = this;
    protected OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityManager.addActivity(this);
		initOnClickListener(); 
		doOnCreate(savedInstanceState);
    }
    
    protected abstract void doOnCreate(Bundle savedInstanceState);

    /**
     * this method call onClickListenerActions(int); method and 
     * must be called before doOnCreate(Bundle); method
     */
    protected void initOnClickListener() {
    	listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickListenerActions(v.getId());				
			}
		};
    };
    
    protected void onClickListenerActions(int viewId) { }

    /**
     * toast is something like alert view
     * avoid toast always showing on the screen
     * @param msg 	 message to show
     * @param isLong whether to showing with long time or not
     */
    protected void showToast(String msg, boolean isLong) {
    	ToastManager.showToast(toast, getApplicationContext(), msg, isLong);
    }
}
