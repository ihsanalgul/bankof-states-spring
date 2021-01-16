package com.bank.dao;

import lombok.Data;
/*
 * Data Access Object is used for data flow to ui
 */
@Data
public class UserDAO {
	private Long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Boolean isAdmin;
}