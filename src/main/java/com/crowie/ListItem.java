package com.crowie;

import toggle.Item;
import toggle.AnimationTiming.AnimationTiming;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

public class ListItem extends Item {

    private toggle.AnimationTiming.AnimationTiming animationTiming_ItemList_UPDOWN;

    private String titulo = "Item Title";
    private String subTitulo = "Item Description";
    private String moviendo = "Moviendo...";

    private boolean dragReady = false;
    private MouseEvent mouseEventMousePressed;

    public ListItem() {
        initComponents();
        //components = new Component[]{new SubListItem(), new SubListItem(), new ListItem(new SubListItem(), new ListItem(new SubListItem()))};
        //components = new Component[]{new SubListItem(), new SubListItem(), new ListItem(new SubListItem(), new SubListItem())};   
        //components = new Component[]{new SubListItem(), new SubListItem()};
        //components = new Component[0];
        this.initAnimationtiming();
        this.initOnItemActionListener();

        this.lb_Title.setText(titulo);
        this.lb_SubTitle.setText(subTitulo);
        this.lb_HashCode.setText("hashCode: " + this.hashCode());
        this.lb_SubItemNumber.setText("" + this.getSubItems().size());
        this.colocarSize(this.getPreferredSize().width, this.getPreferredSize().height);

    }

    private void initAnimationtiming() {
        this.animationTiming_ItemList_UPDOWN = new AnimationTiming();
        this.animationTiming_ItemList_UPDOWN.setAnimationTime(2000);
        this.animationTiming_ItemList_UPDOWN.setAnimationRepeatCount(1);
        this.animationTiming_ItemList_UPDOWN.setAnimationComportamientoOnRepeat(org.jdesktop.animation.timing.Animator.RepeatBehavior.REVERSE);
        this.animationTiming_ItemList_UPDOWN.addEventAnimationTimingAdapter(new toggle.AnimationTiming.AnimationTimingAdapter() {
            @Override
            public void onAnimatedStarted(boolean started) {
                ListItem.this.dragReady = false;
                System.out.println(ListItem.this.getClass().getName() + ".onAnimatedStarted() ");
            }

            @Override
            public void onAnimated(float animated) {
                ListItem.this.rSProgressCircle1.setValue(((int) (100 * animated)));
            }

            @Override
            public void onAnimatedEnded(boolean ended) {
                ListItem.this.dragReady = true;
                System.out.println(ListItem.this.getClass().getName() + ".onAnimatedEnded() ");
                ListItem.this.onDrag(ListItem.this.mouseEventMousePressed, ListItem.this.dragReady);
            }

        });
    }

 
    private void initOnItemActionListener() {
        this.setOnItemActionListener(new Item.onItemActionsAdapter() {
            @Override
            public void setToggleSelection(boolean selection, boolean animated) {
                ListItem.this.toggleButton_ShowSubItems.setSelected(selection);
            }

            @Override
            public void setToggleSelectionSinEventNotification(boolean selection, boolean animated) {
                ListItem.this.toggleButton_ShowSubItems.setSelected(selection);
            }

            @Override
            public void showSubItems(boolean show) {
                ListItem.this.toggleButton_ShowSubItems.setSelected(show);
            }
        });
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.lb_Title.setText(this.titulo);
    }

