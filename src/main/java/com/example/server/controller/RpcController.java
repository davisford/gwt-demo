/**
 * 
 */
package com.example.server.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.example.server.service.AbstractService;
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
		// delegate, will eventually call into processCall below
		super.doPost(request, response);
		
		// inject the session into the service class
		((AbstractService) service).setHttpSession(request.getSession());
		
		// no need for ModelAndView
		return null;
	}
	
	@Override
	public String processCall(String content) throws SerializationException {
		try {
			// decode the request
			RPCRequest rpcRequest = RPC.decodeRequest(content, this.serviceClass);
			// TODO: check security here
			// delegate work to the spring injected service
			return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
		} catch (IncompatibleRemoteServiceException ex) {
			getServletContext().log("An IncompatibleRemoteServiceException was thrown while processing this call.",	ex);
			return RPC.encodeResponseForFailure(null, ex);
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
