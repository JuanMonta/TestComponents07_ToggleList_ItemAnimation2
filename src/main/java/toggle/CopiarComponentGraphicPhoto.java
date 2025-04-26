
package toggle;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class CopiarComponentGraphicPhoto extends javax.swing.JPanel {

   
    public CopiarComponentGraphicPhoto() {
        initComponents();
    }
    
    public void colocarImagenComponentPhoto(BufferedImage image){
        if (image !=null) {
            this.jLabel1.setIcon(new ImageIcon(image));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jLabel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
