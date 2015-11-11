package org.thenesis.planetino2.backend.awt;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.thenesis.planetino2.graphics.Graphics;
import org.thenesis.planetino2.graphics.Screen;
import org.thenesis.planetino2.input.InputManager;

public class AWTScreen implements Screen, KeyListener, MouseListener, MouseMotionListener {

	JPanel panel;
	private JFrame frame;

	private int screenWidth = 1024;
	private int screenHeight = 640;
	protected BufferedImage screenImage;
	private Graphics screenGraphics;

	private InputManager inputManager;

	public AWTScreen(InputManager inputManager, int screenWidth, int screenHeight) {

		this.inputManager = inputManager;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		screenImage = new BufferedImage(screenWidth, screenWidth, BufferedImage.TYPE_INT_ARGB_PRE);
		screenGraphics = new AWTGraphics(screenImage);
		
		final Dimension dimension = new Dimension(screenWidth, screenHeight);
		panel = new JPanel() {

			public Dimension getMinimumSize() {
				return dimension;
			}

			public Dimension getPreferredSize() {
				return dimension;
			}

			public void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g); 
				g.drawImage(screenImage, 0, 0, null);
			}
		};
		panel.setFocusable(true);
		panel.addKeyListener(this);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	public void show() {
		frame = new JFrame();
		//frame.addWindowListener(this);
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center the frame (has to be called after pack)
		frame.setVisible(true);
		panel.requestFocusInWindow();
	}

	public Graphics getGraphics() {
		return screenGraphics;
	}

	public void update() {
		panel.repaint();
	}

	public int getWidth() {
		return screenWidth;
	}

	public int getHeight() {
		return screenHeight;
	}

	public void restoreScreen() {
		if (frame != null) {
			frame.dispose();
		}
	}

	/* Event listener interfaces */

	public void keyPressed(KeyEvent e) {
		//System.out.println("[DEBUG] AWTBackend.keyPressed(): key code: " + e.getKeyCode() + " char: "+ e.getKeyChar());
		inputManager.keyPressed(e.getKeyCode());
		//listener.keyPressed(e.getKeyCode(), e.getKeyChar(), e.getModifiers());
	}

	public void keyReleased(KeyEvent e) {
		//System.out.println("[DEBUG] AWTBackend.keyReleased(): key code: " + e.getKeyCode() + " char: " + e.getKeyChar());
		//listener.keyReleased(e.getKeyCode(), e.getKeyChar(), e.getModifiers());
		inputManager.keyReleased(e.getKeyCode());
	}

	// Not used
	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println("[DEBUG] AWTBackend.mousePressed()");
		//listener.mousePressed(e.getX(), e.getY(), e.getModifiers());
		inputManager.pointerPressed(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println("[DEBUG] AWTBackend.mouseReleased()");
		//listener.mouseReleased(e.getX(), e.getY(), e.getModifiers());
		inputManager.pointerReleased();
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("[DEBUG] AWTBackend.mouseMoved()");
		//listener.mouseMoved(e.getX(), e.getY(), e.getModifiers());
		inputManager.pointerDragged(e.getX(), e.getY());
	}

	//	public void windowClosing(WindowEvent e) {
	//		listener.windowClosed();
	//	}

}
