package com.neighborparrot.examples;

import java.net.URI;

import com.neighborparrot.ConnectionRequest;
import com.neighborparrot.Parrot;
import com.neighborparrot.ParrotListener;
import com.neighborparrot.ParrotSigner;

/**
 * Sample client for the Neighborparrot Service
 * http://nbparrot.com
 *  
 * @author Eloy Gomez
 *
 */
public class Echo implements ParrotListener {

	private static final String API_ID="your_api_id";
	private static final String API_KEY="your_api_key";
	
	private Parrot parrot;
	
	/**
	 * Create the Parrot, add the event listener 
	 * and connect to the server.
	 */
	public Echo()	{		
		// For demostration purpose, we create the request and sign it
		// but the same can be archived with
		// parrot = new Parrot("test", API_ID, API_KEY);
		
		// Create one request with presence information
		// for this exapmple, we connect to channel test with user_id 1 and 
		// a simple description
		ConnectionRequest request = new ConnectionRequest("test", "1", "One little parrot");
		
		// Request should be signed with your keys
		URI signed_request = ParrotSigner.signConnectRequest(request, API_ID, API_KEY);
		
		// With the signed request, create a Parrot
		parrot = new Parrot(signed_request);		
		// Add listeners. The parrot should trigger events 
		// in the listeners when events
		parrot.addParrotListener(this);
		
		// And open the WebSocket connection
		parrot.connect();
	}
	
	/**
	 * Send messages to the connected channel
	 * @param message
	 */
	public void send(String message)	{
		parrot.send(message);		
	}
	
	/**
	 * Main
	 * Create en echo instance and send one message 
	 * in order to start the loop
	 * @param args
	 */
	public static void main(String[] args) {		
		Echo echo = new Echo();
		echo.send("Ping");
	}	

	/**
	 * On open event.
	 * The parrot trigger this event when 
	 * the connection is successful
	 */
	@Override
	public void onOpen() {
		System.out.println("Conectado");		
	}

	private int counter = 0;
	private int MAX_MESSAGES = 5;
	
	/**
	 * onMessage event
	 * The parrot trigger this event 
	 * when receive messages.
	 * The echo implementation will 
	 * send a echo reply MAX_MESSAGES
	 * times.
	 */
	@Override
	public void onMessage(String data) {
		System.out.println(data);
		if (counter++ > MAX_MESSAGES) {
			return;
		}	
		send("Ping");				
	}

	/**
	 * OnError event.
	 * The parrot trigger this event when some 
	 * thing was wrong 
	 */
	@Override
	public void onError(Exception ex) {
		System.out.println(ex.getMessage());	
		ex.printStackTrace();	
	}

	
	/**
	 * OnClose event.
	 * The parrot trigger this event when the connection 
	 * is closed
	 */
	@Override	
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("Closed: [" + code + "] " + reason);		
	}	
}
