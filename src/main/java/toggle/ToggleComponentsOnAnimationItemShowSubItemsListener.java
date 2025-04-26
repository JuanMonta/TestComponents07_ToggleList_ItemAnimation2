package toggle;

interface ToggleComponentsOnAnimationItemShowSubItemsListener {

    public void onSelected(boolean selected);

    public void onAnimated(float animated);

    public void onAnimatedStarted(boolean started);

    public void onAnimatedEnded(boolean ended);

    public void onAnimatedRepeated(boolean repeated);
}
