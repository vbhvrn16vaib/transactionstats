package com.transactions.controller;

import com.transactions.dto.Statistics;
import com.transactions.service.StatisticsServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

	private StatisticsServiceImpl statisticsServiceImpl;

	public StatisticController(StatisticsServiceImpl statisticsServiceImpl) {
		this.statisticsServiceImpl = statisticsServiceImpl;
	}

	@GetMapping("/statistics")
	public Statistics getStatistics(){
		return statisticsServiceImpl.getStats();
	}
}
