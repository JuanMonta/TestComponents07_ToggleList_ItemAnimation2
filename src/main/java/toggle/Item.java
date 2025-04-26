package toggle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public abstract class Item extends JPanel {

    public Item() {
        this.toggleList = new ToggleComponents();
        this.toggleList.setSelected(true);
        this.iniciarListaComponentes();
    }

    private ToggleListAnimationLayout toggleListLayout;
    private ToggleComponentsAdapter toggleComponentsAdapter;

    public ToggleListAnimationLayout getToggleListLayout() {
        return toggleListLayout;
    }

    
    public abstract void iniciarAlgoEnElComponente();

    /*public void show(Component components[], float animated, boolean selected) {
        int width = (int) (100f * animated);
        int gap = (int) ((50 * (1f - (animated))));
        for (Component com : components) {
            int height = (int) (com.getPreferredSize().height * animated);
            getToggleListLayout().getMigLayout().setComponentConstraints(com, "h " + height + "!, w " + width + "%!, gap " + gap + "%! n 0px 0px");
            if (com instanceof Item) {
                Item item = (Item) com;
                if (item.getSubItems() != null) {
                    if ((!selected && item.getToggleList().isSelected()) || (selected && item.getToggleList().isSelected())) {
                        show(item.getSubItems(), animated, item.getToggleList().isSelected());
                    }
                }
            }
        }
        getToggleListLayout().revalidate();
    }*/
    public void show(List<Component> components, float animated, boolean selected) {
        int width = (int) (100f * animated);
        int gap = (int) ((50 * (1f - (animated))));
        for (Component com : components) {
            int height = (int) (com.getPreferredSize().height * animated);
            getToggleListLayout().getMigLayout().setComponentConstraints(com, "h " + height + "!, w " + width + "%!, gap " + gap + "%! n 0px 0px");
            if (com instanceof Item) {
                Item item = (Item) com;
                if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
                    if ((!selected && item.getToggleList().isSelected()) || (selected && item.getToggleList().isSelected())) {
                        show(item.getSubItems(), animated, item.getToggleList().isSelected());
                    }
                }
            }
        }
        getToggleListLayout().revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(230, 230, 230));
        g2.drawLine(0, 0, 0, getHeight() - 1);
        g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        g2.dispose();
    }
    // <editor-fold defaultstate="collapse" desc="CONTROL DE LOS SUB-ITEMS">
    private List<Component> components;
    private ToggleComponents toggleList;

    private void iniciarListaComponentes() {
        if (this.components == null) {
            this.components = new CopyOnWriteArrayList<>();
        }
    }

    public void addSubItems(Component... components) {
        this.iniciarListaComponentes();
        this.components.addAll(Arrays.asList(components));
    }

    public void addSubItems(List<Component> components) {
        this.iniciarListaComponentes();
        for (Component component : components) {
            this.components.add(component);
        }
    }

    public void addSubItem(Component component) {
        this.iniciarListaComponentes();
        this.components.add(component);
    }

    public void addSubItem(Component component, int index) {
        this.iniciarListaComponentes();
        this.components.add(index, component);
    }

    public void removeSubItem(Component subItemToRemove) {
        this.iniciarListaComponentes();
        boolean removido = this.components.remove(subItemToRemove);
        if (subItemToRemove instanceof Item.SubItem) {
            System.out.println(Item.this.getClass().getName() + "-> removiendo SubItem del Item: "
                    + this.getIndex() + " - "
                    + " - hashCode: " + subItemToRemove.hashCode()
                    + " removido?= " + removido);
        }
    }

    public void removeSubItem(int index) {
        this.iniciarListaComponentes();
        if (!this.components.isEmpty() && index >= 0 && index < this.components.size()) {
            this.components.remove(index);
        }
    }

     public void removeAllSubItems() {
        this.iniciarListaComponentes();
        this.components.clear();
    }

    public List<Component> getSubItems() {
        return this.components;
    }

    public ToggleComponents getToggleList() {
        return toggleList;
    }

    public void setToggleSelection(boolean selection, boolean animated) {
        this.toggleList.setSelected(selection, animated);
        if (this.itemActionListener != null) {
            this.itemActionListener.setToggleSelection(selection, animated);
        }
    }

    public void setToggleSelectionSinEventNotification(boolean selection, boolean animated) {
        this.toggleList.setSelectedSinSelectEventNotification(selection, animated);
        if (this.itemActionListener != null) {
            this.itemActionListener.setToggleSelectionSinEventNotification(selection, animated);
        }
    }

    public void setToggleList(ToggleComponents toggleList) {
        this.toggleList = toggleList;
    }

    public void showSubItems(boolean show) {
        this.toggleList.setSelected(show, true);
        if (this.itemActionListener != null) {
            this.itemActionListener.showSubItems(show);
        }
    }

    private int getIndex() {
        int index = -1;
        if (this.getParent() != null) {
            Component[] componentParent = this.getParent().getComponents();
            for (int i = 0; i < componentParent.length; i++) {
                if (componentParent[i].equals(this)) {
                    //System.out.println(this.getClass().getName()+"-> item Index: "+i);
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private onItemActionListener itemActionListener;

    public static interface onItemActionListener {

        public void setToggleSelection(boolean selection, boolean animated);

        public void setToggleSelectionSinEventNotification(boolean selection, boolean animated);

        public void showSubItems(boolean show);

       /* public void addSubItems(Component... components);

        public void addSubItems(List<Component> components);

        public void addSubItem(Component component);

        public void addSubItem(Component component, int index);

        public void removeSubItem(Component subItemToRemove);

        public void removeSubItem(int index);

        public void removeAllSubItems();*/
    }

    public static abstract class onItemActionsAdapter implements onItemActionListener {

        @Override
        public void setToggleSelection(boolean selection, boolean animated) {

        }

        @Override
        public void setToggleSelectionSinEventNotification(boolean selection, boolean animated) {

        }

        @Override
        public void showSubItems(boolean show) {

        }

       /* @Override
        public void addSubItems(Component... components) {
        }

        @Override
        public void addSubItems(List<Component> components) {
        }

        @Override
        public void addSubItem(Component component) {
        }

        @Override
        public void addSubItem(Component component, int index) {
        }

        @Override
        public void removeSubItem(Component subItemToRemove) {
        }

        @Override
        public void removeSubItem(int index) {
        }

        @Override
        public void removeAllSubItems() {
        }
*/
    }

    public void setOnItemActionListener(Item.onItemActionListener itemActionListener) {
        this.itemActionListener = itemActionListener;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="INTERFACES CUANDO SE ANIMA SHOW SUBITEMS DE UN ITEM">
    public void setToggleListLayout(ToggleListAnimationLayout toggleListLayout) {
        getToggleList().removeEventToggleSelected(this.toggleComponentsAdapter);
        this.toggleListLayout = toggleListLayout;
        this.toggleComponentsAdapter = new ToggleComponentsAdapter() {

            @Override
            public void onSelected(boolean selected) {
                System.out.println("Item class is: " + Item.this.getClass().getName() + ".onSelected()");
                Item.this.runEventOnAnimationItem_OnSelected(selected);
            }

            @Override
            public void onAnimatedStarted(boolean started) {
                System.out.println("Item class is: " + Item.this.getClass().getName() + ".onAnimatedStarted()");
                Item.this.runEventOnAnimationItem_OnStarted(started);
            }

            @Override
            public void onAnimated(float animated) {
                show(getSubItems(), animated, getToggleList().isSelected());
            }

            @Override
            public void onAnimatedRepeated(boolean repeated) {
                System.out.println("Item class is: " + Item.this.getClass().getName() + ".onAnimatedRepeated()");
                Item.this.runEventOnAnimationItem_OnRepeated(repeated);
            }

            @Override
            public void onAnimatedEnded(boolean ended) {
                System.out.println("Item class is: " + Item.this.getClass().getName() + ".onAnimatedEnded()");
                Item.this.runEventOnAnimationItem_OnEnded(ended);
            }

        };
        getToggleList().addEventToggleSelected(this.toggleComponentsAdapter);
    }
    
    protected void removeToggleComponentsAdapter() {
        this.toggleListLayout = null;
        getToggleList().removeEventToggleSelected(this.toggleComponentsAdapter);
    }

    private toggle.ToggleComponentsOnAnimationItemShowSubItemsListener events;
    
    /**
     * Para identificar las diferentes acciones de la animacion de show de los
     * subItems de un Item, como onSelected, onAnimatedStarted,
     * onAnimatedRepeated, onAnimatedEnded
     *
     * @param toggleOnAnimationItemListener
     */
    public void setToggleOnAnimationItemShowSubItemsListener(toggle.ToggleComponentsOnAnimationItemShowSubItemsListener toggleOnAnimationItemListener) {
       this.events = toggleOnAnimationItemListener;
    }

    private void runEventOnAnimationItem_OnSelected(boolean selected) {
        if (this.events !=null) {
        this.events.onSelected(selected);
        }
    }

    private void runEventOnAnimationItem_OnStarted(boolean started) {
        if (this.events !=null) {
            this.events.onAnimatedStarted(started);
        }
    }

    private void runEventOnAnimationItem_OnRepeated(boolean repeated) {
        if (this.events !=null) {
            this.events.onAnimatedRepeated(repeated);
        }
    }

    private void runEventOnAnimationItem_OnEnded(boolean ended) {
        if (this.events !=null) {
            this.events.onAnimatedEnded(ended);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="INTERFACES PARA HACER UP DOWM DE ITEM Y SUBITEMS">
    /**
     * Indentifica la accion - subir el item
     */
    public static final String ITEM_UP = "ITEM_UP";
    /**
     * Indentifica la accion - bajar el item
     */
    public static final String ITEM_DOWN = "ITEM_DOWN";

    private static ToggleListAnimationLayout.InternalOnUPDOWNComponentListener internalOnUPDOWNComponentListener;

    public void setOnInternal_UPDOWN_ItemListener(ToggleListAnimationLayout.InternalOnUPDOWNComponentListener internalOnUPDOWNComponentListener) {
        this.internalOnUPDOWNComponentListener = internalOnUPDOWNComponentListener;
        SubItem.setInternalOnUPDOWNComponentListener(this.internalOnUPDOWNComponentListener);
    }

    public void onMoveItem(String moveDirection) {
        if (this.internalOnUPDOWNComponentListener != null) {
            this.internalOnUPDOWNComponentListener.compMove(this, moveDirection);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="INTERFACES PARA HACER DRAG AND DROP DE ITEM Y SUBITEMS">
    private static ToggleListAnimationLayout.InternalOnDragComponentListener internalOnDragItemInterface;

    /**
     * Listener para la clase de tipo Item y SubItem para identificar las
     * acciones de onDrag, onDragready, onDragEnded
     *
     * @param internalOnDragItemInterface
     */
    public void setOnInternalDragItemListener(ToggleListAnimationLayout.InternalOnDragComponentListener internalOnDragItemInterface) {
        this.internalOnDragItemInterface = internalOnDragItemInterface;
        //colocando la interface para el subItem tambien
        SubItem.setInternalOnDragItemInterface(this.internalOnDragItemInterface);
    }

    public void onDrag(MouseEvent compMousePressed, boolean dragReady) {
        if (this.internalOnDragItemInterface != null) {
            //System.out.println("swing.togglelist.Item.onDrag(): ");
            /*if (this instanceof ListItem listI) {
                    System.out.println("swing.togglelist.Item.onDrag() "+listI.getTitulo()+" hashCode: "+listI.hashCode());
            }*/
            /**
             * Al enviar "this" que es un SubItem, se envia la clase que
             * implementa Item la que lanzó este método.
             */

            this.internalOnDragItemInterface.onDragItem(this, compMousePressed, dragReady);
        }
    }

    public void onDragReady(MouseEvent evt, boolean dragReady) {
        if (this.internalOnDragItemInterface != null) {

            this.internalOnDragItemInterface.onDragReady(evt, dragReady);
        }
    }

    public void onDragEnded(boolean dragReady) {
        if (this.internalOnDragItemInterface != null) {

            this.internalOnDragItemInterface.onDragEnded(dragReady);
        }
    }

    // </editor-fold>
    public static abstract class SubItem extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(230, 230, 230));
            g2.setColor(new Color(230, 230, 230));
            g2.drawLine(0, 0, 0, getHeight() - 1);
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            g2.dispose();
        }

        public abstract void iniciarAlgoEnEl_SubComponente();

        // <editor-fold defaultstate="collapse" desc="INTERFACES PARA HACER UP DOWM DE ITEM Y SUBITEMS">
        /**
         * Indentifica la accion - subir el item
         */
        public static final String ITEM_UP = "ITEM_UP";
        /**
         * Indentifica la accion - bajar el item
         */
        public static final String ITEM_DOWN = "ITEM_DOWN";

        private static ToggleListAnimationLayout.InternalOnUPDOWNComponentListener internalOnUPDOWNComponentListener;

        private static void setInternalOnUPDOWNComponentListener(ToggleListAnimationLayout.InternalOnUPDOWNComponentListener internalOnUPDOWNComponentListener) {
            SubItem.internalOnUPDOWNComponentListener = internalOnUPDOWNComponentListener;
        }

        public void onMoveSubItem(String moveDirection) {
            this.internalOnUPDOWNComponentListener.compMove(this, moveDirection);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapse" desc="INTERFACES PARA HACER DRAG AND DROP DE ITEM Y SUBITEMS">
        private static ToggleListAnimationLayout.InternalOnDragComponentListener internalOnDragItemInterface;

        private static void setInternalOnDragItemInterface(ToggleListAnimationLayout.InternalOnDragComponentListener internalOnDragItemInterface) {
            SubItem.internalOnDragItemInterface = internalOnDragItemInterface;
        }

        public void onDrag(MouseEvent compMousePressed, boolean dragReady) {
            if (this.internalOnDragItemInterface != null) {
                //System.out.println("swing.togglelist.Item.SubItem.onDrag(): SubItem Size: "+this.getSize()+" prefSize: "+this.getPreferredSize()+" maxSize: "+this.getMaximumSize()+" minSize: "+this.getMinimumSize());
                /**
                 * Al enviar "this" que es un SubItem, se envia la clase que
                 * implementa Item la que lanzó este método.
                 */
                this.internalOnDragItemInterface.onDragSubItem(this, compMousePressed, dragReady);
            }
        }

        public void onDragReady(MouseEvent evt, boolean dragReady) {
            if (this.internalOnDragItemInterface != null) {
                this.internalOnDragItemInterface.onDragReady(evt, dragReady);
            }
        }

        public void onDragEnded(boolean dragReady) {
            if (this.internalOnDragItemInterface != null) {
                this.internalOnDragItemInterface.onDragEnded(dragReady);
            }
        }
        // </editor-fold>
    }
}
