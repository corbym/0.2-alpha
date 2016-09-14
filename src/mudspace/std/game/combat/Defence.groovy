package std.game.combat;

public interface Defence {

    public String getSkillUsed();

    public String getDefendAttackType();

    /**
     * returns a damage modifier if the player has a defence against this type
     * of attack. arm bracers for example would reduce the damage hit by any
     * kind of attack, but might not be so effective against magic
     * 
     * @param attack
     * @param attacker
     * @param defender
     * @return
     */
    public long defend(def attack, long damage, long attackToHit, long roll);
}
