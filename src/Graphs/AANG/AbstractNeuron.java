package Graphs.AANG;

public abstract class AbstractNeuron{

    protected static final double pulse = 1.0;
    protected static final double afterPulse = -1.0;

    protected double outerWeight;
    protected double currentActivationValue;
    protected double previousActivationValue;
    protected double relaxationValue;
    protected boolean isStimulated;
    protected boolean spiked;
    protected boolean incremented;

    protected int timesActivated;

    public AbstractNeuron() {
        this.currentActivationValue = 0.0;
        this.previousActivationValue = 0.0;
        this.relaxationValue = 0.9;
        this.isStimulated = false;
        this.spiked = false;
        this.incremented = false;
        this.timesActivated = 0;
    }

    public abstract double calculateOuterWeight();
    public abstract void spike();
    public void stimuli(){
        this.currentActivationValue = 1.0;
        this.previousActivationValue = 1.0;
        this.isStimulated = true;
    }

    public void update(){

        if (!isStimulated) {
            if (currentActivationValue < 0) {
                previousActivationValue = currentActivationValue;
                currentActivationValue += 0.25;
            }

            if (!incremented && currentActivationValue > 0) {
                previousActivationValue = currentActivationValue;
                currentActivationValue *= relaxationValue;

                if (currentActivationValue < 0.05) currentActivationValue = 0.0;
            }

            if (spiked) {
                previousActivationValue = afterPulse;
                currentActivationValue = afterPulse;
                spiked = false;
            }

            incremented = false;
        }
    }

    public double getCurrentActivationValue() {
        return currentActivationValue;
    }

    public void setCurrentActivationValue(double currentActivationValue) {
        this.currentActivationValue = currentActivationValue;
    }

    public double getPreviousActivationValue() {
        return previousActivationValue;
    }

    public void setPreviousActivationValue(double previousActivationValue) {
        this.previousActivationValue = previousActivationValue;
    }

    public boolean isStimulated() {
        return isStimulated;
    }

    public void setStimulated(boolean stimulated) {
        isStimulated = stimulated;
    }

    public boolean isIncremented() {
        return incremented;
    }

    public void setIncremented(boolean incremented) {
        this.incremented = incremented;
    }

    public int getTimesActivated() {
        return timesActivated;
    }
}
