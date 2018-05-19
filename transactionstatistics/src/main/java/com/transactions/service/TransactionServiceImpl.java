package com.transactions.service;

import com.transactions.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

	private Logger log = LoggerFactory.getLogger(TransactionService.class);
	private StatisticsService statisticsService;

	public TransactionServiceImpl(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public boolean add(TransactionDto transactionDto) {
		if (!validateTransaction(transactionDto.getTimestamp())) {
			log.info("Transaction log : {}", transactionDto.toString());
			return false;
		}
		return statisticsService.addStats(transactionDto);
	}

	private boolean validateTransaction(long timestamp) {
		long currentTime = System.currentTimeMillis();
		log.info("time difference : {},{},{}", currentTime, timestamp, currentTime - timestamp);
		return currentTime - timestamp <= 60000 && currentTime - timestamp >= 0;
	}
}
