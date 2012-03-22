package com.neighborparrot.internal.bridge;

import java.net.URI;
import java.util.ArrayList;

import org.java_websocket.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.neighborparrot.ParrotListener;

/**
 * Bridge between Neighbporparrot service 
 * and the websocket low level driver 
 * 
 * @author Eloy Gomez
 *
 */
public class WebSocketBridge extends WebSocketClient {

	private ArrayList<ParrotListener> parrotListeners = new ArrayList<ParrotListener>();
	
	public WebSocketBridge(URI serverURI) {
		super(serverURI);
	}

	public void addParrotListener(ParrotListener listener)	{
		parrotListeners.add(listener);
	}
	
	public void removeParrotListener(ParrotListener listener)	{
		parrotListeners.remove(listener);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {	
		for (ParrotListener listener : parrotListeners)	{
			listener.onOpen();
		}
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote) {	
		for (ParrotListener listener : parrotListeners)	{
			listener.onClose(code, reason, remote);
		}
	}

	@Override
	public void onError(Exception ex) {		
		for (ParrotListener listener : parrotListeners)	{
			listener.onError(ex);
		}
	}
	
	@Override
	public void onMessage(String message) {
		for (ParrotListener listener : parrotListeners)	{
			listener.onMessage(message);
		}
	}
}
