package com.unimelb.swen30006.metromadness.stations;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

import java.util.Iterator;

/**
 * Created by Sean on 9/9/2016. and the visibility has been modified
 */
public class ShortPlatformStation extends Station{

    public ShortPlatformStation(float x, float y, PassengerRouter router, String name, boolean isActive, float maxPax) {
        super(x, y, router, name, isActive, maxPax);
    }

    public void enter(Train t, Line l, boolean forward) throws Exception {
        if(trains.size() >= PLATFORMS){
            throw new Exception();
        } else {
            l.enter(this, forward);
            // Add the train
            this.trains.add(t);

            //this train can add passengers if it is a small train
            if(t.isSmallTrain()) {
                // Add the waiting passengers
                Iterator<Passenger> pIter = this.waiting.iterator();
                while (pIter.hasNext()) {
                    Passenger p = pIter.next();
                    try {
                        t.embark(p);
                        pIter.remove();
                    } catch (Exception e) {
                        // Do nothing, already waiting
                        break;
                    }
                }

                //Do not add new passengers if there are too many already
                if (this.waiting.size() > maxVolume) {
                    return;
                }
                // Add the new passenger
                Passenger[] ps = this.g.generatePassengers();
                for (Passenger p : ps) {
                    try {
                        t.embark(p);
                    } catch (Exception e) {
                        this.waiting.add(p);
                    }
                }
            }
        }
    }

    public boolean canHold(Train t){
        return t.isSmallTrain();
    }
}
