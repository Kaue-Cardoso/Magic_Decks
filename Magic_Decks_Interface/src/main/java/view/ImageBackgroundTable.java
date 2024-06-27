package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImageBackgroundTable extends JTable {
    private Image backgroundImage;

    public ImageBackgroundTable(DefaultTableModel model, Image backgroundImage) {
        super(model);
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int width = getWidth();
            int height = getHeight();
            g.drawImage(backgroundImage, 0, 0, width, height, this);
        }
    }
}
