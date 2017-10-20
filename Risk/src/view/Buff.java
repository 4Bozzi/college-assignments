package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Attack;
import model.Board;
import model.Fortify;
import model.Game;
import model.Player;
import model.StageEnum;
import model.Territory;
/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Paul
 *
 *The buff class is the class in charge of highlighting different territories in different 
 *circumstances. This greatly improves the flow of the game as it visually informs the 
 *user who it can attack, who his allies are, etc.
 */

public class Buff extends JLabel {

  private static final long serialVersionUID = 1L;
  private int uniqueID;
  private boolean selected = false;
  private boolean mouseOver = false;
  // this is used for the small case where some wants to fortify from a
  // territory with over two armies too a territory with over two armies
  private boolean fortifyFlag = false;

  private MapPanel mapPanel;

  /**
   * This is the buff constructor. It constructs a buff based on the territory selected, it uses
   * observer-observable to communicate with the game to know what stage it is in so it knows
   * what type of buff to display. It uses the territory id to know where to place the buff.
   * 
   * *Note: A buff is basically an icon that gets applied to a planet.
   * 
   * @param id - the id number of the territory selected
   * @param mapPanel - the panel that stores the map that has all of the planets on it.
   */
  public Buff(int id, MapPanel mapPanel) {
    String filePath = "images/noBuff.png";
    this.mapPanel = mapPanel;
    ImageIcon icon = new ImageIcon(filePath);
    this.addMouseListener(new LabelMouseListener());
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(id), Board.getBoard().getTerritoryY(id), 70, 70);
    uniqueID = id;
    this.setToolTipText("Owner: " + Board.getBoard().getGlobe().getTerritoryByID(id).getOwner().getName() + " ||| " + "Number of Armies: "
        + Board.getBoard().getGlobe().getTerritoryByID(id).getNumDice());
  }

  /**
   *
   */
  private class LabelMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent arg0) {
      boolean canBeModifiedByHuman = Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().isPlayerHuman();

      Player player = Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner();

      Territory territory = Board.getBoard().getGlobe().getTerritoryByID(uniqueID);

      // ------------------------------------------------------------//
      // -------------------------ATTACK-----------------------------//
      // ------------------------------------------------------------//
      // If they are in attack stage, this selects first allied terr to
      // attack from.
      if (Game.stage == StageEnum.ATTACK) {
        if (canBeModifiedByHuman) {

          mapPanel.clearBuffs();

          selectBuff();
          selected = true;
          mapPanel.setAttackingTerritory(territory);
          Iterator<Territory> iter = Board.getBoard().getAdjEnemies(uniqueID).iterator();
          while (iter.hasNext()) {
            Territory terr = iter.next();
            mapPanel.getBuff(terr.getUniqueID()).targetBuff();
            mapPanel.getBuff(terr.getUniqueID()).setSelected();
          }
        } else if (!canBeModifiedByHuman && selected) {
          Iterator<Territory> iter = Board.getBoard().getAdjEnemies(mapPanel.getAttackingTerritory()).iterator();
          while (iter.hasNext()) {
            Territory terr = iter.next();
            mapPanel.getBuff(terr.getUniqueID()).targetBuff();
            mapPanel.getBuff(terr.getUniqueID()).setSelected();
          }
          attackedBuff();
          Game.game.update(new Attack(mapPanel.getAttackingTerritory(), territory));
        }
      }

      // ------------------------------------------------------------//
      // ---------------------REINFORCEMENT--------------------------//
      // ------------------------------------------------------------//

      // this increments the armies in the territory
      if (Game.stage == StageEnum.REINFORCE && canBeModifiedByHuman) {
        if (player.getReinforcements() > 0) {
          // Sound.playReinforce();
          player.decrementReinforcements();
          territory.adjustDice(1);
          mapPanel.updateArmyCount();
          Game.game.update();
        }
      }
      // ------------------------------------------------------------//
      // -------------------------FORTIFY----------------------------//
      // ------------------------------------------------------------//

      if (Game.stage == StageEnum.FORTIFY && canBeModifiedByHuman) {
        if (territory.getNumDice() > 1 && !fortifyFlag && !selected) {
          mapPanel.clearBuffs();
          mapPanel.setSourceTerritory(territory);
          selectBuff();
          setSelected();
          Iterator<Territory> iter = Board.getBoard().getAllies(territory).iterator();
          while (iter.hasNext()) {
            Territory terr = iter.next();
            mapPanel.getBuff(terr.getUniqueID()).fortifyBuff();
            mapPanel.getBuff(terr.getUniqueID()).setSelected();
          }
        } else if (selected && !fortifyFlag) {
          Game.game.update(new Fortify(mapPanel.getSourceTerritory(), territory));
          // mapPanel.clearBuffs();
          mapPanel.getBuff(mapPanel.getSourceTerritory().getUniqueID()).selectBuff();
          mapPanel.getBuff(mapPanel.getSourceTerritory().getUniqueID()).setSelected();
          fortifyBuff();
          fortifyFlag = true;
          selected = true;

        } else if (selected && fortifyFlag && territory.getNumDice() > 1) {
          mapPanel.clearBuffs();
          mapPanel.setSourceTerritory(territory);
          selectBuff();
          Iterator<Territory> iter = Board.getBoard().getAllies(territory).iterator();
          while (iter.hasNext()) {
            Territory terr = iter.next();
            mapPanel.getBuff(terr.getUniqueID()).fortifyBuff();
            mapPanel.getBuff(terr.getUniqueID()).setSelected();
          }
        }

      }

    }

    public void mouseEntered(MouseEvent arg0) {
      if (Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().isPlayerHuman() && !selected && (Game.stage == StageEnum.ATTACK)) {
        selectableBuff();
        mouseOver = true;
      } else if (Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().isPlayerHuman() && !selected && (Game.stage == StageEnum.FORTIFY)
          && Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getNumDice() > 1) {
        selectableBuff();
        mouseOver = true;
      }
    }

    public void mouseExited(MouseEvent arg0) {
      if (Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().isPlayerHuman() && !selected && mouseOver
          && Board.getBoard().getGlobe().getTerritoryByID(uniqueID) != mapPanel.getSourceTerritory()) {
        clearBuff();
      }
    }

    public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub

    }

  }

  public void clearBuff() {
    String filePath = "images/noBuff.png";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
    this.selected = false;
    this.fortifyFlag = false;
  }

  public void attackedBuff() {
    // String filePath = "images/attackedBuff.png";
    String filePath = "images/attacked.gif";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);

  }

  public void selectBuff() {
    String filePath = "images/selectBuff.png";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
  }

  public void selectableBuff() {
    String filePath = "images/selectableBuff.png";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
  }

  public void targetBuff() {
    String filePath = "images/targetBuff.png";
    selected = true;
    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
  }

  public void reinforceBuff() {
    String filePath = "images/reinforceBuff.png";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
  }

  public void fortifyBuff() {
    String filePath = "images/fortifyBuff.png";

    ImageIcon icon = new ImageIcon(filePath);
    this.setIcon(icon);
    this.setBounds(Board.getBoard().getTerritoryX(uniqueID), Board.getBoard().getTerritoryY(uniqueID), 70, 70);
  }

  public int getID() {
    return uniqueID;
  }

  public void updateBuff() {
	  this.setToolTipText("Owner: " + Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().getName() + " ||| " + "Number of Armies: "
		        + Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getNumDice());
	  
    //Territory terr = Board.getBoard().getGlobe().getTerritoryByID(uniqueID);
    if (Game.stage == StageEnum.FORTIFY) {

      clearBuff();

    }
    if ((Game.stage == StageEnum.REINFORCE) && Board.getBoard().getGlobe().getTerritoryByID(uniqueID).getOwner().isPlayerHuman()) {
      reinforceBuff();
    }

  }

  public void setSelected() {
    this.selected = true;
  }

}
