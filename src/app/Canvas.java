package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import model.Axis;
import model.AxisPyramids;
import model.Cube;
import model.WireCube;
import model.Pyramid;
import model.WirePyramid;
import model.WireTriangle;
import rasterdata.DepthBuffer;
import rasterdata.ImageBuffer;
import rasterdata.ZBufferTest;
import render.Renderer;
import model.Triangle;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotXYZ;
import transforms.Mat4Scale;
import transforms.Mat4ViewRH;
import transforms.Vec3D;

/**
 * Tøída reprezentující plátno
 * 
 * @author pechami2
 * 
 */

public class Canvas {

	private JFrame frame;
	private JPanel panel;
	private BufferedImage img;
	private ImageBuffer image;
	private ZBufferTest zBuffer;
	private DepthBuffer depthBuffer;
	private Axis axis = new Axis();
	private AxisPyramids axisPyramids = new AxisPyramids();
	private Cube cube = new Cube();
	private WireCube wireCube = new WireCube();
	private Pyramid pyramid = new Pyramid();
	private Triangle triangle = new Triangle();
	private WirePyramid wirePyramid = new WirePyramid();
	private WireTriangle wireTriangle = new WireTriangle();
	private Renderer render;
	private Mat4 matrixProj, matrixView, model;
	private Camera camera;
	private boolean projection;
	private double i = 0;

	private double w = 0;
	private double s = 3.2;
	private double v = 0;
	private double firstX, firstY, secX, secY;
	private final double shift = 0.6;
	private static final int COLOR = 0xcccccc;
	private double sc = 1;
	private boolean isCube = false;
	private boolean isTriangle = false;
	private boolean isWireTriangle = false;
	private boolean isPyramid = false;
	private boolean isPyramidWire = false;
	private boolean isCubeWire = false;
	private boolean isAxis = false;
	private boolean isAxisPyramids = false;

	private JRadioButton menuButtonCube;
	private JRadioButton menuButtonAxis;
	private JRadioButton menuButtonPyramid;
	private JRadioButton menuButtonWirePyramid;
	private JRadioButton menuButtonWireCube;
	private JRadioButton menuButtonTriangle;
	private JRadioButton menuButtonWireTriangle;

