package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.Board;

/**
 * The Menu that the user will see right below the Frame Title bar. This menu is
 * used to change some of the settings of this space risk game, as well as give
 * information about it's others and finally to exit the game with 'Quit'.
 * 
 * @author Matt Giardina & Paul Frost
 * 
 */
public class MenuBar extends JMenuBar {
  private static final long serialVersionUID = 1L;
  private JMenu file, settings, sound, animations, help;
  private JMenuItem fileExit, fileAbout, soundOff, soundOn, animationsOn, animationsOff, icons;

  /**
   * Create Everything with this Constructor!
   */
  public MenuBar() {

    // We create a menu called 'File'
    file = new JMenu("File");

    fileAbout = new JMenuItem("About");
    fileAbout.addActionListener(new MenuListener(fileAbout.getText()));
    file.add(fileAbout);

    fileExit = new JMenuItem("Quit");
    fileExit.addActionListener(new MenuListener(fileExit.getText()));
    file.add(fileExit);

    // We add the 'File' menu to the JMenuBar we are creating.
    this.add(file);

    // We Create a menu called 'settings'
    settings = new JMenu("Settings");

    // We Create a sub-menu called 'sound'
    sound = new JMenu("Sound");
    soundOff = new JMenuItem("Sound Off");
    soundOff.addActionListener(new MenuListener(soundOff.getText()));
    soundOn = new JMenuItem("Sound On");
    soundOn.addActionListener(new MenuListener(soundOn.getText()));
    sound.add(soundOn);
    sound.add(soundOff);
    settings.add(sound);

    // We Create s sub-menu called 'animations'
    animations = new JMenu("Animations");
    animationsOff = new JMenuItem("Animations Off");
    animationsOff.addActionListener(new MenuListener(animationsOff.getText()));
    animationsOn = new JMenuItem("Animations On");
    animationsOn.addActionListener(new MenuListener(animationsOn.getText()));
    animations.add(animationsOn);
    animations.add(animationsOff);
    settings.add(animations);

    // We add the 'Settings' menu to the JMenuBar we are creating.
    this.add(settings);

    // We Create a menu called 'Help'
    help = new JMenu("Help");
    icons = new JMenuItem("Icons");
    // Anonymous Inner Class (Listener)
    icons.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        new IconHelpFrame();
      }

    });
    help.add(icons);

    this.add(help);

  }

  /**
   * The Listener that will do different things depending on what menu item is
   * selected.
   * 
   * @author Matt Giardina || Paul Frost || Steven Downs
   * 
   */
  private class MenuListener implements ActionListener {
    private String input;

    public MenuListener(String input) {
      this.input = input;
    }

    public void actionPerformed(ActionEvent e) {
      if (input.compareTo("About") == 0)
        JOptionPane.showMessageDialog(null, "Story:\n In a quest to unite the galaxy, " + (Board.board.getPlayers().size() - 1)
            + " extraordinary evils rose to challenge you. Now you must put these Space guerillas back in there place in SPACE RISK: A CSC335 Final Project.\n\n"
            + "Grader:Dave 'Dave-A-saurus Dave' Gallup \n" + " Risk, made by Super Kewl Team. \n" + "Andrew Bozzi, Steven Downs, Paul Frost, & Matt Giardina \n\n");
      // Quit the Program
      else if (input.compareTo("Quit") == 0)
        System.exit(0);
      // Disallow Sound Actions
      else if (input.compareTo("Sound Off") == 0)
        Sound.off();
      // Allow Sound Actions
      else if (input.compareTo("Sound On") == 0)
        Sound.on();
      else {
        System.out.println("Menu Item Not Set up Yet");
      }
    }
  }
}
