package com.crowie;

import toggle.AnimationTiming.AnimationTiming;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import toggle.Item;
import toggle.ToggleListAnimationLayout;

class TestFrame extends javax.swing.JFrame {

    public TestFrame() {
        initComponents();
        //this.toggleListAnimationLayout1.setjScrollPaneLayoutContainer(jScrollPane1);
        
        
    }
    //--------------------------------------------------------------------------

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public ListItem getListItem1() {
        return listItem1;
    }

    public ListItem getListItem2() {
        return listItem2;
    }

    public ListItem getListItem3() {
        return listItem3;
    }

    /*
    public ListItem getListItem4() {
        return listItem4;
    }

    public ListItem getListItem5() {
        return listItem5;
    }

    public ListItem getListItem6() {
        return listItem6;
    }

    public ListItem getListItem7() {
        return listItem7;
    }
     */

    public SubListItem getSubListItem1() {
        return subListItem1;
    }
   
    public SubListItem getSubListItem2() {
        return subListItem2;
    }
    public SubListItem getSubListItem3() {
        return subListItem3;
    }

    public SubListItem getSubListItem4() {
        return subListItem4;
    }

    
    public ToggleListAnimationLayout getToggleListAnimationLayout1() {
        return toggleListAnimationLayout1;
    }

    //--------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        toggleListAnimationLayout1 = new toggle.ToggleListAnimationLayout();
        listItem1 = new com.crowie.ListItem();
        subListItem1 = new com.crowie.SubListItem();
        subListItem2 = new com.crowie.SubListItem();
        listItem2 = new com.crowie.ListItem();
        subListItem3 = new com.crowie.SubListItem();
        subListItem4 = new com.crowie.SubListItem();
        listItem3 = new com.crowie.ListItem();
        btn_ItemUP = new javax.swing.JButton();
        btn_ItemDOWN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        btn_AddItemOnIndex = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sp_AddItemOnIndex = new javax.swing.JSpinner();
        sp_AddSubItemOnIndex = new javax.swing.JSpinner();
        btn_AddSubItemOnIndex = new javax.swing.JButton();
        btn_RemoveSubItemOnIndex = new javax.swing.JButton();
        btn_RemoveItemOnIndex = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        sp_RemoveItemOnIndex = new javax.swing.JSpinner();
        sp_RemoveSubItemOnIndex = new javax.swing.JSpinner();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        listItem1.setBackground(new java.awt.Color(153, 255, 255));
        listItem1.setTitulo("Item Title 1");
        toggleListAnimationLayout1.add(listItem1);

        subListItem1.setTitulo("SubItem Title 01");
        toggleListAnimationLayout1.add(subListItem1);

        subListItem2.setTitulo("SubItem Title 02");
        toggleListAnimationLayout1.add(subListItem2);

        listItem2.setBackground(new java.awt.Color(255, 204, 204));
        listItem2.setTitulo("Item Title 2");
        toggleListAnimationLayout1.add(listItem2);

        subListItem3.setTitulo("SubItem Title 03");
        toggleListAnimationLayout1.add(subListItem3);

        subListItem4.setTitulo("SubItem Title 04");
        toggleListAnimationLayout1.add(subListItem4);

        listItem3.setBackground(new java.awt.Color(255, 204, 204));
        listItem3.setTitulo("Item Title 3");
        toggleListAnimationLayout1.add(listItem3);

        jScrollPane1.setViewportView(toggleListAnimationLayout1);

