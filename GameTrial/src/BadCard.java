
public class BadCard extends Card {
	
	public BadCard(String text, String action, int cash) {
		super(text,action,cash);
		this.checkCard="bad";
	}
	public BadCard(String text, String action) {
		super(text,action);
		this.checkCard="bad";
	}

}
