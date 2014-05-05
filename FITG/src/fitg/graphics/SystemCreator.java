package fitg.graphics;

import fitg.Planet;
import fitg.StarSystem;
import fitg.graphics.framework.RenderViewport;
import java.util.List;

public class SystemCreator {
    
    private int StarRadius = 90;
    private float xPos;
    private float yPos;
    private String name;
    
    public SystemCreator(){}
    
    public void CreateSystem(StarSystem system, RenderViewport viewport){
        Star star = new Star();
        xPos = (system.GetX() * 4000);
        yPos = (system.GetY() * 3000);
        name = system.GetSystemName();
        Planet planet;
        int environCount;
        
        star.SetPosition(xPos, yPos);
        star.SetRadius(StarRadius);
        star.SetName(name);
        viewport.ItsRenderObjects.add(star);
        
        List planets = system.GetPlanets();
        for (int i = 0; i < planets.size(); i++){
            planet = system.GetPlanetByID(i);
            planet.BuildPlanet();
            
            if (i == 0){
                planet.SetPosition(xPos - 56, yPos - 60);
            }
            else if (i == 1){
                planet.SetPosition(xPos - 119, yPos - 123);
            }
            else {
                planet.SetPosition(xPos - 181, yPos - 185);
            }
            
            viewport.ItsRenderObjects.add(planet);
        }
    }
}
