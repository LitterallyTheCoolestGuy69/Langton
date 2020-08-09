import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
public class Ant extends JPanel implements MouseListener, ActionListener{
	
	private int[][] grid = new int[3][3];
	private boolean[][] touched = new boolean[3][3];
	private int stepCount = 0;
	private int stepAmt = 2;
	private boolean[] leftTurns = new boolean[] {true, false};
	private Color[] colors = new Color[] {Color.white, Color.red, Color.green, Color.cyan, Color.yellow, Color.blue, Color.MAGENTA, Color.pink, Color.orange, Color.black, new Color(100, 200, 50), new Color(50, 255, 128), new Color(255, 128, 75)};
	private boolean running = false;
	private boolean paused = false;
	private boolean changing = true;
	private boolean picking = false;
	private int antRow = 2, antCol = 1;
	private Timer timer;
	private int stepSpeed = 512;
	private direction dir = direction.up;
	public Ant() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				grid[i][j] = 0;
			}
		}
	}
	 
	public enum direction{
		left, right, up, down;
	}
	
	public void leftTurn() {
		if(dir == direction.up)
			dir = direction.left;
		else if (dir == direction.left)
			dir = direction.down;
		else if (dir == direction.down)
			dir = direction.right;
		else
			dir = direction.up;
	}
	
	public void rightTurn() {
		if(dir == direction.up)
			dir = direction.right;
		else if (dir == direction.right)
			dir = direction.down;
		else if (dir == direction.down)
			dir = direction.left;
		else
			dir = direction.up;
	}
	
	public void increaseSize() {
		int size = grid.length;
		int[][] newGrid = new int[size * 3][size * 3];
		boolean[][] newTouched = new boolean[size * 3][size * 3];
		for(int i = size; i < size * 2; i++) {
			for(int j = size; j < size * 2; j++) {
				newGrid[i][j] = grid[i-size][j-size];
				newTouched[i][j] = touched[i-size][j-size];
			}
		}
		
		
		grid = newGrid;
		touched = newTouched;
		antRow += size;
		antCol += size;
	}
	public void progressAnt() {
		stepCount += 1;
		if(dir == direction.up) {
			System.out.println("here");
			try {
				if(leftTurns[grid[antRow - 1][antCol]]) {
					leftTurn();
					antRow = antRow - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antRow = antRow - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
			catch(Exception e) {
				increaseSize();
				if(leftTurns[grid[antRow - 1][antCol]]) {
					leftTurn();
					antRow = antRow - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antRow = antRow - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
		}
		else if(dir == direction.left) {
			try {
				if(leftTurns[grid[antRow][antCol - 1]]) {
					leftTurn();
					antCol = antCol - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antCol = antCol - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
			catch(Exception e) {
				increaseSize();
				if(leftTurns[grid[antRow][antCol - 1]]) {
					leftTurn();
					antCol = antCol - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antCol = antCol - 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
		}
		else if(dir == direction.down) {
			try {
				if(leftTurns[grid[antRow + 1][antCol]]) {
					leftTurn();
					antRow = antRow + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antRow = antRow + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
			catch(Exception e) {
				increaseSize();
				if(leftTurns[grid[antRow + 1][antCol]]) {
					leftTurn();
					antRow = antRow + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antRow = antRow + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
		}
		else if(dir == direction.right) {
			try {
				if(leftTurns[grid[antRow][antCol + 1]]) {
					leftTurn();
					antCol = antCol + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antCol = antCol + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
			catch(Exception e) {
				increaseSize();
				if(leftTurns[grid[antRow][antCol + 1]]) {
					leftTurn();
					antCol = antCol + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
				else {
					rightTurn();
					antCol = antCol + 1;
					grid[antRow][antCol] = (grid[antRow][antCol] + 1) % stepAmt;
				}
			}
		}
		touched[antRow][antCol] = true;
		update();
	}
	
	public void paint(Graphics g) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				if(touched[j][i])
					g.setColor(colors[grid[j][i]]);
				else
					g.setColor(new Color(215,215,215));
				g.fillRect((500/grid.length) * i, (500/grid.length) * j, (500/grid.length), (500/grid.length));
			}
		}
		g.setColor(Color.black);
		g.setFont(new Font("Times Roman", 0, 24));
		g.drawString("" + stepCount, 600, 475);
		
		if(changing || picking) {
			g.setColor(new Color(250,250,250));
			g.fillRect(10, 510, 100, 50);
			g.setColor(Color.black);
			g.drawRect(10, 510, 100, 50);
			g.drawString("s t a r t", 23, 543);
			
			

			g.setColor(new Color(250,250,250));
			g.fillRect(200, 510, 100, 50);
			g.setColor(Color.black);
			g.drawRect(200, 510, 100, 50);
			g.drawString(" a d d", 213, 543);
			
			if(stepAmt == 18) {
				g.setColor(Color.red);
				g.drawLine(201, 511, 299, 559);
				g.drawLine(201, 559, 299, 511);
			}
			
			g.setColor(new Color(250,250,250));
			g.fillRect(310, 510, 100, 50);
			g.setColor(Color.black);
			g.drawRect(310, 510, 100, 50);
			g.drawString("remove", 321, 543);
			
			if(stepAmt == 2) {
				g.setColor(Color.red);
				g.drawLine(311, 511, 409, 559);
				g.drawLine(311, 559, 409, 511);
			}
		}
		if(picking) {
			g.setColor(new Color(245, 245, 245));
			g.fillRect(175, 515, 150, 40);
			g.setColor(Color.black);
			g.drawRect(175, 515, 150, 40);
			

			g.setFont(new Font("Times Roman", 0, 20));
			g.setColor(new Color(250, 250, 250));
			g.fillRect(180, 520, 60, 30);
			g.setColor(Color.black);
			g.drawRect(180, 520, 60, 30);
			g.drawString("left", 193, 542);
			
			g.setColor(new Color(250, 250, 250));
			g.fillRect(260, 520, 60, 30);
			g.setColor(Color.black);
			g.drawRect(260, 520, 60, 30);
			g.drawString("right", 270, 542);
		}
		
		
		for(int i = 0; i < stepAmt && i < 9; i++) {
			g.setColor(colors[i]);
			g.fillRect(510, 5 + (55*i), 50, 50);
			g.setColor(Color.black);
			g.drawRect(510, 5 + (55*i), 50, 50);
			
			g.setColor(Color.black);
			g.drawLine(520, 30 + (55*i), 550, 30 + (55*i));
			if(leftTurns[i]) {
				g.drawLine(520, 30 + (55*i), 525, 25 + (55*i));
				g.drawLine(520, 30 + (55*i), 525, 35 + (55*i));
			}
			else {
				g.drawLine(550, 30 + (55*i), 545, 25 + (55*i));
				g.drawLine(550, 30 + (55*i), 545, 35 + (55*i));
			}
		}
		for(int i = 0; i + 9 < stepAmt; i++) {
			g.setColor(colors[i + 9]);
			g.fillRect(610, 5 + (55*i), 50, 50);
			g.setColor(Color.black);
			g.drawRect(610, 5 + (55*i), 50, 50);
			
			g.setColor(Color.white);
			if(i != 0)
				g.setColor(Color.black);
			g.drawLine(620, 30 + (55*i), 650, 30 + (55*i));
			if(leftTurns[i + 9]) {
				g.drawLine(620, 30 + (55*i), 625, 25 + (55*i));
				g.drawLine(620, 30 + (55*i), 625, 35 + (55*i));
			}
			else {
				g.drawLine(650, 30 + (55*i), 645, 25 + (55*i));
				g.drawLine(650, 30 + (55*i), 645, 35 + (55*i));
			}
		}
		
		if(running) {
			g.setColor(new Color(250, 250, 250));
			g.fillRect(70, 510, 50, 50);
			g.setColor(Color.black);
			g.drawRect(70, 510, 50, 50);
			g.setFont(new Font("Times Roman", 1, 40));
			g.drawString("ll", 85, 547);
			
			g.setColor(new Color(250, 250, 250));
			g.fillRect(10, 510, 50, 50);
			g.setColor(Color.black);
			g.drawRect(10, 510, 50, 50);
			g.drawString(">", 25, 550);
			
			g.setColor(new Color(250, 250, 250));
			g.fillRect(130, 510, 50, 50);
			g.setColor(Color.black);
			g.drawRect(130, 510, 50, 50);
			g.setFont(new Font("Times Roman", 1, 30));
			g.drawString(">>", 140, 547);
		}
		
		if(paused) {
			g.setColor(new Color(250, 250, 250));
			g.fillRect(70, 510, 50, 50);
			g.setColor(Color.black);
			g.drawRect(70, 510, 50, 50);
			g.fillPolygon(new int[] {80, 80, 110}, new int[] {520, 550, 535}, 3);
			
			g.setColor(new Color(250,250,250));
			g.fillRect(200, 510, 100, 50);
			g.setColor(Color.black);
			g.drawRect(200, 510, 100, 50);
			g.drawString("change", 213, 543);
			
		}
	}
	
	public void update() {
		this.setSize(700,601);
		this.setSize(700,600);
	}
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + "  " + e.getY());
		if(picking) {
			if(e.getX() > 260 && e.getX() < 320 && e.getY() > 550 && e.getY() < 580) {
				picking = false;
				stepAmt++;
				boolean[] newTurns = new boolean[leftTurns.length + 1];
				for(int i = 0; i < leftTurns.length; i++) {
					newTurns[i] = leftTurns[i];
				}
				newTurns[leftTurns.length] = false;
				leftTurns = newTurns;
			}
			else if(e.getX() > 180 && e.getX() < 240 && e.getY() > 550 && e.getY() < 580) {
				picking = false;
				stepAmt++;
				boolean[] newTurns = new boolean[leftTurns.length + 1];
				for(int i = 0; i < leftTurns.length; i++) {
					newTurns[i] = leftTurns[i];
				}
				newTurns[leftTurns.length] = true;
				leftTurns = newTurns;
			}
			update();
		}
		else if(changing) {
			if(e.getX() > 200 && e.getX() < 300 && e.getY() > 540 && e.getY() < 590 && stepAmt != 18) {
				picking = true;
				update();
			}
			else if(e.getX() > 310 && e.getX() < 410 && e.getY() > 540 && e.getY() < 590 && stepAmt != 2) {
				stepAmt--;
				boolean[] newTurns = new boolean[leftTurns.length - 1];
				for(int i = 0; i < leftTurns.length - 1; i++) {
					newTurns[i] = leftTurns[i];
				}
				leftTurns = newTurns;
				update();
			}
			else if(e.getX() > 10 && e.getX() < 110 && e.getY() > 540 && e.getY() < 590) {
				changing = false;
				running = true;
				stepSpeed = 500;
				timer = new Timer(stepSpeed, this);
				timer.start();
				update();
			}
			else if(e.getX() > 510 && e.getX() < 560 && e.getY() > 35 && e.getY() < 85) {
				leftTurns[0] = !leftTurns[0];
				update();
			}
			else if(e.getX() > 510 && e.getX() < 560 && e.getY() > 95 && e.getY() < 145) {
				leftTurns[1] = !leftTurns[1];
				update();
			}
		}
		else if (running) {
			if(e.getX() > 10 && e.getX() < 60 && e.getY() > 540 && e.getY() < 590) {
				if(stepSpeed == 0) {
					stepSpeed = 1;
				}
				if(stepSpeed != 4096)
					stepSpeed *= 2;
				timer.stop();
				timer = new Timer(stepSpeed, this);
				timer.start();
				update();
			}
			else if(e.getX() > 70 && e.getX() < 120 && e.getY() > 540 && e.getY() < 590) {
				timer.stop();
				running = false;
				paused = true;
				update();
			}
			else if(e.getX() > 130 && e.getX() < 180 && e.getY() > 540 && e.getY() < 590) {
				if(stepSpeed == 1) {
					stepSpeed = 0;
				}
				else if(stepSpeed != 0) {
					stepSpeed /= 2;
				}
				timer.stop();
				timer = new Timer(stepSpeed, this);
				timer.start();
			}
		}
		else if(paused) {
			if(e.getX() > 70 && e.getX() < 120 && e.getY() > 540 && e.getY() < 590) {
				timer.start();
				paused = false;
				running = true;
				update();
			}
			else if(e.getX() > 200 && e.getX() < 300 && e.getY() > 540 && e.getY() < 590) {
				paused = false;
				changing = true;
				grid = new int[3][3];
				touched = new boolean[3][3];
				stepCount = 0;
				antRow = 2;
				antCol = 1;
				stepSpeed = 512;
				dir = direction.up;
				update();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			timer.restart();
			progressAnt();
		}
	}
	

}
