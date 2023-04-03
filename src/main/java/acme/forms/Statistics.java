package acme.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {

    protected int count;
    protected Double average;
    protected Double minimum;
    protected Double maximum;
    protected Double stdDeviation;

}
