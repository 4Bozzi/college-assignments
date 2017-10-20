package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {
  private ImageIcon icon = new ImageIcon("frame-background.png");

  // Constructor
  public TestFrame() {
    // Set-up Initial Frame Settings
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setVisible(true);
    this.setResizable(true);

    // add components
    ImageIcon imh = new ImageIcon("frame-background.png");
    JPanel panel = new JPanel() {
      public void paintComponent(Graphics g) {
        Image img = new ImageIcon("frame-background.png").getImage();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        g.drawImage(img, 0, 0, null);
      }
    };
    panel.setOpaque(false);
    panel.setBounds(0, 0, imh.getIconWidth(), imh.getIconHeight());
    this.add(BorderLayout.CENTER, panel);
    this.add(BorderLayout.SOUTH, new JButton("Im on a boat."));
    // play an audio file
    try {
      InputStream in = new FileInputStream("Tera-Fied.au");
      AudioStream as = new AudioStream(in);
      AudioPlayer.player.start(as);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException ie) {
      ie.printStackTrace();
    }

    this.pack();
  }

  // Main Method
  public static void main(String[] args) {
    new TestFrame();
  }

}
