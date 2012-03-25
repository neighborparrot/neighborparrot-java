package com.neighborparrot;

import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ConnectionRequest {

	private HashMap<String, String> params = new HashMap<String, String>();

	private static final String CHANNEL = "channel";
	private static final String SERVICE = "service";
	private static final String PRESENCE_USER_ID = "presence_user_id";
	private static final String PRESENCE_DATA = "presence_data";
	private static final String PRESENCE_INVISIBLE = "presence_invisible";

	public static final String SERVICE_EVENTSOURCE = "es";
	public static final String SERVICE_WEBSOCKET = "ws";
	private static final String DEFAULT_SERVICE = SERVICE_WEBSOCKET;
	
	private static final String WS_ENDPOINT = "/ws";
	private static final String ES_ENDPOINT = "/open";

	private String NEIGHBOPARROT_SERVER = "neighborparrot.net";

	private static final String SIGNATURE_PREFIX = "GET\n" + WS_ENDPOINT + "\n";

	private static final String AUTH_VERSION = "auth_version";
	private static final String AUTH_KEY = "auth_key";
	private static final String AUTH_TIMESTAMP = "auth_timestamp";
	private static final String AUTH_SIGNATURE = "auth_signature";

	private static final String CURRENT_AUTH_VERSION = "1.0";

	/**
	 * @param channel
	 */
	public ConnectionRequest(String channel) {
		params.put(CHANNEL, channel);
		params.put(SERVICE, DEFAULT_SERVICE);
	}

	/**
	 * @param channel
	 * @param service
	 */
	public ConnectionRequest(String channel, String service) {
		params.put(CHANNEL, channel);
		params.put(SERVICE, service);
	}

	/**
	 * @param channel
	 * @param service
	 * @param presence_user_id
	 * @param presence_data
	 */
	public ConnectionRequest(String channel, String service,
			String presence_user_id, String presence_data) {
		params.put(CHANNEL, channel);
		params.put(SERVICE, service);
		params.put(PRESENCE_USER_ID, presence_user_id);
		params.put(PRESENCE_DATA, presence_data);
	}

	/**
	 * @param channel
	 * @param presence_user_id
	 * @param presence_data
	 */
	public ConnectionRequest(String channel, String service, String presence_user_id,
			String presence_data, boolean presence_invisible) {
		params.put(CHANNEL, channel);
		params.put(SERVICE, service);
		params.put(PRESENCE_USER_ID, presence_user_id);
		params.put(PRESENCE_DATA, presence_data);
		params.put(PRESENCE_INVISIBLE, presence_invisible + "");
	}

	/**
	 * Sign the request with given api_id and api_key
	 * 
	 * @param api_id
	 * @param api_key
	 */
	public void sign(String api_id, String api_key) {
		params.put(AUTH_VERSION, CURRENT_AUTH_VERSION);
		params.put(AUTH_TIMESTAMP, new Date().getTime() + "");
		params.put(AUTH_KEY, api_id);

		Object[] keys = params.keySet().toArray();
		ArrayList<String> items = new ArrayList<String>();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++) {
			items.add(keys[i] + "=" + params.get(keys[i]));
		}

		String to_sign = SIGNATURE_PREFIX + join(items, "&");
		String signature = signString(to_sign, api_key);
		params.put(AUTH_SIGNATURE, signature);

	}
	
	public HashMap<String, String> toHasMap()	{
		return params;
	}

	
	/**
	 * Return a JSON represention for the request 
	 */
	@Override
	public String toString() {
		ArrayList<String> items = new ArrayList<String>();
		for (String key : params.keySet()) {
			items.add("\"" + key + "\":\"" + params.get(key) + "\"");
		}
		return "{" + join(items, ",") + "}";
	}

	/**
	 * Return the URI for this request
	 * @return
	 */
	public URI toURI() {
		ArrayList<String> items = new ArrayList<String>();
		
		try {
			for (String key : params.keySet()) {
				String value = URLEncoder.encode(params.get(key), "UTF-8"); 
				items.add(key + "=" + value);
			}
			
			String params_url = join(items, "&");
			if (SERVICE_WEBSOCKET.equals(params.get(SERVICE)))	{
				return URI.create("wss://" + NEIGHBOPARROT_SERVER + WS_ENDPOINT +"?" + params_url);
			}
			else {
				return URI.create("https://" + NEIGHBOPARROT_SERVER + "/"+ ES_ENDPOINT+"?" + params_url);
			}			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Array join helper.. Ruby, I miss you...
	 * 
	 * @param items
	 * @param d
	 * @return
	 */
	private static String join(ArrayList<String> items, String d) {
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < (items.size() - 1); i++)
			sb.append(items.get(i) + d);
		return sb.toString() + items.get(i);
	}

	/**
	 * Returns a HMAC/SHA256 representation of the given string
	 * 
	 * @param data
	 * @return
	 */
	private static String signString(String data, String key) {
		try {
			// Create the message authentication code (MAC)
			final Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));

			byte[] digest = mac.doFinal(data.getBytes("UTF-8"));
			BigInteger bigInteger = new BigInteger(1, digest);
			return String.format("%0" + (digest.length << 1) + "x", bigInteger);
		} catch (Exception e) {
			throw new RuntimeException("Sign error", e);
		}
	}
	
	/**
	 * For internal use only
	 * Override the Neighborparrot server
	 * 
	 * @param server
	 */
	public void overrideServer(String server)	{
		NEIGHBOPARROT_SERVER = server;
	}
}
