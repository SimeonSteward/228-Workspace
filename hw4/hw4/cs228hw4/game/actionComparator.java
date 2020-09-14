package cs228hw4.game;

import edu.iastate.cs228.game.Agent;
import edu.iastate.cs228.game.GalaxyState;
import edu.iastate.cs228.game.SystemState;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Simeon Steward
 * A comparator class that compares actions using their strategic priority
 */
public class actionComparator implements Comparator<Agent.AgentAction> {

    private int energy;
    private GalaxyState galaxyState;
    private Color myColor;
    private Color opponentColor;
    private int scan;

    /**
     * Public constructor for the action comparator class
     * @param energy the Amount of energy that the agent has
     * @param galaxyState the current galaxy state
     * @param myColor the color of my agent
     * @param opponentColor the color of the opponent's agent
     * @param scan the level that set scan is set too.
     */
    public actionComparator(int energy, GalaxyState galaxyState, Color myColor, Color opponentColor,int scan) {
        this.energy = energy;
        this.galaxyState = galaxyState;
        this.myColor = myColor;
        this.opponentColor = opponentColor;
        this.scan = scan;
    }

    @Override
    public int compare(Agent.AgentAction first, Agent.AgentAction second) {
        double firstPriority = getPriority(first);
        double secondPriority = getPriority(second);

        if (firstPriority-secondPriority>0) return -1;
        else if(firstPriority-secondPriority==0) return 0;
        return 1;

    }

    /**
     * Determines the priority an action should have
     * @param action the action that will be compared
     * @return the priority in the form of a double(not scaled, completely arbitrary based on "What works")
     */
    private double getPriority(Agent.AgentAction action) {
        double priority = 1;
        if(action instanceof Agent.Capture){
            //If I already own, I do not want to capture, or not enough energy, don't do this action
            if(getMySystem().getOwner().equals(myColor) ||
            energy<getMySystem().getCostToCapture()){
                return -1000;
            }
            //If It is my opponents system, double priority
            if(getMySystem().getOwner().equals(opponentColor)){
                priority = 2;
            }
            priority = priority * 19+energy;
        }else if(action instanceof Agent.Refuel){
            if(getMySystem().getOwner().equals(opponentColor)){
                return -1000;
            }

            priority = getMySystem().getEnergyStored();
        }else if(action instanceof Agent.ContinueCapture){
            //low priority because bot captures everything at once.
            return 0;
        }else if(action instanceof Agent.Fortify){
            if(getMySystem().getCostToCapture()>25)
                return -1;
            return 1.5/((double)getMySystem().getCostToCapture());
            //Not exactly sure what this priority is or if I need some logic

        }else if(action instanceof Agent.Move){
            final Agent.Move moveAction = (Agent.Move) action;
            final SystemState destination = getSystem(moveAction.getDestination());
            final int cost = costToMove(destination);

            //If there isn't enough energy, won't try to go to destination
            if(cost>energy) return -1000;
            //if(energy>50)
            //    return 10*Math.random();
            if(destination.getOwner().equals(myColor)){
                if(energy>cost){
                   ArrayList<SystemState> neighbors = new ArrayList<>(Arrays.asList(destination.getNeighbors()));
                    for (SystemState neighbor : neighbors) {
                        if (neighbor!=null&&!neighbor.getOwner().equals(myColor)) {
                            return 3;
                        }
                    }
                    if(energy>50) return Math.random()*5;
                }
                return destination.getEnergyStored()-cost;
            }
            if(destination.getOwner().equals(opponentColor)){
                priority = 1.2;
            }
            if(energy>cost+destination.getCostToCapture()&&getMySystem().getOwner().equals(myColor))priority = priority*5 -cost-destination.getCostToCapture()+energy*.3;

            //The priority for a move action is the priority of the destination - the cost to get there
            //Agent.AgentAction destinationActivity = myAgent.getBestActivity(galaxyState,energy,myColor,opponentColor,true);
            //priority = getPriority(destinationActivity)-cost;

            priority *= Math.random()*((double) destination.getMaximumGeneratorCount()/4+3)-(double)cost/energy;
        }else if(action instanceof Agent.NoAction){
            return 0;

        }else if(action instanceof Agent.SetScan){
            if(scan == 0){
                return 1;
            }
            //May Implement later if needed
            return -1;

        }else if(action instanceof Agent.Shoot){
            Agent.Shoot shooter = (Agent.Shoot) action;

//            if (shooter.getPath().length>0
//
//                    &&(shooter.getPath()[0].equals(galaxyState.getCurrentSystemFor(opponentColor).toString())))
//                return Math.random()*4;
            //May Implement later if needed
            return -1;
        }

        return priority;
    }

    /**
     * @return the system that my agent is already at
     */
    private SystemState getMySystem(){
        //Wrong because the 3 turns in the future rule
        return galaxyState.getCurrentSystemFor(myColor);
    }

    /**
     * Finds the cost to move to an adjacent system
     * @param system the destination
     * @return the cost to move to the destination
     */
    private int costToMove(SystemState system){
        ArrayList<SystemState> systems = new ArrayList<>(Arrays.asList(getMySystem().getNeighbors()));
        int[] travelCosts = getMySystem().getTravelCost();
        int index = systems.indexOf(system);
        if(index == -1) return Integer.MAX_VALUE;
        return travelCosts[index];
    }

    /**
     * Returns the system object corresponding to the systemName
     * @param systemName the name of the system that is being searched for
     * @return the system or null if it cannot be found
     */
    private SystemState getSystem(String systemName){
       SystemState[] systems = galaxyState.getSystems();
       for (SystemState system : systems) {
           if (system.getName().equals(systemName)) return system;
       }
       return null;
    }
}
