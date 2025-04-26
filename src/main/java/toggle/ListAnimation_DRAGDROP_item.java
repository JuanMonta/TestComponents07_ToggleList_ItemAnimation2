package toggle;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import com.crowie.ListItem;
import com.crowie.ListSubItem_Blank;
import com.crowie.SubListItem;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

class ListAnimation_DRAGDROP_item {

    private final toggle.ToggleListAnimationLayout toggleListAnimationLayout;
    /**
     * indentificar el primer click sostenido sobre el item
     */
    private Point initialClick;
    /**
     * para calcular la diferencia entre el click y el alto del item
     */
    private int yDiff = 0;
    /**
     * para calcular la diferencia entre el click y el ancho del item
     */
    private int xDiff = 0;
    /**
     * Almacena la posición Y anterior, nos servirá para saber si se está
     * moviendo hacia arriba o hacia abajo el mouse.
     */
    private int lastY = -1;
    /**
     * el item al cual se clickeó y cuyo es el item que se desea mover.
     */
    private int itemClickIndex = -1;
    /**
     * según el movimiento del mouse se identifcará el item debajo del click, el
     * cual servirá como identificador del lugar de donde se moverá el item que
     * se desea mover.s
     */
    private int itemMoveIndex = -1;
    /**
     * emula que el item que se desea mover paresca flotar, ésta ventana es la
     * que se moverá para identificar el index final para el item que se desea
     * mover.
     */
    private final JWindow jWindow;
    /**
     * coloca un item blanco justo en el index donde se va moviendo el jwindow,
     * creando un espacio en blanco para dar a entender que ese lugar en blanco
     * es donde se reubicarpa el item que se desea mover.
     */
    private Component item_Blank;
    /**
     * se usa para que el item clickeado y el cual es el que se va a mover, se
     * copia para añadirlo al jwindow que simula que el item está flotando.
     */
    private Component itemToJWindow;
    /**
     * se usa para guardar el item que se desea mover, y se usará para
     * reubicarlo en el nuevo index.
     */
    private Component itemToClone;
    /**
     * Guarda el evento de click del item para rastrear su drag.
     */
    private MouseEvent mousePressedEvent;
    /**
     * Guarda el estado del boton que muestra los subItems de un Item
     */
    private boolean isItemToDragToggleButtonSelected;
    /**
     * Guarda todos los subItems que tenga el item que se moverá.
     */
    private final List<Component> listSubItemsFromItemToDrag;
    /**
     * Almacena todos los items junto con sus subItems, excepto el item que
     * habrá de mover.
     */
    private final List<AlmacenamientoComponentesLista> listalmacenamientoComponentes;

    protected toggle.toggleComponentsListeners.ToggleListAnimationLayout_DRAGDROP_Listener listAnimationLayout_DRAGDROP_Listener;

    protected void setToggleListAnimationLayout_DRAGDROP_Listener(toggle.toggleComponentsListeners.ToggleListAnimationLayout_DRAGDROP_Listener listAnimationLayout_DRAGDROP_Listener) {
        this.listAnimationLayout_DRAGDROP_Listener = listAnimationLayout_DRAGDROP_Listener;
    }

    /**
     * Clase para guardar las referencias de los items y sus respectivos
     * subItems dentro de una lista usando esta clase.
     */
    private final class AlmacenamientoComponentesLista {

        private boolean toogleSelection;

        private Component item;

        private final List<Component> subItems;

        public AlmacenamientoComponentesLista() {
            subItems = new CopyOnWriteArrayList<>();
        }

        public boolean isToogleSelection() {
            return toogleSelection;
        }

        public void setToogleSelection(boolean toogleSelection) {
            this.toogleSelection = toogleSelection;
        }

        public Component getItem() {
            return item;
        }

        public void setItem(Component item) {
            this.item = item;
        }

        public List<Component> getSubItems() {
            return subItems;
        }

        public void setSubItems(List<Component> subItems) {
            this.subItems.addAll(subItems);
        }
    }

    public ListAnimation_DRAGDROP_item(toggle.ToggleListAnimationLayout toggleListAnimationLayout) {
        this.toggleListAnimationLayout = toggleListAnimationLayout;
        this.listSubItemsFromItemToDrag = new CopyOnWriteArrayList<>();
        this.listalmacenamientoComponentes = new CopyOnWriteArrayList<>();
        this.onAnimationListeners();
        this.jWindow = new JWindow();
        this.itemToJWindow = new Component() {
        };
        this.jWindow.add(itemToJWindow);
    }

