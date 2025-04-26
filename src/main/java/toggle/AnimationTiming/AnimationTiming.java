package toggle.AnimationTiming;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class AnimationTiming {

    private Animator animator;
    private float animate;
    private boolean fade;
    private final List<AnimationTimingListener> events = new ArrayList<>();
    private int animationTime = 350;
    private int animationRepeatCount = 1;

    public static class ComportamientoOnRepeat {

        public static final String HOLD = "HOLD";
        public static final String REVERSE = "REVERSE";
    }

    public AnimationTiming() {
        initAnimator();
    }

    public void animar() {
        this.setFade(true, true);
    }

    private void setFade(boolean ocultar, boolean animated) {
        if (!this.animator.isRunning()) {
            this.fade = ocultar;
            runEventOculto();
            if (animated) {
                start(ocultar);
            } else {
                if (ocultar) {
                    animate = 1f;
                } else {
                    animate = 0;
                }

            }
        }
    }

    public void addEventAnimationTimingAdapter(AnimationTimingListener event) {
        this.events.add(event);
    }

    /**
     * Tiempo en milis, el valor debe ser mayor a cero, caso contrario se tomará
     * el valor por defecto de 350 milis.
     *
     * @param milis
     */
    public void setAnimationTime(int milis) {
        if (milis > 0) {
            this.animationTime = milis;
            this.initAnimator();
        }
    }

    public boolean isAnimatorRunnning() {
        return this.animator.isRunning();
    }

    private boolean animationStop = false;

    public void animatorStop() {
        this.animationStop = true;
        this.animator.stop();
    }

    /**
     * Las veces que en animator se repetirá, por defecto se lanzará solo una
     * vez.
     *
     * @param animationRepeatCount valores mayores a cero. Si es cero o menor a
     * cero se establecerá a 1 por defecto.
     * org.jdesktop.animation.timing.Animator.INFINITE public static final int
     * INFINITE = -1 Se utiliza para especificar una duración interminable o
     * repetirCount
     */
    public void setAnimationRepeatCount(int animationRepeatCount) {
        if (org.jdesktop.animation.timing.Animator.INFINITE == animationRepeatCount) {
            this.animationRepeatCount = animationRepeatCount;
        } else {
            this.animationRepeatCount = animationRepeatCount <= 0 ? 1 : animationRepeatCount;
        }
        this.initAnimator();
    }

    /**
     * REVERSE - valores de 0.0 a 1.0 y luego en reversa 1.0 a 0.0 , HOLD -
     * valores de 0.0 a 1.0 y se repite desde cero. Por defecto es HOLD.
     *
     * @param onRepeatBehavior
     */
    public void setAnimationComportamientoOnRepeat(Animator.RepeatBehavior onRepeatBehavior) {
        //Each cycle proceeds in the opposite direction as the previous one
        //animator.setRepeatBehavior(Animator.RepeatBehavior.REVERSE);
        animator.setRepeatBehavior(onRepeatBehavior);
    }

    private void initAnimator() {
        int animationRepeatC = this.animationRepeatCount > 0 ? this.animationRepeatCount : this.animationRepeatCount * -1;
        animator = new Animator(this.animationTime / animationRepeatC, new TimingTargetAdapter() {
            @Override
            public void begin() {
                runEventAnimatedStarted();
            }

            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                runEventAnimated();
                //System.out.println(AnimationTiming.this.getClass().getName() + "-> timingEvent: animate " + animate + " fraction " + fraction);
            }

            @Override
            public void repeat() {
                runEventAnimatedRepeated();
            }

            @Override
            public void end() {
                if (AnimationTiming.this.animationStop == false) {
                    runEventAnimatedEnded();
                }
                AnimationTiming.this.animationStop = false;
            }

        });
        animator.setResolution(5);
        //numero de repeticiones del animator
        animator.setRepeatCount(animationRepeatC);

    }

    private void start(boolean selected) {
        this.fade = selected;
        animator.start();
    }

    private void runEventOculto() {
        for (AnimationTimingListener event : events) {
            event.onOculto(fade);
        }
    }

    private void runEventAnimatedStarted() {
        for (AnimationTimingListener event : events) {
            event.onAnimatedStarted(true);
        }
    }

    private void runEventAnimated() {
        for (AnimationTimingListener event : events) {
            event.onAnimated(animate);
        }
    }

    private void runEventAnimatedEnded() {
        for (AnimationTimingListener event : events) {
            event.onAnimatedEnded(true);
        }
    }

    private void runEventAnimatedRepeated() {
        for (AnimationTimingListener event : events) {
            event.onAnimatedRepeated(true);
        }
    }

}
