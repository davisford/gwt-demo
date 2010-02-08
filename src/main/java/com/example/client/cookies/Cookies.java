/**
 * 
 */
package com.example.client.cookies;

import java.util.Collection;
import java.util.Date;

/**
 * Extracts the interface from {@link com.google.gwt.user.client.Cookies} so the
 * class can be properly mocked.
 */
public interface Cookies {

	/**
	 * Gets the cookie with the specified name
	 * @param name the cookie name
	 * @return the cookie
	 */
	String getCookie(String name);
	
	/**
	 * Gets the names of all cookies in this page's domain
	 * @return
	 */
	Collection<String> getCookieNames();
	
	/**
	 * Gets the URIencode flag
	 * @return
	 */
	boolean getUriEncode();
	
	/**
	 * Checks whether or not cookies are enabled or disabled
	 * @return
	 */
	boolean isCookieEnabled();
	
	/**
	 * Removes the cookie associated with the given name
	 * @param name
	 */
	void removeCookies(String name);
	
	/**
	 * Removes the cookie with the name and path
	 * @param name
	 * @param path
	 */
	void removeCookies(String name, String path);
	
	/**
	 * Native method to remove a cookie with a path
	 * @param name
	 * @param path
	 */
	void removeCookieNative(String name, String path);

	/**
	 * Sets a cookie
	 * @param name
	 * @param value
	 */
	void setCookie(String name, String value);
	
	/**
	 * Sets a cookie
	 * @param name
	 * @param value
	 * @param expires
	 */
	void setCookie(String name, String value, Date expires);
	
	/**
	 * Sets a cookie
	 * @param name
	 * @param value
	 * @param expires
	 * @param domain
	 * @param path
	 * @param secure
	 */
	void setCookie(String name, String value, Date expires, String domain, String path, boolean secure);

	/**
	 * Updates the URIencode flag and empties the cached cookie set
	 * @param encode
	 */
	void setUriEncode(boolean encode);
}
