package toggle;

import toggle.AnimationTiming.AnimationTiming;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.crowie.ListItem;
import com.crowie.ListSubItem_Blank;
import com.crowie.SubListItem;

class ListAnimation_UP_DOWN_item {

    private final toggle.ToggleListAnimationLayout toggleListAnimationLayout;

    private toggle.AnimationTiming.AnimationTiming animationTiming_ItemList_UPDOWN;

    /**
     * Guarda la indentificacion de la accion - subir bajar item
     */
    private String ITEM_UPDOWN = "";
    /**
     * índice del item que se ocultará para dar la ilusión de cambio de posicion
     * del item designado original.
     */
    private Component itemToHide;
    /**
     * índice del item que se mostrará para dar la ilusión de cambio de posicion
     * del item designado original.
     */
    private Component itemToShow;

   

    private Component itemOrSubItem_ToMove_UP_DOWN_Respaldo;

    private Component isAItem_Respaldo;

    private boolean isItemToMoveToggleButtonSelected;

    private boolean isItemToHideToggleButtonSelected;

    private boolean isAlreadyEnded;

    private final List<Component> listSubItemsFromItemToMove;

    private final List<Component> listSubItemsFromItemToHide;

    private String ITEM_SUBITEM_MOVEMENT = "";

    private final String SUBITEM_TO_ITEM = "MoviendoSubItemHaciaUnItem";

    private final String SUBITEM_TO_SUBITEM = "MoviendoSubItemHaciaUnSubItem";

    private final String ITEM_TO_ITEM = "MoviendoItemHaciaUnItem";

    protected toggle.toggleComponentsListeners.ToggleListAnimationLayout_UPDOWN_Listener listAnimationLayout_UPDOWN_Listener;

    protected void setToggleListAnimationLayout_UPDOWN_Listener(toggle.toggleComponentsListeners.ToggleListAnimationLayout_UPDOWN_Listener listAnimationLayout_UPDOWN_Listener) {
        this.listAnimationLayout_UPDOWN_Listener = listAnimationLayout_UPDOWN_Listener;
    }

