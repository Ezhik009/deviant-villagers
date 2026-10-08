package gg.melty.deviant;

/** Simple meter. Stage thresholds must come from sheets/revolution_meter.json (not hardcoded in the final build). */
public final class RevolutionMeter {
    private int value = 0;
    public void add(int delta) { value += delta; }
    public int value() { return value; }
}
