package toggle.toggleComponentsListeners;

import toggle.Item;

public interface ToggleListAnimationLayout_UPDOWN_Listener {

    public void onMoveItem_Move_Started(String direction, Item itemMoved);

    public void onMoveItem_AnimatedShow(float animated, Item itemMoved);

    public void onMoveItem_Move_Ended(String direction, Item itemMoved);

    //--------------------------------------------------------------------------
    public void onMoveSubItem_Move_Started(String direction, Item parent, Item.SubItem SubItemMoved);

    public void onMoveSubItem_Move_Started(String direction, Item.SubItem SubItemMoved);

    public void onMoveSubItem_AnimatedShow(float animated, Item parent, Item.SubItem SubItemMoved);

    public void onMoveSubItem_AnimatedShow(float animated, Item.SubItem SubItemMoved);

    public void onMoveSubItem_Move_Ended(String direction, Item parent, Item.SubItem SubItemMoved);

    public void onMoveSubItem_Move_Ended(String direction, Item.SubItem SubItemMoved);
}
