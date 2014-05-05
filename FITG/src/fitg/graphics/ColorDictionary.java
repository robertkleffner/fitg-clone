package fitg.graphics;

import java.awt.Color;

public class ColorDictionary {
    public static Color UnitCircleBorder = Color.green;
    public static Color ImperialUnitCircle = Color.blue;
    public static Color RebelUnitCircle = Color.red;
    public static Color UnitCircleFont = Color.lightGray.brighter();
    public static Color PDBBorder = Color.orange;
    public static Color PlanetFont = Color.lightGray;
    public static Color PlanetPatriotic = new Color(Color.blue.getRed(),
            Color.blue.getGreen(), Color.blue.getBlue(), 135);
    public static Color PlanetLoyal = new Color(Color.cyan.getRed(),
            Color.cyan.getGreen(), Color.cyan.getBlue(), 135);
    public static Color PlanetNeutral = new Color(Color.lightGray.getRed(),
            Color.lightGray.getGreen(), Color.lightGray.getBlue(), 135);
    public static Color PlanetDissent = new Color(Color.orange.getRed(),
            Color.orange.getGreen(), Color.orange.getBlue(), 135);
    public static Color PlanetUnrest = new Color(Color.red.getRed(),
            Color.red.getGreen(), Color.red.getBlue(), 135);
    
    public static Color EnvironUrban = new Color(255, 221, 0, 135);
    public static Color EnvironWild = new Color(0, 159, 1, 135);
    public static Color EnvironFire = new Color(255, 61, 0, 135);
    public static Color EnvironLiquid = new Color(0, 164, 255, 135);
    public static Color EnvironSubterranean = new Color(143, 87, 10, 135);
    public static Color EnvironAir = new Color(0, 185, 185, 135);
}
