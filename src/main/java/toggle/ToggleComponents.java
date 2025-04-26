package toggle;

import toggle.ToggleComponentsListener;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ToggleComponents {

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            if (selected) {
                animate = 1f;
            } else {
                animate = 0;
            }
        }
    }

    public void setSelected(boolean selected, boolean animated) {
        if (this.selected != selected) {
            this.selected = selected;
            runEventSelected();
            if (animated) {
                start(selected);
            } else {
                if (selected) {
                    animate = 1f;
                } else {
                    animate = 0;
                }

            }
        } else {
            //just run ended, because do nothing
            runEventAnimatedEnded();
        }
    }

    public void setSelectedSinSelectEventNotification(boolean selected, boolean animated) {
        this.selected = selected;
        if (selected) {
            animate = 1f;
        } else {
            animate = 0;    
        }
        if (animated) {
            start(selected);
        } else {
            runEventAnimated();
        }

    }

    public void addEventToggleSelected(ToggleComponentsListener event) {
        this.events.add(event);
        System.out.println("toggle.ToggleComponents.addEventToggleSelected()-> eventsListSize: "+this.events.size());
    }

    protected void removeEventToggleSelected(ToggleComponentsListener event) {
        this.events.remove(event);
        System.out.println("toggle.ToggleComponents.removeEventToggleSelected()-> eventsListSize: "+this.events.size());
    }
    
    private Animator animator;
    private float animate;
    private boolean selected;
    private final List<ToggleComponentsListener> events = new ArrayList<>();

    public ToggleComponents() {
        initAnimator();
    }

    private void initAnimator() {
        animator = new Animator(350, new TimingTargetAdapter() {

            @Override
            public void begin() {
                System.out.println(ToggleComponents.this.getClass().getName() + ".begin()");
                ToggleComponents.this.runEventAnimatedStarted();
            }

            @Override
            public void timingEvent(float fraction) {
                //System.out.println(ToggleComponents.this.getClass().getName() + ".timingEvent() fraction: " + fraction);
                if (isSelected()) {
                    animate = fraction;
                } else {
                    animate = 1f - fraction;
                }
                runEventAnimated();
            }

            @Override
            public void repeat() {
                System.out.println(ToggleComponents.this.getClass().getName() + ".repeat()");
                ToggleComponents.this.runEventAnimatedRepeated();
            }

            @Override
            public void end() {
                System.out.println(ToggleComponents.this.getClass().getName() + ".end()");
                ToggleComponents.this.runEventAnimatedEnded();
            }
        });
        animator.setResolution(5);
    }

    private void start(boolean selected) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0);
        }
        this.selected = selected;
        animator.start();
    }

    private void runEventSelected() {
        for (ToggleComponentsListener event : events) {
            event.onSelected(selected);
        }
    }

    private void runEventAnimatedStarted() {
        for (ToggleComponentsListener event : events) {
            event.onAnimatedStarted(true);
        }
    }

    private void runEventAnimated() {
        for (ToggleComponentsListener event : events) {
            event.onAnimated(animate);
        }
    }

    private void runEventAnimatedEnded() {
        for (ToggleComponentsListener event : events) {
            event.onAnimatedEnded(true);
        }
    }

    private void runEventAnimatedRepeated() {
        for (ToggleComponentsListener event : events) {
            event.onAnimatedRepeated(true);
        }
    }
}
