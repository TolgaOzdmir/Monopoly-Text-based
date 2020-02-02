
public class GoodCard extends Card {
	
	public GoodCard(String text, String action, int cash) {
		super(text,action,cash);
		this.checkCard="good";
	}
	public GoodCard(String text, String action) {
		super(text,action);
		this.checkCard="good";
	}
}
