package com.neighborparrot;

public interface ParrotListener {
	
	public void onConnect();
	
	public void onMessage(String data);
	
	public void onError(Exception ex);
	
	public void onClose(int code, String reason, boolean remote);

}
