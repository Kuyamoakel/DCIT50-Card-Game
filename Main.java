import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isRunning = true;
        BattleEngine engine = new BattleEngine();
        Deck masterDeck = new Deck(engine);
        Difficulty difficulty = null;
        Enemy boss = null;
        Player hero;

            System.out.println("====== CARD BATTLE ======");
            System.out.println("1. Start Game");
            System.out.println("2. Selection Mode");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String menu = in.nextLine().strip();

            switch (menu) {
                case "1":
                    CharacterClass characterClassSG = selectCharacter();
                    if (characterClassSG == null) {
                        return;
                    }
                    hero = new Player("Player", characterClassSG);

                    System.out.println("====== SELECT DIFFICULTY ======");
                    System.out.println("1. Easy");
                    System.out.println("2. Medium");
                    System.out.println("3. Hard");
                    System.out.print("Choose: ");
                    String level = in.nextLine().strip();

                    switch (level) {
                        case "1":
                            difficulty = Difficulty.EASY;
                            boss = engine.drawBoss(difficulty.getBosses());
                            break;
                        case "2":
                            difficulty = Difficulty.MEDIUM;
                            boss = engine.drawBoss(difficulty.getBosses());
                            break;
                        case "3":
                            difficulty = Difficulty.HARD;
                            boss = engine.drawBoss(difficulty.getBosses());
                            break;
                        default:
                            System.out.println("Invalid Input!");
                            break;
                    }
                    break;
                case "2":
                    CharacterClass characterClassSM = selectCharacter();
                    if (characterClassSM == null) {
                        return;
                    }

                    hero = new Player("Player", characterClassSM);
                    boss = selectBoss();
                    break;
                case "3":
                    System.out.println("Thank you so much byes~");
                    return;
                default:
                    System.out.println("Invalid input!");
                    return;
            }

            if (boss == null) {
                System.out.println("No boss Found!");
                return;
            }
            while (isRunning) {
                startGame(engine,hero,boss,masterDeck, difficulty, in); // used a static method for player input
                String choice;
                do {
                    System.out.print("Would you like to continue? (y/n): ");
                    choice = in.nextLine().strip().toLowerCase();
                    if (!choice.equals("y") && !choice.equals("n")) {
                        System.out.println("Please enter y or n.");
                    }
                } while (!choice.equals("y") && !choice.equals("n"));

                switch (choice) {
                    case "y":
                        CharacterClass characterClass = selectCharacter();
                        if (characterClass == null) {
                            isRunning = false;
                            break;
                        }
                        Player newHero = new Player("Player", characterClass);
                        Enemy newBoss = nextBattle(engine, difficulty);
                        if (newBoss == null) {
                            isRunning = false;
                            break;
                        }
                        hero = newHero;
                        boss = newBoss;
                        break;
                    case "n":
                        System.out.println("Thank you for Playing!");
                        isRunning = false;
                        break;
                }
            }
    }
    public static CharacterClass selectCharacter() {
        System.out.println("====== SELECT CLASS ======");
        System.out.println("1. Mage     (100 HP, 12 Mana)");
        System.out.println("2. Wizard   (90 HP, 15 Mana)");
        System.out.println("3. Archmage (80 HP, 20 Mana)");
        System.out.print("Choose: ");
        String classChoice = in.nextLine().strip();
        switch (classChoice) {
            case "1": return CharacterClass.MAGE;
            case "3": return CharacterClass.ARCHMAGE;
            case "2": return CharacterClass.WIZARD;
            default: System.out.println("Invalid Input!");
            return null;
        }
    }

    private static Enemy selectBoss() {
        List<Enemy> bosses = new ArrayList<>();
        System.out.println("====== SELECT BOSS ======");
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty == Difficulty.ALL) {
                continue;
            }
            for (Enemy boss : difficulty.getBosses()) {
                bosses.add(boss);
                System.out.printf("%2d. %-22s (%s) HP: %d | Damage: %d%n",
                        bosses.size(), boss.getName(), difficulty,
                        boss.getHp(), boss.getDamage());
            }
        }
        while (true) {
            System.out.print("Choose a boss: ");
            try {
                int choice = Integer.parseInt(in.nextLine().strip());
                if (choice < 1 || choice > bosses.size()) {
                    System.out.println("Invalid boss selection!");
                    continue;
                }
                return bosses.get(choice - 1);
            } catch (NumberFormatException e) {
                System.out.println("Invalid boss selection!");
            }
        }
    }

    private static void startGame(BattleEngine engine,Player hero, Enemy boss, Deck masterDeck, Difficulty difficulty, Scanner in) {;
        System.out.printf("Class: %s | HP: %d | Mana: %d%n",
                hero.getClassName(), hero.getHp(), hero.getMana());
        if (!boss.isDead()) {
            masterDeck = new Deck(engine);
            engine.drawHand(masterDeck, 4);
            engine.executeTurn(hero, boss, masterDeck, in);
        }
    }
    public static Enemy nextBattle(BattleEngine engine, Difficulty difficulty) {
        List<Enemy> bosses = new ArrayList<>();
        difficulty = Difficulty.ALL;
        System.out.printf("===== CHOOSE NEXT OPPONENT ===== %n");
            for (int i = 0; i < difficulty.getBosses().size(); i++) {
                if (i <= 2) {
                    Enemy boss = engine.drawBoss(difficulty.getBosses());
                    bosses.add(boss);
                    System.out.printf("%2d. %-22s HP: %d | Damage: %d%n",
                            bosses.size(), boss.getName(),
                            boss.getHp(), boss.getDamage());
                }
        }
        System.out.print("Choose your next opponent('0' to end session): ");
        try {
            int choice = Integer.parseInt(in.nextLine().strip());
            if (choice == 0) {
                return null;
            }
            else if (choice < 1 || choice > bosses.size()) {
                System.out.println("Invalid boss.");
            }
            return bosses.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid boss selection!");
        }
        return null;
    }
}
