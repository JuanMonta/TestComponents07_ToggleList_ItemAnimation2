package toggle;

import com.crowie.SubListItem;
import com.crowie.ListItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class ToggleListAnimationLayout extends JComponent {

    public ToggleListAnimationLayout() {
        init();
        this.listaRoodItems = Collections.synchronizedList(new ArrayList<>());
        this.listAnimation_DRAGDROP_item = new ListAnimation_DRAGDROP_item(this);
        this.listAnimation_UP_DOWN_item = new ListAnimation_UP_DOWN_item(this);
    }
    private MigLayout layout;

    private void init() {
        setBackground(Color.WHITE);
        net.miginfocom.layout.LC lc = new net.miginfocom.layout.LC();
        layout = new MigLayout("wrap, fillx, inset 0, hidemode 1", "[fill]", "[top]0[]");
        setLayout(layout);
    }

    public MigLayout getMigLayout() {
        return layout;
    }

    private JScrollPane jScrollPaneLayoutContainer = null;

    protected JScrollPane getjScrollPaneLayoutContainer() {
        if (this.jScrollPaneLayoutContainer == null) {
            Container contenedor = this.getParent();
            if (contenedor != null) {
                System.out.println("Está dentro de un contenedor: " + contenedor.getClass().getName());
                // Si se agrega a un JScrollPane, el contenedor inmediato suele ser un JViewport
                if (contenedor instanceof JViewport) {
                    Container padreDelViewport = contenedor.getParent();
                    if (padreDelViewport instanceof JScrollPane scroll) {
                        System.out.println("El componente está dentro de un JScrollPane: " + scroll.getClass().getName());
                        this.jScrollPaneLayoutContainer = scroll;
                    }
                }
            } else {
                //System.out.println("El componente no está contenido en ningún contenedor.");
            }
        }
        return jScrollPaneLayoutContainer;
    }

    public void setjScrollPaneLayoutContainer(JScrollPane jScrollPaneLayoutContainer) {
        this.jScrollPaneLayoutContainer = jScrollPaneLayoutContainer;
    }

    // <editor-fold defaultstate="collapse" desc="CLASES PARA EL MOVIMIENTO DE LOS ITEMS">
    public final toggle.ListAnimation_DRAGDROP_item listAnimation_DRAGDROP_item;
    public final toggle.ListAnimation_UP_DOWN_item listAnimation_UP_DOWN_item;

    public void setToggleListAnimationLayout_DRAGDROP_Listener(toggle.toggleComponentsListeners.ToggleListAnimationLayout_DRAGDROP_Listener listAnimationLayout_DRAGDROP_Listener) {
        this.listAnimation_DRAGDROP_item.setToggleListAnimationLayout_DRAGDROP_Listener(listAnimationLayout_DRAGDROP_Listener);
    }

    public void setToggleListAnimationLayout_UPDOWN_Listener(toggle.toggleComponentsListeners.ToggleListAnimationLayout_UPDOWN_Listener listAnimationLayout_UPDOWN_Listener) {
        this.listAnimation_UP_DOWN_item.setToggleListAnimationLayout_UPDOWN_Listener(listAnimationLayout_UPDOWN_Listener);
    }

    // </editor-fold>
    protected final List<Component> listaRoodItems;

    protected synchronized void actualizarListaRoodItems() {
        this.listaRoodItems.clear();
        for (Component component : this.getComponents()) {
            if (component instanceof Item item) {
                item.removeAllSubItems();
                this.listaRoodItems.add(item);
            }
            if (component instanceof Item.SubItem) {
                ((Item) this.listaRoodItems.get(this.listaRoodItems.size() - 1)).addSubItem(component);
            }
        }
    }
    /**
     * Para validar si en el momento se está llevando acabo una adición de un
     * componente a este layout.
     */
    protected boolean isRemovingComponent = false;
    protected boolean isAddingSubItemToItem = false;

    // <editor-fold defaultstate="collapse" desc="INTERNAL INTERFACE SHOW ANIMATION ACTIONS">
    private toggle.ToggleComponentsOnAnimationItemShowSubItemsListener internal_ToggleOnAnimationItemShowSubItemsListener;

    /**
     * Para identificar las diferentes acciones de la animacion de show de los
     * subItems de un Item, como onSelected, onAnimatedStarted,
     * onAnimatedRepeated, onAnimatedEnded
     *
     * @param internal_ToggleOnAnimationItemShowSubItemsListener
     */
    protected synchronized void setInternal_ToggleOnAnimationItemShowSubItemsListener(ToggleComponentsOnAnimationItemShowSubItemsListener internal_ToggleOnAnimationItemShowSubItemsListener) {
        this.internal_ToggleOnAnimationItemShowSubItemsListener = internal_ToggleOnAnimationItemShowSubItemsListener;
        for (Component component : this.getListOnlyItems()) {
            ((Item) component).setToggleOnAnimationItemShowSubItemsListener(this.internal_ToggleOnAnimationItemShowSubItemsListener);
        }
    }

    private synchronized void addIternalToggleOnAnimationItemShowSubItemsListener(Component comp) {
        if (this.internal_ToggleOnAnimationItemShowSubItemsListener != null) {
            if (comp != null && comp instanceof Item item) {
                item.setToggleOnAnimationItemShowSubItemsListener(this.internal_ToggleOnAnimationItemShowSubItemsListener);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapse" desc="INTERNAL INTERFACES UP DOWN ITEMS Y SUBITEMS">
    protected interface InternalOnUPDOWNComponentListener {

        public void compMove(Item item, String moveDirection);

        public void compMove(Item.SubItem subItem, String moveDirection);
    }

    private final InternalOnUPDOWNComponentListener internalOnUPDOWNComponentListener = new InternalOnUPDOWNComponentListener() {
        @Override
        public void compMove(Item item, String moveDirection) {
            if (ToggleListAnimationLayout.this.internalMoveUPDOWNComponentAdapter != null) {
                int itemIndex = ToggleListAnimationLayout.this.getListComponentIndex(item);
                ToggleListAnimationLayout.this.internalMoveUPDOWNComponentAdapter.componentMove(itemIndex, moveDirection);
            }
        }

        @Override
        public void compMove(Item.SubItem subItem, String moveDirection) {
            if (ToggleListAnimationLayout.this.internalMoveUPDOWNComponentAdapter != null) {
                int itemIndex = ToggleListAnimationLayout.this.getListComponentIndex(subItem);
                ToggleListAnimationLayout.this.internalMoveUPDOWNComponentAdapter.componentMove(itemIndex, moveDirection);
            }
        }
    };

    protected toggle.MoveUPDOWNComponentAdapter internalMoveUPDOWNComponentAdapter;

    protected void setInternalOnMoveUPDOWNComponentListener(MoveUPDOWNComponentAdapter moveUPDOWNComponentAdapter) {
        this.internalMoveUPDOWNComponentAdapter = moveUPDOWNComponentAdapter;
        for (Component rootItem : this.listaRoodItems) {
            ((Item) rootItem).setOnInternal_UPDOWN_ItemListener(this.internalOnUPDOWNComponentListener);
        }
    }

    private void setInternalOnMoveUPDOWNComponentListener(Component comp) {
        if (comp != null && comp instanceof Item item) {
            item.setOnInternal_UPDOWN_ItemListener(this.internalOnUPDOWNComponentListener);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="INTERFACES DRAG AND DROP DE ITEMS Y SUBITEMS">
    /**
     * Iterfaz solo usada dentro de esta clase para funcionar en conjunto de
     * otras interfaces públicas.
     */
    protected interface InternalOnDragComponentListener {

        /**
         *
         * @param item El item objetivo para mover.
         * @param compMousePressed el evento de mouse click para identificar el
         * drag sobre el item.
         * @param dragReady se activa despues de un determinado tiempo para
         * informar de que se está listo para mover el item.
         */
        public void onDragItem(Item item, MouseEvent compMousePressed, boolean dragReady);

        /**
         *
         * @param SubItem El SubItem objetivo para mover.
         * @param compMousePressed el evento de mouse click para identificar el
         * drag sobre el SubItem.
         * @param dragReady se activa despues de un determinado tiempo para
         * informar de que se está listo para mover el SubItem.
         */
        public void onDragSubItem(Item.SubItem SubItem, MouseEvent compMousePressed, boolean dragReady);

        /**
         * Captura el mouse event y si ya se está preparado para moverse el
         * Componente objetivo
         *
         * @param evt
         * @param dragReady
         */
        public void onDragReady(MouseEvent evt, boolean dragReady);

        /**
         * El movimiento de drag del mouse ha terminado.
         *
         * @param dragReady
         */
        public void onDragEnded(boolean dragReady);
    }

    private final InternalOnDragComponentListener internalOnDragComponentInterface = new InternalOnDragComponentListener() {
        @Override
        public void onDragItem(Item item, MouseEvent compMousePressed, boolean dragReady) {
            if (ToggleListAnimationLayout.this.dragComponentListener != null) {
                int index = ToggleListAnimationLayout.this.getListOnlyItemIndex(item);
                ToggleListAnimationLayout.this.dragComponentListener.onDragItem(index, item, compMousePressed, dragReady);
            }
        }

        @Override
        public void onDragSubItem(Item.SubItem SubItem, MouseEvent compMousePressed, boolean dragReady) {
            if (ToggleListAnimationLayout.this.dragComponentListener != null) {
                Item parent = ToggleListAnimationLayout.this.getListSubItemParent(SubItem);
                int index = ToggleListAnimationLayout.this.getListSubItemIndexFromParent(parent, SubItem);
                ToggleListAnimationLayout.this.dragComponentListener.onDragSubItem(parent, index, SubItem, compMousePressed, dragReady);
            }
        }

        @Override
        public void onDragReady(MouseEvent evt, boolean dragReady) {
            if (ToggleListAnimationLayout.this.dragComponentListener != null) {
                ToggleListAnimationLayout.this.dragComponentListener.onDragReady(evt, dragReady);
            }
        }

        @Override
        public void onDragEnded(boolean dragReady) {
            if (ToggleListAnimationLayout.this.dragComponentListener != null) {
                ToggleListAnimationLayout.this.dragComponentListener.onDragEnded(dragReady);
            }
        }
    };

    private toggle.MoveDragComponentListener dragComponentListener;

    /**
     * Listener para la clase de tipo Item y SubItem para identificar las
     * acciones de onDrag, onDragready, onDragEnded
     *
     * @param dragComponentListener
     */
    protected void setOnDragComponentListener(MoveDragComponentListener dragComponentListener) {
        this.dragComponentListener = dragComponentListener;
        for (Component rootItem : this.listaRoodItems) {
            ((Item) rootItem).setOnInternalDragItemListener(this.internalOnDragComponentInterface);
        }
    }

    private void setOnDragComponentListener(Component comp) {
        if (comp != null && comp instanceof Item item) {
            item.setOnInternalDragItemListener(this.internalOnDragComponentInterface);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="Override metodos add remove revalidate">
    @Override
    public synchronized Component add(Component comp) {
        Component com = super.add(comp);
        if (!java.beans.Beans.isDesignTime()) {
            this.actualizarListaRoodItems();
            this.addIternalToggleOnAnimationItemShowSubItemsListener(comp);
            this.setOnDragComponentListener(comp);
            this.setInternalOnMoveUPDOWNComponentListener(comp);
            this.checkComponent(comp);
        }
        return com;
    }

    @Override
    public synchronized Component add(Component comp, int index) {
        if (comp != null) {
            System.out.println("toggle.ToggleListAnimationLayout.add()-> " + comp.toString() + " Absolte_Index: " + index);
            if (!java.beans.Beans.isDesignTime()) {
                try {
                    Component com = this.verificarComponenteAgregado(comp, index);
                    this.actualizarListaRoodItems();
                    this.addIternalToggleOnAnimationItemShowSubItemsListener(comp);
                    this.setOnDragComponentListener(comp);
                    this.setInternalOnMoveUPDOWNComponentListener(comp);
                    this.checkComponent(comp);
                    return com;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } finally {
                    return null;
                }
            } else {
                Component com = super.add(comp, index);
                return com;
            }

        } else {
            return null;
        }
    }

    @Override
    public synchronized void remove(Component comp) {
        this.isRemovingComponent = true;
        super.remove(comp);
        if (!java.beans.Beans.isDesignTime()) {
            this.actualizarListaRoodItems();
            //this.imprimirLista("toggle.ToggleListAnimationLayout.remove(comp)");
        }
        this.isRemovingComponent = false;
    }

    @Override
    public synchronized void remove(int index) {
        super.remove(index);
        if (!java.beans.Beans.isDesignTime()) {
            if (!this.isRemovingComponent) {
            }
            this.actualizarListaRoodItems();
            //this.imprimirLista("toggle.ToggleListAnimationLayout.remove(index "+index+")");
        }
    }

    @Override
    public synchronized void revalidate() {
        super.revalidate();
        if (!java.beans.Beans.isDesignTime()) {
            //this.imprimirComponentsToggleList(this.getComponents());
            this.actualizarListaRoodItems();
            //this.ordenarComponentes();
            //this.imprimirLista("toggle.ToggleListAnimationLayout.revalidate()");
            this.actualizarComponentes();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="Metodos cuando se agrega un componente">
    private synchronized Component verificarComponenteAgregado(Component comp, int index) {
        Component c = null;
        int absoluteIndex = -1;
        if (index > -1 && index < this.getComponents().length) {
            if (comp instanceof Item) {

                List<Component> items = this.getListOnlyItems();

                if (index < (items.size())) {
                    absoluteIndex = this.getListComponentIndex(items.get(index));
                } else {
                    absoluteIndex = -1;
                }
                c = super.add(comp, absoluteIndex);

            } else if (comp instanceof Item.SubItem) {
                c = super.add(comp, index);
            }

        } else {
            c = super.add(comp, -1);
        }
        return c;
    }

    //revisado
    public synchronized void addItem(Item item) {
        super.add(item);
    }

    //revisado
    public synchronized void addItem(Item item, int index) {
        this.verificarComponenteAgregado(item, index);
    }

    public synchronized void addItemAtItemPos(Item oldItem, Item newItem) {
        int index = this.getListComponentIndex(oldItem);
        this.verificarComponenteAgregado(newItem, index);
    }

    //revisado
    public synchronized void addSubItem(Item.SubItem subItem) {
        super.add(subItem);
    }

    //revisado
    public synchronized void addSubItem(Item.SubItem subItem, int index) {
        this.verificarComponenteAgregado(subItem, index);
    }

    //revisado
    public synchronized void addSubItem(int indexItem, Item.SubItem subItem, int indexSubItem) {
        this.verificarComponenteAgregado(subItem, indexItem + indexSubItem + 1);
    }

    public synchronized void addSubItem(Item parentItem, Item.SubItem subItem) {
        int itemIndex = this.getListComponentIndex(parentItem) + 1;
        this.verificarComponenteAgregado(subItem, itemIndex);
    }

    //revisado
    public synchronized void addSubItem(Item parentItem, Item.SubItem subItem, int indexSubItem) {
        int itemIndex = this.getListComponentIndex(parentItem) + 1;
        this.verificarComponenteAgregado(subItem, itemIndex + itemIndex);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapse" desc="Metodos cuando se elimina un componente">
    public synchronized void removeItem(Item item) {
        this.remove(item);
    }

    public synchronized void removeItem(int index) {
        this.remove(index);
    }

    public synchronized void removeSubItem(Item.SubItem subItem) {
        this.remove(subItem);
    }

    public synchronized void removeSubItem(int index) {
        this.remove(index);
    }

    public synchronized void removeSubItem(Item parentItem, Item.SubItem subItem) {
        this.remove(subItem);
    }

    public synchronized void removeSubItem(int parentItemIndex, Item.SubItem subItem) {
        this.remove(subItem);
    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapse" desc="Metodos para actualizar cada uno de los componentes">
    public synchronized void actualizarComponentes() {
        this.actualizarComponentes(0);
    }

    public synchronized void actualizarComponentes(int index) {
        if (index >= 0) {
            //System.out.println(ToggleListAnimationLayout.this.getClass().getName() + "-> actualizando los componentes a partir desde el index: " + index);
            for (int i = index; i < this.getComponentCount(); i++) {
                Component comp = this.getComponent(i);
                if (comp instanceof toggle.Item) {
                    ((Item) (comp)).iniciarAlgoEnElComponente();
                } else if (comp instanceof toggle.Item.SubItem) {
                    ((Item.SubItem) (comp)).iniciarAlgoEnEl_SubComponente();
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapse" desc="Metodos de obtención de índices de componentes">
    /**
     * Obtener el index absoluto del componente en la Lista.
     *
     * @param comp Item o Subitem a buscar su index absoluto.
     * @return
     */
    public synchronized int getListComponentIndex(Component comp) {
        int index = -1;
        for (Component component : this.getComponents()) {
            index++;
            if (component.equals(comp)) {
                break;
            }
        }
        return index;
    }

    /**
     * Devuelve el componente de la lista del index absoluto en la pila de
     * componentes.
     *
     * @param index indice obsoluto de la pila de componentes.
     * @return
     */
    public synchronized Component getListComponentAtIndex(int index) {
        return index < this.getComponents().length ? this.getComponent(index) : null;
    }

    /**
     * Listado de todos los componentes de tipo Item.
     *
     * @return
     */
    public synchronized List<Component> getListOnlyItems() {
        List<Component> items = new ArrayList<>();
        for (Component comp : listaRoodItems) {
            if (comp instanceof Item) {
                items.add(comp);
            }
        }
        return items;
    }

    /**
     * Devuelte el componente de tipo Item por el index de la lista de Items.
     *
     * @param index indice de la lista a recuperar o null si el index supera el
     * tamaño de la lista.
     * @return componente del tipo Item.
     */
    public synchronized Item getListOnlyItemAtIndex(int index) {
        Item item = null;
        if (index < this.getListOnlyItems().size()) {
            item = (Item) this.getListOnlyItems().get(index);
        }
        return item;
    }

    /**
     * Devuelve el componente de tipo Item que es el parent del SubItem
     * objetivo.
     *
     * @param subItem componente SubItem al cual encontrar su parent.
     * @return null si no existe.
     */
    public synchronized Item getListSubItemParent(Item.SubItem subItem) {
        Item item = null;
        for (Component comp : this.getListOnlyItems()) {
            if (((Item) comp).getSubItems().contains(subItem)) {
                item = (Item) comp;
                break;
            }
        }
        return item;
    }

    /**
     * Devuel el componente de tipo SubItem de la lista de SubItems.
     *
     * @param index
     * @return null si no lo encuentra.
     */
    public synchronized Item.SubItem getListOnlySubItemAtIndex(int index) {
        Item.SubItem subItem = null;
        if (index > -1 && index < this.getListOnlySubItems().size()) {
            subItem = (Item.SubItem) this.getListOnlySubItems().get(index);
        }
        return subItem;
    }

    /**
     * Busca si el componente corresponde a un Item y devuelve su index
     * correspondiente de la lista de solo Items.
     *
     * @param comp Componente a buscar en la lista de Items.
     * @return Item de la lista si lo encuentra, de lo contrario -1;
     */
    public synchronized int getListOnlyItemIndex(Component comp) {
        return this.getListOnlyItems().indexOf(comp);
    }

    /**
     * Devuelve una lista de todos los subItems.
     *
     * @return lista de subItems
     */
    public synchronized List<Component> getListOnlySubItems() {
        List<Component> subItems = new ArrayList<>();
        for (Component comp : this.getComponents()) {
            if (comp instanceof Item.SubItem) {
                subItems.add(comp);
            }
        }
        return subItems;
    }

    /**
     * Busca en la lista de subItems el componente especificado y retorna su
     * index absoluto con respecto a la lista de SubItems, -1 si no lo
     * encuentra.
     *
     * @param subItem Componente subItem a buscar en la lista de SubItems.
     * @return El index absoluto con respecto a la lista de SubItems, -1 si no
     * encuentra el componente especificado.
     */
    public synchronized int getListSubItemAbsoluteIndex(Component subItem) {
        int index = -1;
        if (this.getListOnlySubItems().contains(subItem)) {
            index = this.getListComponentIndex(subItem);
        }
        return index;
    }

    /**
     * Busca en la lista de subItems el componente especificado y retorna su
     * index relativo con respecto a la lista de SubItems, -1 si no lo
     * encuentra.
     *
     * @param comp Componente subItem a buscar en la lista de SubItems.
     * @return El index relativo con respecto a la lista de SubItems, -1 si no
     * encuentra el componente especificado.
     */
    public synchronized int getListSubItemRelativeIndex(Component comp) {
        return this.getListOnlySubItems().indexOf(comp);
    }

    public synchronized int getListSubItemIndexFromParent(Item parent, Item.SubItem subItem) {
        int index = -1;
        if (this.getListOnlyItems().contains(parent)) {
            index = parent.getSubItems().indexOf(subItem);
        }
        return index;
    }

    // </editor-fold>
    private synchronized void setEnabledItemsRecursively(Component comp, boolean enabled) {
        comp.setEnabled(enabled);
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                setEnabledItemsRecursively(child, enabled);
            }
        }
    }

    public synchronized void setItemsEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component comp : getComponents()) {
            setEnabledItemsRecursively(comp, enabled);
        }
    }

    private synchronized void checkComponent(Component comp) {
        if (comp instanceof Item item) {
            item.iniciarAlgoEnElComponente();
            if (item.getSubItems() != null) {
                item.setToggleListLayout(this);
                for (Component com : item.getSubItems()) {
                    if (item.getToggleList().isSelected()) {
                        super.add(com);
                    } else {
                        super.add(com, "h 0!");
                    }
                    if (com instanceof Item) {
                        checkComponent(com);
                    } else if (com instanceof Item.SubItem subItem) {
                        subItem.iniciarAlgoEnEl_SubComponente();
                    }
                }
                revalidate();
                repaint();
            }
        } else if (comp instanceof Item.SubItem subItem) {
            subItem.iniciarAlgoEnEl_SubComponente();
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (isOpaque()) {
            grphcs.setColor(getBackground());
            grphcs.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(grphcs);
    }

    @Override
    public synchronized Component getComponentAt(Point p) {
        Component c = null;
        //System.out.println("swing.togglelist.ToggleListAnimationLayout.getComponentAt() compCount: " + this.getComponents().length);
        for (Component comp : this.getComponents()) {
            if (comp.getBounds().contains(p)) {
                //System.out.println("swing.togglelist.ToggleListAnimationLayout.getComponentAt(): compHashCode: " + comp.hashCode());
                c = comp;
                break;
            }
        }
        //System.out.println("swing.togglelist.ToggleListAnimationLayout.getComponentAt() compCount: " + this.getComponents().length);
        return c;
    }

    @Override
    public synchronized Component[] getComponents() {
        return super.getComponents();
    }

    /**
     * Para ordenar la lista principal de Items en el orden en la que se
     * encuentran dentro de este Layout.
     */
    private synchronized void ordenarComponentes() {
        // Obtener el orden actual de los componentes en MigLayout
        Component[] ordenMigLayout = this.getComponents();

        // Crear un mapa de referencia para la posición de cada componente
        Map<Component, Integer> posiciones = new HashMap<>();
        for (int i = 0; i < ordenMigLayout.length; i++) {
            posiciones.put(ordenMigLayout[i], i);
        }

        // Ordenar la lista principal `listaRoodItems` según el orden de MigLayout
        listaRoodItems.sort(Comparator.comparingInt(posiciones::get));
        for (Component rootItem : this.listaRoodItems) {
            if (((Item) rootItem).getSubItems() != null) {
                ((Item) rootItem).getSubItems().sort(Comparator.comparingInt(posiciones::get));
            }
        }

    }

    private synchronized void imprimirLista(String... text) {
        if (text != null) {
            for (String string : text) {
                System.out.println(this.getClass().getName() + "-> " + string);
            }
        }
        int cont = -1;
        for (Component c : this.getComponents()) {
            cont++;
            if (c instanceof ListItem) {
                ListItem item = (ListItem) c;
                System.out.println(this.getClass().getName() + "-> index: " + cont + "  " + item.getTitulo() + " hashCode: " + item.hashCode());
            } else if (c instanceof SubListItem) {
                SubListItem subItem = (SubListItem) c;
                System.out.println(this.getClass().getName() + "-> \tindex: " + cont + "  " + subItem.getTitulo() + " hashCode: " + subItem.hashCode());
            }
        }
        /*for (int i = 0; i < this.listaRoodItems.size(); i++) {
            Component c = this.listaRoodItems.get(i);
            if (c instanceof ListItem) {
                ListItem item = (ListItem) c;
                System.out.println(this.getClass().getName() + "-> index: " + i + "  " + item.getTitulo());
                for (int j = 0; j < item.getSubItems().size(); j++) {
                    SubListItem subItem = (SubListItem) item.getSubItems().get(j);
                    System.out.println(this.getClass().getName() + "-> \tindex: " + j + "  " + subItem.getTitulo());
                }
            } else if (c instanceof SubListItem) {
                SubListItem subItem = (SubListItem) c;
                System.out.println(this.getClass().getName() + "-> \tindex: " + i + "  " + subItem.getTitulo());
            }
        }*/
        System.out.println("----------------------------------------------------");
    }

}
