package com.transactions.service;

import com.transactions.dto.Statistics;
import com.transactions.dto.TransactionDto;

public interface StatisticsService {

	Statistics getStats();

	boolean addStats(TransactionDto transactionDto);

}
