package com.padroes.java.spring.services.exception;

public class SystemPolicyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemPolicyException(String msg) {
		super(msg);
	}
	
}