	public Canvas(int width, int height) {
		frame = new JFrame() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
			}
		};

		frame.setTitle("KGR2 úloha 1");
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present();
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		JToolBar tb = new JToolBar();
		tb.setFocusable(false);
		frame.add(tb, BorderLayout.NORTH);
		setToolbar(tb);

		image = new ImageBuffer(img);
		depthBuffer = new DepthBuffer(img.getWidth(), img.getHeight());
		zBuffer = new ZBufferTest(depthBuffer, image);

		camera = new Camera(new Vec3D(12, 0, 0), 0.0f, 0.0f, 20, false);
		matrixProj = new Mat4PerspRH(Math.PI / 6, (float) this.image.getHeight() / (float) this.image.getWidth(), 0.01,
				100.0);
		model = new Mat4Identity();
		matrixView = new Mat4ViewRH(new Vec3D(w, s, -0.99999), new Vec3D(v, i, -1), new Vec3D(-1, 0, 0));

		render = new Renderer(image, matrixProj, matrixView, model, zBuffer, camera);
		paint();

		MouseInputAdapter ip = new MouseInputAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				firstX = e.getX();
				firstY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				clear(COLOR);
				secX = firstX;
				secY = firstY;

				firstX = e.getX();
				firstY = e.getY();

				int posunoutX = (int) (firstX - secX);
				int posunoutY = (int) (firstY - secY);
				switch (e.getModifiers()) {
				case MouseEvent.BUTTON1_MASK:
					camera = camera.addAzimuth((double) -posunoutX * Math.PI / 720);
					camera = camera.addZenith((double) -posunoutY * Math.PI / 720);
					break;
				case MouseEvent.BUTTON3_MASK:
					Mat4 rot = new Mat4RotXYZ(0, -posunoutY * Math.PI / 180, posunoutX * Math.PI / 180);
					model = model.mul(rot);
					break;
				}
				paint();
			}

		};

		panel.addMouseListener(ip);
		panel.addMouseMotionListener(ip);
		panel.requestFocus();
		panel.requestFocusInWindow();
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					camera = camera.forward(shift);
					break;
				case KeyEvent.VK_S:
					camera = camera.backward(shift);
					break;
				case KeyEvent.VK_A:
					camera = camera.left(shift);
					break;
				case KeyEvent.VK_D:
					camera = camera.right(shift);
					break;
				case KeyEvent.VK_E:
					camera = camera.down(shift);
					break;
				case KeyEvent.VK_Q:
					camera = camera.up(shift);
					break;
				case KeyEvent.VK_I:
					sc = sc * shift;
					Mat4 sc1 = new Mat4Scale(sc, sc, sc);
					model = model.mul(sc1);
					break;
				case KeyEvent.VK_O:
					sc = sc * (1 + shift);
					Mat4 sc2 = new Mat4Scale(sc, sc, sc);
					model = model.mul(sc2);
				case 32:
					projection = !projection;
					break;

				}

				paint();
			}
		});
		paint();

	}

	private void setToolbar(JToolBar toolBar) {
		ButtonGroup groupWireOrNot = new ButtonGroup();

		menuButtonCube = new JRadioButton("Cube");
		menuButtonAxis = new JRadioButton("Axis");
		menuButtonPyramid = new JRadioButton("Pyramid");
		menuButtonWirePyramid = new JRadioButton("Wire Pyramid");
		menuButtonTriangle = new JRadioButton("Triangle");
		menuButtonWireTriangle = new JRadioButton("Wire Triangle");
		menuButtonWireCube = new JRadioButton("Wire Cube");

		menuButtonCube.setFocusable(false);
		menuButtonAxis.setFocusable(false);
		menuButtonPyramid.setFocusable(false);
		menuButtonWirePyramid.setFocusable(false);
		menuButtonTriangle.setFocusable(false);
		menuButtonWireTriangle.setFocusable(false);
		menuButtonWireCube.setFocusable(false);

		groupWireOrNot.add(menuButtonCube);
		groupWireOrNot.add(menuButtonWireCube);
		groupWireOrNot.add(menuButtonTriangle);
		groupWireOrNot.add(menuButtonWireTriangle);
		groupWireOrNot.add(menuButtonPyramid);
		groupWireOrNot.add(menuButtonWirePyramid);

		toolBar.add(menuButtonCube);
		toolBar.add(menuButtonWireCube);
		toolBar.add(menuButtonPyramid);
		toolBar.add(menuButtonWirePyramid);
		toolBar.add(menuButtonTriangle);
		toolBar.add(menuButtonWireTriangle);
		toolBar.add(menuButtonAxis);

		menuButtonCube.addActionListener(e -> setCube());
		menuButtonAxis.addActionListener(e -> setAxis());
		menuButtonPyramid.addActionListener(e -> setPyramid());
		menuButtonWirePyramid.addActionListener(e -> setPyramidWire());
		menuButtonTriangle.addActionListener(e -> setTriangle());
		menuButtonWireTriangle.addActionListener(e -> setWireTriangle());
		menuButtonWireCube.addActionListener(e -> setCubeWire());

	}

	private void setCube() {
		if (menuButtonCube.isSelected()) {
			isCube = true;
			isCubeWire = false;
			draw();

		} else {
			isCube = false;

			draw();
		}

	}

	private void setAxis() {
		if (menuButtonAxis.isSelected()) {
			isAxis = true;
			isAxisPyramids = true;
			draw();

		} else {
			isAxis = false;
			isAxisPyramids = false;
			draw();
		}

	}

	private void setPyramid() {
		if (menuButtonPyramid.isSelected()) {
			isPyramid = true;
			isPyramidWire = false;
			draw();

		} else {
			isPyramid = false;
			draw();
		}

	}

	private void setPyramidWire() {
		if (menuButtonWirePyramid.isSelected()) {
			isPyramidWire = true;
			isPyramid = false;
			draw();

		} else {
			isPyramid = false;
			draw();
		}

	}

	private void setCubeWire() {
		if (menuButtonWireCube.isSelected()) {
			isCubeWire = true;
			isCube = false;
			draw();

		} else {
			isCubeWire = false;

			draw();
		}

	}

	private void setTriangle() {
		if (menuButtonTriangle.isSelected()) {
			isTriangle = true;
			isWireTriangle = false;
			draw();

		} else {
			isTriangle = false;
			draw();
		}
	}

	private void setWireTriangle() {
		if (menuButtonWireTriangle.isSelected()) {
			isWireTriangle = true;
			isTriangle = false;
			draw();

		} else {
			isTriangle = false;
			draw();
		}
	}

	public void info() {
		Graphics info = panel.getGraphics();
		info.setColor(Color.BLACK);
		info.drawString("INFORMACE (KPGR2 úloha 1 Milan Pechánek)", 10, 15);
		info.drawString("Pohyb: WASD po ose XY ", 10, 30);
		info.drawString("Otáèení kamerou pomocí levého tlaèítka myši", 10, 45);
		info.drawString("Pohyb kamerou nahoru/dolù: Q,E ", 10, 60);
		info.drawString("Rotace objektù pomocí právého tlaèítka myši: Y,X,C,V", 10, 75);
		info.drawString("Zmìna mìøítka: I,O", 10, 90);
		info.drawString("Ortogonální/perspektivní zobrazení: mezerník", 10, 105);
	}

	protected void paint() {
		depthBuffer.clean();
		image.clean();
		clear(COLOR);
		if (!projection) {
			matrixProj = new Mat4PerspRH(Math.PI / 6, (float) this.image.getHeight() / (float) this.image.getWidth(),
					0.01, 100.0);
		}
		if (projection) {
			matrixProj = new Mat4OrthoRH(img.getWidth() / 75, img.getHeight() / 75, -10.0, 100.0);
		}
		render.setProjection(matrixProj);
		render.setModel(model);
		render.setCamera(camera);

		if (isCube)
			render.draw(cube);
		if (isCubeWire)
			render.draw(wireCube);
		if (isTriangle)
			render.draw(triangle);
		if (isWireTriangle)
			render.draw(wireTriangle);
		if (isPyramid)
			render.draw(pyramid);
		if (isPyramidWire)
			render.draw(wirePyramid);
		if (isAxis && isAxisPyramids) {
			render.draw(axis);
			render.draw(axisPyramids);
		} else {
			isAxis = false;
			isAxisPyramids = false;
		}

		present();

	}

	public void clear(int color) {
		Graphics graphics = img.getGraphics();
		graphics.setColor(new Color(color));
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());

	}

	public void present() {
		if (panel.getGraphics() != null)
			panel.getGraphics().drawImage(img, 0, 0, null);
		info();
	}

	public void draw() {
		clear(COLOR);
		paint();
	}

	public void start() {

		draw();
		present();
	}

	public static void main(String[] args) {
		Canvas canvas = new Canvas(800, 600);
		SwingUtilities.invokeLater(() -> {
			SwingUtilities.invokeLater(() -> {
				SwingUtilities.invokeLater(() -> {
					SwingUtilities.invokeLater(() -> {
						canvas.start();
					});
				});
			});
		});
	}

}