    private void onAnimationListeners() {
        this.toggleListAnimationLayout.setOnDragComponentListener(new MoveDragComponentAdapter() {

            @Override
            public void onDragItem(int indexItem, Item item, MouseEvent compMousePressed, boolean dragReady) {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragItem()");
                if (ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener !=null) {
                    ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener.onDragItem(indexItem, item, compMousePressed, dragReady);
                }
                if (item instanceof ListItem litem) {
                    System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragItem() item: " + litem.getTitulo() + " hashCode: " + litem.hashCode() + " loctOnScrn: " + litem.getLocationOnScreen());
                }
                if (dragReady) {
                    ListAnimation_DRAGDROP_item.this.itemClickIndex = indexItem;
                    ListAnimation_DRAGDROP_item.this.mousePressedEvent = compMousePressed;
                    ListAnimation_DRAGDROP_item.this.itemToClone = item;
                    ListAnimation_DRAGDROP_item.this.verificarComponenteToDrag(ListAnimation_DRAGDROP_item.this.itemToClone);
                }
            }

            @Override
            public void onDragSubItem(Item parent, int indexSubItem, Item.SubItem SubItem, MouseEvent compMousePressed, boolean dragReady) {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragSubItem()");
                if (ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener !=null) {
                    ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener.onDragSubItem(parent, indexSubItem, SubItem, compMousePressed, dragReady);
                }
                if (SubItem instanceof SubListItem sItem) {
                    System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragSubItem() Subitem: " + sItem.getTitulo() + " hashCode: " + sItem.hashCode());
                }
                if (dragReady) {
                    ListAnimation_DRAGDROP_item.this.itemClickIndex = indexSubItem;
                    ListAnimation_DRAGDROP_item.this.mousePressedEvent = compMousePressed;
                    ListAnimation_DRAGDROP_item.this.itemToClone = SubItem;
                    ListAnimation_DRAGDROP_item.this.verificarComponenteToDrag(ListAnimation_DRAGDROP_item.this.itemToClone);
                }
            }

            @Override
            public void onDragReady(MouseEvent evt, boolean dragReady) {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragReady()-> ready: " + dragReady + " point: " + evt.getComponent().getLocationOnScreen());
                if (ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener !=null) {
                    ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener.onDragReady(evt, dragReady);
                }
                if (dragReady) {
                    ListAnimation_DRAGDROP_item.this.mousePressedEvent = evt;
                    ListAnimation_DRAGDROP_item.this.mouseDraggedDragComponent(evt);
                }
            }

            @Override
            public void onDragEnded(boolean dragReady) {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".onDragEnded()-> ready: " + dragReady);
                if (ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener !=null) {
                    ListAnimation_DRAGDROP_item.this.listAnimationLayout_DRAGDROP_Listener.onDragEnded(dragReady);
                }
                ListAnimation_DRAGDROP_item.this.mouseReleasedDragComponent();
            }

            private void simulateMouse(Component target, Component container) {
                try {
                    // Obtener las coordenadas absolutas del centro del componente
                    Point locationOnScreen = target.getLocationOnScreen();
                    int centerX = locationOnScreen.x + target.getWidth() / 2;
                    int centerY = locationOnScreen.y + target.getHeight() / 2;
                    System.out.println("locationOnScreen: " + locationOnScreen);
                    // Mover el mouse al centro del componente
                    java.awt.Robot robot = new Robot();
                    robot.mouseMove(centerX, centerY);

                    // Obtener las coordenadas relativas al contenedor
                    Point relativePoint = SwingUtilities.convertPoint(target, target.getWidth() / 2, target.getHeight() / 2, container);

                    System.out.println("Coordenadas relativas al contenedor: " + relativePoint);

                    // Simular un evento de mouse
                    MouseEvent simulatedMoveMouseEvent = new MouseEvent(
                            target,
                            MouseEvent.MOUSE_MOVED,
                            System.currentTimeMillis(),
                            0,
                            relativePoint.x,
                            relativePoint.y,
                            0,
                            false
                    );
                    // Despachar el evento para que el componente lo maneje
                    target.dispatchEvent(simulatedMoveMouseEvent);
                    System.out.println("Evento Mouse Move despachado: " + simulatedMoveMouseEvent.getPoint());

                    // Crear un evento de presionar el botón del mouse
                    MouseEvent pressEvent = new MouseEvent(
                            container,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            0, // Modificadores
                            target.getWidth() / 2, // Coordenada X relativa
                            target.getHeight() / 2, // Coordenada Y relativa
                            1, // Número de clics
                            false // Botón secundario
                    );

                    // Despachar el evento de presionar
                    //container.dispatchEvent(pressEvent);
                    //ListAnimation_DRAGDROP_item.this.mouseAdapter.mousePressed(pressEvent);
                    System.out.println("Evento de presionar despachado: " + pressEvent.getPoint());

                    // Ahora el usuario puede mover el mouse libremente
                    System.out.println("El mouse está listo para moverse a voluntad.");

                    MouseEvent dragEvent = new MouseEvent(
                            container,
                            MouseEvent.MOUSE_DRAGGED,
                            System.currentTimeMillis() + 10,
                            0, // Modificadores
                            (target.getWidth() / 2) + 1, // Coordenada X relativa
                            (target.getHeight() / 2) + 10, // Coordenada Y relativa
                            1, // Número de clics
                            false // Botón secundario
                    );

                    // Despachar el evento de arrastre
                    //container.dispatchEvent(dragEvent);
                    //ListAnimation_DRAGDROP_item.this.motionAdapter.mouseDragged(dragEvent);
                    System.out.println("Evento de arrastre despachado: " + dragEvent.getPoint());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        );

    }

    private void verificarComponenteToDrag(Component comp) {
        if (comp instanceof Item item) {
            this.listSubItemsFromItemToDrag.clear();
            this.isItemToDragToggleButtonSelected = item.getToggleList().isSelected();
            if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
                this.listSubItemsFromItemToDrag.addAll(item.getSubItems());
                if (this.isItemToDragToggleButtonSelected == true) {
                    item.setToggleSelection(false, true);
                }
            }
            this.mousePressedDragComponent();
        } else if (comp instanceof Item.SubItem) {
            this.mousePressedDragComponent();
        }
    }

    private void mousePressedDragComponent() {
        try {

            Component compToDrag = ListAnimation_DRAGDROP_item.this.itemToClone;
            MouseEvent mousePressed = ListAnimation_DRAGDROP_item.this.mousePressedEvent;

            ListAnimation_DRAGDROP_item.this.listalmacenamientoComponentes.clear();
            if (compToDrag instanceof Item) {
                for (Component item : ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListOnlyItems()) {
                    if (!item.equals(compToDrag)) {
                        AlmacenamientoComponentesLista almacenamiento = new AlmacenamientoComponentesLista();
                        almacenamiento.setToogleSelection(((Item) item).getToggleList().isSelected());
                        almacenamiento.setItem(item);
                        almacenamiento.setSubItems(((Item) item).getSubItems());
                        ((Item) item).setToggleSelectionSinEventNotification(false, true);
                        ListAnimation_DRAGDROP_item.this.listalmacenamientoComponentes.add(almacenamiento);

                        for (Component subItem : almacenamiento.getSubItems()) {
                            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.remove(subItem);
                        }
                    }
                }

            }

            /*boolean scrollVerticalIsVisible = this.toggleListAnimationLayout.getjScrollPaneLayoutContainer().getVerticalScrollBar().isVisible();
            int scrollVerticalAncho = 0;
            System.out.println(this.getClass().getName() + "-> scroll Vertical is Visible: " + scrollVerticalIsVisible);
            
            if (scrollVerticalIsVisible) {
                scrollVerticalAncho = this.toggleListAnimationLayout.getjScrollPaneLayoutContainer().getVerticalScrollBar().getWidth();
                System.out.println(this.getClass().getName() + "-> ancho del scroll vertical: " + scrollVerticalAncho);
            }*/
            //location del componente dentro de su contenedor que seria el toggleList
            Point relativePoint = SwingUtilities.convertPoint(mousePressed.getComponent(), mousePressed.getPoint(), ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout);

            //guardamos el click sobre el item
            ListAnimation_DRAGDROP_item.this.initialClick = compToDrag.getLocationOnScreen();
            //asignamos el lugar para mostrar el jwindow en la misma xy del item
            ListAnimation_DRAGDROP_item.this.jWindow.setLocation(compToDrag.getLocationOnScreen());
            //le damos las mismas dimensiones del item
            ListAnimation_DRAGDROP_item.this.jWindow.setMinimumSize(compToDrag.getMinimumSize());
            ListAnimation_DRAGDROP_item.this.jWindow.setMaximumSize(compToDrag.getMaximumSize());
            ListAnimation_DRAGDROP_item.this.jWindow.setPreferredSize(compToDrag.getPreferredSize());
            ListAnimation_DRAGDROP_item.this.jWindow.setSize(compToDrag.getSize());
            ListAnimation_DRAGDROP_item.this.jWindow.setAlwaysOnTop(true);
            ListAnimation_DRAGDROP_item.this.jWindow.setVisible(true);
            ListAnimation_DRAGDROP_item.this.jWindow.revalidate();
            ListAnimation_DRAGDROP_item.this.jWindow.repaint();
            JWindow jwindowComp = ListAnimation_DRAGDROP_item.this.jWindow;
            System.out.println("compToDrag: prefSize: " + compToDrag.getPreferredSize() + " size: " + compToDrag.getSize() + " minSize: " + compToDrag.getMinimumSize() + " maxSize: " + compToDrag.getMaximumSize());
            System.out.println("jwindow   : prefSize: " + jwindowComp.getPreferredSize() + " size: " + jwindowComp.getSize() + " minSize: " + jwindowComp.getMinimumSize() + " maxSize: " + jwindowComp.getMaximumSize());

            System.out.println("c: " + compToDrag.getLocationOnScreen() + " point: " + jWindow.getLocation() + " " + ListAnimation_DRAGDROP_item.this.jWindow.isVisible() + " hashCode: " + compToDrag.hashCode());

            //creamos de igual forma las dimensiones para el item en blanco
            if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item) {

                ListAnimation_DRAGDROP_item.this.item_Blank = new ListItem_Blank();
                ListAnimation_DRAGDROP_item.this.item_Blank.setPreferredSize(compToDrag.getPreferredSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setSize(compToDrag.getSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setMinimumSize(compToDrag.getMinimumSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setMaximumSize(compToDrag.getMaximumSize());

            } else if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item.SubItem) {
                /**
                 * aqui no se que pasa que no toma un tamaño grande como si lo
                 * hace el de arriba, aparece muy pequeño el sublistItem Blank
                 * en el toggle layout
                 */
                ListAnimation_DRAGDROP_item.this.item_Blank = new ListSubItem_Blank();
                ListAnimation_DRAGDROP_item.this.item_Blank.setPreferredSize(compToDrag.getSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setSize(compToDrag.getSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setMinimumSize(compToDrag.getMinimumSize());
                ListAnimation_DRAGDROP_item.this.item_Blank.setMaximumSize(compToDrag.getSize());
            }
            ListAnimation_DRAGDROP_item.this.item_Blank.revalidate();
            Component itemBlank = ListAnimation_DRAGDROP_item.this.item_Blank;
            System.out.println("itemBlank : prefSize: " + itemBlank.getPreferredSize() + " size: " + itemBlank.getSize() + " minSize: " + itemBlank.getMinimumSize() + " maxSize: " + itemBlank.getMaximumSize());

            //obetenemos las diferencias entre los tamaños del item y el lugar en donde se le hizo click en el componente
            ListAnimation_DRAGDROP_item.this.yDiff = relativePoint.y - compToDrag.getLocation().y;
            ListAnimation_DRAGDROP_item.this.xDiff = relativePoint.x - compToDrag.getLocation().x;

            //por defecto el index donde se movera el item deseado, será su mismo lugar en caso de soltarlo en el mismo index.
            ListAnimation_DRAGDROP_item.this.itemMoveIndex = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListComponentIndex(compToDrag);

            ListAnimation_DRAGDROP_item.this.lastY = mousePressed.getLocationOnScreen().y;
            System.out.println("ePoint: " + relativePoint.y + " cLocation: " + compToDrag.getLocation().y + " xDiff: " + xDiff + " yDiff: " + yDiff + " itemClickIndex: " + itemClickIndex);

            /**
             * si se envia el componente "c" recuperado de la lista tal cual, y
             * se lo agrega al jwindow, entonces desaparece de la lista para
             * ahora pertenecer al jwindow, a menos que se haga una copia manual
             * del componente "c" para que sea una insatancia nueva el cual
             * poder enviar al jwindow, así ya no se borraria el componente de
             * la lista, ya que se enviaria al jwindow una copia que es un
             * componente totalmente nuevo.
             */
            ListAnimation_DRAGDROP_item.this.copiarItemInJWindow(compToDrag);
            //una vez identificado el item que se desea mover, en su lugar reemplazamos por el item en blanco 
            if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item) {
                ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.addItem((Item) ListAnimation_DRAGDROP_item.this.item_Blank, ListAnimation_DRAGDROP_item.this.itemClickIndex);

            } else if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item.SubItem subItem) {
                Item it = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListSubItemParent(subItem);
                ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.addSubItem(it, (Item.SubItem) ListAnimation_DRAGDROP_item.this.item_Blank, ListAnimation_DRAGDROP_item.this.itemClickIndex);
            }

            //redimensionamos el componente que se moverá, se lo hace así, porque al retirarlo
            //se elimida el mousevent de click que se le hizo al item o SubItem, ya que 
            //ese evento se necesita activo para reconocer el movimiento de drag del mouse,
            //por ello se reduce a cero el tamaño para que paresca que no está y que siga activo el
            //movimiento de drag del mouse sobre ese item o subItem.
            ListAnimation_DRAGDROP_item.this.redimensionar(compToDrag, new Dimension(compToDrag.getWidth(), 0));
            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();
            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.repaint();

            System.out.println("");
        } catch (java.awt.IllegalComponentStateException illegalComponentStateException) {
        }
    }

    private void mouseDraggedDragComponent(MouseEvent e) {
        System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + "-> motionAdapter.mouseDragged()");
        // Obtener las coordenadas del mouse en la pantalla
        Point location = e.getLocationOnScreen();
        //System.out.println("mouse location: " + location);
        // Verificar si es el primer movimiento (para evitar comparación incorrecta)
        if (ListAnimation_DRAGDROP_item.this.lastY == -1) {
            ListAnimation_DRAGDROP_item.this.lastY = location.y;  // Inicializar la posición inicial
        }
        String movement = "";
        final String moveUP = "ARRIBA";
        final String moveDOWN = "ABAJO";

        // Determinar si se está moviendo hacia arriba o hacia abajo
        if (location.y < ListAnimation_DRAGDROP_item.this.lastY) {
            //System.out.println("Arrastrando hacia arriba");
            movement = moveUP;
        } else if (location.y > ListAnimation_DRAGDROP_item.this.lastY) {
            //System.out.println("Arrastrando hacia abajo");
            movement = moveDOWN;
        }

        // Actualizar la última posición Y
        ListAnimation_DRAGDROP_item.this.lastY = location.y;
        JScrollPane jScrollListContainer = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getjScrollPaneLayoutContainer();
        // Obtener las coordenadas y tamaño del contenedor de la lista
        Rectangle panelBounds = jScrollListContainer.getBounds();
        Point panelLocationOnScreen = jScrollListContainer.getLocationOnScreen();

        // Limitar el movimiento del JWindow dentro de los límites del contenedor de la lista
        int windowWidth = ListAnimation_DRAGDROP_item.this.jWindow.getWidth();
        int windowHeight = ListAnimation_DRAGDROP_item.this.jWindow.getHeight();

        // Limitar las coordenadas en el eje X
        int minX = panelLocationOnScreen.x;
        int maxX = panelLocationOnScreen.x + panelBounds.width - windowWidth;
        location.x = Math.max(minX, Math.min(maxX, location.x));

        // Limitar las coordenadas en el eje Y
        int minY = panelLocationOnScreen.y + ListAnimation_DRAGDROP_item.this.yDiff;
        int maxY = panelLocationOnScreen.y + (panelBounds.height - windowHeight) + ListAnimation_DRAGDROP_item.this.yDiff;
        location.y = Math.max(minY, Math.min(maxY, location.y));
        //System.out.println("mouse location limitado: " + location);
        // Mover el JWindow dentro de los límites calculados
        if (ListAnimation_DRAGDROP_item.this.jWindow.isVisible()) {
            int yNew = location.y;
            yNew = yNew - ListAnimation_DRAGDROP_item.this.yDiff;
            //para poder mover el escroll cuando al mover el jwindow llega al limite
            //del ui del contenedor de la lista, haci mover el scroll segun siga 
            //moviendo el mouse hacia arriba o abajo del todo el contenedor de la lista.
            JViewport viewport = jScrollListContainer.getViewport();
            ToggleListAnimationLayout panel = (ToggleListAnimationLayout) viewport.getView();  // El panel dentro del JScrollPane

            // Posición actual del scroll (vertical)
            int currentY = viewport.getViewPosition().y;
            // Tamaño total del contenido del panel
            int contentHeight = panel.getHeight();
            // Tamaño visible en el JScrollPane
            int viewHeight = viewport.getExtentSize().height;

            // Verificar si ya está en la parte inferior
            boolean isAtBottom = currentY + viewHeight >= contentHeight;

            // Verificar si ya está en la parte superior
            boolean isAtTop = currentY == 0;
            //System.out.println("isAtBottom: " + isAtBottom + " isAtTop: " + isAtTop);

            if (location.y == minY) {
                if (!isAtTop) {
                    // Mover el scroll verticalmente (arriba/abajo)
                    currentY = viewport.getViewPosition().y;
                    int newY = currentY - 3;  // Mover píxeles hacia abajo (usar un valor negativo para arriba)
                    viewport.setViewPosition(new Point(viewport.getViewPosition().x, newY));
                }

            } else if (location.y == maxY) {
                if (!isAtBottom) {
                    // Mover el scroll verticalmente (arriba/abajo)
                    currentY = viewport.getViewPosition().y;
                    int newY = currentY + 3;  // Mover píxeles hacia abajo (usar un valor negativo para arriba)
                    viewport.setViewPosition(new Point(viewport.getViewPosition().x, newY));
                }

            } else {
                ListAnimation_DRAGDROP_item.this.jWindow.setLocation(ListAnimation_DRAGDROP_item.this.initialClick.x, yNew);
                for (Component component : ListAnimation_DRAGDROP_item.this.jWindow.getContentPane().getComponents()) {
                    if (component instanceof ListItem item) {
                        item.mirarLocationOnScreenComponente(ListAnimation_DRAGDROP_item.this.jWindow.getLocation());
                    }
                }
                //System.out.println("initialClick: " + initialClick + " Location: " + "{" + initialClick.x + ";" + location.y + "}" + " Windowlocation: " + jWindow.getLocation() + " maxLotation: " + maxY + " minLocation: " + (minY));
                Point e2 = e.getPoint();
                Point relativePoint = e.getComponent().getLocationOnScreen();
                //location del componente dentro de su contenedor que seria el toggleList
                Point relativePoint2 = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout);
                e2.x = relativePoint2.x;
                e2.y = relativePoint2.y;

                if (movement.equals(moveUP)) {
                    e2.y = e2.y - ListAnimation_DRAGDROP_item.this.yDiff;
                } else if (movement.equals(moveDOWN)) {
                    int hdiff = ListAnimation_DRAGDROP_item.this.yDiff >= ListAnimation_DRAGDROP_item.this.jWindow.getHeight() ? ListAnimation_DRAGDROP_item.this.yDiff - ListAnimation_DRAGDROP_item.this.jWindow.getHeight() : ListAnimation_DRAGDROP_item.this.jWindow.getHeight() - yDiff;
                    e2.y = e2.y + hdiff;

                }
                //System.out.println("e: " + e.getPoint() + " e2: " + e2 + " location: " + "{" + initialClick.x + ";" + location.y + "}" + " Windowlocation: " + jWindow.getLocation());
                //busca en el contenedor toggleList un componente en las coordenadas del 
                //propio contenedor toggleList y no de la ubicacion en la pantalla(getLocationOnScreen)
                Component listItem = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getComponentAt(e2);
                if (listItem != null && !listItem.equals(ListAnimation_DRAGDROP_item.this.itemToClone)) {
                    //int indexComponentList = ListAnimation_DRAGDROP_item.this.frame.getToggleListAnimationLayout1().getListComponentIndex(listItem);
                    int indexComponentList = -1;
                    Component parent = null;
                    boolean move = false;

                    if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item && listItem instanceof ListItem) {
                        move = true;
                        indexComponentList = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListOnlyItemIndex(listItem);
                        System.out.println("indexComponentList on Move Item To Item: " + indexComponentList + " - " + ((ListItem) listItem).getTitulo());

                    } else if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item.SubItem) {
                        move = true;
                        indexComponentList = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListComponentIndex(ListAnimation_DRAGDROP_item.this.item_Blank);
                        //System.out.println("indexComponentList on Move SubItem to SubItem: " + indexComponentList + " - " + ((SubListItem) listItem).getTitulo());
                        if (listItem instanceof ListItem item) {
                            if (movement.equals(moveUP)) {
                                int itemIndex = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListOnlyItemIndex(item);
                                if (itemIndex > 0) {
                                    indexComponentList = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListComponentIndex(item);
                                }
                            } else if (movement.equals(moveDOWN)) {
                                parent = item;
                                indexComponentList = 0;
                                System.out.println("indexComponentList on Move SubItem to Item: " + indexComponentList + " - parent: " + item.getTitulo());
                            }
                        } else if (listItem instanceof SubListItem subItem) {
                            indexComponentList = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListComponentIndex(subItem);
                            System.out.println("indexComponentList on Move SubItem to SubItem: " + indexComponentList + " - " + subItem.getTitulo());
                        }
                    }

                    System.out.println("jwindow-> location " + ListAnimation_DRAGDROP_item.this.jWindow.getLocation() + "\tsize " + ListAnimation_DRAGDROP_item.this.jWindow.getSize());
                    System.out.println("compont-> location " + listItem.getLocationOnScreen() + "\tsize " + listItem.getSize() + "\tclass: " + listItem.getClass().getName());
                    //tomamos la posicion del item de la lista
                    Point compLocationScreen = listItem.getLocationOnScreen();
                    //tomamos la posicion del jwindows que movemos
                    Point jwindLocationScreen = ListAnimation_DRAGDROP_item.this.jWindow.getLocationOnScreen();
                    //creamos las variables para sus respectivos Y, ya que tomandolo directo del point no me funciona
                    double compY = compLocationScreen.getY();
                    double jwindY = jwindLocationScreen.getY();
                    double jwindCompResultY;
                    //servirá para saber el calculo si llega a un determinado tañamo del item al mover el jwindow
                    boolean halfHeight = false;
                    /**
                     * segun el movimiento del mouse hacia arriba o hacia abajo,
                     * se calcula si llega a un determinado tamaño del item al
                     * mover el jwindow,
                     */
                    if (movement.equals(moveUP)) {
                        //al mover hacia arriba, calcular la diferencia de posiciones del item y jwindow
                        jwindCompResultY = jwindY - compY;
                        //con la diferencia tenemos un alto, con ésta se puede verificar si la posicion Y del jwindow a llegado 
                        //o subido lo suficiente para ser comparable al alto en % del tamaño del item
                        halfHeight = jwindCompResultY <= (listItem.getHeight() * 0.70);
                        System.out.println("move up: jwindow-comp location Y: " + jwindY + " - " + compY + " = " + jwindCompResultY + " halfH: " + halfHeight);
                    } else if (movement.equals(moveDOWN)) {
                        /**
                         * al mover hacia abajo, se calcula la diferencia de
                         * posiciones, pero primero sumando a la posicion Y del
                         * jwindow su propia altura para con esta suma crear una
                         * nueva posición Y, y con ella calcular en el item que
                         * tiene debajo del jwindows, el saber si al mover el
                         * jwindow ya ha sobrepasado el alto en % asignado para
                         * el item,
                         */
                        jwindCompResultY = (jwindY + ListAnimation_DRAGDROP_item.this.jWindow.getHeight()) - compY;
                        halfHeight = jwindCompResultY >= (listItem.getHeight() * 0.30);
                        System.out.println("move down: jwindow-comp location Y: " + (jwindY + ListAnimation_DRAGDROP_item.this.jWindow.getHeight()) + " - " + compY + " = " + jwindCompResultY + " halfH: " + halfHeight);
                    }
                    /**
                     * si al mover el jwindow se ha determinado que si sobrepasa
                     * los tamaños asignados, y si el item que tiene debajo el
                     * jwindow es diferente a un item de la clase ListItem_Blank
                     * que se usa para representar un espacio vacío(dando a
                     * intuir que en ese espacio vacío se moverá el item que
                     * deseamos mover), entonces, que guarde el index del item
                     * que se usará para mover el item que estamos deseando
                     * mover, y reemplazamos en ese mismo lugar por la clase
                     * ListItem_Blank que representa un espacio vacío.
                     */
                    if (move) {
                        if (halfHeight == true && !(listItem instanceof ListItem_Blank) && !(listItem instanceof ListSubItem_Blank)) {
                            //mantener el último index válido
                            ListAnimation_DRAGDROP_item.this.itemMoveIndex = indexComponentList;
                            //cuando el item a mover es un item, y el Componente que se tiene debajo del mouse es tambien un item
                            if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item && listItem instanceof ListItem) {
                                ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.add(ListAnimation_DRAGDROP_item.this.item_Blank, indexComponentList);
                                System.out.println("Añadiendo Item to Item");

                                //cuando el item a mover es un SubItem
                            } else if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof Item.SubItem) {
                                //Si Componente que se tiene debajo del mouse es un Item
                                //entonces se identifica que se está moviendo un subItem hacia un nuevo Item del que dónde está.
                                if (listItem instanceof ListItem item) {
                                    //identificamos si se está moviendo el subItem hacia arriba o hacia abajo
                                    //para saber a qué item trasladar el subItem.
                                    if (movement.equals(moveUP)) {
                                        //de esta manera se colocará el subItem en el index en el que estaba el Item,
                                        //ya que gracias al override de .add() modifiqué para identificar donde colocarse un item o SubItem,
                                        //de tal forma que el subItem se colocará en el ultimo index de todos los subItems que tenga el Item.
                                        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.add(ListAnimation_DRAGDROP_item.this.item_Blank, indexComponentList);
                                    } else if (movement.equals(moveDOWN)) {
                                        //removiendo item en blanco porque genera conflicto al agregar un subItem a un Item,
                                        //ya que no coinciden el index del subItem para el item
                                        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.remove(ListAnimation_DRAGDROP_item.this.item_Blank);
                                        //si se esta moviendo hacia abajo, entonces el subItem se agregará al primer lugar de sus SubItems que tenga el Item
                                        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.addSubItem((Item) parent, (Item.SubItem) ListAnimation_DRAGDROP_item.this.item_Blank, indexComponentList);
                                        System.out.println("Añadiendo SubItem to Item en index: " + indexComponentList + " - parent: " + item.getTitulo());
                                        ListAnimation_DRAGDROP_item.this.itemMoveIndex = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListComponentIndex(ListAnimation_DRAGDROP_item.this.item_Blank);
                                    }
                                    //Si Componente que se tiene debajo del mouse es un SubItem
                                    //entonces se identifica que se está moviendo un subItem hacia otro subItem
                                } else if (listItem instanceof SubListItem subItem) {
                                    ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.add(ListAnimation_DRAGDROP_item.this.item_Blank, indexComponentList);
                                    System.out.println("Añadiendo SubItem to SubItem: " + indexComponentList + " - " + subItem.getTitulo());
                                }
                            }
                            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();
                        }
                        System.out.println("itemMoveIndex: " + ListAnimation_DRAGDROP_item.this.itemMoveIndex);
                    }
                    System.out.println("");
                }
            }
        }
    }

    private void mouseReleasedDragComponent() {
        System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + "->mouseAdapter.Mouse Release");
        System.out.println("toggleList component count: " + ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getComponentCount());

        // Ocultar la ventana cuando el clic es liberado
        jWindow.getContentPane().removeAll();
        jWindow.setVisible(false);

        //procedemos a añadir el item que se desea mover dentro de la lista de nuevo en el index en el que será recolocado.
        Component component = ListAnimation_DRAGDROP_item.this.itemToClone;
        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.add(ListAnimation_DRAGDROP_item.this.itemToClone, ListAnimation_DRAGDROP_item.this.itemMoveIndex);
        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();
        if (component instanceof ListItem item) {
            System.out.println("titulo: " + item.getTitulo());
        } else if (component instanceof SubListItem subItem) {
            Item parent = ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getListSubItemParent(subItem);
            if (!parent.getToggleList().isSelected()) {
                parent.setToggleSelectionSinEventNotification(true, true);
            }
            System.out.println("titulo: " + subItem.getTitulo());
        }

        for (Component subItem : ListAnimation_DRAGDROP_item.this.listSubItemsFromItemToDrag) {
            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.addSubItem((Item) ListAnimation_DRAGDROP_item.this.itemToClone, (Item.SubItem) subItem);
        }
        ListAnimation_DRAGDROP_item.this.listSubItemsFromItemToDrag.clear();

        //una vez que se haya elegido el index donde mover el item deseado, se elimina el item en blanco
        //para recolocar el item en el nuevo index de la lista
        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.remove(ListAnimation_DRAGDROP_item.this.item_Blank);

        for (AlmacenamientoComponentesLista alma : ListAnimation_DRAGDROP_item.this.listalmacenamientoComponentes) {
            for (Component subItem : alma.getSubItems()) {
                ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.addSubItem((Item) alma.getItem(), (Item.SubItem) subItem);
            }
            ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();
            ((Item) alma.item).setToggleSelectionSinEventNotification(!alma.toogleSelection, false);
            ((Item) alma.item).setToggleSelectionSinEventNotification(alma.toogleSelection, false);
        }
        ListAnimation_DRAGDROP_item.this.listalmacenamientoComponentes.clear();

        if (ListAnimation_DRAGDROP_item.this.itemToClone instanceof ListItem item) {
            if (ListAnimation_DRAGDROP_item.this.isItemToDragToggleButtonSelected != item.getToggleList().isSelected()) {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + "-> OnSelected animation true por isItemToMoveToggleButtonSelected " + ListAnimation_DRAGDROP_item.this.isItemToDragToggleButtonSelected + " Componente Count: " + ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getComponentCount());
                item.setToggleSelection(ListAnimation_DRAGDROP_item.this.isItemToDragToggleButtonSelected, true);
            } else {
                System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + "-> OnSelected animation false por isItemToMoveToggleButtonSelected " + ListAnimation_DRAGDROP_item.this.isItemToDragToggleButtonSelected + " Componente Count: " + ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getComponentCount());
                item.setToggleSelectionSinEventNotification(ListAnimation_DRAGDROP_item.this.isItemToDragToggleButtonSelected, false);
            }
        }

        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();
        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.repaint();

        System.out.println("");
    }

    private void redimensionar(Component comp, Dimension dim) {

        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.getMigLayout().setComponentConstraints(comp, "h " + dim.height + "!");
        ListAnimation_DRAGDROP_item.this.toggleListAnimationLayout.revalidate();

        System.out.println(ListAnimation_DRAGDROP_item.this.getClass().getName() + ".redimensionar()-> compSize: "
                + comp.getClass().getName()
                + " " + comp.getSize()
                + " " + comp.hashCode());
    }

    /**
     * Crea los valores necesarios de un item para añadirselos al item del
     * jwindow.
     *
     * @param item
     */
    private void copiarItemInJWindow(Component item) {
        // Remover el componente anterior
        this.jWindow.getContentPane().removeAll();

        Component c = new Component() {
        };
        /* c = item;
        // Verificar si el item es de tipo ListItem
        if (item instanceof ListItem it) {
            //ListItem it = (ListItem) item;
            ListItem itemToAdd = new ListItem();
            itemToAdd.setTitulo(it.getTitulo());
            itemToAdd.setBackground(it.getBackground());
            itemToAdd.colocarSize(it.getWidth(), it.getHeight());
            c = itemToAdd; // Asignar el ListItem modificado al nuevo componente
        } else if (item instanceof SubListItem subIt) {
            SubListItem subItemToAdd = new SubListItem();
            subItemToAdd.setTitulo(subIt.getTitulo());
            subItemToAdd.setBackground(subIt.getBackground());
            subItemToAdd.colocarSize(subIt.getWidth(), subIt.getHeight());
            c = subItemToAdd; // Asignar el ListItem modificado al nuevo componente
        }
         */
        c = this.componentGraphicPhoto(item, 1.0f);
        // Asignar el componente a itemToJWindow y añadirlo a jWindow
        this.itemToJWindow = c;
        this.jWindow.add(itemToJWindow);

        // Actualizar la interfaz gráfica
        this.jWindow.revalidate();
        this.jWindow.repaint();
    }

    private JPanel componentGraphicPhoto(Component comp, float opacity) {
        /*
        // Establecer el tamaño del panel si aún no tiene dimensiones
        if (comp.getWidth() == 0 || comp.getHeight() == 0) {
            comp.setSize(new Dimension(300, 200));  // Define dimensiones si es necesario
        }*/

        // Crear una imagen con las dimensiones del panel
        BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Obtener el contexto gráfico de la imagen
        Graphics2D g2d = image.createGraphics();
        // Establecer la opacidad
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        // Renderizar el panel en la imagen
        comp.paint(g2d);
        // Liberar los recursos del gráfico
        g2d.dispose();

        MigLayout layout = new MigLayout("wrap, fillx, inset 0, hidemode 1", "[fill]", "[top]0[]");
        JPanel panel = new JPanel(layout);

        JLabel label = new JLabel(new ImageIcon(image));
        panel.add(label);

        return panel;
    }

}
