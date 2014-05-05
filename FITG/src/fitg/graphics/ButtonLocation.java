package fitg.graphics;

public enum ButtonLocation {

    PLANET(70f),
    CENTER(50f),
    ORBIT(-10f),
    ENV_ONE(110f),
    ENV_TWO(190f),
    ENV_THREE(-90f);
    private final float degree;

    private ButtonLocation(float degree)
    {
        this.degree = degree;
    }

    public float getStartDegree()
    {
        return degree;
    }
}