package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Attack;
import model.Board;
import model.Game;
import model.StageEnum;

/**
 * This panel is one of the major components of the Risk Game that the User can
 * interact with. This Panel contains all of the controls for each one of the
 * stages in the space risk game. This class must interact with the current
 * selection of planets on the MapPanel (Done by Paul Frost). Game is what can
 * be Observed by this class.
 * 
 * @author Matt Giardina
 */
public class PlayerPanel extends JPanel implements Observer {
	private StagePanel stagePanel; // The Left Hand Stage Panel
	private CardPanel cardPanel; // The Right Hand Card Panel
	private JPanel infoPanel; // The VERY IMPORTANT Panel
	private EndPanel endPanel; // The Next Stage/End Turn Panel
	boolean isPlaying = false;

	/**
	 * The Constructor for this massive beast of a class we call PlayerPanel
	 * 
	 */
	public PlayerPanel() {
		// setup this panel
		this.setBackground(Color.DARK_GRAY);
		this.setBounds(new Rectangle(1000, 150));
		this.setSize(new Dimension(1000, 150));
		this.setPreferredSize(new Dimension(1000, 150));
		this.setMaximumSize(new Dimension(1000, 150));

		// Create Sub-Panels
		stagePanel = new StagePanel();
		cardPanel = new CardPanel();
		endPanel = new EndPanel();
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());

		// ** NULL LAYOUT SETUP **/
		this.setLayout(null);
		stagePanel.setBounds(0, 0, 150, 150);
		infoPanel.setBounds(150, 0, 400, 150);
		cardPanel.setBounds(800, 0, 200, 150);
		endPanel.setBounds(550, 0, 250, 150);
		this.add(stagePanel, 0);
		this.add(cardPanel, 1);
		this.add(infoPanel, 2);
		this.add(endPanel, 3);

