
public class PhysicalCard extends Card {
    private int damage;

    public PhysicalCard(String name, int manaCost, int damage) {
        super(name, manaCost);
        this.damage = damage;
    }

    @Override 
    public void play(Player player, Enemy boss) {

        
        if (player.getMana() < getManaCost()) {
            System.out.println("Not Enough Mana!");
            return;
        }

        System.out.printf("%s Cast %s Dealt %d to %s", player.getName(), super.getName(), damage, boss.getName());
        player.spendMana(super.getManaCost());
        boss.takeDamage(damage);


    }

    @Override
    public void printDetails() {
        System.out.println("------------ PHYSICAL CARD ------------");
        System.out.printf("%nCard: %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.println("----------------------------------------");
    }

}