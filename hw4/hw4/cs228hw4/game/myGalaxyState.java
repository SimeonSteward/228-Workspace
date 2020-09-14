package cs228hw4.game;

import edu.iastate.cs228.game.GalaxyState;
import edu.iastate.cs228.game.SystemState;
import javafx.util.Pair;
import java.awt.*;

/**
 * @author SimeonSteward
 * A modifiable galaxy state used to compute actions 2 and 3 after previous actions have changed the galaxy state
 */
public class myGalaxyState implements GalaxyState {
    private SystemState[] systemStates;
    private Pair<Color, SystemState>[] systemColorPairs;

    /**
     * A public constructor for this galaxy state
     * @param systemStates the systemStates in the new galaxy state
     * @param systemColorPairs where the agents are at
     */
    public myGalaxyState(SystemState[] systemStates, Pair<Color, SystemState>[] systemColorPairs) {
        this.systemStates = systemStates;
        this.systemColorPairs = systemColorPairs;
    }

    /**
     * Changes the system for the given color
     * @param color the agent's color to be changed
     * @param stateName the destination for this agent
     */
    public void updateColor(Color color, String stateName){
        for (int i = 0; i < systemColorPairs.length; i++) {
            if (systemColorPairs[i].getKey().equals(color)){
                systemColorPairs[i]= new Pair<>(color,getSystem(stateName));
            }
        }
    }

    @Override
    public SystemState[] getSystems() {
        return systemStates;
    }

    @Override
    public SystemState getCurrentSystemFor(Color color) {
        for (Pair<Color, SystemState> systemColorPair : systemColorPairs) {
            if (systemColorPair.getKey() == color) return systemColorPair.getValue();
        }
        return null;
    }

    /**
     * A private helper method to find the system for a given name
     * @param systemName the name that is being searched for
     * @return the system or null if it cannot be found
     */
    private SystemState getSystem(String systemName){
        for (SystemState system : systemStates) {
            if (system.getName().equals(systemName)) return system;
        }
        return null;
    }
}
