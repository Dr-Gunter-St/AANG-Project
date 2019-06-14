package Graphs.Parameters;

import Graphs.Assotiative.Item;

import java.util.ArrayList;
import java.util.List;


public class Parameter {
    private int id = 0;
    private String parameterName;
    private ParameterValue min, max;
    private double minValue, maxValue;
    private List<ParameterValue> values;

    public Parameter(String parameterName){
        this.parameterName = parameterName;
        min = null;
        max = null;
        minValue = 0.0;
        maxValue = 0.0;
        values = new ArrayList<>();
    }

    public void addValue(double value, Item item){

        if (min == null && max == null){
            min = new ParameterValue(value, item, id);
            max = min;
            minValue = value;
            maxValue = value;
            item.getValues().add(min);
            values.add(min);
            id++;
            return;
        }

        ParameterValue p = min;
        if (p.getValue() < value) while (p.getValue() < value && p.getNext() != null) p = p.getNext();

        if (p.getValue() > value) while (p.getValue() > value && p.getPrevious() != null) p = p.getPrevious();

        if (p.getValue() == value) {
            p.getItems().add(item);
            item.getValues().add(p);
            return;
        }

        ParameterValue newValue = new ParameterValue(value, item, id);
        values.add(newValue);

        if (value < p.getValue()){

            if (p.getPrevious() != null) {
                p.getPrevious().setNext(newValue);
                newValue.setPrevious(p.getPrevious());
            }
            p.setPrevious(newValue);
            newValue.setNext(p);

            item.getValues().add(newValue);

            if (newValue.getPrevious() == null){
                min = newValue;
                minValue = value;
            }

            recalculateWeights();
            id++;
        }
        if (value > p.getValue()){

            if (p.getNext() != null){
                p.getNext().setPrevious(newValue);
                newValue.setNext(p.getNext());
            }
            p.setNext(newValue);
            newValue.setPrevious(p);

            item.getValues().add(newValue);

            if (newValue.getNext() == null){
                max = newValue;
                maxValue = value;
            }

            recalculateWeights();
            id++;
        }



    }

    private void recalculateWeights(){
        Double currentWeight;
        ParameterValue p = min;

        while (p.getNext() != null){
            currentWeight = 1 - Math.abs(p.getNext().getValue() - p.getValue())/(maxValue-minValue);
            p.setNextWeight(currentWeight);
            p.getNext().setPreviousWeight(currentWeight);
            p.calculateOuterWeight();
            p = p.getNext();
        }

    }

    public ParameterValue getByValue(double value){
        if (value > maxValue || value < minValue) return null;

        ParameterValue p = min;

        while (p.getValue() != value){
            p = p.getNext();
            if (p == null) return p;
        }


        return p;

    }

    @Override
    public String toString() {


        ParameterValue p = min;
        String s = "Graph.Parameters.Parameter{" + "min=" + p + "\n";

        while (p.getNext() != null){
            p = p.getNext();
            s += p.toString() + "\n";
        }

        return s + '}';
    }

    public int countValues(){
        ParameterValue p = min;
        int res = 0;

        if (p != null) res++;

        while (p.getNext() != null){
            p = p.getNext();
            res++;
        }

        return res;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public ParameterValue getMin() {
        return min;
    }

    public ParameterValue getMax() {
        return max;
    }

    public List<ParameterValue> getValues() {
        return values;
    }
}
