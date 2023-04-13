
package acme.forms;

import java.util.Collection;
import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {

	protected int		count;
	protected Double	average;
	protected Double	minimum;
	protected Double	maximum;
	protected Double	stdDeviation;


	private void obtainCount(final Collection<Double> values) {
		final int result = values.size();
		this.setCount(result);
	}

	private void obtainMax(final Collection<Double> values) {
		final Double result = values.stream().max(Comparator.comparing(x -> x)).get();
		this.setMaximum(result);
	}

	private void obtainMin(final Collection<Double> values) {
		final Double result = values.stream().min(Comparator.comparing(x -> x)).get();
		this.setMinimum(result);
	}

	private void obtainAverage(final Collection<Double> values) {
		final Double result = values.stream().mapToDouble(x -> x).sum() / values.size();
		this.setAverage(result);
	}

	private void obtainDeviation(final Collection<Double> values) {

	}

	public void obtainValues(final Collection<Double> values) {
		this.obtainCount(values);
		this.obtainMax(values);
		this.obtainMin(values);
		this.obtainAverage(values);
		this.obtainDeviation(values);
	}

}
