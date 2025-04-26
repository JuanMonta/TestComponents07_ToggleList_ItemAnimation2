package toggle;

import java.awt.event.MouseEvent;

interface MoveDragComponentListener {

    public void onDragItem(int indexItem, Item item, MouseEvent compMousePressed, boolean dragReady);

    public void onDragSubItem(Item parent, int indexSubItem, Item.SubItem SubItem, MouseEvent compMousePressed, boolean dragReady);

    public void onDragReady(MouseEvent evt, boolean dragReady);

    public void onDragEnded(boolean dragReady);
}
