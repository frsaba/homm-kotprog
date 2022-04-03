package Players;

/**
 * Tudáspont tulajdonság osztály
 */
public class Skill {
    static final int MAX_SKILL = 10;

    private int skill;
    private final String displayName;

    public boolean increment(){
        if(skill < MAX_SKILL){
            skill++;
            return true;
        }
        return false;
    }

    
    public int getSkill(){
        return skill;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Skill(String displayName) {
        skill = 1;
        this.displayName = displayName;
    }
}
