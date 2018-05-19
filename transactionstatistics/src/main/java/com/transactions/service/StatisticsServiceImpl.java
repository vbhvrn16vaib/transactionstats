package com.transactions.service;

import static com.transactions.common.Constants.DURATION;

import com.transactions.dto.Statistics;
import com.transactions.dto.TransactionDto;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	private Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);
	private NavigableMap<Long, Statistics> statisticsMap;

	public StatisticsServiceImpl() {
		this.statisticsMap = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
	}

	/**
	 * Fetch the statistics in best possible way : always stored in reverse order to find the fromTime.
	 * @return Statistics.
	 */
	public Statistics getStats() {
		long toTime = System.currentTimeMillis();
		long fromTime = toTime - DURATION; // TODO Statistics time duration could be configurable (from application/bootstrap config file)
		log.info("fromTime : {} - uptoTime: {}", fromTime, toTime);
		return calculateStats(fromTime);
	}

	/**
	 * This will be created at the time of request in memory. To keep track of transactions.
	 * @param transactionDto transactionDTO.
	 * @return
	 */
	public boolean addStats(TransactionDto transactionDto) {
		try {
			statisticsMap.putIfAbsent(transactionDto.getTimestamp(), new Statistics());
			Statistics statistics = statisticsMap.get(transactionDto.getTimestamp());
			statistics.setCount(statistics.getCount() + 1);
			statistics.setSum(statistics.getSum() + transactionDto.getAmount());
			statistics.setAvg(statistics.getSum() / statistics.getCount());
			statistics.setMax(Math.max(statistics.getMax(), transactionDto.getAmount()));
			statistics.setMin(Math.min(statistics.getMin(), transactionDto.getAmount()));
			return true;
		} catch (RuntimeException e) {
			log.error("Failed to add data !! : {} ", transactionDto);
			//TODO CAN THROW OUR OWN EXCEPTION.
		}
		return false;
	}

	private Statistics calculateStats(Long from) {
		Statistics finaResult = new Statistics();
		statisticsMap.entrySet().stream().filter(x -> x.getKey() >= from).forEach(temp -> {
			Statistics tempS = temp.getValue();
			finaResult.setMax(Math.max(tempS.getMax(), finaResult.getMax()));
			finaResult.setMin(Math.min(tempS.getMin(), finaResult.getMin()));
			finaResult.setCount(tempS.getCount() + finaResult.getCount());
			finaResult.setSum(tempS.getSum() + finaResult.getSum());
		});

		finaResult.setAvg(finaResult.getCount() != 0 ? finaResult.getSum() / finaResult.getCount() : 0);
		if(finaResult.getCount() == 0){
			finaResult.setMin(0D);
			finaResult.setMax(0D);
		}
		log.info("{}", finaResult);
		System.out.println(finaResult);
		return finaResult;
	}

}
