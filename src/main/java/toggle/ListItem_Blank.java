package toggle;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

class ListItem_Blank extends Item {

    private final String hoverIcon = "SVGImages/item_card_lined.svg";

    private Image imagenHover;

    public ListItem_Blank() {
        initComponents();
        //components = new Component[]{new SubListItem(), new SubListItem(), new ListItem(new SubListItem(), new ListItem(new SubListItem()))};
        //components = new Component[]{new SubListItem(), new SubListItem(), new ListItem(new SubListItem(), new SubListItem())};   
        //components = new Component[]{new SubListItem(), new SubListItem()};
        //components = new Component[0];
        this.imagenHover = com.crowie.ImagenFlatSVGIcon.svgImagen(hoverIcon, this.getWidth(), this.getHeight()).getImage();

    }

    public ListItem_Blank(Component componentToClone) {
        initComponents();
        this.jLabel1.setIcon(new ImageIcon(this.getPanelImageWithTransparency(componentToClone, 1.0f)));
    }
    
    public void setComponentToImage(Component componentToClone) {
        this.jLabel1.setIcon(new ImageIcon(this.getPanelImageWithTransparency(componentToClone, 1.0f)));
    }

    // Método para capturar la imagen de un JPanel con opacidad sobre toda la imagen
    private BufferedImage getPanelImageWithTransparency(Component panel, float opacity) {
        // Establecer el tamaño del panel si aún no tiene dimensiones
        if (panel.getWidth() == 0 || panel.getHeight() == 0) {
            panel.setSize(new Dimension(300, 200));  // Define dimensiones si es necesario
        }

        // Crear una imagen con canal alfa (transparente)
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtener el contexto gráfico de la imagen
        Graphics2D g2d = image.createGraphics();

        // Activar antialiasing para una mejor calidad de imagen
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Renderizar el panel en la imagen (sin aplicar transparencia aún)
        panel.paint(g2d);

        // Ahora aplicar transparencia global usando AlphaComposite
        BufferedImage transparentImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTrans = transparentImage.createGraphics();
        g2dTrans.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        // Dibujar la imagen capturada con la transparencia deseada
        g2dTrans.drawImage(image, 0, 0, null);

        // Liberar los recursos de los gráficos
        g2d.dispose();
        g2dTrans.dispose();

        return transparentImage;
    }

    @Override
    public void iniciarAlgoEnElComponente() {
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        /**
         * Una vez que el componente haya dibujado lo que debiía, tambien le
         * mandamos a dibujar nuestra imagen que actuará como fondo de este
         * panel principal.
         */

        //System.out.println(this.getClass().getName() + "-> x: " + x + " y: " + y);
        //g.drawImage(this.imagenHover, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
