package coder4869.demo.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;
import coder4869.demo.app.protobuf.UserInfo.ResponseInfo;
import coder4869.demo.app.protobuf.UserInfoConvert;

public class SocketServerUtil {  
  
	private static ServerSocket mServerSocket;
	private volatile static Boolean isReceivingMsg = false;
	private volatile static Boolean isServerRunning = false;
	
	public static void startSocketServer() throws IOException {
		
		Thread serverThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(!isServerRunning) {
						isServerRunning = true;
						mServerSocket = new ServerSocket(3030);
					}
					while (true) {
//						if(!isReceivingMsg) {
//							isReceivingMsg = true;	
							Socket sock = mServerSocket.accept();
							byte[] msg = new byte[1024];
							sock.getInputStream().read(msg);
							if(msg.length > 1) { 
						        System.out.println("Start Receiving");
								int msgBodyLength = msg[0];
								System.out.println("Body Length:" + msgBodyLength);
								byte[] msgbody = new byte[msgBodyLength];
						        System.arraycopy(msg, 1, msgbody, 0, msgBodyLength);
						        UserInfoConvert.parseProtoBufResponseInfo(msgbody);
						        System.out.println("Finish Receiving");
						        
						        //Responsing
						        System.out.println("Start Responsing");
						        byte[] respMsgBody = UserInfoConvert.getResponseInfo();
						        int headerLen = 1;
						        byte[] respMsg = new byte[headerLen + respMsgBody.length];
						        respMsg[0] = (byte)respMsgBody.length;
						        System.arraycopy(respMsgBody, 0, respMsg, 1, respMsgBody.length);
						        System.out.println("response message length:" + respMsg.length);
						        sock.getOutputStream().write(respMsg);
						        sock.getOutputStream().flush();
						        System.out.println("Finish Responsing");
					        }						
					        else 
					        {
						        System.out.println("received empty message body");					        	
					        }
//							isReceivingMsg = false;
//						}
//						else 
//						{
//					        System.out.println("is receiving message");
//						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						mServerSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		serverThread.start();
	}
}
