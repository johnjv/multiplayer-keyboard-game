import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player{

	private String playerName;
	private String[] playerIcons = {"p1.png", "p2.png", "p3.png", "p4.png", "p5.png", "p6.png"};
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
		int count = 1;
		final Image image = new ImageIcon(playerIcons[count]).getImage();
		g.drawImage(image, this.x, this.y, size, size, null);
		g.drawString(this.playerName, this.x, this.y);
		if(count == 6){
			count = 1;
		}else{
			count++;
		}
	
	}
}