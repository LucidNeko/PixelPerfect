package nz.lucidstudios.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import nz.lucidstudios.image.PixelImage;
import nz.lucidstudios.input.Keyboard;
import nz.lucidstudios.input.Mouse;

public class Main {
	
	public static void main(String[] args) {
		final JFrame frame = new JFrame("Pixel Perfect");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final PixelImage canvas = new PixelImage(1024, 576);
		
		frame.add(new JComponent() {
			{
				this.setFocusable(true);
				this.setPreferredSize(new Dimension(1024*2, 576*2));
				Mouse.register(this);
				Keyboard.register(this);
			}
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				synchronized(canvas) {
					g.drawImage(canvas.getScaledInstance(2).getImage(), 0, 0, null);
				}
			}
		});
		
		frame.pack();
		frame.setVisible(true);
		
		GameLoop loop = new GameLoop(60, 50) {
			
			World world = Level.load(2);

			@Override
			protected void tick(float delta) {
				for(Entity e : world.getEntities()) {
					e.update(delta);
				}
			}

			@Override
			protected void fixedTick(float delta) {
				world.generateCollisionMask();
				for(Entity e : world.getEntities()) {
					e.integrate(delta);
				}
			}

			@Override
			protected void render() {
				synchronized(canvas) {
					canvas.fill(0xFF87CEFA);
					
					for(Entity e : world.getEntities()) {
						e.render(canvas);
					}
					
					for(Entity e : world.getEntities()) {
						e.render(canvas);
					}
				}
				
				frame.repaint();
				
			}
			
		};
		loop.start();
	}

}
