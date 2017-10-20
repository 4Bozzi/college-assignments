package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Board;
import model.Card;
import model.CardType;
import model.Game;
import model.Player;

public class CardFrame extends JFrame {

	private Player player;
	private CardTable table;

	public CardFrame(Player player) {
		// Setup this Frame
		this
				.setTitle("CSC335: Super Kewl Space Risk (Giardina, Frost, Downs, Bozzi)");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(610, 450);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(Color.DARK_GRAY);

		table = new CardTable(player);

		this.add(table, BorderLayout.NORTH);
		this.add(new ClearButton(table), BorderLayout.SOUTH);
		this.setVisible(true);

	}
	private CardTable getCardTable(){
		return table;
	}

	private class CardTable extends JPanel {
		public JLayeredPane layeredPane = new JLayeredPane();
		private ArrayList<CardPlanet> cardPlanets;
		Player player;

		private CardTable(Player player) {
			this.player = player;
			this.setLayout(null);
			this.setBackground(Color.DARK_GRAY);
			layeredPane.setSize(600, 300);

			cardPlanets = new ArrayList<CardPlanet>();

			Iterator<Card> iter = player.playersHand().getCurrentDeck()
					.iterator();

			int i = 0; // this is a temp variable

			while (iter.hasNext()) {
				Card card = iter.next();
				System.out.print("card");
				CardPlanet temp = new CardPlanet(card.getID(), i);
				cardPlanets.add(temp);
				layeredPane.add(temp);
				i++;

			}
			for (int j = i; j < 12; j++) {
				CardPlanet temp = new CardPlanet(43, j);
				cardPlanets.add(temp);
				layeredPane.add(temp);

			}
			this.add(layeredPane);
		}

		public void update() {
			Iterator<Card> iter = player.playersHand().getCurrentDeck()
					.iterator();

			int i = 0; // this is a temp variable

			while (iter.hasNext()) {
				Card card = iter.next();
				cardPlanets.get(i).update(card.cardID);
				i++;

			}
			for (int j = i; j < 12; j++) {
				cardPlanets.get(j).update(43);

			}
		}

		public void paintComponent(Graphics g) {
			Image img = new ImageIcon("images/cardTable.png").getImage();
			Dimension size = new Dimension(img.getWidth(null), img
					.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
			g.drawImage(img, 0, 0, null);
		}

	}

	private class CardPlanet extends JLabel {
		private int[][] locationArray = { { 0, 0 }, { 100, 0 }, { 200, 0 },
				{ 300, 0 }, { 400, 0 }, { 500, 0 }, { 0, 150 }, { 100, 150 },
				{ 200, 150 }, { 300, 150 }, { 400, 150 }, { 500, 150 } };

		private CardPlanet(int id, int index) {
			String filePath = "";
			filePath = "images/planets/planet" + (id + 1) + ".png";
			ImageIcon icon = new ImageIcon(filePath);
			this.setIcon(icon);
			this.setBounds(locationArray[index][0] + 15,
					locationArray[index][1] + 25, 70, 70);
		}

		private void update(int id) {
			String filePath = "";
			filePath = "images/planets/planet" + (id + 1) + ".png";
			ImageIcon icon = new ImageIcon(filePath);
			this.setIcon(icon);
		}
	}
	private class CardType extends JLabel {
		private int[][] locationArray = { { 0, 0 }, { 100, 0 }, { 200, 0 },
				{ 300, 0 }, { 400, 0 }, { 500, 0 }, { 0, 150 }, { 100, 150 },
				{ 200, 150 }, { 300, 150 }, { 400, 150 }, { 500, 150 } };

		private CardType(int type, int index) {
			String filePath = "";
			if(type == 0){
				filePath = "images/cards/none.png";

			}else if(type == 1){
				filePath = "images/cards/oneBar.png";

				
			}else if(type == 2){
				filePath = "images/cards/twoBar.png";

			}else if(type == 3){
				filePath = "images/cards/threeBar.png";

			}else if(type == 4){
				filePath = "images/cards/wildBar.png";

			}
			ImageIcon icon = new ImageIcon(filePath);
			this.setIcon(icon);
			this.setBounds(locationArray[index][0] + 15,
					locationArray[index][1] + 95, 70, 30);
		}

		private void update(int type) {
			String filePath = "";
			if(type == 0){
				filePath = "images/cards/none.png";

			}else if(type == 1){
				filePath = "images/cards/oneBar.png";

				
			}else if(type == 2){
				filePath = "images/cards/twoBar.png";

			}else if(type == 3){
				filePath = "images/cards/threeBar.png";

			}else if(type == 4){
				filePath = "images/cards/wildBar.png";

			}
			ImageIcon icon = new ImageIcon(filePath);
			this.setIcon(icon);
		}
	}
	private class ClearButton extends JLabel {
		private CardTable cardTable;
		public ClearButton(CardTable cardTable) {
			this.cardTable = cardTable;
			this.setLayout(null);
			String filepath = "images/cards/cardframe_clear.png";
			ImageIcon icon = new ImageIcon(filepath);
			this.setIcon(icon);
			this.setBounds(5, 50, 140, 50);
			this.addMouseListener(new ClearButtonListener(this));
			this.repaint();
		}
		public CardTable getCardTable(){
			return cardTable;
		}


	}

	private class ClearButtonListener implements MouseListener {
		private ClearButton theButton;

		public ClearButtonListener(ClearButton label) {
			theButton = label;
		}

		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent arg0) {
			theButton.setIcon(new ImageIcon(
					"images/cards/cardframe_clear_hover.png"));

		}

		public void mouseExited(MouseEvent arg0) {
			theButton
					.setIcon(new ImageIcon("images/cards/cardframe_clear.png"));

		}

		public void mousePressed(MouseEvent arg0) {
			Board.getBoard().getPlayers().get(0).draw();
			theButton.getCardTable().update();

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}