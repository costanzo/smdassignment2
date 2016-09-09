package com.unimelb.swen30006.metromadness.tracks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

import java.awt.geom.Point2D;

/**
 * Created by Shuyi Sun on 9/9/2016.
 */
public class SingleTrack extends Track{

    public boolean occupied;

    public SingleTrack(Point2D.Float start, Point2D.Float end, Color trackCol){
        super(start, end, trackCol);
        this.occupied = false;
    }

    public boolean canEnter(boolean forward){
        return !this.occupied;
    }

    public void enter(Train t){
        this.occupied = true;
    }

    public void leave(Train t){
        this.occupied = false;
    }

    public void render(ShapeRenderer renderer){
        renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH);
    }
}
