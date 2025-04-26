package toggle.AnimationTiming;

public interface AnimationTimingListener {

    public void onOculto(boolean selected);

    public void onAnimatedStarted(boolean started);
    
    public void onAnimated(float animated);
    
    public void onAnimatedEnded(boolean ended);
    
    public void onAnimatedRepeated(boolean repeated);
}