		this.update(null, null);
	}

	/**
	 * Want to return the InfoPanel which is the Panel that gets changed a lot
	 * (Next to stage buttons).
	 * 
	 * @return - A reference to the infoPanel.
	 */
	public JPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * The Update method that must be implemented because of the Observer /
	 * Observable connection between this class and Game.
	 */
	public void update(Observable arg0, Object arg1) {
		// Update Stage Buttons
		stagePanel.update();

		// Update End Panel Button
		endPanel.update();

		// Update for InfoPanel
		infoPanel.removeAll(); // HouseKeeping
		// If in the Reinforce Stage
		if (Game.stage == StageEnum.REINFORCE) {
			JLabel infoLabel = new JLabel();
			infoLabel.setIcon(new ImageIcon("images/infopanel_reinforce.png"));
			infoPanel.add(infoLabel, BorderLayout.CENTER);
		}
		// If in Attack Stage and Ready to Battle Two Valid Planets
		else if (Game.stage == StageEnum.ATTACK && arg1 instanceof model.Attack) {
			Attack theAttack = (model.Attack) arg1;
			// If the the Human Player is Attacking.
			if (theAttack.getAttackingTerritory().getOwner() == Board
					.getBoard().getPlayers().get(0))
				infoPanel.add(new BattlePanel((model.Attack) arg1, true),
						BorderLayout.CENTER);
			else
				// If the Human Player is Defending
				infoPanel.add(new BattlePanel((model.Attack) arg1, true),
						BorderLayout.CENTER);
		}
		// If in Attack Stage but no valid planet setups selected.
		else if (Game.stage == StageEnum.ATTACK) {
			JLabel infoLabel = new JLabel();
			infoLabel.setIcon(new ImageIcon("images/infopanel_attack.png"));
			infoPanel.add(infoLabel, BorderLayout.CENTER);
		}
		// If in the Fortify Stage and Valid Planets Selected.
		else if (Game.stage == StageEnum.FORTIFY
				&& arg1 instanceof model.Fortify) {
			infoPanel.add(new FortifyPanel((model.Fortify) arg1, false),
					BorderLayout.CENTER);
		}
		// If in Fortify Stage with none/not correct Planets Selected
		else if (Game.stage == StageEnum.FORTIFY) {
			FortifyPanel.amountToMove = new JLabel("0");
			JLabel theLabel = new JLabel("");
			theLabel.setIcon(new ImageIcon("images/infopanel_fortify.png"));
			infoPanel.add(theLabel, BorderLayout.CENTER);
		}
		// If an AI is running the show
		else if (Game.stage == StageEnum.AI_TURN) {
			infoPanel.setEnabled(false); // FOR NOW!
		} else {
			infoPanel.add(new JLabel("Not in Normal State"),
					BorderLayout.CENTER);
		}
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.paintAll(infoPanel.getGraphics()); // Paste it on there!

		// Repaint Everything
		this.repaint();

	}

	/**
	 * This panel is on the very left of the PlayerPanel and it shows the
	 * current stage (none click-able labels).
	 * 
	 * @author Matt Giardina & Paul Frost
	 * 
	 */
	@SuppressWarnings("serial")
	private class StagePanel extends JPanel {
		private JLabel fortLabel; // Fortify Stage Label
		private JLabel reinLabel; // Reinforce Stage Label
		private JLabel attackLabel; // Attack Stage Label

		/**
		 * The Constructor for this Panel
		 */
		private StagePanel() {
			// Set up this Panel
			this.setLayout(new BorderLayout());
			this.setBackground(Color.DARK_GRAY);

			// fortify label
			fortLabel = new JLabel();
			fortLabel.setBounds(new Rectangle(50, 150));
			fortLabel.setIcon(new ImageIcon("images/FortifyStage.png"));

			// reinforce label
			reinLabel = new JLabel();
			reinLabel.setBounds(new Rectangle(50, 150));
			reinLabel.setIcon(new ImageIcon("images/ReinforceStage.png"));

			// attack label
			attackLabel = new JLabel();
			attackLabel.setBounds(new Rectangle(50, 150));
			attackLabel.setIcon(new ImageIcon("images/AttackStage.png"));

			// add to panel
			this.add(fortLabel, BorderLayout.SOUTH);
			this.add(reinLabel, BorderLayout.NORTH);
			this.add(attackLabel, BorderLayout.CENTER);

			this.repaint(); // Show it!
		}

		/**
		 * Update the buttons depending on the stage
		 */
		public void update() {
			// If in the Attack Stage
			if (model.Game.stage == model.StageEnum.ATTACK) {
				attackLabel.setEnabled(true);
				reinLabel.setEnabled(false);
				fortLabel.setEnabled(false);
			}
			// If in the Fortify Stage
			else if (model.Game.stage == model.StageEnum.FORTIFY) {
				attackLabel.setEnabled(false);
				reinLabel.setEnabled(false);
				fortLabel.setEnabled(true);
			}
			// If in the Reinforce Stage
			else if (model.Game.stage == model.StageEnum.REINFORCE) {
				attackLabel.setEnabled(false);
				reinLabel.setEnabled(true);
				fortLabel.setEnabled(false);
			}
			// If in some unknown Stage
			else {
				attackLabel.setEnabled(false);
				reinLabel.setEnabled(false);
				fortLabel.setEnabled(false);
			}
		}
	}

	/**
	 * On the Far right, this panel is used as a click-able JLabel for the user
	 * to go into a new Frame and see their cards / turn them in if needed.
	 * 
	 * @author Matt Giardina & Paul Frost
	 * 
	 */
	private class CardPanel extends JPanel {
		private JLabel cardCount; // The Card Label

		/**
		 * The Constructor for this Panel
		 */
		private CardPanel() {
			// / Setup the Card Label
			cardCount = new JLabel();
			cardCount.setIcon(new ImageIcon("images/cardpanel.png"));

			// Add & Finish Panel Setup
			this.addMouseListener(new CardMouseListener());
			this.add(cardCount, BorderLayout.CENTER);
			this.setBackground(Color.DARK_GRAY);
		}

		/**
		 * The Listener that will do actions on the Click-able JLabel that is
		 * acting as a JButton in this case.
		 * 
		 * @author Matt Giardina
		 * 
		 */
		private class CardMouseListener implements MouseListener {

			/**
			 * On a Mouse Press and Release (Click)
			 */
			public void mouseClicked(MouseEvent arg0) {
				new CardFrame(Board.getBoard().getPlayers().get(0));
			}

			/**
			 * The Mouse enters the hover area of the image
			 */
			public void mouseEntered(MouseEvent arg0) {
				cardCount.setIcon(new ImageIcon("images/cardpanel_hover.png"));

			}

			/**
			 * The Mouse leaves the hover area of the image
			 */
			public void mouseExited(MouseEvent arg0) {
				cardCount.setIcon(new ImageIcon("images/cardpanel.png"));

			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		}
	}

	/**
	 * This Panel is in the middle of the Player panel and is used to change
	 * between Attack, Reinforce, and Fortify stages as well as display
	 * information if needed for each one of these stages. This will also allow
	 * the user to End a Turn and let the AI players play their part of the
	 * game.
	 * 
	 * @author Matt Giardina
	 * 
	 */
	public class EndPanel extends JPanel {
		private JLabel turnLabel; // The Next Stage / End Turn Button
		private JLabel theLabel; // 1st Label if Needed (Upper-Label)
		private JLabel theLabel2; // 2nd Label if Needed (Lower-Label)

		/**
		 * The Constructor for this Panel
		 */
		public EndPanel() {
			this.setBackground(Color.DARK_GRAY);
			this.setLayout(new BorderLayout());

			// Setup a Label
			theLabel = new JLabel("");
			theLabel.setForeground(Color.white);
			theLabel.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
			theLabel.setHorizontalTextPosition(theLabel.CENTER);
			this.add(theLabel, BorderLayout.CENTER);

			// Setup 2nd Label
			theLabel2 = new JLabel("");
			theLabel2.setForeground(Color.white);
			theLabel2.setHorizontalAlignment((int) this.CENTER_ALIGNMENT);
			theLabel2.setHorizontalTextPosition(theLabel.CENTER);
			this.add(theLabel2, BorderLayout.SOUTH);

			// Setup a Button
			turnLabel = new JLabel();
			turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
			turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
			turnLabel.setIcon(new ImageIcon("images/endpanel_nextstage.png"));
			this.add(turnLabel, BorderLayout.NORTH);
			// The Listener for the Button
			turnLabel.addMouseListener(new MouseListener() {

				/**
				 * On a Mouse Press and Release
				 */
				public void mouseClicked(MouseEvent e) {
					Sound.playStageChange(); // Play the Sound if Sound is
					// enabled!
					// If in the Reinforce Stage
					if (Game.stage == StageEnum.REINFORCE) {
						Game.stage = StageEnum.ATTACK;
					}
					// If in the Attack Stage
					else if (Game.stage == StageEnum.ATTACK) {
						Game.stage = StageEnum.FORTIFY;
						turnLabel.setIcon(new ImageIcon(
								"images/endpanel_endturn.png"));
					}
					// If in the Fortify or Other Stage
					else {
						turnLabel.setEnabled(false);
						theLabel.setText("AI's Turn to Play");
						Game.stage = StageEnum.AI_TURN;
						Game.game.getRiskFrame().setEnabled(false);
						Game.game.runAIs();
						Game.stage = StageEnum.REINFORCE;
						Game.game.update();
					}
					Game.game.update(); // Update the whole Frame & Model
				}

				/**
				 * On Mouse Hover
				 */
				public void mouseEntered(MouseEvent e) {
					if (Game.stage == StageEnum.FORTIFY
							|| Game.stage == StageEnum.AI_TURN)
						turnLabel.setIcon(new ImageIcon(
								"images/endpanel_endturn_hover.png"));
					else
						turnLabel.setIcon(new ImageIcon(
								"images/endpanel_nextstage_hover.png"));

				}

				/**
				 * When the Mouse Leaves the Hovered Image
				 */
				public void mouseExited(MouseEvent e) {
					if (Game.stage == StageEnum.FORTIFY
							|| Game.stage == StageEnum.AI_TURN)
						turnLabel.setIcon(new ImageIcon(
								"images/endpanel_endturn.png"));
					else
						turnLabel.setIcon(new ImageIcon(
								"images/endpanel_nextstage.png"));

				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
		}

		/**
		 * Update the End Panel. This is called by the PlayerPanel update method
		 * that is used from the notifyObservers() connection with the game
		 * class.
		 */
		public void update() {
			// If in the Reinforce Stage
			if (Game.stage == StageEnum.REINFORCE) {
				// If there Are no more Reinforcements to Deploy
				if (Board.getBoard().getPlayers().get(0).getReinforcements() == 0) {
					turnLabel.setEnabled(true);
					Game.stage = StageEnum.ATTACK;
					theLabel2.setText("");
					theLabel.setText("");
					this.repaint();
					Game.game.update();

				}
				// If there ARE reinforcements to deploy
				else {
					turnLabel.setEnabled(false);
					theLabel.setText("Reinforcements To Deploy");
					theLabel.setForeground(Color.white);
					theLabel2.setText(Board.getBoard().getPlayers().get(0)
							.getReinforcements()
							+ "");
					theLabel2.setForeground(Color.white);
					theLabel.setFont(new Font("Arial", Font.BOLD, 14));
					theLabel2.setFont(new Font("Arial", Font.BOLD, 40));
				}
			}
			// If not in Reinforce Stage, Don't do anything!
			else {
				theLabel2.setText("");
				theLabel.setText("");
				theLabel.setForeground(Color.LIGHT_GRAY);
				theLabel.setBackground(Color.DARK_GRAY);
				this.repaint(); // Show the stuff
			}
		}
	}

}
