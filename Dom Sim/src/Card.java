public class Card {
	private int cards;
	private int actions;
	private int coins;
	private int buys;
	private String name;
	private String type;
	public Card(int setCards,int setActions,int setCoins,int setBuys,String type, String name) {
		cards = setCards;
		actions = setActions;
		coins = setCoins;
		buys = setBuys;
		this.name = name;
		this.type = type;
	}
	public int getCards() {
		return cards;
	}
	public void setCards(int cards) {
		this.cards = cards;
	}
	public int getActions() {
		return actions;
	}
	public void setActions(int actions) {
		this.actions = actions;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public int getBuys() {
		return buys;
	}
	public void setBuys(int buys) {
		this.buys = buys;
	}
	public String getName(){
		return name;
	}
	public String getType() {
		return type;
	}
}
