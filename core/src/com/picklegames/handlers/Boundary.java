package com.picklegames.handlers;

public class Boundary {
	private int minx;
	private int miny;
	private int maxx;
	private int maxy;
	
	public Boundary(int minx, int miny, int maxx, int maxy){
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
	}
	
	public boolean isInBoundary(int x, int y){
		boolean inX = x > this.minx && x < this.maxx;
		boolean inY = y > this.miny && y < this.maxy;
		return inX && inY;
	}
	
	public String toString(){
		return "x: (" + minx + ", " + maxx + "), " + "y: (" + miny + ", " + maxy + ")";
	}
	
}
