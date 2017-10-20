package test;

import model.Game;
import model.StageEnum;

public class FakeTurn extends Thread {

  private Game theGame;
  private int count;

  public FakeTurn(Game theGame) {
    count = 0;
    this.theGame = theGame;
    this.start();
  }

  public void run() {
      theGame.stage = StageEnum.REINFORCE;
      theGame.update();
      try {
        this.sleep(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      theGame.stage = StageEnum.ATTACK;
      theGame.update();
      try {
        this.sleep(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      theGame.stage = StageEnum.FORTIFY;
      theGame.update();
      try {
        this.sleep(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  }
}
