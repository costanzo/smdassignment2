package com.unimelb.swen30006.metromadness.stations;
/*
Visibility has been modified
 */
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Station {
	
	private static final int PLATFORMS=2;
	
	private Point2D.Float position;
	private static final float RADIUS=6;
	private static final int NUM_CIRCLE_STATMENTS=100;
	private static final int MAX_LINES=3;
	private String name;
	private ArrayList<Line> lines;
	private ArrayList<Train> trains;
	private static final float DEPARTURE_TIME = 2;
	private PassengerRouter router;
	private boolean isActive;
	private float maxVolume;
	private ArrayList<Passenger> waiting;
	private PassengerGenerator g;

	public Station(float x, float y, PassengerRouter router, String name, boolean isActive, float maxPax){
		this.name = name;
		this.router = router;
		this.position = new Point2D.Float(x,y);
		this.lines = new ArrayList<Line>();
		this.trains = new ArrayList<Train>();
		this.isActive = isActive;
		this.maxVolume = maxPax;
		this.g = new PassengerGenerator(this, maxPax);
		this.waiting = new ArrayList<Passenger>();
	}

	public String getName(){
	    return this.name;
    }
	public Point2D.Float getPosition(){
		return this.position;
	}

	public void registerLine(Line l){
		this.lines.add(l);
	}
	
	public void render(ShapeRenderer renderer){
		float radius = RADIUS;
		for(int i=0; (i<this.lines.size() && i<MAX_LINES); i++){
			Line l = this.lines.get(i);
			renderer.setColor(l.getLineColour());
			renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);
			radius = radius - 1;
		}
		
		// Calculate the percentage
		float t = this.trains.size()/(float)PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		renderer.setColor(c);
		renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);		
	}

	public void enter(Train t, Line l, boolean forward) throws Exception {
		if(trains.size() >= PLATFORMS){
			throw new Exception();
		} else {
		    l.enter(this, forward);
			// Add the train
			this.trains.add(t);
			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while(pIter.hasNext()){
				Passenger p = pIter.next();
				try {
					t.embark(p);
					pIter.remove();
				} catch (Exception e){
					// Do nothing, already waiting
					break;
				}
			}

			//Do not add new passengers if there are too many already
			if (this.waiting.size() > maxVolume){
				return;
			}
			// Add the new passenger
			Passenger[] ps = this.g.generatePassengers();
			for(Passenger p: ps){
				try {
					t.embark(p);
				} catch(Exception e){
					this.waiting.add(p);
				}
			}
		}
	}
	
	
	public void depart(Train t, Line l, boolean forward) throws Exception {
		if(this.trains.contains(t)){
			this.trains.remove(t);
            l.depart(this, forward);
		} else {
			throw new Exception();
		}
	}
	
	public boolean canEnter(Line l) throws Exception {
		return trains.size() < PLATFORMS;
	}

	public boolean canHold(Train t){
		return true;
	}

	// Returns departure time in seconds
	public float getDepartureTime() {
		return DEPARTURE_TIME;
	}

	public boolean shouldLeave(Passenger p) {
		return this.router.shouldLeave(this, p);
	}

	@Override
	public String toString() {
		return "Station [position=" + position + ", name=" + name + ", trains=" + trains.size()
				+ ", router=" + router + "]";
	}

	public Passenger generatePassenger(Station s) {
		return new Passenger(this, s);
	}

	public Line getRandomLine(){
		Line l = lines.get((int)(Math.random()*(lines.size()-1)));
		return l;
	}

	public boolean endStation(Line l) throws Exception{
	    return l.endOfLine(this);
    }

    public boolean canDepart(Line l, boolean forward){
        return l.canDepart(this, forward);
    }

    public Station nextStation(Line l, boolean forward) throws Exception{
        return l.nextStation(this, forward);
    }
}
