package Graphs.Parameters;

import Graphs.AANG.AbstractNeuron;
import Graphs.Assotiative.Item;

import java.util.ArrayList;
import java.util.List;


public class ParameterValue extends AbstractNeuron{

    private int id;
    private double value;
    private ParameterValue previous, next;
    private double previousWeight, nextWeight;
    private List<Item> items;
    private int itemsQuantity;


    public ParameterValue(double value, Item item, int id){
        super();
        this.value = value;
        previous = null;
        next = null;
        previousWeight = 0.0;
        nextWeight = 0.0;

        items = new ArrayList<>();
        items.add(item);
        itemsQuantity = 1;

        this.id = id;
    }

    public Double getValue(){
        return value;
    }

    public ParameterValue getPrevious() {
        return previous;
    }

    public void setPrevious(ParameterValue previous) {
        this.previous = previous;
    }

    public ParameterValue getNext() {
        return next;
    }

    public void setNext(ParameterValue next) {
        this.next = next;
    }

    public Double getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(Double previousWeight) {
        this.previousWeight = previousWeight;
    }

    public Double getNextWeight() {
        return nextWeight;
    }

    public void setNextWeight(Double nextWeight) {
        this.nextWeight = nextWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getOuterWeight() {
        return outerWeight;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterValue that = (ParameterValue) o;

        if (Double.compare(that.value, value) != 0) return false;
        if (Double.compare(that.previousWeight, previousWeight) != 0) return false;
        if (Double.compare(that.nextWeight, nextWeight) != 0) return false;
        if (Double.compare(that.outerWeight, outerWeight) != 0) return false;
        if (itemsQuantity != that.itemsQuantity) return false;
        if (previous != null ? !previous.equals(that.previous) : that.previous != null) return false;
        if (next != null ? !next.equals(that.next) : that.next != null) return false;
        return items != null ? items.equals(that.items) : that.items == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(previousWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nextWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(outerWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + itemsQuantity;
        return result;
    }

    @Override
    public String toString() {
        return "Graph.Parameters.ParameterValue{" +
                "value=" + value +
                ", previousWeight=" + previousWeight +
                ", nextWeight=" + nextWeight +
                '}';
    }

    @Override
    public double calculateOuterWeight() {
        itemsQuantity = items.size();
        outerWeight = 1.0/itemsQuantity;
        return outerWeight;
    }

    @Override
    public void spike() {
        calculateOuterWeight();
        for (Item item: items) {
            if (!item.isStimulated() & item.getCurrentActivationValue() < 1.0) {
                item.setPreviousActivationValue(item.getCurrentActivationValue());
                item.setCurrentActivationValue(item.getCurrentActivationValue() + pulse * outerWeight);
                item.setIncremented(true);

                if (item.getCurrentActivationValue() > 1.0) item.setCurrentActivationValue(1.0);
            }
        }
        if (previous != null) previous.currentActivationValue += previousWeight;
        if (next != null) next.currentActivationValue += nextWeight;
        if (!isStimulated){
            spiked = true;
            timesActivated++;
        }
    }

}
