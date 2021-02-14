package com.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dao.UserDAO;
import com.bank.model.Recipient;
import com.bank.model.User;
import com.bank.request.RecipientForm;
import com.bank.request.TransactionRequest;
import com.bank.request.TransferRequest;
import com.bank.response.TransactionResponse;
import com.bank.service.AccountService;
import com.bank.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/account") // Ctrl+Shift+O  to import related package
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/deposit")
	private ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest request){
		
		TransactionResponse response = new TransactionResponse();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		accountService.deposit(request, user);
		response.setMessage("Amount succesfully deposited");
		response.setSuccess(true);
		UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
		response.setUser(userDAO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/withdraw")
	private ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest request){
		
		TransactionResponse response = new TransactionResponse();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Account balance check
		if(user!= null && user.getAccount() != null 
				&& user.getAccount().getAccountBalance().doubleValue() >= request.getAmount()) {
			accountService.withdraw(request, user);
			response.setMessage("Amount succesfully withdrawed");
			response.setSuccess(true);
			UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
			response.setUser(userDAO);
		}else {
			response.setMessage("Sorry, you do not have sufficient amount to withdraw");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/addRecipient")
	private ResponseEntity<TransactionResponse> addRecipient(@Valid @RequestBody RecipientForm recipientForm){
		
		TransactionResponse response = new TransactionResponse();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Recipient recipient = new Recipient(recipientForm.getName(), recipientForm.getEmail(),recipientForm.getPhone(),recipientForm.getBankName(),recipientForm.getBankNumber());
		recipient.setUser(user);
		accountService.saveRecipient(recipient);
		response.setMessage("Recipient has been added successfully");
		response.setSuccess(true);
		UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
		response.setUser(userDAO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/transfer")
	private ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request){
		TransactionResponse response = new TransactionResponse();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Account balance check
		if(user!= null && user.getAccount() != null 
				&& user.getAccount().getAccountBalance().doubleValue() >= request.getAmount()) {
			accountService.transfer(request, user);
			response.setMessage("Amount succesfully"+ request.getRecipientName() +" transfered");
			response.setSuccess(true);
			UserDAO userDAO = userService.getUserDAOByName(user.getUsername());
			response.setUser(userDAO);
		}else {
			response.setMessage("Sorry, you do not have sufficient amount to transfer");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	

}
