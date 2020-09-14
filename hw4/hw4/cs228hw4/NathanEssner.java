package cs228hw4;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import edu.iastate.cs228.game.Agent;
import edu.iastate.cs228.game.GalaxyState;
import edu.iastate.cs228.game.SystemState;

/**
 * Simple implementation of the Agent interface. Will not be entered into tournament.
 * 
 * @author Nathan Essner
 *
 */
public class NathanEssner implements Agent {
	
	/**
	 * Color assigned to this Agent, 
	 */
	private Color thisColor = null;
	
	@Override
	public String getFirstName() {
		return "Nathan";
	}

	@Override
	public String getLastName() {
		return "Essner";
	}

	@Override
	public String getStuID() {
		return "738252540";
	}

	@Override
	public String getUsername() {
		return "njessner";
	}

	@Override
	public String getAgentName() {
		return "Bap";
	}

	@Override
	public File getAgentImage() {
		return null;
	}

	@Override
	public boolean inTournament() {
		return false;
	}

	@Override
	public AgentAction[] getCommandTurnTournament(GalaxyState g, int energyLevel) {
		return null;
	}

	@Override
	public AgentAction[] getCommandTurnGrading(GalaxyState g, int energyLevel) {
		
		int modifiedEnergyLevel = energyLevel;
		
		SystemState[] neighbors = g.getSystems();
		
		AgentAction[] retActions = new AgentAction[3];
		
		SystemState currentSystem = g.getCurrentSystemFor(thisColor);
		if (!thisColor.equals(currentSystem.getOwner())) {
			
			//Actions to take when the current system the Agent is in is not owned by them.
			int costToCapture = currentSystem.getCostToCapture();
			retActions[0] = new Capture(costToCapture-1);
			modifiedEnergyLevel -= costToCapture-1;
			
			
			modifiedEnergyLevel += currentSystem.getEnergyStored();
			retActions[1] = new Refuel();
			
			SystemState nextSystem = findEfficientSystem(neighbors, modifiedEnergyLevel-1, currentSystem);
			
			if (nextSystem == null) {
				
				Random rand = new Random();
				retActions[2] = new Move(neighbors[rand.nextInt(neighbors.length)].toString());
			} else {
				
				retActions[2] = new Move(nextSystem.toString());
			}
			
			
		} else {
			
			//Actions to do when the current system the Agent is in is owned by them.
			retActions[0] = new Refuel();
			
			SystemState nextSystem = findEfficientSystem(neighbors, modifiedEnergyLevel-2, currentSystem);
			
			if (nextSystem == null) {
				
				Random rand = new Random();
				retActions[1] = new Refuel();
				retActions[2] = new Move(neighbors[rand.nextInt(neighbors.length)].toString());
			} else {
				
				retActions[1] = new Move(nextSystem.toString());
				retActions[2] = new Capture(nextSystem.getCostToCapture()+1);
			}
			
		}
		
		return retActions;
	}

	@Override
	public void setColor(Color c) {
		thisColor = c;
	}

	@Override
	public void setOpponentColor(Color c) {
		return;
	}
	
	/**
	 * Helper function to find the next system to move to based on the cost of taking the system and the cost to move there.
	 * 
	 * @param neighbors of the system the Agent is in.
	 * @param currentEnergyLevel approximate energy level of the Agent at this point.
	 * @param currentSystem current system the Agent is in.
	 * @return next system to move to.
	 */
	private SystemState findEfficientSystem(SystemState[] neighbors, int currentEnergyLevel, SystemState currentSystem) {
		
		//CS228Graph<SystemState> diGraph = new CS228Graph<>();
		//diGraph.addVertex(currentSystem);
		
		SystemState retSystem = null;
		for (SystemState neighbor : neighbors) {
			
			if (!currentSystem.equals(neighbor) && !neighbor.getOwner().equals(thisColor)) {
				
				int costToCapture = neighbor.getTravelCost()[0] + neighbor.getCostToCapture();
			
				//I would use Dijkstra's here, but this is just so much simpler.
				if (retSystem == null) retSystem = neighbor;
				if (costToCapture < retSystem.getTravelCost()[0] + retSystem.getCostToCapture()) retSystem = neighbor;
				
			}
		}
		
		return retSystem;
	}

}
