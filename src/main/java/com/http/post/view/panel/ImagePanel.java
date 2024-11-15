package com.http.post.view.panel;

import com.http.post.utils.formatter.ContextFormatter;
import com.http.post.utils.formatter.FormatterException;
import file.ByteArrayToFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements Responsible  {

    private JLabel labelImage;
    private JPanel panel;

    public ImagePanel(String name, Boolean editable, String layout) {
        setLayout(new BorderLayout());
        this.labelImage = new JLabel(new ImageIcon("src/main/resources/images/Untitled.png"));
        add(createImagePanel(name, labelImage, layout), BorderLayout.CENTER);
    }

    private JPanel createImagePanel(String title, JLabel labelImage, String layout) {
        this.panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(labelImage), layout);
        return panel;
    }


    @Override
    public void setBody(String content, String contentType) {
        try {
            ContextFormatter contextFormatter = new ContextFormatter(contentType);
            byte[] imageBytes = contextFormatter.format(content).getBytes();
            if (imageBytes.length == 0) {
                throw new IOException("No image data found after formatting.");
            }
            File tempFile = ByteArrayToFile.writeFile(imageBytes);
            BufferedImage image = ImageIO.read(tempFile);
            if (image == null) {
                throw new IOException("Image data could not be decoded.");
            }
            labelImage.setIcon(new ImageIcon(image));
            panel.revalidate();
            panel.repaint();
        } catch (FormatterException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("Error formatting content: " + e.getMessage(), e);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Error loading image: " + e.getMessage(), e);
        }
    }
}