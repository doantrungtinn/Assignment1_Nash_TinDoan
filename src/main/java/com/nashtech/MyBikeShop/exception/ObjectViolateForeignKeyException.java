package com.nashtech.MyBikeShop.exception;

public class ObjectViolateForeignKeyException extends RuntimeException {
	private static final long serialVersionUID = 1947077612237154249L;
	private String message;
	
	public ObjectViolateForeignKeyException() {
		super();
	}
	
	public ObjectViolateForeignKeyException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

