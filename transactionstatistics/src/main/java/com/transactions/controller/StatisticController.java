package com.transactions.controller;

import com.transactions.dto.Statistics;
import com.transactions.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

	private StatisticsService statisticsService;

	public StatisticController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping("/statistics")
	public Statistics getStatistics(){
		return statisticsService.getStats();
	}
}
