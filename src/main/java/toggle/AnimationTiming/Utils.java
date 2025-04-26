package toggle.AnimationTiming;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Utils {

    public Utils() {
    }

    private BufferedImage makeImageTransparent(BufferedImage image, float alpha) {
        // Create a buffered image with transparency
        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // Get the graphics object of the new image
        Graphics2D g2d = transparentImage.createGraphics();
        // Set the composite with the specified alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Draw the original image on the new image with the transparency
        g2d.drawImage(image, 0, 0, null);
        // Dispose of the graphics object
        g2d.dispose();
        return transparentImage;
    }

    private BufferedImage createBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

}
