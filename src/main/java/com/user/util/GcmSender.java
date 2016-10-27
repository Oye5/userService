package com.user.util;

import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class GcmSender {

	public static final String GCM_API_KEY = "AIzaSyDDC0VG3t9fv6dbhQxJhscrZ19uZCsg6oI";// "AIzaSyB7k-NYr1JfE8FKxJvVJo5Jyseb4_XbaiM";
	public static final String MESSAGE_KEY = "message";

	public void SenderMsg() {
		System.out.println("===================1===");
		MulticastResult result = null;
		try {
			Sender sender = new Sender(GCM_API_KEY);
			ArrayList<String> devicesList = new ArrayList<String>();
			devicesList.add("APA91bEBegIZpKZwmtQAbECdbsfYz1G1I_pGMKhwieDIl6hv1qat5-oywuBClki0eSiWqgJzaA3bhZw4sikqKdvkzh62Yb4zuHHyQ4okldGpdiluzK2q0sOFGpfINmbqcvNy-o200NX2");
			Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, "msg define ").build();
			result = sender.send(message, devicesList, 2); // sender.send(message, devicesList, 0);
			System.out.println("====res===============1===" + result);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Error Message:" + ex.getMessage());
		}
	}

	public static void main(String a[]) {
		GcmSender s = new GcmSender();
		s.SenderMsg();
	}
}