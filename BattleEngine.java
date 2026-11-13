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

        System.out.print("MATCH MAKING");

        for (int i = 0; i < 3; i++) {
            pause(1000);
            System.out.print(".");
        }

        System.out.printf("%nBOSS ENCOUNTERED: %s", boss.getName());
        System.out.printf("%nHP: %d | Damage: %d%n", boss.getHp(), boss.getDamage());

        pause(2000);

        System.out.print("----- Boss Battle Begins -----");
        System.out.printf("%n%s | HP: %d | Mana: %d%n",player.getName(), player.getHp(), player.getMana());

        while (player.getHp() > 0 && !boss.isDead()) {
            if (masterDeck.getCardPool().isEmpty() && hand.isEmpty()) {
                System.out.printf("%nNo more cards in the deck.%n");
                break;
            }
            if (turn > 1 && hand.isEmpty()) {
                drawHand(masterDeck, 3);
            }
            System.out.printf("%n===== TURN: %d =====%n", turn);
            System.out.println("===== CURRENT HAND =====");
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf("%d.) %s%n", i + 1, hand.get(i).getName());
            }
            System.out.printf("===== BATTLE STATUS ===== %n");
            System.out.printf("%s | HP: %d | Mana: %d%n",player.getName(), player.getHp(), player.getMana());
            System.out.printf("%s | HP: %d | Damage: %d%n",boss.getName(), boss.getHp(), boss.getDamage());
            System.out.printf("---------------------%n");
            System.out.println("'h': Check one card stats");
            System.out.println("'a': Check all card stats");
            System.out.println("'e': End Turn");
            System.out.println("----------------------");
            System.out.print("Choose a number(card) to play: ");
            String choice = inpt.nextLine();
            boolean endTurn = false;
            if(choice.equalsIgnoreCase("h")) {
                System.out.println("Which card to check?: ");
                String statsInput = inpt.nextLine().strip();
                try {
                    int stats = Integer.parseInt(statsInput);
                    if (stats >= 1 && stats <= hand.size()) {
                        hand.get(stats - 1).printDetails();
                    } else {
                        System.out.println("Invalid card number!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid card number!");
                }
            }
            else if(choice.equalsIgnoreCase("a")) {
                for(Card cards : hand) {
                    cards.printDetails();
                }
            }
            else if (choice.equalsIgnoreCase("e")) {
                System.out.println("You end your turn.");
                hand.clear();
                endTurn = true;
            }
                try{
                int cardChoice = Integer.parseInt(choice);
                if(cardChoice > 0 && cardChoice <= hand.size()) {
                    endTurn = true;
                    int card = cardChoice-1;
                    hand.get(card).play(player,boss);
                    hand.remove(card);
                } else {
                    System.out.println("Invalid card number!");
                }
            }
            catch (NumberFormatException e) {
                if (!choice.equalsIgnoreCase("h") && !choice.equalsIgnoreCase("a")
                && !choice.equalsIgnoreCase("e")) {
                    System.out.println("Invalid Input");
                }
            }
            if (endTurn && hand.isEmpty()) {
                if (!boss.isDead() && player.getHp() > 0) {
                    int hpBeforeAttack = player.getHp();
                    boss.isInflicted();
                    boss.rageMode();
                    if (!boss.isDead()) {
                        boss.attack(player);
                    }
                    player.restoreMana(1);
                    playerTotalDamageTaken += hpBeforeAttack - player.getHp();
                }
                turn++;
            }
        }
            System.out.println("\n------------ BATTLE FINISHED ------------");
            if (!boss.isDead() && player.getHp() > 0 && masterDeck.getCardPool().isEmpty() && hand.isEmpty()) {
                System.out.println("~~~ INSUFFICIENT CARDS TO PULL ~~~");
            }
            if (player.getHp() <= 0) {
                System.out.printf("%n~~~ %s LOSE ~~~%n", player.getName());
            } else {
                System.out.printf("%n~~~ %s WINS ~~~%n", player.getName());
            }
            System.out.printf("%n%s Final Status%n", player.getName());
            System.out.printf("HP: %d/%d | Mana: %d/%d", player.getHp(), player.getMaxHp(), player.getMana(), player.getMaxMana());
            System.out.printf("%nTotal Damage Taken: %d%n", playerTotalDamageTaken);

            if (boss.isDead()) {
                System.out.printf("%n~~~ %s LOSE ~~~%n", boss.getName());
            } else {
                System.out.printf("%n~~~ %s WINS ~~~%n", boss.getName());
            }
            System.out.printf("%s Final Status", boss.getName());
            System.out.printf("%nHP: %s/%s %n", boss.getHp(), boss.getMaxHp());
            System.out.println("Total Damage Taken: " + (bossStartingHP - boss.getHp()));
            System.out.println("---------------------------");

            hand.clear();
    }

}   