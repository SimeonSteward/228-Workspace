package cs228hw4.game;

import edu.iastate.cs228.game.Agent;
import edu.iastate.cs228.game.GalaxyState;
import edu.iastate.cs228.game.SystemState;
import javafx.util.Pair;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author SimeonSteward
 * myAgent implements Agent, uses a priority system to find which 3 next actions it will use
 */
public class myAgent implements Agent {
    private Color myColor;
    private Color opponentColor;
    private int scan;


    @Override
    public String getFirstName() {
        return "Simeon";
    }

    @Override
    public String getLastName() {
        return "Steward";
    }

    @Override
    public String getStuID() {
        return "576639100";
    }

    @Override
    public String getUsername() {
        return "SStew";
    }

    @Override
    public String getAgentName() {
        return "Walk Light is on ";
    }

    @Override
    public File getAgentImage() {
        return null;
    }

    @Override
    public boolean inTournament() {
        return true;
    }

    @Override
    public AgentAction[] getCommandTurnTournament(GalaxyState galaxyState, int i) {
        return getCommandTurnGrading(galaxyState, i);

    }

    @Override
    public AgentAction[] getCommandTurnGrading(GalaxyState galaxyState, int energyLevel) {
        SystemState[] states = galaxyState.getSystems();
        AgentAction[] retVal = new AgentAction[3];

        Pair<Color, SystemState>[] systemColorPairs = new Pair[2];
        systemColorPairs[0] = new Pair<Color, SystemState>(myColor,galaxyState.getCurrentSystemFor(myColor));
        systemColorPairs[1] = new Pair<Color, SystemState>(opponentColor,galaxyState.getCurrentSystemFor(opponentColor));

        myGalaxyState updatingGalaxyState = new myGalaxyState(states,systemColorPairs);
        for (int i = 0; i < retVal.length; i++) {
            AgentAction toAdd = getBestActivity(updatingGalaxyState,energyLevel,myColor,opponentColor, scan);
            //Update where things are at
            ArrayList<String> systemsToUpdate = new ArrayList<>();
            systemsToUpdate.add(updatingGalaxyState.getCurrentSystemFor(myColor).getName());
            if(toAdd instanceof Agent.Move){
                String destination = ((Move) toAdd).getDestination();
                systemsToUpdate.add(destination);
                updatingGalaxyState.updateColor(myColor,destination);
            }
            if(toAdd instanceof Agent.SetScan){
                scan =  ((SetScan) toAdd).getEnergyToSpend();
            }
            if(toAdd instanceof Agent.Refuel){
                energyLevel += updatingGalaxyState.getCurrentSystemFor(myColor).getEnergyStored();
            }
            //Either 1 or 2 times depending upon weather it is a move action
            for (String s : systemsToUpdate) {
                for (int k = 0; k < states.length; k++) {
                    if (states[k].getName().equals(s)) states[k] = new mySystemState(states[k], toAdd, myColor);
                }
            }
            retVal[i] = toAdd;

        }


        //Minimize movement
        //Collect energy
        //Only capture if agent has enough energy to capture
        //Shoot enemy systems when the enemy is near
        //Make priority que of possible actions
        //Modify priority based off of what works
        //Polls And adds to agent action array

        return retVal;
    }


    @Override
    public void setColor(Color color) {
        myColor=color;
    }

    Color getMyColor() {
        return myColor;
    }

    @Override
    public void setOpponentColor(Color color) {
        opponentColor=color;

    }

    /**
     * Private static helper method which takes into account many aspects of the game state to develop a strategy
     * @param galaxyState The current State of the galaxy
     * @param energyLevel The energy level of the agent
     * @param myColor This agent's color
     * @param opponentColor The opposing agent's color
     * @param scan The set scan of this object
     * @return
     */
    private static AgentAction getBestActivity(GalaxyState galaxyState, int energyLevel, Color myColor, Color opponentColor, int scan) {
        PriorityQueue<AgentAction> ActionPriorities = new PriorityQueue<>(new actionComparator(energyLevel,galaxyState,myColor,opponentColor,scan));

        ActionPriorities.add(new Agent.Capture(galaxyState.getCurrentSystemFor(myColor).getCostToCapture()));

        ActionPriorities.add(new Agent.Fortify(energyLevel-energyLevel%2-10));

        ActionPriorities.add(new Agent.NoAction());

        ActionPriorities.add(new Agent.Refuel());

        ActionPriorities.add(new Agent.SetScan(1));
        //ActionPriorities.add(new Agent.SetScan(2));

        SystemState[] neighbors = galaxyState.getCurrentSystemFor(myColor).getNeighbors();
         for (SystemState neighbor : neighbors) {
             if(neighbor!=null) {
                 ActionPriorities.add(new Move(neighbor.getName()));
                 String[] add = new String[1];
                 add[0] = neighbor.getName();
                 ActionPriorities.add(new Shoot(add,energyLevel/2));
             }

         }
        return ActionPriorities.poll();
    }
}
