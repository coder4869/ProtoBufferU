package coder4869.demo.app.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import coder4869.demo.app.protobuf.UserInfo.RequestInfo;
import coder4869.demo.app.protobuf.UserInfo.ResponseInfo;

public class UserInfoConvert {
	
	public static byte [] getRequestInfo() {
		RequestInfo.Builder reqBuilder = RequestInfo.newBuilder();
		reqBuilder.setUserId(2016); //101101201612121234
		return reqBuilder.build().toByteArray();
	}
	
	public static byte [] getResponseInfo() {
		ResponseInfo.Builder respBuilder = ResponseInfo.newBuilder();
		
		respBuilder.setUserId(2016); //101101201612121234
		respBuilder.setUserName("Coder4869");
		respBuilder.setIdNumber("101101201612121234");
		respBuilder.setBirthday("2016.12.12");
		respBuilder.setGender("MALE");
		
		return respBuilder.build().toByteArray();
	}
	
	public static ResponseInfo parseProtoBufResponseInfo(byte [] protoBuf) throws InvalidProtocolBufferException {
		ResponseInfo respInfo = ResponseInfo.parseFrom(protoBuf); //InvalidProtocolBufferException
		
		System.out.println("Name = " + respInfo.getUserName());
		System.out.println("UserID = " + respInfo.getUserId());
		System.out.println("IdNumber = " + respInfo.getIdNumber());
		System.out.println("Gender = " + respInfo.getGender());
		System.out.println("Birthday = " + respInfo.getBirthday());
		
		return respInfo;
	}
	
}
