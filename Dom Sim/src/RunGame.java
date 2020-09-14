import java.util.ArrayList;

public class RunGame {
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private static ArrayList<Card> hand = new ArrayList<Card>();
	private static ArrayList<Card> discard = new ArrayList<Card>();
	private static ArrayList<Card> kingdom = new ArrayList<Card>();
	private static ArrayList<Card> actionPlayOrder = new ArrayList<Card>();
	private static ArrayList<Card> treasurePlayOrder = new ArrayList<Card>();
	private static int actions = 1;
	private static int coins = 0;
	private static int buys = 0;
	private static Card village = new Card(1, 2, 0, 0, "action", "village");
	private static Card market = new Card(1, 1, 1, 1, "action", "market");
	private static Card smithy = new Card(3, 0, 0, 0, "action", "smithy");
	private static Card copper = new Card(0, 0, 1, 0, "treasure", "copper");
	private static Card estate = new Card(0, 0, 0, 0, "victory", "estate");
	public RunGame() {
		actionPlayOrder.add(village);
		actionPlayOrder.add(market);
		actionPlayOrder.add(smithy);
		treasurePlayOrder.add(copper);
		for (int i = 0; i < 10; i++) {
			discard.add(estate);
			discard.add(copper);
		}
		shuffle();
		for (int i = 0; i < 5; i++) {
			hand.add(deck.get(0));
			deck.remove(0);
		}
		turn();
	}
	public static void main(String[] args) {
		actionPlayOrder.add(village);
		actionPlayOrder.add(market);
		actionPlayOrder.add(smithy);
		treasurePlayOrder.add(copper);
		for (int i = 0; i < 10; i++) {
			discard.add(estate);
			discard.add(copper);
		}
		shuffle();
		for (int i = 0; i < 5; i++) {
			hand.add(deck.get(0));
			deck.remove(0);
		}
		turn();
		System.out.println("Actions: " + actions);
		System.out.println("Coins: " + coins);
		System.out.println("Buys: " + buys);
		printHand();
	}

	/**
	 * Plays the action phase, playing actions until it runs out of actions in hand
	 * or it runs out of actions. Order is dictated by actionPlayOrder.
	 */

	private static void turn() {
		actions = 1;
		coins = 0;
		buys = 0;
		actionPhase();
		treasurePhase();
	}

	private static void treasurePhase() {
		for (int i = 0; i < treasurePlayOrder.size(); i++) {
			if (hand.contains(treasurePlayOrder.get(i))) {
				play(treasurePlayOrder.get(i));
				i = -1;
			}
		}
	}

	private static void actionPhase() {
		for (int i = 0; i < actionPlayOrder.size(); i++) {
			if ((hand.contains(actionPlayOrder.get(i))) && (actions > 0)) {
				play(actionPlayOrder.get(i));
				i = -1;
			}
		}
	}

	/**
	 * Plays a card, changing actions down by 1, removing it from hand and playing
	 * the text on the card
	 */
	private static void play(Card card) {
		hand.remove(card);
		if (card.getType() == "action") {
			for (int i = 0; i < card.getCards(); i++) {
				try {
					hand.add(deck.get(0));
					deck.remove(0);
				} catch (IndexOutOfBoundsException e) {
				}
			}
			actions--;
			actions += card.getActions();
			coins += card.getCoins();
			buys += card.getBuys();
			System.out.println("plays a " + card.getName());
		}
		if (card.getType() == "treasure") {
			for (int i = 0; i < card.getCards(); i++) {
				try {
					hand.add(deck.get(0));
					deck.remove(0);
				} catch (IndexOutOfBoundsException e) {
				}
			}
			actions += card.getActions();
			coins += card.getCoins();
			buys += card.getBuys();
		}
	}

	private static void shuffle() {
		while (discard.size() != 0) {
			int i = (int) Math.floor(Math.random() * discard.size());
			deck.add(discard.get(i));
			discard.remove(i);
		}
	}

	private static void printHand() {
		for (int i = 0; i < hand.size(); i++) {
			System.out.print(hand.get(i).getName()+", ");
			System.out.println();
		}
	}

}
