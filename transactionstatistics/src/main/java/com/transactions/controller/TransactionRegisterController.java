package com.transactions.controller;

import com.transactions.dto.TransactionDto;
import com.transactions.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionRegisterController {

	private TransactionService transactionService;

	public TransactionRegisterController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transactions")
	public ResponseEntity<Object> addTranscation(@RequestBody TransactionDto transactionDto) {
		if (transactionService.add(transactionDto)) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
