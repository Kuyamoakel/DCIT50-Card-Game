
public class PhysicalCard extends Card {
    private int damage;
    private int StaggerFiller;

    public PhysicalCard(String name, int manaCost, int damage, int StaggerFill) {
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

        player.spendMana(super.getManaCost());
        boss.takeDamage(damage);
        boss.addStaggerPTS(this.StaggerFiller);
        System.out.println("Player Cast " + super.getName() + " Dealt "  + damage + " to " + boss.getName());
        System.out.println("STAGGER METER STATUS: " + boss.getStaggerMeter() + " | " + boss.getMaxStagger());



    }

    @Override
    public void printDetails() {
        System.out.println("------------ PHYSICAL CARD ------------");
        System.out.printf("%nCard: %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.println("StaggerDamge: " + this.StaggerFiller);
        System.out.println("----------------------------------------");
    }

}
