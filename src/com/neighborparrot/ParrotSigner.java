package com.neighborparrot;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Parrot Signer allow you sign request 
 * for your clients.
 * Currently, only websockets connections are supported. 
 * 
 * @author Eloy Gomez
 *
 */
public class ParrotSigner {
	
	private static final String WS_ENDPOINT = "/ws";
	private static final String SIGNATURE_PREFIX = "GET\n"+ WS_ENDPOINT +"\n";
	
	private static final String AUTH_VERSION = "auth_version";
	private static final String AUTH_KEY = "auth_key";
	private static final String AUTH_TIMESTAMP = "auth_timestamp";
	private static final String AUTH_SIGNATURE = "auth_signature";
	
	private static final String CURRENT_AUTH_VERSION = "1.0";
	

	/**
	 * Sign a connect request and return a valid URI 
	 * ready for use in with any Neighborparrot driver.
	 * @param request
	 * @param api_id
	 * @param api_key
	 * @return ready to use signed request
	 */
	public static URI signConnectRequest(ConnectionRequest request, String api_id, String api_key)	{
		HashMap<String, String> params = request.getParams();
		
		params.put(AUTH_VERSION, CURRENT_AUTH_VERSION);
		params.put(AUTH_TIMESTAMP, new Date().getTime()+ "");
		params.put(AUTH_KEY, api_id);
		
		 
		 Object[] keys = params.keySet().toArray();
		 ArrayList<String> items = new ArrayList<String>();
         Arrays.sort(keys);
         for (int i=0; i < keys.length; i++)	{
        	 items.add( keys[i] + "=" + params.get(keys[i]));
         }
         String to_sign = SIGNATURE_PREFIX + join(items, "&");
         String signature =  signString(to_sign, api_key);
         
         items.add(AUTH_SIGNATURE+"="+signature);
         String params_url = join(items, "&");
         return URI.create("wss://neighborparrot.net/ws?" + params_url);          
	}
	
	/**
	 * Array join helper..
	 * Ruby, I miss you...
	 * @param items
	 * @param d
	 * @return
	 */
	private static String join(ArrayList<String> items, String d) {
	        StringBuilder sb = new StringBuilder();
	        int i;
	        for(i=0; i < (items.size() - 1); i++)
	            sb.append(items.get(i)+d);
	        return sb.toString()+items.get(i);
	}	
	
	/**
	 * Returns a HMAC/SHA256 representation of the given string
	 * @param data
	 * @return
	 */
    private static String signString(String data, String key) {
        try {
            // Create the message authentication code (MAC)
            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec( key.getBytes(), "HmacSHA256"));
    
            byte[] digest = mac.doFinal(data.getBytes("UTF-8"));            
            BigInteger bigInteger = new BigInteger(1,digest);
    		return String.format("%0" + (digest.length << 1) + "x", bigInteger);            
        } catch (Exception e) {
        	throw new RuntimeException("Sign error", e);
        } 
    }	

}
