package Graphs;

import Graphs.AANG.AbstractNeuron;
import Graphs.Assotiative.Item;

import java.util.ArrayList;
import java.util.List;

public class ClassName extends AbstractNeuron {
    private String className;
    private List<Item> items;

    public ClassName(String className){
        super();
        this.className = className;
        items = new ArrayList<>();
    }

    public  String getClassName(){
        return className;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getOuterWeight(){
        outerWeight = 1/items.size();
        return outerWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassName className1 = (ClassName) o;

        return className.equals(className1.className);

    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

    @Override
    public double calculateOuterWeight() {
        outerWeight = 1.0/items.size();
        return outerWeight;
    }

    @Override
    public void spike() {
        calculateOuterWeight();
        for (Item item: items) {
            if (!item.isStimulated() && item.getCurrentActivationValue() < 1.0) {
                item.setPreviousActivationValue(item.getCurrentActivationValue());
                item.setCurrentActivationValue(item.getCurrentActivationValue() + pulse * outerWeight);
                item.setIncremented(true);

                if (item.getCurrentActivationValue() > 1.0) item.setCurrentActivationValue(1.0);
            }
        }
        if (!isStimulated){
            spiked = true;
            timesActivated++;
        }

    }

}
