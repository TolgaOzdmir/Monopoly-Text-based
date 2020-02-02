import java.util.*;
public class TwoDice {

	private int dice1,dice2;
	
	public void rollDice() {
		dice1=(int) (Math.random()*6)+1;
		dice2=(int) (Math.random()*6)+1;
	}
	public int getDice1() {
		return dice1;
	}
	private void setDice1(int dice1) {
		this.dice1 = dice1;
	}
	public int getDice2() {
		return dice2;
	}
	private void setDice2(int dice2) {
		this.dice2 = dice2;
	}
	public int getTotalDice() {
		return dice1+dice2;
	}
}
