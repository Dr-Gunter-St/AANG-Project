package Graphs.Assotiative;

import Graphs.ClassName;
import Graphs.Parameters.Parameter;
import Graphs.Parameters.ParameterValue;
import Training.TrainingInput;
import Training.TrainingSet;

import java.util.ArrayList;
import java.util.List;


public class AssociativeGraph {
    private List<Parameter> parameters;
    private List<String> parameterNames;
    private List<ClassName> classNames;
    private List<Item> items;

    public AssociativeGraph(TrainingSet set, List<String> parameterNames){
        this.parameterNames = parameterNames;

        parameters = new ArrayList<>();
        items = new ArrayList<>();
        classNames = new ArrayList<>();

        for (int i = 0; i < set.getInputs().get(0).getInputs().length; i++) {
            parameters.add(new Parameter(parameterNames.get(i)));
        }

        for (String s: set.getClasses()) {
            classNames.add(new ClassName(s));
        }

        int id = 1;
        for (TrainingInput i: set.getInputs()) {
            Item newItem = new Item(i.getClassName(), id);
            items.add(newItem);
            for (ClassName name: classNames) {
                if (newItem.getName().equals(name.getClassName())){
                    newItem.setClassName(name);
                    name.getItems().add(newItem);
                }
            }
            for (int j = 0; j < i.getInputs().length; j++) {
                parameters.get(j).addValue(i.getInputs()[j], newItem);
            }
            id++;
        }


    }

    public double similarityOf2Items(Item from, Item to){

        double similarity = 0.0;

        for (int i = 0; i < parameters.size(); i++) {
            ParameterValue valueFrom = from.getValues().get(i);
            ParameterValue p = valueFrom;
            ParameterValue valueTo = to.getValues().get(i);

            double weight = 1.0;

            if (valueFrom.getValue() > valueTo.getValue()){
                while (p.getValue() - valueTo.getValue()  > 0.000000000001){
                    p = p.getPrevious();
                    weight *= p.getNextWeight();
                }
                similarity += weight*(1.0/parameters.size());
            } else if (valueFrom.getValue() < valueTo.getValue()){
                while (p.getValue() - valueTo.getValue() > 0.000000000001){
                    p = p.getNext();
                    weight *= p.getPreviousWeight();
                }
                similarity += weight*(1.0/(parameters.size()+1));
            } else {
                similarity += weight*(1.0/(parameters.size()+1));
            }

        }

        if (from.getName().equals(to.getName())){
            similarity += 1/(parameters.size() + 1);
        }

        return similarity;
    }

    public List<Item> findSimilars(Item item, double treshold){
        List<Item> similars = new ArrayList<>();
        ParameterValue p;
        double weight;
        double div = 1.0/(parameters.size() + 1);

        items.forEach(item1 -> item1.setSimilarityValue(0.0));

        for (ParameterValue value: item.getValues()) {
            weight = 1.0;
            p = value;

            while (p.getPrevious() != null){
                p = p.getPrevious();
                weight = weight * p.getNextWeight();

                double finalWeight = weight * div;

                p.getItems().forEach(item1 -> item1.setSimilarityValue(item1.getSimilarityValue() + finalWeight ));
            }

        }

        for (ParameterValue value: item.getValues()) {
            weight = 1.0;
            p = value;

            while (p.getNext() != null){
                p = p.getNext();
                weight = weight * p.getPreviousWeight();

                double finalWeight = weight * div;

                p.getItems().forEach(item1 -> item1.setSimilarityValue(item1.getSimilarityValue() + finalWeight));
            }

        }

        items.forEach(item1 -> {
            if (item.getName().equals(item1.getName())) {
                item1.setSimilarityValue(item1.getSimilarityValue() + div);
            }
        });

        item.setSimilarityValue(1.0);

        items.forEach(item1 -> {
            if (item1.getSimilarityValue() >= treshold){
                similars.add(item1);
            }
        });

        return similars;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<ClassName> getClasses() {
        return classNames;
    }

    public void spikeAll() {
        for (Item item : items) {
            if (item.getCurrentActivationValue() >= 1 && !item.isIncremented()) item.spike();
        }
        for (Parameter p : parameters) {
            for (ParameterValue pv : p.getValues()) {
                if (pv.getCurrentActivationValue() >= 1 && !pv.isIncremented()) pv.spike();
            }
        }
        for (ClassName cn : classNames) {
            if (cn.getCurrentActivationValue() >= 1 && !cn.isIncremented()) cn.spike();
        }

    }

    public void updateAll(){

        for (Item item: items) {
            item.update();
        }
        for (Parameter p:  parameters) {
            for (ParameterValue pv: p.getValues()) {
                pv.update();
            }
        }
        for (ClassName cn: classNames) {
            cn.update();
        }

    }
}