    public String getTitulo() {
        return this.titulo;
    }

    
    @Override
    public void iniciarAlgoEnElComponente() {
        this.lb_Parent.setText("" + (this.getParent() != null ? "Parent: " + this.getParent().getClass().getName() : "Parent: es null"));
        this.lb_ItemIndex.setText("" + this.getIndex());
        this.lb_SubItemNumber.setText("" + this.getSubItems().size());

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                ListItem.this.mirarLocationOnScreenComponente();
                ListItem.this.colocarSize(ListItem.this.getWidth(), ListItem.this.getHeight());
            }
        };

        scheduler.scheduleAtFixedRate(runn, 0, 1, TimeUnit.SECONDS);
    }

    private void mirarLocationOnScreenComponente() {
        if (ListItem.this.isShowing()) {
            Point p = this.getLocationOnScreen();
            ListItem.this.lb_Location.setText("Loct: [x:" + p.x + " y:" + p.y + "]");
        }
    }

    public void mirarLocationOnScreenComponente(Point p) {
        ListItem.this.lb_Location.setText("Loct: [x:" + p.x + " y:" + p.y);
        ListItem.this.lb_HxHy.setText("Hy: {" + (this.getHeight() + p.y) + "}");
    }

    public void colocarSize(int w, int h) {
        this.lb_Height.setText("H:" + h);
        this.lb_Width.setText("W:" + w);
    }

    private void justSeeSubItemsFromItem(List<Component> listaComps) {
        StringBuilder builder = new StringBuilder("subItems {");
        for (Object object : listaComps) {
            if (object instanceof Item.SubItem) {
                SubListItem sli = (SubListItem) object;
                builder.append("[").append(sli.getTitulo()).append(" hasCode: ").append(sli.hashCode()).append("]");
            }
        }
        builder.append("}");
        System.out.println(ListItem.this.getClass().getName() + "-> " + builder.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_SubTitle = new javax.swing.JLabel();
        lb_Title = new javax.swing.JLabel();
        btn_UP = new javax.swing.JButton();
        btn_DOWN = new javax.swing.JButton();
        toggleButton_ShowSubItems = new javax.swing.JToggleButton();
        lb_SubItemNumber = new javax.swing.JLabel();
        lb_HashCode = new javax.swing.JLabel();
        lb_Parent = new javax.swing.JLabel();
        lb_ItemIndex = new javax.swing.JLabel();
        lb_Location = new javax.swing.JLabel();
        rSProgressCircle1 = new rojerusan.RSProgressCircle();
        lb_Width = new javax.swing.JLabel();
        lb_Height = new javax.swing.JLabel();
        lb_HxHy = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(448, 110));

        lb_SubTitle.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lb_SubTitle.setForeground(new java.awt.Color(99, 98, 98));
        lb_SubTitle.setText("Item Description");

        lb_Title.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lb_Title.setForeground(new java.awt.Color(0, 0, 0));
        lb_Title.setText("Item Title");

        btn_UP.setText("UP");
        btn_UP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_UPMouseClicked(evt);
            }
        });

        btn_DOWN.setText("DOWN");
        btn_DOWN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_DOWNMouseClicked(evt);
            }
        });

        toggleButton_ShowSubItems.setSelected(true);
        toggleButton_ShowSubItems.setText("SHOW");
        toggleButton_ShowSubItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleButton_ShowSubItemsMouseClicked(evt);
            }
        });

        lb_SubItemNumber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_SubItemNumber.setForeground(new java.awt.Color(102, 102, 102));
        lb_SubItemNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_SubItemNumber.setText("0");

        lb_HashCode.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lb_HashCode.setForeground(new java.awt.Color(99, 98, 98));
        lb_HashCode.setText("hashCode: ");

        lb_Parent.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lb_Parent.setForeground(new java.awt.Color(99, 98, 98));
        lb_Parent.setText("parent: ");

        lb_ItemIndex.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_ItemIndex.setForeground(new java.awt.Color(0, 0, 0));
        lb_ItemIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_ItemIndex.setText("0");

        lb_Location.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lb_Location.setForeground(new java.awt.Color(99, 98, 98));
        lb_Location.setText("LctOnScrn: ");

        rSProgressCircle1.setValue(100);
        rSProgressCircle1.setStringPainted(false);
        rSProgressCircle1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                rSProgressCircle1MouseDragged(evt);
            }
        });
        rSProgressCircle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rSProgressCircle1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rSProgressCircle1MouseReleased(evt);
            }
        });

        lb_Width.setForeground(new java.awt.Color(102, 102, 102));
        lb_Width.setText("W:000");

        lb_Height.setForeground(new java.awt.Color(102, 102, 102));
        lb_Height.setText("H:000");

        lb_HxHy.setForeground(new java.awt.Color(102, 102, 102));
        lb_HxHy.setText("Hy ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rSProgressCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Height)
                    .addComponent(lb_Width))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_ItemIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_SubItemNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lb_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lb_SubTitle))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(toggleButton_ShowSubItems))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lb_HashCode, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_Parent, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_Location, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_HxHy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(rSProgressCircle1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lb_ItemIndex)
                        .addGap(9, 9, 9)
                        .addComponent(lb_SubItemNumber))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_Title)
                        .addGap(9, 9, 9)
                        .addComponent(lb_SubTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(toggleButton_ShowSubItems)
                        .addGap(7, 7, 7)
                        .addComponent(lb_HashCode))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btn_UP)
                        .addGap(6, 6, 6)
                        .addComponent(btn_DOWN)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Parent)
                    .addComponent(lb_Height))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Location)
                    .addComponent(lb_Width)
                    .addComponent(lb_HxHy)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_UPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_UPMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            //this.toggleList.setSelected(!this.toggleList.isSelected(), true);
            //this.moveItemAdapter.componentMove(this.getIndex(), MoveUPDOWNComponentAdapter.ITEM_UP);
            this.onMoveItem(this.ITEM_UP);
        }
    }//GEN-LAST:event_btn_UPMouseClicked

    private void btn_DOWNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_DOWNMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            //this.toggleList.setSelected(!this.toggleList.isSelected(), true);
            //this.moveItemAdapter.componentMove(this.getIndex(), MoveUPDOWNComponentAdapter.ITEM_DOWN);
            this.onMoveItem(this.ITEM_DOWN);
        }
    }//GEN-LAST:event_btn_DOWNMouseClicked

    private void toggleButton_ShowSubItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleButton_ShowSubItemsMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            System.out.println(ListItem.this.getClass().getName() + "-> hasCode: " + this.hashCode() + " click btn_show index: " + this.getIndex());
            this.getToggleList().setSelected(!this.getToggleList().isSelected(), true);
        }
    }//GEN-LAST:event_toggleButton_ShowSubItemsMouseClicked

    private void rSProgressCircle1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSProgressCircle1MousePressed
        this.dragReady = false;
        this.mouseEventMousePressed = evt;
        this.animationTiming_ItemList_UPDOWN.animar();
    }//GEN-LAST:event_rSProgressCircle1MousePressed

    private void rSProgressCircle1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSProgressCircle1MouseReleased
        this.rSProgressCircle1.setValue(100);
        this.dragReady = false;
        this.mouseEventMousePressed = null;
        if (!this.animationTiming_ItemList_UPDOWN.isAnimatorRunnning()) {
            this.onDragEnded(dragReady);
        }
        if (this.animationTiming_ItemList_UPDOWN.isAnimatorRunnning()) {
            this.animationTiming_ItemList_UPDOWN.animatorStop();
        }
    }//GEN-LAST:event_rSProgressCircle1MouseReleased

    private void rSProgressCircle1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSProgressCircle1MouseDragged
        //System.out.println(this.getClass().getName()+".rSProgressCircle1MouseDragged() "+evt.getPoint());
        this.onDragReady(evt, dragReady);
    }//GEN-LAST:event_rSProgressCircle1MouseDragged

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DOWN;
    private javax.swing.JButton btn_UP;
    private javax.swing.JLabel lb_HashCode;
    private javax.swing.JLabel lb_Height;
    private javax.swing.JLabel lb_HxHy;
    private javax.swing.JLabel lb_ItemIndex;
    private javax.swing.JLabel lb_Location;
    private javax.swing.JLabel lb_Parent;
    private javax.swing.JLabel lb_SubItemNumber;
    private javax.swing.JLabel lb_SubTitle;
    private javax.swing.JLabel lb_Title;
    private javax.swing.JLabel lb_Width;
    private rojerusan.RSProgressCircle rSProgressCircle1;
    private javax.swing.JToggleButton toggleButton_ShowSubItems;
    // End of variables declaration//GEN-END:variables

}
