package Training;

import java.util.Arrays;

public class TrainingInput {

    private double[] inputs;
    private String className;

    TrainingInput(String line, TrainingSet trainingSet){

        String[] values = line.split(",");
        this.className = values[values.length - 1];
        trainingSet.getClasses().add(className);

        inputs = new double[values.length - 1];
        for (int i = 0; i < values.length - 1; i++) {
            inputs[i] = Double.parseDouble(values[i]);
        }
    }

    public double[] getInputs() {
        return inputs;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "TrainingInput{" +
                "inputs=" + Arrays.toString(inputs) +
                ", className='" + className + '\'' +
                "}\n";
    }
}
