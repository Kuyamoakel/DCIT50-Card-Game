
public class DemiCard extends Card {
    private double damage;
    private int StaggerFiller;

    public DemiCard(String name, int manaCost, double damage, int StaggerFill) {
        super(name, manaCost);
        this.damage = damage;
        this.StaggerFiller = StaggerFill;
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

        System.out.println("Player Cast " + super.getName() + " Dealt "  + (finalDamage + boss.getBonusdamage() ) + " to " + boss.getName());
        System.out.println("STAGGER METER STATUS: " + boss.getStaggerMeter() + " | " + boss.getMaxStagger());
    }

    @Override
    public void printDetails() {
        System.out.println("------------ DEMI CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %.0f%%", damage);
        System.out.println("StaggerDamge: " + this.StaggerFiller);
        System.out.println("----------------------------------------");
    }

}
