package gg.codie.mineonline.gui.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisplayManager {

    public static int getWidth() {
        return WIDTH;
    }

    public static int scaledWidth(int width) {
        return width * (Display.getWidth() / width);
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int scaledHeight(int height) {
        return height * (Display.getHeight() / height);
    }

    private static final int WIDTH = 854;
    private static final int HEIGHT = 480;
    private static final int FPS = 120;

    public static Frame getFrame() {
        return frame;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    private static Frame frame = null;
    private static Canvas canvas = null;

    public static void init() {
        frame = new Frame("MineOnline");
        canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new Dimension(854, 480));
        frame.setBackground(Color.black);
        frame.pack();
        frame.setLocationRelativeTo(null);

        Image img = Toolkit.getDefaultToolkit().getImage(DisplayManager.class.getResource("/img/favicon.png"));
        frame.setIconImage(img);

//        frame.setSize(DisplayManager.getWidth() + frame.getInsets().left + frame.getInsets().right, DisplayManager.getHeight() + frame.getInsets().left + frame.getInsets().right);
//        frame.pack();
    }

    public static void createDisplay() {
        createDisplay(WIDTH, HEIGHT);
    }

    public static void createDisplay(int width, int height) {

        if(Display.isCreated()) {
            System.out.println("Display already active!");
            return;
        }

        //ContextAttribs attribs = new ContextAttribs(3,2).withProfileCompatibility(true);

        try {
            Display.setParent(canvas);
            Display.setDisplayMode(new DisplayMode(854, 480));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, height);

        frame.setVisible(true);

//        frame.setSize(DisplayManager.getWidth() + frame.getInsets().left + frame.getInsets().right, DisplayManager.getHeight() + frame.getInsets().left + frame.getInsets().right);
//        frame.pack();

    }

    public static void updateDisplay() {
        Display.sync(FPS);
        Display.update();

    }

    public static void closeDisplay() {
        Display.destroy();
        //frame.dispose();
        //frame.setVisible(false);
    }

    public static void fullscreen(boolean on) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();

//        try {
//            Display.setDisplayMode(Display.getDesktopDisplayMode());
//            Display.setFullscreen(on);
//
//
////            frame.setVisible(!on);
//        } catch (Exception ex) {
//            //return on;
//        }
//        return !on;
//        frame.setUndecorated(on);

        if(on) {
            dev.setFullScreenWindow(frame);
        } else {
            dev.setFullScreenWindow(null);
        }
    }

}
