
public class Card extends Area {

	protected String text;
    protected String action;
    protected int cash;
    protected String checkCard;

    public Card(String text, String action, int cash) {
        this.text = text;
        this.action = action;
        this.cash = cash;
        this.checkArea="Card";
    }
    public Card(String text, String action) {
        this.text = text;
        this.action = action;
        this.checkArea="Card";
    }
    public Card() {
    	this.checkArea="Card";
    }
	public String toString() {
		return "Chance Card";
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public String getText() {
		return text;
	}
	public String getCheckCard() {
		return checkCard;
	}
	
}
