package com.unimelb.swen30006.metromadness.stations;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * Created by Sean on 9/9/2016.
 */
public class ShortPlatformStation extends Station{

    public ShortPlatformStation(float x, float y, PassengerRouter router, String name, boolean isActive, float maxPax) {
        super(x, y, router, name, isActive, maxPax);
    }

    public boolean canHold(Train t){
        return t.isSmallTrain();
    }
}
