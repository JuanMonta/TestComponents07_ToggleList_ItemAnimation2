
package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RestrictedDragWindowExample {
    private static JWindow window;
    private static Timer clickTimer;
    private static JPanel panel;  // El panel sobre el cual se moverá el JWindow

    public static void main(String[] args) {
        JFrame frame = new JFrame("Restricted JWindow Example");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel base
        panel = new JPanel(null);
        panel.setBackground(Color.LIGHT_GRAY);

        // Crear un botón para iniciar el drag
        JButton button = new JButton("Mantén presionado");
        button.setBounds(50, 50, 150, 50);
        panel.add(button);

        // Crear un segundo componente para el "drop"
        JButton dropButton = new JButton("Suelta aquí");
        dropButton.setBounds(300, 200, 150, 50);
        panel.add(dropButton);

        // Crear el JWindow
        window = new JWindow();
        window.setSize(150, 50);
        window.setLayout(new BorderLayout());
        window.add(new JLabel("Ventana en movimiento", SwingConstants.CENTER), BorderLayout.CENTER);

        // Crear un temporizador que se activa después de 2 segundos
        clickTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar la ventana cuando el temporizador se complete
                Point location = MouseInfo.getPointerInfo().getLocation();
                window.setLocation(location);
                window.setVisible(true);
            }
        });
        clickTimer.setRepeats(false);  // No repetir el temporizador

        // Mouse listener para el botón
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Iniciar el temporizador cuando el clic es presionado
                clickTimer.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Si se suelta el clic antes de 2 segundos, detener el temporizador
                if (clickTimer.isRunning()) {
                    clickTimer.stop();
                } else {
                    // Ocultar la ventana cuando el clic es liberado
                    window.setVisible(false);

                    // Detectar componente bajo el cursor al soltar
                    Component componentUnderCursor = SwingUtilities.getDeepestComponentAt(panel, e.getXOnScreen(), e.getYOnScreen());
                    if (componentUnderCursor == dropButton) {
                        JOptionPane.showMessageDialog(frame, "Suelto sobre el botón!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Suelto fuera del botón.");
                    }
                }
            }
        });

        // MouseMotionListener para mover el JWindow dentro del panel
        button.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Obtener las coordenadas del mouse
                Point location = e.getLocationOnScreen();
                
                // Obtener las coordenadas y tamaño del JPanel
                Rectangle panelBounds = panel.getBounds();
                Point panelLocationOnScreen = panel.getLocationOnScreen();

                // Limitar el movimiento del JWindow dentro de los límites del JPanel
                int windowWidth = window.getWidth();
                int windowHeight = window.getHeight();

                // Limitar las coordenadas en el eje X
                int minX = panelLocationOnScreen.x;
                int maxX = panelLocationOnScreen.x + panelBounds.width - windowWidth;
                location.x = Math.max(minX, Math.min(maxX, location.x));

                // Limitar las coordenadas en el eje Y
                int minY = panelLocationOnScreen.y;
                int maxY = panelLocationOnScreen.y + panelBounds.height - windowHeight;
                location.y = Math.max(minY, Math.min(maxY, location.y));

                // Mover el JWindow dentro de los límites calculados
                if (window.isVisible()) {
                    window.setLocation(location.x, location.y);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // No se necesita implementar para este caso
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
