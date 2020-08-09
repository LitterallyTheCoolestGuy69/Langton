import javax.swing.JFrame;

public class Runner {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600);
		frame.setVisible(true);
		Ant ant = new Ant();
		frame.addMouseListener(ant);
		frame.add(ant);
	}
}
