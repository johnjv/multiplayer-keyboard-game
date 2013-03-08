import java.awt.Graphics;

import javax.swing.*;

public class Player{

	private String playerName;
	private int x, y;
	private int size;

	Player(String name, int y) {
		this.playerName = name;
		this.x = 50;
		this.y = y;
		this.size = 50;
	}

	public int getX() {
		return this.x;
	}

	public void move(int distance) {
		this.x += distance;
	}

	public void draw(Graphics g) {
		g.fillRect(this.x, this.y, size, size);
		g.drawString(this.playerName, this.x, this.y);
	}
}