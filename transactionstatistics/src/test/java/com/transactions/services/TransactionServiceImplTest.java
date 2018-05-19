package com.transactions.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.transactions.dto.TransactionDto;
import com.transactions.service.StatisticsService;
import com.transactions.service.StatisticsServiceImpl;
import com.transactions.service.TransactionService;
import com.transactions.service.TransactionServiceImpl;
import org.junit.Test;

public class TransactionServiceImplTest {

	private StatisticsService statisticsService = new StatisticsServiceImpl();
	private TransactionService transactionService = new TransactionServiceImpl(statisticsService);

	@Test
	public void testTransaction201(){
		TransactionDto transactionDto = new TransactionDto(34.0,System.currentTimeMillis()-1000);
		assertTrue(transactionService.add(transactionDto));
	}

	@Test
	public void testTransaction204(){
		TransactionDto transactionDto = new TransactionDto(34.0,System.currentTimeMillis()-61000);
		assertFalse(transactionService.add(transactionDto));
	}
}
