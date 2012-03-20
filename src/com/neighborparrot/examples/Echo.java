package com.neighborparrot.examples;

import com.neighborparrot.Parrot;
import com.neighborparrot.ParrotListener;


public class Echo implements ParrotListener {

	private static final String API_ID="d95601c3-d9bf-4de3-9791-ad46d133229d";
	private static final String API_KEY="f8530868-9279-4eef-a690-709c84fcee13";
	
	private Parrot parrot;
	
	public Echo()	{		
		parrot = new Parrot("test", API_ID, API_KEY);
		parrot.addParrotListener(this);	
		parrot.connect();
	}
	
	public void send(String message)	{
		parrot.send(message);		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Echo echo = new Echo();
		echo.send("Ping");
	}	

	@Override
	public void onConnect() {
		System.out.println("Conectado");		
	}

	private int counter = 0;
	@Override
	public void onMessage(String data) {
		System.out.println(data);
		if (counter++ > 5) {
			return;
		}	
		send("Ping");				
	}

	@Override
	public void onError(Exception ex) {
		System.out.println(ex.getMessage());	
		ex.printStackTrace();	
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("Closed: [" + code + "] " + reason);		
	}	
}