        btn_ItemUP.setText("ItemUp");
        btn_ItemUP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ItemUPMouseClicked(evt);
            }
        });

        btn_ItemDOWN.setText("ItemDown");
        btn_ItemDOWN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ItemDOWNMouseClicked(evt);
            }
        });

        jLabel1.setText("MOVER");

        jLabel2.setText("DESDE POSICION: ");

        jLabel3.setText("HASTA POSICION: ");

        jButton1.setText("MOVER");

        jButton2.setText("jButton2");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        btn_AddItemOnIndex.setText("AddItem");
        btn_AddItemOnIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AddItemOnIndexMouseClicked(evt);
            }
        });

        jLabel4.setText("ADD ITEM ON INDEX");

        jLabel5.setText("ADD SUBITEM ON INDEX");

        btn_AddSubItemOnIndex.setText("AddSubItem");
        btn_AddSubItemOnIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AddSubItemOnIndexMouseClicked(evt);
            }
        });

        btn_RemoveSubItemOnIndex.setText("RemoveSubItem");
        btn_RemoveSubItemOnIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_RemoveSubItemOnIndexMouseClicked(evt);
            }
        });

        btn_RemoveItemOnIndex.setText("RemoveItem");
        btn_RemoveItemOnIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_RemoveItemOnIndexMouseClicked(evt);
            }
        });

        jLabel6.setText("REMOVE ITEM ON INDEX");

        jLabel7.setText("REMOVE SUBITEM ON INDEX");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(btn_ItemUP)
                                .addGap(72, 72, 72)
                                .addComponent(btn_ItemDOWN)))
                        .addGap(108, 108, 108)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sp_RemoveItemOnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(sp_RemoveSubItemOnIndex))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_RemoveSubItemOnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_RemoveItemOnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sp_AddItemOnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(sp_AddSubItemOnIndex))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_AddSubItemOnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_AddItemOnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ItemUP)
                            .addComponent(btn_ItemDOWN))
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(sp_AddItemOnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_AddItemOnIndex))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(sp_AddSubItemOnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_AddSubItemOnIndex))
                        .addGap(9, 9, 9)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(sp_RemoveItemOnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_RemoveItemOnIndex))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(sp_RemoveSubItemOnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_RemoveSubItemOnIndex))
                        .addGap(12, 12, 12))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ItemUPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ItemUPMouseClicked

    }//GEN-LAST:event_btn_ItemUPMouseClicked

    private void btn_ItemDOWNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ItemDOWNMouseClicked

    }//GEN-LAST:event_btn_ItemDOWNMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            /*for (Component comp : this.toggleListAnimationLayout1.getComponents()) {
                this.verSizes(comp);
            }*/
            AnimationTiming animationTiming = new AnimationTiming();
            animationTiming.setAnimationTime(200);
            animationTiming.setAnimationRepeatCount(org.jdesktop.animation.timing.Animator.INFINITE);
            animationTiming.setAnimationComportamientoOnRepeat(Animator.RepeatBehavior.REVERSE);
            animationTiming.addEventAnimationTimingAdapter(new toggle.AnimationTiming.AnimationTimingAdapter() {
                @Override
                public void onAnimatedStarted(boolean started) {
                    System.out.println("onAnimatedStarted");
                }

                @Override
                public void onAnimatedRepeated(boolean repeated) {
                    System.out.println("onAnimatedRepeated");
                }

                @Override
                public void onAnimated(float animated) {
                    System.out.println("onAnimated: " + animated);
                }

                @Override
                public void onAnimatedEnded(boolean ended) {
                    System.out.println("onAnimatedEnded\n");
                }

            });
            animationTiming.animar();
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void btn_AddItemOnIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AddItemOnIndexMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            int index = Integer.parseInt(this.sp_AddItemOnIndex.getValue().toString());
            ListItem item = new ListItem();
            item.setTitulo("Item Añadido " + Math.random());
            this.toggleListAnimationLayout1.add(item, index);
            this.toggleListAnimationLayout1.revalidate();
            this.toggleListAnimationLayout1.repaint();
        }
    }//GEN-LAST:event_btn_AddItemOnIndexMouseClicked

    private void btn_AddSubItemOnIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AddSubItemOnIndexMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            int index = Integer.parseInt(this.sp_AddSubItemOnIndex.getValue().toString());
            SubListItem subItem = new SubListItem();
            subItem.setTitulo("SubItem Añadido " + subItem.hashCode());
            //this.toggleListAnimationLayout1.add(subItem, index);

            //this.toggleListAnimationLayout1.addSubItem(this.listItem7, subItem, index);
            this.toggleListAnimationLayout1.revalidate();
            this.toggleListAnimationLayout1.repaint();
        }
    }//GEN-LAST:event_btn_AddSubItemOnIndexMouseClicked

    private void btn_RemoveItemOnIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RemoveItemOnIndexMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            int index = Integer.parseInt(this.sp_RemoveItemOnIndex.getValue().toString());

            //Component c = this.toggleListAnimationLayout1.getListOnlyItemAtIndex(index);
            //this.toggleListAnimationLayout1.remove(c);
            this.toggleListAnimationLayout1.remove(index);
            this.toggleListAnimationLayout1.revalidate();
            this.toggleListAnimationLayout1.repaint();
        }
    }//GEN-LAST:event_btn_RemoveItemOnIndexMouseClicked

    private void btn_RemoveSubItemOnIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RemoveSubItemOnIndexMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
            int index = Integer.parseInt(this.sp_RemoveSubItemOnIndex.getValue().toString());

            Component c = this.toggleListAnimationLayout1.getListOnlyItemAtIndex(index);
            this.toggleListAnimationLayout1.removeSubItem((Item) c, this.toggleListAnimationLayout1.getListOnlySubItemAtIndex(index));
            //this.toggleListAnimationLayout1.remove(index);
            this.toggleListAnimationLayout1.revalidate();
            this.toggleListAnimationLayout1.repaint();
        }
    }//GEN-LAST:event_btn_RemoveSubItemOnIndexMouseClicked

    private void verSizes(Component comp) {
        System.out.println("comp: " + comp.getClass().getName());
        System.out.println("MaxZ: " + comp.getMaximumSize());
        System.out.println("MinZ: " + comp.getMinimumSize());
        System.out.println("PreZ: " + comp.getPreferredSize());
        System.out.println("sizZ: " + comp.getSize());
        System.out.println("");
    }

    private Dimension dimensionToHalf(Dimension dim) {
        dim.height = dim.height / 2;
        dim.width = dim.width / 2;
        return dim;
    }

    private Dimension dimensionPercent(Dimension dim, float percent) {
        dim.height = (int) (dim.height * percent);
        dim.width = (int) (dim.width * percent);
        return dim;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AddItemOnIndex;
    private javax.swing.JButton btn_AddSubItemOnIndex;
    private javax.swing.JButton btn_ItemDOWN;
    private javax.swing.JButton btn_ItemUP;
    private javax.swing.JButton btn_RemoveItemOnIndex;
    private javax.swing.JButton btn_RemoveSubItemOnIndex;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private com.crowie.ListItem listItem1;
    private com.crowie.ListItem listItem2;
    private com.crowie.ListItem listItem3;
    private javax.swing.JSpinner sp_AddItemOnIndex;
    private javax.swing.JSpinner sp_AddSubItemOnIndex;
    private javax.swing.JSpinner sp_RemoveItemOnIndex;
    private javax.swing.JSpinner sp_RemoveSubItemOnIndex;
    private com.crowie.SubListItem subListItem1;
    private com.crowie.SubListItem subListItem2;
    private com.crowie.SubListItem subListItem3;
    private com.crowie.SubListItem subListItem4;
    private toggle.ToggleListAnimationLayout toggleListAnimationLayout1;
    // End of variables declaration//GEN-END:variables
}
