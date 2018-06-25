package com.coffeeshop.exception;

import org.springframework.http.HttpStatus;

public class CoffeeShopException extends Exception {
	
	private static final long serialVersionUID = 4664456874499611218L;

	private HttpStatus httpStatusCode;
	
	public CoffeeShopException() {
		super();
    }    
	public CoffeeShopException(HttpStatus httpStatusCode, String message){
        super(message);
        this.httpStatusCode = httpStatusCode;  
	}
	public HttpStatus gethttpStatusCode() {
	   return this.httpStatusCode;
	 }

}
