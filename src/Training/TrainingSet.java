package Training;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainingSet {

    private List<TrainingInput> inputs;
    private Set<String> classes;

    public TrainingSet(Path file){

        inputs = new ArrayList<>();
        classes = new HashSet<>();

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                inputs.add(new TrainingInput(line, this));
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public List<TrainingInput> getInputs() {
        return inputs;
    }

    public Set<String> getClasses() {
        return classes;
    }

    public void normalize(){
        double maxes[] = new double[inputs.get(0).getInputs().length];
        double mins[] = new double[inputs.get(0).getInputs().length];
        for (int i = 0; i < mins.length; i++) {
            mins[i] = 100.0;
        }
        for (TrainingInput ti: inputs) {
            for (int i = 0; i < ti.getInputs().length; i++) {
                if (maxes[i] < ti.getInputs()[i]) maxes[i] = ti.getInputs()[i];
                else if (mins[i] > ti.getInputs()[i]) mins[i]  = ti.getInputs()[i];
            }
        }

        inputs.forEach(trainingInput -> {
            for (int i = 0; i < trainingInput.getInputs().length; i++) {
                trainingInput.getInputs()[i] = (trainingInput.getInputs()[i] - mins[i])/(maxes[i] - mins[i]);
            }
        });

    }

    @Override
    public String toString() {
        return "TrainingSet{" +
                "inputs=" + inputs +
                ", classes=" + classes +
                '}';
    }
}
