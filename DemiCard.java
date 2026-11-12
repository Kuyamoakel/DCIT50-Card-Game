
public class DemiCard extends Card {
    private double damage;

    public DemiCard(String name, int manaCost, double damage) {
        super(name, manaCost);
        this.damage = damage;
    }

    @Override 
    public void play(Player player, Enemy boss) {
        
        if (player.getMana() < getManaCost()) {
            System.out.println("Not Enough Mana!");
            return;
        }

        double damageCal = boss.getHp() * (damage / 100);
        int finalDamage = (int) Math.ceil(damageCal); // always round up siya
        player.spendMana(super.getManaCost());
        boss.takeDamage(finalDamage);

        System.out.printf("%s Cast %s Dealt %d to %s", player.getName(), super.getName(), finalDamage, boss.getName());

    }

    @Override
    public void printDetails() {
        System.out.println("------------ DEMI CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %.0f%%", damage);
        System.out.println("----------------------------------------");
    }

}