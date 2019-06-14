package Graphs.Assotiative;

import Graphs.AANG.AbstractNeuron;
import Graphs.ClassName;
import Graphs.Parameters.ParameterValue;

import java.util.ArrayList;
import java.util.List;

public class Item extends AbstractNeuron implements Comparable<Item> {

    private List<ParameterValue> values;
    private String name;
    private ClassName className;
    private int id;
    private double similarityValue;
    private Item nextItem;
    private Item previousItem;
    private List<Item> connectedItems;

    public Item(String name, int id){
        super();
        this.name = name;
        values = new ArrayList<>();
        this.id = id;
        similarityValue = 0.0;
        connectedItems = new ArrayList<>();
    }

    public Item(List<ParameterValue> values, String name){
        super();
        this.values = values;
        this.name = name;
        connectedItems = new ArrayList<>();
    }

    public List<ParameterValue> getValues() {
        return values;
    }

    public void setValues(List<ParameterValue> values) {
        this.values = values;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSimilarityValue() {
        return similarityValue;
    }

    public void setSimilarityValue(double similarityValue) {
        this.similarityValue = similarityValue;
    }

    public Item getNextItem() {
        return nextItem;
    }

    public void setNextItem(Item nextItem) {
        this.nextItem = nextItem;
    }

    public Item getPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(Item previousItem) {
        this.previousItem = previousItem;
    }

    public List<Item> getConnectedItems() {
        return connectedItems;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        for (int i = 0; i < values.size(); i++) {
            if (Math.abs(this.values.get(i).getValue() - item.values.get(i).getValue()) > 0.00000001) return false;
        }

        return name.equals(item.name);

    }

    @Override
    public int hashCode() {
        int result = 1;
        for (ParameterValue pv: values) {
            result = 31 * result + pv.hashCode();
        }
        result = 31 * result + name.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Graph.Item{" +
                "values=" + values +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", similarityValue=" + similarityValue +
                "}\n";
    }

    @Override
    public int compareTo(Item o) {
        if (id > o.id) return 1;
        return -1;
    }

    @Override
    public double calculateOuterWeight() {
        outerWeight = 1.0/values.size();
        return outerWeight;
    }

    @Override
    public void spike() {
        calculateOuterWeight();
        for (ParameterValue pv: values) {
            if (!pv.isStimulated() && pv.getCurrentActivationValue() < 1.0) {
                pv.setPreviousActivationValue(pv.getCurrentActivationValue());
                pv.setCurrentActivationValue(pv.getCurrentActivationValue() + pulse * outerWeight);
                pv.setIncremented(true);

                if (pv.getCurrentActivationValue() > 1.0) pv.setCurrentActivationValue(1.0);
            }
        }
        if (!className.isStimulated() && className.getCurrentActivationValue() < 1.0) {
            className.setPreviousActivationValue(className.getCurrentActivationValue());
            className.setCurrentActivationValue(className.getCurrentActivationValue() + pulse);
            className.setIncremented(true);

            if (className.getCurrentActivationValue() > 1.0) className.setCurrentActivationValue(1.0);
        }
        if (!isStimulated){
            spiked = true;
            timesActivated++;
        }
    }

    public String activatedString(){
        if (!isStimulated)
            return "Id: " + id + "; Times activated: " + timesActivated + ".";
        else
            return "Id: " + id + "; Was stimulated neuron.";
    }

}
