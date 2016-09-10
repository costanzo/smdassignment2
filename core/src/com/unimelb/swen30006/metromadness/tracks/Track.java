package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

public abstract class Track {
	static final float DRAW_RADIUS=10f;
	static final int LINE_WIDTH=6;
	Point2D.Float startPos;
	Point2D.Float endPos;
	Color trackColour;

	
	Track(Point2D.Float start, Point2D.Float end, Color trackCol){
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
	}
	
	public abstract void render(ShapeRenderer renderer);
	
	public abstract boolean canEnter(boolean forward);
	
	public abstract void enter(boolean forward);


	public abstract void leave(boolean forward);
}
