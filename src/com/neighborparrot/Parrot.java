package com.neighborparrot;

import java.nio.channels.NotYetConnectedException;

import com.neighborparrot.internal.bridge.WebSocketBridge;

/**
 * Java driver for the Neighborparrot Service http://nbparrot.com
 * 
 * This driver only implement websocket connections.
 * 
 * @author Eloy Gomez
 * 
 */
public class Parrot {

	private WebSocketBridge bridge;

	/**
	 * Neighborparrot constructor, create a new WebSocket ready for receive the
	 * connect order. This contructor sign the request and prepare a websocket
	 * connection without presence information.
	 * 
	 * @param channel
	 * @param api_id
	 * @param api_key
	 */
	public Parrot(String channel, String api_id, String api_key) {
		this(new ConnectionRequest(channel), api_id, api_key);
	}

	/**
	 * Neighborparrot constructor, create a new WebSocket ready for receive the
	 * connect order. This constructor take a ConnectionRequest with yor values
	 * for channel, presence and other options.
	 * 
	 * @param request
	 * @param api_id
	 * @param api_key
	 */
	public Parrot(ConnectionRequest request, String api_id, String api_key) {
		request.sign(api_id, api_key);		
		this.bridge = new WebSocketBridge(request.toURI());
	}

	/**
	 * Add a ParrotListener Each listener receive the websockets events
	 * 
	 * @param listener
	 */
	public void addParrotListener(ParrotListener listener) {
		bridge.addParrotListener(listener);
	}

	/**
	 * Remove a parrot listener
	 * 
	 * @param listener
	 */
	public void removeParrotListener(ParrotListener listener) {
		bridge.removeParrotListener(listener);
	}

	/**
	 * Open websocket connection
	 */
	public void connect() {
		bridge.connect();
	}

	/**
	 * Close websocket connection
	 */
	public void close() {
		bridge.close();
	}

	/**
	 * Send a message to the connected channel trigger onError if fail
	 * 
	 * @param message
	 */
	public void send(String message) {
		try {
			bridge.send(message);
		} catch (Exception e) {
			bridge.onError(e);
		}
	}

	/**
	 * Send a message to the connected channel and throw exceptions on errors.
	 * 
	 * @param message
	 * @throws NotYetConnectedException
	 * @throws InterruptedException
	 */
	public void sendEx(String message) throws NotYetConnectedException,	InterruptedException {
		bridge.send(message);
	}

}
