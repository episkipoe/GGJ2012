package com.episkipoe.ggj.main;

import com.google.gwt.user.client.Random;

public class Color {
	public String name;
	public String value;
	public Color(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static Color getRandomColor() {
		int numColors=2+Main.level;
		if(numColors>6) numColors=6;
		switch(Random.nextInt(numColors)) {
			default: //Fallthrough
			case 0: return new Color("Red", "rgba(255,0,0,1)");
			case 1: return new Color("Green", "rgba(0,255,0,1)");
			case 2: return new Color("Blue", "rgba(0,0,255,1)");
			case 3: return new Color("Cyan", "rgba(255,255,0,1)");
			case 4: return new Color("Magenta", "rgba(255,0,255,1)");
			case 5: return new Color("Yellow", "rgba(255,255,0,1)");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
