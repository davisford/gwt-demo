/**
 * 
 */
package com.example.client.cookies;

import java.util.Collection;
import java.util.Date;

/**
 * Implementation of {@link Cookies} that wraps
 * {@link com.google.gwt.user.client.Cookies}
 */
public class DefaultCookies implements Cookies {

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#getCookie(java.lang.String)
	 */
	@Override
	public String getCookie(String name) {
		return com.google.gwt.user.client.Cookies.getCookie(name);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#getCookieNames()
	 */
	@Override
	public Collection<String> getCookieNames() {
		return com.google.gwt.user.client.Cookies.getCookieNames();
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#getUriEncode()
	 */
	@Override
	public boolean getUriEncode() {
		return com.google.gwt.user.client.Cookies.getUriEncode();
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#isCookieEnabled()
	 */
	@Override
	public boolean isCookieEnabled() {
		return com.google.gwt.user.client.Cookies.isCookieEnabled();
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#removeCookieNative(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeCookieNative(String name, String path) {
		com.google.gwt.user.client.Cookies.removeCookieNative(name, path);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#removeCookies(java.lang.String)
	 */
	@Override
	public void removeCookies(String name) {
		com.google.gwt.user.client.Cookies.removeCookie(name);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#removeCookies(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeCookies(String name, String path) {
		com.google.gwt.user.client.Cookies.removeCookie(name, path);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#setCookie(java.lang.String, java.lang.String)
	 */
	@Override
	public void setCookie(String name, String value) {
		com.google.gwt.user.client.Cookies.setCookie(name, value);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#setCookie(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public void setCookie(String name, String value, Date expires) {
		com.google.gwt.user.client.Cookies.setCookie(name, value, expires);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#setCookie(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void setCookie(String name, String value, Date expires, String domain, String path, boolean secure) {
		com.google.gwt.user.client.Cookies.setCookie(name, value, expires, domain, path, secure);
	}

	/* (non-Javadoc)
	 * @see com.example.client.cookies.Cookies#setUriEncode(boolean)
	 */
	@Override
	public void setUriEncode(boolean encode) {
		com.google.gwt.user.client.Cookies.setUriEncode(encode);
	}

}
