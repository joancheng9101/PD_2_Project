package com.mygdx.game.Tools;

import com.badlogic.gdx.math.Vector2;

public class MathTool {
	public static float radiansTodegrees (float radians) {
		return (float) Math.toDegrees(radians);
	}
	
	public static float degreesToradians (float degrees) {
		return (float) Math.toRadians(degrees);
	}
}
