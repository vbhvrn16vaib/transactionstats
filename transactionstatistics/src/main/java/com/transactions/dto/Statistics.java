package com.transactions.dto;

import java.util.Objects;

public class Statistics {
	private Double sum = 0.0;
	private Double avg = 0.0;
	private Double max = Double.MIN_VALUE;
	private Double min = Double.MAX_VALUE;
	private Long count = 0L;

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Statistics that = (Statistics) o;
		return Objects.equals(sum, that.sum) &&
			   Objects.equals(avg, that.avg) &&
			   Objects.equals(max, that.max) &&
			   Objects.equals(min, that.min) &&
			   Objects.equals(count, that.count);
	}

	@Override
	public int hashCode() {

		return Objects.hash(sum, avg, max, min, count);
	}

	@Override
	public String toString() {
		return "Statistics{" +
			   "sum=" + sum +
			   ", avg=" + avg +
			   ", max=" + max +
			   ", min=" + min +
			   ", count=" + count +
			   '}';
	}
}
