
public class DemiCard extends Card {
    private double damage;
    private int StaggerFiller;

    public DemiCard(String name, int manaCost, double damage, int StaggerFiller) {
        super(name, manaCost);
        this.damage = damage;
        this.StaggerFiller = StaggerFiller;
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
        boss.addStaggerPTS(this.StaggerFiller);

        System.out.printf("%s Cast %s Dealt %d to %s",player.getName(), super.getName(), finalDamage, boss.getName());
        System.out.printf("%nSTAGGER METER STATUS: %d | %d%n",boss.getStaggerMeter(),boss.getMaxStagger());

    }

    @Override
    public void printDetails() {
        System.out.print("------------ DEMI CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %.0f", damage);
        System.out.printf("%nStagger Damage: %d%n", this.StaggerFiller);
        System.out.print("-----------------------------------");
    }

}