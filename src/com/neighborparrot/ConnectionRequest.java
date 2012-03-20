package com.neighborparrot;

import java.util.HashMap;

public class ConnectionRequest {
	
	private HashMap<String, String> params = new HashMap<String, String>();
	
	private static final String CHANNEL = "channel";
	private static final String PRESENCE_USER_ID = "presence_user_id";
	private static final String PRESENCE_DATA = "presence_data";
	private static final String PRESENCE_INVISIBLE = "presence_invisible";
	
	
	/**
	 * @param channel
	 */
	public ConnectionRequest(String channel) {
		super();
		params.put(CHANNEL, channel);
	}


	/**
	 * @param channel
	 * @param presence_user_id
	 * @param presence_data
	 */
	public ConnectionRequest(String channel, String presence_user_id, String presence_data) {
		super();
		params.put(CHANNEL, channel);
		params.put(PRESENCE_USER_ID, presence_user_id);
		params.put(PRESENCE_DATA, presence_data);
	}
	
	/**
	 * @param channel
	 * @param presence_user_id
	 * @param presence_data
	 */
	public ConnectionRequest(String channel, String presence_user_id,
			String presence_data, boolean presence_invisible) {
		super();
		params.put(CHANNEL, channel);
		params.put(PRESENCE_USER_ID, presence_user_id);
		params.put(PRESENCE_DATA, presence_data);
		params.put(PRESENCE_INVISIBLE, presence_invisible + "");
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String> getParams() {
		return params;
	}
		
}
