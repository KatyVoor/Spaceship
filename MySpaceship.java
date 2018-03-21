package student;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import models.Edge;
import models.Node;
import models.NodeStatus;
import models.RescueStage;
import models.ReturnStage;
import models.Spaceship;


/** An instance implements the methods needed to complete the mission */
public class MySpaceship extends Spaceship {

	/**
	 * Explore the galaxy, trying to find the missing spaceship that has crashed
	 * on Planet X in as little time as possible. Once you find the missing
	 * spaceship, you must return from the function in order to symbolize that
	 * you've rescued it. If you continue to move after finding the spaceship
	 * rather than returning, it will not count. If you return from this
	 * function while not on Planet X, it will count as a failure.
	 * 
	 * At every step, you only know your current planet's ID and the ID of all
	 * neighboring planets, as well as the ping from the missing spaceship.
	 * 
	 * In order to get information about the current state, use functions
	 * currentLocation(), neighbors(), and getPing() in RescueStage. You know
	 * you are standing on Planet X when foundSpaceship() is true.
	 * 
	 * Use function moveTo(long id) in RescueStage to move to a neighboring
	 * planet by its ID. Doing this will change state to reflect your new
	 * position.
	 * @param Id 
	 */
	 @Override public void rescue(RescueStage state) {
		// TODO : Find the missing spaceship\

		if (state.getPing() == 1){return;}
		HashMap<NodeStatus,NodeStatus> map = new HashMap<NodeStatus,NodeStatus>();
		
		while (!state.foundSpaceship()){
			Collection<NodeStatus> neighbors = state.neighbors();
			Heap<NodeStatus> space = new Heap<NodeStatus>();
			
			for (NodeStatus s : neighbors){
				if (!map.containsKey(s) && !space.valPos.containsKey(s) ){
					space.add(s, -1*(s.getPingToTarget()));	
				}
			}
			if (space.size == 0){
				NodeStatus k=randgenerator(neighbors);
				map.put(k, k);
				state.moveTo(k.getId());	
			}else{
				NodeStatus next = space.poll();
				map.put(next, next);
				state.moveTo(next.getId());	
			}
		}
	}
	/** Generates random number to go to if it gets stuck*/
	 public NodeStatus randgenerator(Collection<NodeStatus> neighbors){
		 	int item = new Random().nextInt(neighbors.size());
			Object[] neigh= neighbors.toArray();
			return (NodeStatus)neigh[item];
	 } 
/**
	 * Get back to Earth, avoiding hostile troops and searching for speed
	 * upgrades on the way. Traveling through 3 or more planets that are hostile
	 * will prevent you from ever returning to Earth.
	 *
	 * You now have access to the entire underlying graph, which can be accessed
	 * through ScramState. currentNode() and getEarth() will return Node objects
	 * of interest, and getNodes() will return a collection of all nodes in the
	 * graph.
	 *
	 * You may use state.grabSpeedUpgrade() to get a speed upgrade if there is
	 * one, and can check whether a planet is hostile using the isHostile
	 * function in the Node class.
	 *
	 * You must return from this function while on Earth. Returning from the
	 * wrong location will be considered a failed run.
	 *
	 * You will always be able to return to Earth without passing through three
	 * hostile planets. However, returning to Earth faster will result in a
	 * better score, so you should look for ways to optimize your return.
	 */
	@Override
	public void returnToEarth(ReturnStage state) {
		// TODO: Return to Earth
		if (state.currentNode() == state.getEarth()){	
			return;}

		List<Node> shortpath = Paths.shortestPath(state.currentNode(), state.getEarth());
	
		for ( Node s : shortpath){
			if (s!=state.currentNode()){		
				state.moveTo(s);			
				}
			if( s.hasSpeedUpgrade()){ state.grabSpeedUpgrade();}
	
		}
	
    }}

		
		


