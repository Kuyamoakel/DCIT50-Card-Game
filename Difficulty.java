import java.util.List;
import java.util.ArrayList;
public enum Difficulty {
    EASY,
    MEDIUM,
    HARD,
    ALL;
    public List<Enemy> getBosses() {

        // this keyword is pertaining to the current object that calls it.
        // 3 objects in this class EASY, MEDIUM, HARD.
        switch (this) {
            case EASY:
                return List.of(
                        new Enemy("Goblin King", 70, 3),
                        new Enemy("Cave Spider", 75, 3),
                        new Enemy("Skeleton Warrior", 80, 4),
                        new Enemy("Swamp Troll", 85, 4),
                        new Enemy("Forest Guardian", 90, 4)
                );
            case MEDIUM:
                return List.of(
                        new Enemy("Shadow Knight", 100, 5),
                        new Enemy("Frost Guardian", 105, 5),
                        new Enemy("Flame Warden", 110, 6),
                        new Enemy("Iron Golem", 115, 6),
                        new Enemy("Dark Sorcerer", 120, 7)
                );
            case HARD:
                return List.of(
                        new Enemy("Abyss Lord", 135, 7),
                        new Enemy("Ancient Dragon", 150, 8),
                        new Enemy("Void Emperor", 145, 9),
                        new Enemy("Death Bringer", 155, 8),
                        new Enemy("Badong the Great", 169, 10)
                );
            case ALL:
                List<Enemy> Bosses = new ArrayList<>();
                Bosses.addAll(EASY.getBosses());
                Bosses.addAll(MEDIUM.getBosses());
                Bosses.addAll(HARD.getBosses());
                return Bosses;
            default:
                throw new IllegalStateException();
        }
    }
}