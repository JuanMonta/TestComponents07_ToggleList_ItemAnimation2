package toggle;

import java.awt.event.MouseEvent;

abstract class MoveDragComponentAdapter implements MoveDragComponentListener {

    @Override
    public void onDragItem(int indexItem, Item item,MouseEvent compMousePressed, boolean dragReady) {
        
    }

    @Override
    public void onDragSubItem(Item parent,int indexSubItem, Item.SubItem SubItem, MouseEvent compMousePressed, boolean dragReady) {
        
    }

    @Override
    public void onDragReady(MouseEvent evt, boolean dragReady) {
        
    }

    @Override
    public void onDragEnded(boolean dragReady) {

    }

    

    

}
