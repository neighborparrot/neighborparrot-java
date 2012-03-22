package com.neighborparrot;

/**
 * ParrotListener is the interface for 
 * Neighborparrot server events
 * 
 * @author Eloy Gomez
 *
 */
public interface ParrotListener {
	/**
	 * Triggered when open connection
	 */
	public void onOpen();
	
	/**
	 * Triggered when the parrot receive a message
	 * @param message
	 */
	public void onMessage(String message);
	
	/**
	 * Triggered when something was wrong 
	 * @param ex
	 */
	public void onError(Exception ex);
	
	/**
	 * Triggered when close the connection
	 * @param code status code
	 * @param reason Closes description
	 * @param remote true if the server close the connection
	 */
	public void onClose(int code, String reason, boolean remote);

}
