package fitg;

/**
 *
 * @author Alex
 */
public class MilitaryForce extends Entity {
    private int environStrength;
    private int spaceStrength;
    private String type;
    private int navigation;
    private boolean mobile;

    public MilitaryForce(SIDE side)
    {
        super(EntityType.MILITARY, side);
    }
    
    public MilitaryForce(SIDE side, String type, int environStr, int spaceStr, boolean mobile)
    {
        super(EntityType.MILITARY, side);
        this.type = type;
        this.environStrength = environStr;
        this.spaceStrength = spaceStr;
        this.mobile = mobile;
    }
    
    public void SetEntityID(int id)
    {
        ID = id;
    }
    
    public String GetType(){
        return type;
    }
    
    public boolean GetMobile(){
        return mobile;
    }
    public int GetEnvironStrength()
    {
        return environStrength;
    }
    
    public int GetSpaceStrength()
    {
        return spaceStrength;
    }
}
