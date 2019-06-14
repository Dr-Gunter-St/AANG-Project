
import Graphs.AANG.AANGraph;
import Graphs.Assotiative.AssociativeGraph;
import Training.TrainingSet;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class GraphVisualiser extends JPanel{

    private static JFrame frame;

    public static void main(String[] args) {
        TrainingSet trainingSet = new TrainingSet(Paths.get("resources\\inputs4"));

        AssociativeGraph associativeGraph = new AssociativeGraph(trainingSet, Arrays.asList("1", "2", "3", "4"));

        frame = new JFrame("Graphics");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel(associativeGraph, 3);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
