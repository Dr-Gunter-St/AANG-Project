package Graphs.AANG;

import Graphs.Assotiative.AssociativeGraph;
import Graphs.ClassName;
import Graphs.Assotiative.Item;
import Graphs.Parameters.Parameter;

import java.util.*;

public class AANGraph {

    private List<Parameter> parameters;
    private List<String> parameterNames;
    private Item firstItem, lastItem;
    private List<ClassName> classNames;

    public AANGraph(AssociativeGraph graph) {
        classNames = new ArrayList<>();

        /////////////////////////////////////////////
        parameters = graph.getParameters();
        parameterNames = graph.getParameterNames();

        /////////////////////////////////////////////
        Set<String> classNamesSet = new HashSet<>();
        List<Item> items = new ArrayList<>();
        for (Item i: graph.getItems()) {
            classNamesSet.add(i.getName());
            if (!items.contains(i)){ // TAKING UNIQUE ITEMS
                items.add(i);
            } else {  // OTHERS ARE ADDED TO ADJACENCY LISTS
                if (!i.equals(items.get(items.size()-1)) && !i.equals(items.get(items.size()-2)) ){
                    Item l = items.get(items.size()-1);
                    Item in = items.get(items.indexOf(i));

                    l.getConnectedItems().add(in);
                    in.getConnectedItems().add(l);
                }
            }
        }
        for (String s: classNamesSet) {
            classNames.add(new ClassName(s));
        }
        /////////////////////////////////////////////

        firstItem = items.get(0); // ITEMS ARE CONNECTED
        Item prev = firstItem;
        Item next;
        for (int i = 1; i < items.size(); i++) {
            next = items.get(i);
            prev.setNextItem(next);
            next.setPreviousItem(prev);
            prev = next;
            lastItem = next;
        }

        for (ClassName cn: classNames) { // CLASS NAMES ARE NEURONS AS WELL
            for (Item i: items) {
                if (cn.getClassName().equals(i.getName())){
                    cn.getItems().add(i);
                    i.setClassName(cn);
                }
            }
        }

    }
}
