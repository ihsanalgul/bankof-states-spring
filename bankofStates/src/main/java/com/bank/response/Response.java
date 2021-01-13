package com.bank.response;

import lombok.Data;

@Data //@Getter+@Setter+@ToString+@Equals for pojo class
public class Response {
	boolean isSuccessful;
	String message;
}
