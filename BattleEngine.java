import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
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
    public void executeTurn(Player player, Enemy boss, Deck masterDeck, Scanner inpt) {

        int bossStartingHP = boss.getHp();
        int playerTotalDamageTaken = 0;
        int turn = 1;

        System.out.print("Searching for an oppenent");

        for (int i = 0; i < 3; i++) {
            pause(1000);
            System.out.print(".");
        }

        System.out.printf("%nBOSS ENCOUNTERED: %s", boss.getName());
        System.out.printf("%nHP: %d | Damage: %d%n", boss.getHp(), boss.getDamage());

        pause(2000);

        System.out.print("----- Boss Battle Begins -----");
        System.out.printf("%nPlayer | HP: %d | Mana: %d%n", player.getHp(), player.getMana());

        while (player.getHp() > 0 && !boss.isDead()) {

            if (turn > 1 && hand.isEmpty()) {
                drawHand(masterDeck, 3);
            }
            System.out.printf("%n===== TURN: %d =====%n", turn);
            System.out.println("===== CURRENT HAND =====");
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf("%d.) %s%n", i + 1, hand.get(i).getName());
            }
            System.out.println("----------------------");
            System.out.println("'h': Check one card stats");
            System.out.println("'a': Check all card stats");
            System.out.println("'s': Check match status");
            System.out.print("Choose a number(card) to play: ");
            String choice = inpt.nextLine();
            if(choice.equalsIgnoreCase("h")) {
                System.out.println("Which card to check?: ");
                int stats = inpt.nextInt();
                hand.get(stats-1).printDetails();
                inpt.nextLine();
            }
            else if(choice.equalsIgnoreCase("a")) {
                for(Card cards : hand) {
                    cards.printDetails();
                }
            } else if(choice.equalsIgnoreCase("s")) {
                System.out.printf("---------------------%n");
                System.out.printf("Player | HP: %d | Mana: %d%n", player.getHp(), player.getMana());
                System.out.printf("---------------------%n");
                System.out.printf("Boss | HP: %d | Damage: %d%n", boss.getHp(), boss.getDamage());
                System.out.printf("---------------------%n");
            }
            try{
                int cardChoice = Integer.parseInt(choice);
                if(cardChoice > 0 && cardChoice <= hand.size()) {
                    int card = cardChoice-1;
                    hand.get(card).play(player,boss);
                    hand.remove(card);
                }
                if (hand.isEmpty()) {
                    turn++;
                }
                if (!boss.isDead() && player.getHp() > 0 && hand.isEmpty()) {
                    boss.endTurn();
                    boss.rageMode();

                    int hpBeforeAttack = player.getHp();
                    boss.isInflicted();
                    if (!boss.isDead()) {
                        boss.attack(player);
                    }
                    playerTotalDamageTaken += hpBeforeAttack - player.getHp();
                }
            }
            catch (NumberFormatException e) {
                if (!choice.equalsIgnoreCase("h") && !choice.equalsIgnoreCase("a")
                && !choice.equalsIgnoreCase("s")) {
                    System.out.println("Invalid Input");
                }
            }
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