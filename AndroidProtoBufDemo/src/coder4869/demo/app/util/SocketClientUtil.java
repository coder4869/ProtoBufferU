package coder4869.demo.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import coder4869.demo.app.protobuf.UserInfoConvert;


public class SocketClientUtil {
	    
	private static Socket mSocket;  
    protected static BufferedReader mReader; // for pushing message
    protected static BufferedWriter mWriter; // for receiving message
    	
    private static SocketHandler mSocketHandler = new SocketHandler();;  

	public static void startSocketRequest() throws UnknownHostException, IOException {


		Thread socketThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
                	while (true) {
    					mSocket = new Socket("127.0.0.1", 3030);
                    	Log.i("SocketClientUtil", "Client Start Sending Request");
						mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));

						byte[] msgBody = UserInfoConvert.getRequestInfo();
						if(msgBody.length != 0) {
    				        int headerLen = 1;
    				        byte[] message = new byte[headerLen+msgBody.length];
    				        message[0] = (byte)msgBody.length;
    				        System.arraycopy(msgBody, 0, message, 1, msgBody.length);
    				        System.out.println("msg len:"+message.length);
    				        mSocket.getOutputStream().write(message);
    				        mSocket.getOutputStream().flush();
    				        
    				        //parse response
    			            byte respBuf[] = new byte[1024]; 
    			            mSocket.getInputStream().read(respBuf); 
    			            int respBodyLen = (int)respBuf[0];
    			            System.out.println("RESPONSE DATA LENGTH " + respBodyLen); 	
    				        byte[] respMsg = new byte[respBodyLen];
    				        System.arraycopy(respBuf, 1, respMsg, 0, respBodyLen);
    				        UserInfoConvert.parseProtoBufResponseInfo(respMsg);
						}
    					mSocket.close();
				        Thread.sleep(5000); //retry each 10s
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		});
		socketThread.start();
	}
	
    static class SocketHandler extends Handler {  
        
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case 0:  
                try {  
                	Log.i("######### RESPONSE DATA #########", (String) msg.obj);
			        UserInfoConvert.parseProtoBufResponseInfo(((String)msg.obj).getBytes());
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                  
                break;  
  
            default:  
                break;  
            }  
        }  
    }

}
