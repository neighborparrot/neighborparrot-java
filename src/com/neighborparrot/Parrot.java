package com.neighborparrot;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;

import com.neighborparrot.internal.bridge.WebSocketBridge;

public class Parrot  {
	
	private WebSocketBridge bridge;
	
	public Parrot(String channel, String api_id, String api_key) {
		this(new ConnectionRequest("test"), api_id, api_key);
	}
	
	public Parrot(ConnectionRequest request, String api_id, String api_key) {
		this(ParrotSigner.signConnectRequest(request, api_id, api_key));
	}
	
	public Parrot(URI connection_uri) {
		bridge = new WebSocketBridge(connection_uri);
	}
	
	/**
	 * @param listener
	 * @see com.neighborparrot.internal.bridge.WebSocketBridge#addParrotListener(com.neighborparrot.ParrotListener)
	 */
	public void addParrotListener(ParrotListener listener) {
		bridge.addParrotListener(listener);
	}

	/**
	 * @param listener
	 * @see com.neighborparrot.internal.bridge.WebSocketBridge#removeParrotListener(com.neighborparrot.ParrotListener)
	 */
	public void removeParrotListener(ParrotListener listener) {
		bridge.removeParrotListener(listener);
	}

	/**
	 * 
	 * @see org.java_websocket.WebSocketClient#connect()
	 */
	public void connect() {
		bridge.connect();
	}

	/**
	 * 
	 * @see org.java_websocket.WebSocketClient#close()
	 */
	public void close() {
		bridge.close();
	}

	/**
	 * @param text
	 * @throws NotYetConnectedException
	 * @throws InterruptedException
	 * @see org.java_websocket.WebSocketClient#send(java.lang.String)
	 */
	public void send(String text) {
		try {
			bridge.send(text);
		} catch (NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param text
	 * @throws NotYetConnectedException
	 * @throws InterruptedException
	 * @see org.java_websocket.WebSocketClient#send(java.lang.String)
	 */
	public void sendEx(String text) throws NotYetConnectedException, InterruptedException {
		bridge.send(text);
	}
}
