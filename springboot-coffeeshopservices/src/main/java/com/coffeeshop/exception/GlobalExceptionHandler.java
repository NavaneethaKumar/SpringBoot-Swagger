package com.coffeeshop.exception;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.coffeeshop.domain.OperationOutcome;

/**
 * @author Navaneeth
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<OperationOutcome> handleException(final HttpServletResponse response,
			final MethodArgumentNotValidException ex) {
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		logger.error(HttpStatus.BAD_REQUEST.name(), "Error");
		OperationOutcome outCome = new OperationOutcome(HttpStatus.BAD_REQUEST.name(), "Error", errors);
		return new ResponseEntity<OperationOutcome>(outCome, HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@ExceptionHandler(CoffeeShopException.class)
	private ResponseEntity<OperationOutcome> handleCustomException(final HttpServletResponse response,
			final CoffeeShopException ex) {
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		logger.error(ex.gethttpStatusCode().toString(), "Error");
		OperationOutcome outCome = new OperationOutcome(ex.gethttpStatusCode().toString(), "Error", errors);
		return new ResponseEntity<OperationOutcome>(outCome, ex.gethttpStatusCode());
	}

}
