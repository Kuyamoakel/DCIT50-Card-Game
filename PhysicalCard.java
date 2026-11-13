
public class PhysicalCard extends Card {
    private int damage;
    private int StaggerFiller;

    public PhysicalCard(String name, int manaCost, int damage, int StaggerFiller) {
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

        System.out.printf("%s Cast %s Dealt %d to %s",player.getName(), super.getName(), damage, boss.getName());
        player.spendMana(super.getManaCost());
        boss.takeDamage(damage);
        boss.addStaggerPTS(StaggerFiller); 
        if (boss.isDead()){
            System.out.println("\n=================================");
        }else {
            System.out.printf("\nSTAGGER METER STATUS: %d | %d%n", boss.getStaggerMeter(), boss.getMaxStagger());
            System.out.println("=================================");
        }

    }

    @Override
    public void printDetails() {
        System.out.print("------------ PHYSICAL CARD ------------");
        System.out.printf("%nCard: %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.printf("%nStagger Damage: %d", StaggerFiller);
        System.out.printf("%n----------------------------------------%n");
    }

}