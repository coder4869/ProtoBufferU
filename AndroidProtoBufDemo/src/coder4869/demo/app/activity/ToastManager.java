package coder4869.demo.app.activity;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {
    /**
     * toast is something like alert view
     * avoid toast always showing on the screen
     * @param msg 	 message to show
     * @param isLong whether to showing with long time or not
     */
    public static void showToast(Toast toast, Context ctx, String msg, boolean isLong) {
        if (toast == null) {
            if (isLong) {
                toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
            }else{
                toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
            }
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
