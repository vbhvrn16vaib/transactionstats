package com.transactions.services;

import static org.junit.Assert.assertEquals;

import com.transactions.dto.TransactionDto;
import com.transactions.service.StatisticsService;
import com.transactions.service.StatisticsServiceImpl;
import com.transactions.service.TransactionService;
import com.transactions.service.TransactionServiceImpl;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class StatisticsServiceImplTest {

	private TransactionService transactionService;
	private StatisticsService statisticsService;

	public StatisticsServiceImplTest() {
		this.statisticsService = new StatisticsServiceImpl();
		this.transactionService = new TransactionServiceImpl(statisticsService);
	}

	@Test
	public void testStatistics(){

		//given
		Double transDiff = Math.random()*60000;
		TransactionDto transactionDto = new TransactionDto(34.0,System.currentTimeMillis()-transDiff.intValue());

		transDiff = Math.random()*60000;
		TransactionDto transactionDto1 = new TransactionDto(35.0,System.currentTimeMillis()-transDiff.intValue());

		transDiff = Math.random()*60000;
		TransactionDto transactionDto2 = new TransactionDto(37.0,System.currentTimeMillis()-transDiff.intValue());

		transDiff = Math.random()*60000;
		TransactionDto transactionDto3 = new TransactionDto(75.0,System.currentTimeMillis()-transDiff.intValue());

		transactionService.add(transactionDto);
		transactionService.add(transactionDto1);
		transactionService.add(transactionDto3);

		//when
		transactionService.add(transactionDto2);

		//then

		assertEquals(statisticsService.getStats().getCount().intValue(),4);
		assertEquals(181, statisticsService.getStats().getSum().intValue());
	}

	@Test
	public void testStatisticsWithLatch() throws BrokenBarrierException, InterruptedException {
		Double transDiff = Math.random()*60000;
		TransactionDto transactionDto = new TransactionDto(34.0,System.currentTimeMillis()-transDiff.intValue());

		transDiff = Math.random()*60000;
		TransactionDto transactionDto2 = new TransactionDto(37.0,System.currentTimeMillis()-transDiff.intValue());

		transDiff = Math.random()*60000;
		TransactionDto transactionDto3 = new TransactionDto(75.0,System.currentTimeMillis()-transDiff.intValue());


		final CyclicBarrier gate = new CyclicBarrier(3);
		Thread t1 = new Thread(() -> {
			try {
				gate.await();
				transactionService.add(transactionDto);
				System.out.println("Thread1");
				statisticsService.getStats();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		});

		Thread t2 = new Thread(() -> {
			try {
				gate.await();
				transactionService.add(transactionDto2);
				transactionService.add(transactionDto3);
				System.out.println("Thread2");
				statisticsService.getStats();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		});

		t1.start();
		t2.start();

		gate.await();
		System.out.println("all threads started");
	}

	@Test
	public void testMultipleRequest() throws InterruptedException {
		int c = 0;
		while (c<62) {
			TransactionDto transactionDto = new TransactionDto(34.0, System.currentTimeMillis() - 1);
			transactionService.add(transactionDto);
			TimeUnit.SECONDS.sleep(1);
			statisticsService.getStats();
			c++;
		}

		assertEquals(statisticsService.getStats().getCount().intValue(),59);
	}
}
