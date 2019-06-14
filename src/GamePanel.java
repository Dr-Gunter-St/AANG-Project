import Graphs.Assotiative.AssociativeGraph;
import Graphs.Assotiative.Item;
import Graphs.ClassName;
import Graphs.Parameters.Parameter;
import Graphs.Parameters.ParameterValue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class GamePanel extends JPanel implements Runnable{

        private boolean isRunning;
        private Thread thread;

        private AssociativeGraph graph;

        private static final int HEIGHT = 800;
        private static final int WIDTH = 800;



        public GamePanel(AssociativeGraph graph, int variant){
            this.graph = graph;

            switch (variant) {
                case 1:
                {
                    graph.getItems().get(12).stimuli();
                }

                case 2: {
                    graph.getItems().get(0).stimuli();
                    graph.getItems().get(14).stimuli();
                    graph.getItems().get(24).stimuli();
                }
                case 3:{
                    graph.getParameters().get(0).getValues().get(0).stimuli();
                    graph.getParameters().get(0).getValues().get(1).stimuli();
                    graph.getParameters().get(0).getValues().get(2).stimuli();
                    graph.getParameters().get(0).getValues().get(3).stimuli();
                }
                break;
            }


            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            start();
        }

        private void start(){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {

            int steps = 0;
            while (steps < 100){

                tick1();
                repaint();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tick2();
                repaint();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                steps++;
            }

            for (Item i: graph.getItems()) {
                System.out.println(i.activatedString());
            }

        }

        private void tick1(){
            graph.spikeAll();
        }

        private void tick2(){
            graph.updateAll();;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);

            drawParameters(g);
            drawClasses(g);
            drawItems(g);



        }

        private void setColor(Graphics g, double activationValue){
            if (activationValue > 1.0) activationValue = 1.0;
            else if (activationValue < -1.0) activationValue = -1.0;

            if (activationValue >= 0.0) {
                g.setColor(new Color(1.0f, 1.0f - (float) activationValue, 1.0f - (float) activationValue));

            } else {
                g.setColor(new Color(1.0f - Math.abs((float) activationValue), 1.0f - Math.abs((float) activationValue), 1.0f));
            }

        }

        private void drawParameters(Graphics g){
            Parameter parameter;

            int id = 0;

            for (int i = 0; i < 4; i++) {
                parameter = graph.getParameters().get(i);
                ParameterValue pv = parameter.getMin();

                int step = 0;

                if (i == 0) {
                    while (pv != null) {
                        g.drawRect(10, step * 30 + 5, 20, 20);

                        setColor(g, pv.getCurrentActivationValue());

                        g.fillRect(11, step * 30 + 6, 19, 19);
                        g.setColor(Color.black);
                        g.drawString(pv.getValue().toString(), 12, step * 30 + 20);

                        if (pv.getNext() != null){
                            g.drawLine(20, step * 30 + 25, 20, step * 30 + 35);
                        }

                        pv = pv.getNext();
                        step++;

                    }
                } else if (i == 1) {
                    while (pv != null) {

                        g.drawRect((step + 2) * 30 + 5, 10, 20, 20);

                        setColor(g, pv.getCurrentActivationValue());

                        g.fillRect((step + 2) * 30 + 6, 11, 19, 19);
                        g.setColor(Color.black);
                        g.drawString(pv.getValue().toString(), (step + 2) * 30 + 7, 25);

                        if (pv.getNext() != null){
                            g.drawLine((step + 2) * 30 + 25, 20, (step + 2) * 30 + 35, 20);
                        }

                        pv = pv.getNext();
                        step++;
                    }
                } else if (i == 2) {
                    while (pv != null) {

                        g.drawRect(WIDTH - 50, step * 30 + 5, 20, 20);

                        setColor(g, pv.getCurrentActivationValue());

                        g.fillRect(WIDTH - 49, step * 30 + 6, 19, 19);
                        g.setColor(Color.black);
                        g.drawString(pv.getValue().toString(), WIDTH - 48, step * 30 + 20);

                        if (pv.getNext() != null){
                            g.drawLine(WIDTH - 40, step * 30 + 25, WIDTH - 40, step * 30 + 35);
                        }

                        pv = pv.getNext();
                        step++;
                    }
                } else if (i == 3) {
                    while (pv != null) {

                        g.drawRect((step) * 30 + 5, HEIGHT - 50, 20, 20);

                        setColor(g, pv.getCurrentActivationValue());

                        g.fillRect((step) * 30 + 6, HEIGHT - 49, 19, 19);
                        g.setColor(Color.black);
                        g.drawString(pv.getValue().toString(), (step) * 30 + 7, HEIGHT - 35);

                        if (pv.getNext() != null){
                            g.drawLine((step) * 30 + 25, HEIGHT - 40, (step) * 30 + 35, HEIGHT - 40);
                        }

                        pv = pv.getNext();
                        step++;
                    }

                }
            }
        }

        private void drawClasses(Graphics g){

            List<ClassName> classes = graph.getClasses();
            ClassName className;

            for (int i = classes.size() - 1; i > -1; i--) {
                className = classes.get(i);

                g.drawRect(WIDTH - 120, HEIGHT + 30 - (i + 3) * 30, 85, 20);

                setColor(g, className.getCurrentActivationValue());
                g.fillRect(WIDTH - 119, HEIGHT + 31 - (i + 3) * 30, 84, 19);

                g.setColor(Color.black);
                g.drawString(classes.get(i).getClassName(), WIDTH - 115, HEIGHT + 45 - (i + 3) * 30);

            }
        }

        private void drawItems(Graphics g){

            List<Item> items = graph.getItems();
            Item it;

            for (int i = 0; i < items.size(); i++) {

                it = items.get(i);

                g.setColor(Color.blue);
                g.drawRect((i+6)*20, (i+6)*13, 15, 15);

                setColor(g, it.getCurrentActivationValue());
                g.fillRect((i+6)*20 + 1, (i+6)*13 + 1, 14, 14);

                g.setColor(Color.black);
                g.drawString(Integer.toString(it.getId()), (i+6)*20 + 2, (i+6)*13 + 13);
                drawItemConnections(g, it, i);
            }

        }

        private void drawItemConnections(Graphics g, Item item, int index){

            int step;
            ParameterValue pv, temp;

            for (int i = 0; i < item.getValues().size(); i++) {


                step = 0;
                pv = item.getValues().get(i);
                temp = graph.getParameters().get(i).getMin();

                while (!temp.equals(pv)) {
                    temp = temp.getNext();
                    step++;
                }

                if (i == 0) g.drawLine((index + 6) * 20, (index + 6) * 13 + 10, 30, step * 30 + 15);
                else if (i == 1) g.drawLine((index + 6) * 20 + 10, (index + 6) * 13, (step + 2) * 30 + 15, 30);
                else if (i == 2) g.drawLine((index + 6) * 20 + 20, (index + 6) * 13 + 10, WIDTH - 50, step * 30 + 15);
                else if (i == 3) g.drawLine((index + 6) * 20 + 10, (index + 6) * 13 + 20, (step) * 30 + 15, HEIGHT - 50);

            }

            switch (item.getName()) {
                case "Iris-setosa":
                    g.drawLine((index + 6) * 20 + 10, (index + 6) * 13 + 20, WIDTH - 120, HEIGHT - 110);
                    break;
                case "Iris-virginica":
                    g.drawLine((index + 6) * 20 + 10, (index + 6) * 13 + 20, WIDTH - 120, HEIGHT - 50);
                    break;
                case "Iris-versicolor":
                    g.drawLine((index + 6) * 20 + 10, (index + 6) * 13 + 20, WIDTH - 120, HEIGHT - 80);
                    break;
            }

        }

}
