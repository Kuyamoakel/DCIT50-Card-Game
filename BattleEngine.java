import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class BattleEngine {
    private ArrayList<Card> hand;
    private Random random = new Random();

    public BattleEngine(){
        this.hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Enemy drawBoss(List<Enemy> selectedBosses) {

        int randomIndex = random.nextInt(selectedBosses.size());
        Enemy boss = selectedBosses.get(randomIndex);

        return boss;
    }
    
    public void drawHand(Deck deck, int numberOfCards) {
        
        for (int i = 0; i < numberOfCards; i++) {
            
            Card drawnCard = deck.drawRandomCard();

            if (drawnCard == null) {
                System.out.println("Deck is Empty!");
                return;
            }

            hand.add(drawnCard);
        }
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
    public void executeTurn(Player player, Enemy boss, Deck masterDeck) {

        int bossStartingHP = boss.getHp();
        int playerTotalDamageTaken = 0;
        int turn = 1;

        System.out.print("Searching for an oppenent");

        for (int i = 0; i < 3; i++) {
            pause(1000);
            System.out.print(".");
        }

        System.out.printf("%nBOSS ENCOUNTERED: %s", boss.getName());
        System.out.printf("%nHP: %d | Damage: %d", boss.getHp(), boss.getDamage());

        pause(2000);

        System.out.println("----- Boss Battle Begins -----");
        System.out.printf("%nPlayer | HP: %d | Mana: %d", player.getHp(), player.getMana());

        while (player.getHp() > 0 && !boss.isDead()) {

            System.out.printf("%n===== TURN %d =====%n", turn);

            if (turn > 1) {
                drawHand(masterDeck, 3);
            }

            if (hand.isEmpty()) {
                System.out.println("No cards avaiable this turn.");
            }

            while (!hand.isEmpty() && player.getHp() > 0 && !boss.isDead()) {
                Card card = hand.remove(0);
                card.printDetails();
                card.play(player, boss);

            }     

            boss.endTurn();
            
            if (!boss.isDead() && player.getHp() > 0) {
                boss.rageMode();

                int hpBeforeAttack = player.getHp();
                boss.attack(player);

                playerTotalDamageTaken += hpBeforeAttack - player.getHp();
            }
            turn++;
        }

            System.out.println("\n------------ BATTLE FINISHED ------------");
            if (player.getHp() > 0 && boss.isDead()) {
                System.out.println("~~~ PLAYER WINS ~~~");
            } else {
                System.out.println("~~~ PLAYER LOSE ~~~");
            }
            System.out.println("Player Final Status");
            System.out.printf("HP: %d/%d | Mana: %d/%d", player.getHp(), player.getMaxHp(), player.getMana(), player.getMaxMana());
            System.out.printf("%nTotal Damage Taken: %d", playerTotalDamageTaken);

            if (player.getHp() == 0 && !boss.isDead()) {
                System.out.println("\n~~~ BOSS WINS ~~~");
            } else {
                System.out.println("\n~~~ BOSS LOSE ~~~");
            }
            System.out.printf("%s Final Status", boss.getName());
            System.out.printf("%nHP: %s/%s %n", boss.getHp(), boss.getMaxHp());
            System.out.println("Total Damage Taken: " + (bossStartingHP - boss.getHp()));
        
    }
}   
