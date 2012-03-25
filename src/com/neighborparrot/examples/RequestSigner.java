package com.neighborparrot.examples;

import com.neighborparrot.ConnectionRequest;

public class RequestSigner {
	
	public static String getSignedRequest(ConnectionRequest request, String api_id, String api_key)	{
		request.sign(api_id, api_key);
		return request.toString();		
	}
	
	private static final String API_ID="d95601c3-d9bf-4de3-9791-ad46d133229d";
	private static final String API_KEY="f8530868-9279-4eef-a690-709c84fcee13";
	
	/**
	 * Main
	 * Sign one request 
	 * @param args
	 */
	public static void main(String[] args) {		
		ConnectionRequest request = new ConnectionRequest("test", ConnectionRequest.SERVICE_EVENTSOURCE); 
		System.out.println(RequestSigner.getSignedRequest(request, API_ID, API_KEY));
	}	
}