    public ListAnimation_UP_DOWN_item(toggle.ToggleListAnimationLayout toggleListAnimationLayout) {
        this.toggleListAnimationLayout = toggleListAnimationLayout;
        this.listSubItemsFromItemToMove = new CopyOnWriteArrayList<>();
        this.listSubItemsFromItemToHide = new CopyOnWriteArrayList<>();

        this.toggleListAnimationLayout.setInternal_ToggleOnAnimationItemShowSubItemsListener(new toggle.ToggleComponentsOnAnimationItemAdapter() {
            @Override
            public void onAnimatedStarted(boolean started) {
                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + " onAnimatedStarted()");

            }

            @Override
            public void onAnimatedEnded(boolean ended) {
                if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_UP)
                        || ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_DOWN)
                        && ListAnimation_UP_DOWN_item.this.isAlreadyEnded == false) {
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + " onAnimatedEnded()");
                    for (Component subItem : ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToMove) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.removeSubItem((Item.SubItem) subItem);
                    }

                    for (Component subItem : ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToHide) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.removeSubItem((Item.SubItem) subItem);
                    }
                    if (!ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToMove.isEmpty()
                            || !ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToHide.isEmpty()) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();
                    }
                    ListAnimation_UP_DOWN_item.this.isAlreadyEnded = true;

                    ListAnimation_UP_DOWN_item.this.animationTiming_ItemList_UPDOWN.animar();

                    System.out.println("------------------------------------------------------------");
                }
            }
        });

        this.animationTiming_ItemList_UPDOWN = new AnimationTiming();
        this.animationTiming_ItemList_UPDOWN.setAnimationTime(500);
        this.animationTiming_ItemList_UPDOWN.setAnimationRepeatCount(1);
        this.animationTiming_ItemList_UPDOWN.setAnimationComportamientoOnRepeat(org.jdesktop.animation.timing.Animator.RepeatBehavior.REVERSE);
        this.animationTiming_ItemList_UPDOWN.addEventAnimationTimingAdapter(new toggle.AnimationTiming.AnimationTimingAdapter() {
            /**
             * dimensiones del item a ocultar para simular el cambio de lugar
             * del item designado original.
             */
            private Dimension dimItemToHide;
            /**
             * dimensiones del item a mostrar para simular el cambio de lugar
             * del item designado original.
             */
            private Dimension dimItemToShow;

            @Override
            public void onAnimatedStarted(boolean started) {
                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> onAnimatedStarted");
                //--------------------------------------------------------------
                //LISTENER
                if (ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener != null) {

                    if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveItem_Move_Started(ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN, (Item) ListAnimation_UP_DOWN_item.this.itemToHide);

                    } else if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item.SubItem) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveSubItem_Move_Started(ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN, (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener
                                .onMoveSubItem_Move_Started(
                                        ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN,
                                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListSubItemParent((Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide),
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                    }
                }
                //--------------------------------------------------------------
                /**
                 * Tomamos todos los valores necesarios del componente para
                 * crear una copia de sus valores y crear una nueva instancia
                 * del componente. Hay que hacerlo así puesto si solo tomamos
                 * como referencia el componente en la lista dentro de una
                 * variable no se creará un duplicado, sino que se moverá de
                 * indice más adelante.
                 */
                ListAnimation_UP_DOWN_item.this.itemToShow = ListAnimation_UP_DOWN_item.this.copiarListComponent(ListAnimation_UP_DOWN_item.this.itemToHide);

                //toammos las dimensiones del item creando las nuevas intancias
                //para las variables de las dimensiones
                this.dimItemToHide = new Dimension(ListAnimation_UP_DOWN_item.this.itemToHide.getSize());
                this.dimItemToShow = new Dimension(ListAnimation_UP_DOWN_item.this.itemToHide.getSize());

                int componentListCount = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount();
                if (ListAnimation_UP_DOWN_item.this.itemToShow instanceof ListItem) {
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName()
                            + "-> itemToShow es un Item: " + ListAnimation_UP_DOWN_item.this.getListComponentTitle(ListAnimation_UP_DOWN_item.this.itemToShow)
                    );
                    ListAnimation_UP_DOWN_item.this.justSeeSubItemsFromItem(((Item) (ListAnimation_UP_DOWN_item.this.itemToShow)).getSubItems());

                } else if (ListAnimation_UP_DOWN_item.this.itemToShow instanceof SubListItem) {
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName()
                            + "-> itemToShow es un SubItem: " + ListAnimation_UP_DOWN_item.this.getListComponentTitle(ListAnimation_UP_DOWN_item.this.itemToShow));
                }
                /**
                 * dependiendo de si el movimiento del item desigando es arrba o
                 * abajo, se asignará su nuevo lugar en la lista para la copia
                 * del componenente a mostrar y el cual forma parte de los
                 * componente que crearán la ilusión de movimiento para el item
                 * designado original.
                 */
                if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_UP)) {
                    /**
                     * el item a mostrarse aparecerá en el índice siguiente del
                     * item designado original.
                     *
                     * Al agregar el nuevo componente a la lista, éste se agrega
                     * con dimensiones [0,0] por defecto por la propia lista.
                     */
                    if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.ITEM_TO_ITEM)) {
                        Item lastItem = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListOnlyItemAtIndex(
                                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListOnlyItems().size() - 1);

                        if (lastItem.equals(ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo)) {
                            ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .addItem(
                                            (Item) ListAnimation_UP_DOWN_item.this.itemToShow);
                        } else {
                            int actualItemIndex = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListOnlyItemIndex(
                                    ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo);
                            Item nextItem = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListOnlyItemAtIndex(actualItemIndex + 1);

                            ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .addItemAtItemPos(
                                            nextItem,
                                            (Item) ListAnimation_UP_DOWN_item.this.itemToShow);
                        }

                    } else if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.SUBITEM_TO_ITEM)) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .addSubItem(
                                        (Item) ListAnimation_UP_DOWN_item.this.isAItem_Respaldo,
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToShow,
                                        ((Item) ListAnimation_UP_DOWN_item.this.isAItem_Respaldo).getSubItems().size());

                    } else if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.SUBITEM_TO_SUBITEM)) {
                        Item item = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .getListSubItemParent((Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                        int subItemIndex = -1;
                        for (int i = 0; i < item.getSubItems().size(); i++) {
                            if (item.getSubItems().get(i).equals(ListAnimation_UP_DOWN_item.this.itemToHide)) {
                                subItemIndex = i + 2;
                                break;
                            }
                        }
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .addSubItem(item,
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToShow,
                                        subItemIndex);
                    }
                    //ListAnimation_UP_DOWN_item.this.frame.getToggleListAnimationLayout1().add(ListAnimation_UP_DOWN_item.this.itemToShow, ListAnimation_UP_DOWN_item.this.itemIndex_Next);
                } else if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_DOWN)) {
                    /**
                     * el item a mostrarse aparecerá en el índice del item
                     * designado original, puesto para crear la ilusion de
                     * movimiento los indices de los componentes enterior y
                     * siguiente del item designado original incluido él mismo
                     * habrán cambiado.
                     *
                     * Al agregar el nuevo componente a la lista, éste se agrega
                     * con dimensiones [0,0] por defecto por la propia lista.
                     */
                    if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.ITEM_TO_ITEM)) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .addItemAtItemPos(
                                        (Item) ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo,
                                        (Item) ListAnimation_UP_DOWN_item.this.itemToShow);

                    } else if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.SUBITEM_TO_ITEM)) {
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .addSubItem(
                                        (Item) ListAnimation_UP_DOWN_item.this.isAItem_Respaldo,
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToShow,
                                        0);

                    } else if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.SUBITEM_TO_SUBITEM)) {
                        Item item = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .getListSubItemParent((Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                        int subItemIndex = -1;
                        for (int i = 0; i < item.getSubItems().size(); i++) {
                            if (item.getSubItems().get(i).equals(ListAnimation_UP_DOWN_item.this.itemToHide)) {
                                subItemIndex = i - 1;
                                break;
                            }
                        }
                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                .addSubItem(item,
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToShow,
                                        subItemIndex);
                    }

                }
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();
                System.out.println("----------------------------------------------------------------------------------------------------------------\n\n");
            }

            @Override
            public void onAnimated(float animated) {

                /**
                 * aquí creamos la ilusion de movimiento del item designado
                 * original ocultando y mostrando una copia de un mismo item, de
                 * esta forma parecerá que el item desigando original se mueve
                 * el la lista.
                 *
                 * Se crea las dimensiones para el item que se esconde y el que
                 * se muestra.
                 */
                //item que se esconderá
                int itemToHideH = (int) (this.dimItemToHide.height - (this.dimItemToHide.height * animated));
                // este item es el duplicado del que se esconde, pero esté se irá mostrando.
                int itemToShowH = (int) (this.dimItemToShow.height * animated);
                //el ancho se conservar, lo importante son el alto para cada item
                Dimension newDimItemToHide = new Dimension(dimItemToHide.width, itemToHideH);
                Dimension newDimItemToShow = new Dimension(dimItemToShow.width, itemToShowH);

                //System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> itemToHideH: " + itemToHideH + " " + stringBuilderItemToHide);
                //System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> itemToShowH: " + itemToShowH + " " + stringBuilderItemToShow);
                this.redimensionar(ListAnimation_UP_DOWN_item.this.itemToHide, newDimItemToHide);
                this.redimensionar(ListAnimation_UP_DOWN_item.this.itemToShow, newDimItemToShow);

                //--------------------------------------------------------------
                //LISTENER
                if (ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener != null) {

                    if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveItem_AnimatedShow(animated, (Item) ListAnimation_UP_DOWN_item.this.itemToHide);

                    } else if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item.SubItem) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveSubItem_AnimatedShow(animated, (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener
                                .onMoveSubItem_AnimatedShow(
                                        animated,
                                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListSubItemParent((Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide),
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                    }
                }
                //--------------------------------------------------------------
                //se valida y se repinta para observar bien los cambios en la UI
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.repaint();
            }

            @Override
            public void onAnimatedRepeated(boolean repeated) {
                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> onAnimatedRepeated");
            }

            @Override
            public void onAnimatedEnded(boolean ended) {
                System.out.println("----------------------------------------------------------------------------------------------------------------\n\n");
                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> onAnimatedEnded");
                //--------------------------------------------------------------
                //LISTENER
                if (ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener != null) {

                    if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveItem_Move_Ended(ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN, (Item) ListAnimation_UP_DOWN_item.this.itemToHide);

                    } else if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item.SubItem) {
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener.onMoveSubItem_Move_Ended(ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN, (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                        ListAnimation_UP_DOWN_item.this.listAnimationLayout_UPDOWN_Listener
                                .onMoveSubItem_Move_Ended(
                                        ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN,
                                        ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListSubItemParent((Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide),
                                        (Item.SubItem) ListAnimation_UP_DOWN_item.this.itemToHide);
                    }
                }
                //--------------------------------------------------------------

                if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_UP)
                        || ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_DOWN)) {
                    //si baja se elimina el item que tiene debajo
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> " + ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN);
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Component To Hide Count SubItems: " + ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToHide.size());
                    System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Component To Move Count SubItems: " + ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToMove.size());
                    
                    //ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.remove(ListAnimation_UP_DOWN_item.this.itemToHide);
                    ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.add(itemToHide, ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListComponentIndex(ListAnimation_UP_DOWN_item.this.itemToShow));
                    ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.remove(ListAnimation_UP_DOWN_item.this.itemToShow);

                    if (ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT.equals(ListAnimation_UP_DOWN_item.this.ITEM_TO_ITEM)) {
                        if (ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo != null
                                && ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo instanceof Item item) {

                            for (Component subItem : ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToMove) {
                                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.addSubItem(item, (Item.SubItem) subItem);
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Added SubItem to ItemToMove Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                            }
                            for (Component subItem : ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToHide) {
                                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.addSubItem((Item) ListAnimation_UP_DOWN_item.this.itemToHide, (Item.SubItem) subItem);
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Added SubItem to ItemToHide Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                            }

                            if (!ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToMove.isEmpty() || !ListAnimation_UP_DOWN_item.this.listSubItemsFromItemToHide.isEmpty()) {
                                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Revalidando por listas Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                            }

                            if (ListAnimation_UP_DOWN_item.this.isItemToMoveToggleButtonSelected != item.getToggleList().isSelected()) {
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> OnSelected animation true por isItemToMoveToggleButtonSelected " + ListAnimation_UP_DOWN_item.this.isItemToMoveToggleButtonSelected + " Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                                item.setToggleSelection(ListAnimation_UP_DOWN_item.this.isItemToMoveToggleButtonSelected, true);
                            } else {
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> OnSelected animation false por isItemToMoveToggleButtonSelected " + ListAnimation_UP_DOWN_item.this.isItemToMoveToggleButtonSelected + " Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                                item.setToggleSelectionSinEventNotification(ListAnimation_UP_DOWN_item.this.isItemToMoveToggleButtonSelected, false);

                            }

                            if (ListAnimation_UP_DOWN_item.this.isItemToHideToggleButtonSelected != ((Item) ListAnimation_UP_DOWN_item.this.itemToHide).getToggleList().isSelected()) {
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> OnSelected animation false por isItemToHideToggleButtonSelected " + ListAnimation_UP_DOWN_item.this.isItemToHideToggleButtonSelected + " Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                                ((Item) ListAnimation_UP_DOWN_item.this.itemToHide).setToggleSelection(ListAnimation_UP_DOWN_item.this.isItemToHideToggleButtonSelected, true);
                            } else {
                                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> OnSelected animation false por isItemToHideToggleButtonSelected " + ListAnimation_UP_DOWN_item.this.isItemToHideToggleButtonSelected + " Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                                ((Item) ListAnimation_UP_DOWN_item.this.itemToHide).setToggleSelectionSinEventNotification(ListAnimation_UP_DOWN_item.this.isItemToHideToggleButtonSelected, false);
                            }


                        } else if (ListAnimation_UP_DOWN_item.this.itemToHide instanceof Item.SubItem) {

                        }

                    }

                }
                System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Componente Count: " + ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount());
                //se valida y se repinta para observar bien los cambios en la UI
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.repaint();
                ListAnimation_UP_DOWN_item.this.limpiarDatos();
                System.out.println("----------------------------------------------------------------------------------------------------------------\n\n");
            }

            private void redimensionar(Component comp, Dimension dim) {

                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getMigLayout().setComponentConstraints(comp, "h " + dim.height + "!");
                ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.revalidate();

                /*System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + ".redimensionar()-> compSize: "
                        + comp.getClass().getName()
                        + " " + comp.getSize()
                        + " " + comp.hashCode());*/
            }
        });

        toggle.MoveUPDOWNComponentAdapter moveItemAdapter = new MoveUPDOWNComponentAdapter() {
            @Override
            public void componentMove(int itemIndex, String moveDirection) {
                ListAnimation_UP_DOWN_item.this.moveItem_UPDOWN(itemIndex, moveDirection);
            }
        };
        this.toggleListAnimationLayout.setInternalOnMoveUPDOWNComponentListener(moveItemAdapter);

        /*this.toggleListAnimationLayout.getListItem1().setMoveItemAdapter(moveItemAdapter);
        this.toggleListAnimationLayout.getSubListItem1().setMoveItemAdapter(moveItemAdapter);
        this.toggleListAnimationLayout.getSubListItem2().setMoveItemAdapter(moveItemAdapter);
        this.toggleListAnimationLayout.getSubListItem3().setMoveItemAdapter(moveItemAdapter);
        this.toggleListAnimationLayout.getSubListItem4().setMoveItemAdapter(moveItemAdapter);*/
        //this.frame.getListItem1().addSubItem(this.frame.getSubListItem1());
        //this.frame.getListItem1().addSubItem(this.frame.getSubListItem2());
        //this.toggleListAnimationLayout.getListItem2().setMoveItemAdapter(moveItemAdapter);
        //this.toggleListAnimationLayout.getListItem3().setMoveItemAdapter(moveItemAdapter);
        /*this.frame.getListItem4().setMoveItemAdapter(moveItemAdapter);
        this.frame.getListItem5().setMoveItemAdapter(moveItemAdapter);
        this.frame.getListItem6().setMoveItemAdapter(moveItemAdapter);
        this.frame.getListItem7().setMoveItemAdapter(moveItemAdapter);*/
    }

    private void moveItem_UPDOWN(int itemIndex, String UPDOWN) {
        if (!this.animationTiming_ItemList_UPDOWN.isAnimatorRunnning()) {
            ListAnimation_UP_DOWN_item.this.isAlreadyEnded = false;
            ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN = UPDOWN;
            ListAnimation_UP_DOWN_item.this.itemOrSubItem_ToMove_UP_DOWN_Respaldo = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getListComponentAtIndex(itemIndex);
            int itemToHideIndex = -1;
            System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> Antes de añadir al toggleList los items a mostrar y ocultar.-----------");
            System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> UP - DOWN: " + ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN);
            System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> itemIndex: " + itemIndex + this.getListComponentTitle(itemIndex));

            if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_UP)
                    && itemIndex > 0) {

                itemToHideIndex = itemIndex - 1;

                if (this.toggleListAnimationLayout.getListComponentAtIndex(itemIndex) instanceof Item item) {
                    if (!this.toggleListAnimationLayout.getListOnlyItems().get(0).equals(item)) {

                        int actualItemIndex = this.toggleListAnimationLayout.getListOnlyItemIndex(item);

                        Item previreItem = this.toggleListAnimationLayout.getListOnlyItemAtIndex(actualItemIndex - 1);

                        if (previreItem != null) {
                            this.itemToHide = previreItem;
                            this.listSubItemsFromItemToHide.clear();
                            this.listSubItemsFromItemToHide.addAll(previreItem.getSubItems());
                            System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> UP item: Anterior Item es: " + ((ListItem) previreItem).getTitulo() + " hashCode: " + previreItem.hashCode());
                            this.isItemToHideToggleButtonSelected = previreItem.getToggleList().isSelected();
                            previreItem.setToggleSelectionSinEventNotification(false, true);
                        }

                        this.listSubItemsFromItemToMove.clear();
                        this.listSubItemsFromItemToMove.addAll(item.getSubItems());
                        this.isItemToMoveToggleButtonSelected = item.getToggleList().isSelected();
                        ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.ITEM_TO_ITEM;
                        item.setToggleSelection(false, true);
                    }

                } else if (this.toggleListAnimationLayout.getListComponentAtIndex(itemIndex) instanceof Item.SubItem) {

                    int previewIndex = itemIndex - 1;
                    if (previewIndex > 0 && previewIndex < this.toggleListAnimationLayout.getComponentCount()) {
                        Component comp = this.toggleListAnimationLayout.getListComponentAtIndex(previewIndex);
                        if (comp instanceof Item) {
                            Component parentItem = comp;
                            int parentIntemIndex = this.toggleListAnimationLayout.getListOnlyItemIndex(parentItem);
                            Item previewItem = this.toggleListAnimationLayout.getListOnlyItemAtIndex(parentIntemIndex - 1);

                            this.isAItem_Respaldo = previewItem;

                            this.itemToHide = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .getListComponentAtIndex(itemIndex);

                            ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.SUBITEM_TO_ITEM;

                        } else if (comp instanceof Item.SubItem) {
                            this.itemToHide = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .getListComponentAtIndex(previewIndex);

                            ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.SUBITEM_TO_SUBITEM;
                        }
                        ListAnimation_UP_DOWN_item.this.animationTiming_ItemList_UPDOWN.animar();
                    }
                }

            } else if (ListAnimation_UP_DOWN_item.this.ITEM_UPDOWN.equals(Item.ITEM_DOWN)
                    && itemIndex < (ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout.getComponentCount() - 1)) {

                itemToHideIndex = itemIndex + 1;

                if (this.toggleListAnimationLayout.getListComponentAtIndex(itemIndex) instanceof Item item) {
                    System.out.println("toggle.ListAnimation_UP_DOWN_item.moveItem_UPDOWN()-> gegetListOnlyItemsSize: "+this.toggleListAnimationLayout.getListOnlyItems().size());
                    if (!this.toggleListAnimationLayout.getListOnlyItems().get(
                            this.toggleListAnimationLayout.getListOnlyItems().size() - 1).equals(item)) {

                        int nextItemIndex = this.toggleListAnimationLayout.getListOnlyItemIndex(item)+1;
                        if (nextItemIndex > -1 && nextItemIndex < this.toggleListAnimationLayout.getListOnlyItems().size()) {
                            Item i = this.toggleListAnimationLayout.getListOnlyItemAtIndex(nextItemIndex);
                            this.itemToHide = i;
                            this.listSubItemsFromItemToHide.clear();
                            this.listSubItemsFromItemToHide.addAll(i.getSubItems());
                            System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> DOWN item: Siguiente Item es: " + ((ListItem) i).getTitulo() + " hashCode: " + i.hashCode());
                            this.isItemToHideToggleButtonSelected = i.getToggleList().isSelected();
                            i.setToggleSelectionSinEventNotification(false, true);
                        }

                        this.listSubItemsFromItemToMove.clear();
                        this.listSubItemsFromItemToMove.addAll(item.getSubItems());
                        this.isItemToMoveToggleButtonSelected = item.getToggleList().isSelected();
                        ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.ITEM_TO_ITEM;
                        item.setToggleSelection(false, true);
                    }

                } else if (this.toggleListAnimationLayout.getListComponentAtIndex(itemIndex) instanceof Item.SubItem) {

                    int nextIndex = itemIndex + 1;
                    if (nextIndex < this.toggleListAnimationLayout.getComponentCount()) {
                        Component itemRespaldo = this.toggleListAnimationLayout.getListComponentAtIndex(nextIndex);
                        if (itemRespaldo instanceof Item) {
                            this.isAItem_Respaldo = itemRespaldo;
                            this.itemToHide = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .getListComponentAtIndex(itemIndex);
                            ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.SUBITEM_TO_ITEM;

                        } else if (itemRespaldo instanceof Item.SubItem) {
                            this.itemToHide = ListAnimation_UP_DOWN_item.this.toggleListAnimationLayout
                                    .getListComponentAtIndex(itemToHideIndex);
                            ListAnimation_UP_DOWN_item.this.ITEM_SUBITEM_MOVEMENT = ListAnimation_UP_DOWN_item.this.SUBITEM_TO_SUBITEM;
                        }
                        ListAnimation_UP_DOWN_item.this.animationTiming_ItemList_UPDOWN.animar();
                    }

                }

            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------\n");
    }

    private void limpiarDatos() {
        this.ITEM_UPDOWN = "";

        this.itemToHide = null;
        this.itemToShow = null;
        //this.itemToHideIndex = -1;
        this.itemOrSubItem_ToMove_UP_DOWN_Respaldo = null;
        this.isAItem_Respaldo = null;

        this.isItemToMoveToggleButtonSelected = false;
        this.isItemToHideToggleButtonSelected = false;

        this.listSubItemsFromItemToMove.clear();
        this.listSubItemsFromItemToHide.clear();

        this.ITEM_SUBITEM_MOVEMENT = "";

    }

    private Component[] getListComponentsArray() {
        return this.toggleListAnimationLayout.getComponents();
    }

    private void justSeeSubItemsFromItem(List<Component> listaComps) {
        StringBuilder builder = new StringBuilder("subItems {");
        for (Object object : listaComps) {
            if (object instanceof Item.SubItem) {
                SubListItem sli = (SubListItem) object;
                builder
                        .append("[")
                        .append("(")
                        .append(sli.getIndex())
                        .append(")")
                        .append(sli.getTitulo())
                        .append(" hasCode: ")
                        .append(sli.hashCode())
                        .append("]");
            }
        }
        builder.append("}");
        System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> justSeeSubItemsFromItem(): " + builder.toString());
    }

    private Component copiarListComponent(Component itemToClone) {
        System.out.println(ListAnimation_UP_DOWN_item.this.getClass().getName() + "-> copiarItem()");
        Component componente = null;
        /*if (itemToClone instanceof Item) {
            ListItem item = (ListItem) itemToClone;
            ListItem listItem = new ListItem();
            listItem.setTitulo(item.getTitulo());
            //listItem.setTitulo(item.getTitulo() + " copia ");
            listItem.setBackground(item.getBackground());
            listItem.setToggleList(item.getToggleList());

            componente = listItem;
        } else if (itemToClone instanceof SubListItem) {
            SubListItem subItem = (SubListItem) itemToClone;
            SubListItem listItem = new SubListItem();
            listItem.setTitulo(subItem.getTitulo());
            //listItem.setTitulo(subItem.getTitulo() + " copia");
            listItem.setBackground(subItem.getBackground());

            componente = listItem;
        }*/

        if (itemToClone instanceof Item) {
            componente = new ListItem_Blank(itemToClone);

        } else if (itemToClone instanceof Item.SubItem) {
            componente = new ListSubItem_Blank(itemToClone);
        }

        return componente;
    }

    private String getListComponentTitle(int index) {
        String title = "";
        if (index >= 0) {
            Component c = this.toggleListAnimationLayout.getComponent(index);
            if (c instanceof Item) {
                ListItem item = (ListItem) c;
                title = " ITEM " + item.getTitulo() + " hashCode: " + item.hashCode();
            } else if (c instanceof Item.SubItem) {
                SubListItem subItem = (SubListItem) c;
                title = " SUB_ITEM " + subItem.getTitulo() + " hashCode: " + subItem.hashCode();
            }
        }
        return title;
    }

    private String getListComponentTitle(Component c) {
        String title = "";
        if (c instanceof Item) {
            ListItem item = (ListItem) c;
            title = " ITEM " + item.getTitulo() + " hashCode: " + item.hashCode();
        } else if (c instanceof Item.SubItem) {
            SubListItem subItem = (SubListItem) c;
            title = " SUB_ITEM " + subItem.getTitulo() + " hashCode: " + subItem.hashCode();
        }

        return title;
    }

}
