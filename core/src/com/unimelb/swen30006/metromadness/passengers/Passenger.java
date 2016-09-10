package com.unimelb.swen30006.metromadness.passengers;

import com.unimelb.swen30006.metromadness.stations.Station;

public class Passenger {

	private Station begining;
	private Station destination;
	private float travelTime;
	private boolean reachedDestination;
	
	public Passenger(Station start, Station end){
		this.begining = start;
		this.destination = end;
		this.reachedDestination = false;
		this.travelTime = 0;
	}

	public Station getDestination(){
		return this.destination;
	}


	
	public void update(float time){
		if(!this.reachedDestination){
			this.travelTime += time;
		}
	}

	
	
}
