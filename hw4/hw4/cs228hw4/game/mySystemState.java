package cs228hw4.game;

import edu.iastate.cs228.game.Agent;
import edu.iastate.cs228.game.SystemState;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author SimeonSteward
 * A system state that contains a seed system, and an action to change it
 */
public class mySystemState implements SystemState {
    private SystemState originalState;
    private Agent.AgentAction action;
    private Color myColor;

    /**
     * Public constructor for this systemState
     * @param originalState the seed state to base this off of
     * @param action the action to modify this state
     * @param myColor the color of the agent
     */
    public mySystemState(SystemState originalState, Agent.AgentAction action, Color myColor) {
        this.originalState = originalState;
        this.action = action;
        this.myColor = myColor;
    }

    @Override
    public String getName() {
        return originalState.getName();
    }

    @Override
    public Color getOwner() {
        if(action instanceof Agent.Capture) return myColor;
        else  return originalState.getOwner();
    }

    @Override
    public int getGeneratorCount() {
        return originalState.getGeneratorCount()+1;
    }

    @Override
    public int getMaximumGeneratorCount() {
        return originalState.getMaximumGeneratorCount();
    }

    @Override
    public int getEnergyStored() {
        if(action instanceof Agent.Refuel) return 0;
        return originalState.getEnergyStored();
    }

    @Override
    public int getCostToCapture() {
        if(action instanceof Agent.Fortify){
            return originalState.getCostToCapture()+((Agent.Fortify) action).getEnergySpent()/2;
        }
        return originalState.getCostToCapture();
    }

    @Override
    public SystemState[] getNeighbors() {
        return originalState.getNeighbors();
    }

    @Override
    public int[] getTravelCost() {
        return originalState.getTravelCost();
    }

    @Override
    public Color[] getAgentsPresent() {
        if(action instanceof Agent.Move){
            ArrayList<Color> colors = new ArrayList<>(Arrays.asList(originalState.getAgentsPresent()));
            if(((Agent.Move) action).getDestination().equals(getName())){
                colors.add(myColor);
            }else{
                colors.remove(myColor);
            }
            return (Color[]) colors.toArray();
        }
        return originalState.getAgentsPresent();
    }
}
