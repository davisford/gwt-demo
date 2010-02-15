/**
 * 
 */
package com.example.server.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.example.client.exception.SessionTimedOutException;
import com.example.client.service.UserService;
import com.example.server.service.BaseService;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This acts as a front door controller for all GWT RPC services
 */
public class RpcController extends RemoteServiceServlet implements Controller,
		ServletContextAware {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(RpcController.class);
	
	private ServletContext servletContext;
	
	// injected service that handles the call
	private RemoteService service;
	
	// the class of the injected service
	private Class<?> serviceClass;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final HttpSession session = request.getSession(true);
		LOGGER.debug("session id: "+session.getId());
		
		// inject the session into the service class
		((BaseService) service).setHttpSession(session);
		
		// delegate, will eventually call into processCall below
		super.doPost(request, response);
		
		// no need for ModelAndView
		return null;
	}
	
	@Override
	public String processCall(String content) throws SerializationException {
		try {
			// decode the request
			RPCRequest rpcRequest = RPC.decodeRequest(content, this.serviceClass);
			
			// if this is any service other than UserService we check session security here
			if(!(service instanceof UserService)) {
				validateSession(rpcRequest.getParameters());
			}
			// delegate work to the spring injected service
			return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
		} catch (IncompatibleRemoteServiceException ex) {
			getServletContext().log("An IncompatibleRemoteServiceException was thrown while processing this call.",	ex);
			return RPC.encodeResponseForFailure(null, ex);
		} catch (SessionTimedOutException ste) {
			return RPC.encodeResponseForFailure(null, ste);
		}
	}
	
	/**
	 * Validates the session id passed into the service
	 * @param params rpc parameters
	 * @throws SessionTimedOutException in all cases unless the sessionId is valid
	 */
	/* package */ void validateSession(Object[] params) throws SessionTimedOutException {
		if(params != null && params.length > 0 && (params[0] instanceof String)) {
			String s = (String) params[0];
			BaseService bs = (BaseService)service;
			if(bs.isSessionValid(s) == false) {
				throw new SessionTimedOutException();
			}
		} else {
			// default 
			throw new SessionTimedOutException();
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext ctx) {
		servletContext = ctx;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * Inject the POJO that implements the GWT service via Spring
	 * @param clazz
	 */
	public void setService(RemoteService remoteService) {
		this.service = remoteService;
	}

}
