package coder4869.demo.app.activity;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import coder4869.demo.app.protobuf.R;
import coder4869.demo.app.util.SocketClientUtil;
import coder4869.demo.app.util.SocketServerUtil;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends BaseActivity {

	final static int START_NEW_SOCKET = 101;
	private Timer timer = new Timer();

	@Override
	protected void doOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	try {
			SocketServerUtil.startSocketServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

    	timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Log.i("TEST", "sub thread");
				mHandler.sendEmptyMessage(START_NEW_SOCKET);
			}
		}, 1000);
    }
    
	
	@SuppressLint("HandlerLeak") 
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_NEW_SOCKET:
				Log.i("TEST", "œﬂ≥ÃÕ‚++++++++++++++++++++++");

				try {
					SocketClientUtil.startSocketRequest();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			default:
				
				break;
			}
		};
	};

}
