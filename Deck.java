import java.util.*;

public class Deck {
    private ArrayList<Card> cardPool;
    private Random randomGenerator;
    public ArrayList<Card> getCardPool() {
        return cardPool;
    }
    
    public Deck(BattleEngine battleEngine) {
        this.cardPool = new ArrayList<>();
        this.randomGenerator = new Random();

        cardPool.add(new PhysicalCard("Quick Attack", 1, 10, 7));
        cardPool.add(new PhysicalCard("Shuriken Toss", 2, 20, 10));
        cardPool.add(new PhysicalCard("Double Edge", 3, 35, 15));
        cardPool.add(new PhysicalCard("Dragon Tail", 1, 12, 8));
        cardPool.add(new PhysicalCard("Primal Roar", 2, 15, 12));

        cardPool.add(new MagicCard("Lucent Beam", 2, 5, 0, 0, 7));
        cardPool.add(new MagicCard("Mana Drain", 0, 3, 0, 3, 9));
        cardPool.add(new MagicCard("Shallow Grave", 5, 0, 30, 0, 8));
        cardPool.add(new MagicCard("Finger of Death", 10, 25, 0, 0, 5));
        cardPool.add(new MagicCard("Mystic Snake", 2, 15, 15, 0, 12));

        cardPool.add(new PureCard("Wild Axes", 5, 15, 0));
        cardPool.add(new PureCard("Consume", 5, 40, 25));
        cardPool.add(new PureCard("Purifying Flames", 3, 50, 35));

        cardPool.add(new BuffCard("Vitality Boost", 2, 20, 0, battleEngine));
        cardPool.add(new BuffCard("Titan's Blessing", 4, 40, 0, battleEngine));
        cardPool.add(new BuffCard("Arcane Wisdome", 2, 20, 1, battleEngine));
        cardPool.add(new BuffCard("Mana Mastery", 4, 20, 2, battleEngine));
        cardPool.add(new BuffCard("Divine Ascension", 6, 60, 0, battleEngine));

        cardPool.add(new DemiCard("Soul Reaver", 1, 10.0, 10));
        cardPool.add(new DemiCard("Blood Moon", 2, 15.0,9));
        cardPool.add(new DemiCard("Void Pulse", 3, 20.0, 7));
        cardPool.add(new DemiCard("Abyssal Judgment", 4, 25.0, 8));
        cardPool.add(new DemiCard("Final Eclipse", 6, 35.0, 11));

        cardPool.add(new InflictionCard("Venomous Bite", 2, 5, 3));
        cardPool.add(new InflictionCard("Burning Mark", 3, 8, 2));
        cardPool.add(new InflictionCard("Frostbite", 2, 4, 4));
        cardPool.add(new InflictionCard("Cursed Wound", 4, 10, 3));
        cardPool.add(new InflictionCard("Plague Strike", 5, 12, 4));

    }

    public Card drawRandomCard() {

        if (cardPool.isEmpty()) {
            return null;
        }
        
        int randomIndex = randomGenerator.nextInt(cardPool.size());
        return cardPool.remove(randomIndex);
    }
}


