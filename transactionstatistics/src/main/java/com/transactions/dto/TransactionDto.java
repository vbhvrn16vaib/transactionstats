package com.transactions.dto;

public class TransactionDto {
	private Double amount;
	private Long timestamp;


	public TransactionDto(Double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "TransactionDto{" +
			   "amount=" + amount +
			   ", timestamp=" + timestamp +
			   '}';
	}
}
