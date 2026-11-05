/***************************************************************
* file: Main.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 2
* date last modified: 11/5/2026
*
* purpose: This class creates the 640x480 window, sets up OpenGL
* (depth test, textures, perspective), and starts the camera loop.
*
****************************************************************/
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class Main {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    // start centered in front of the chunk, looking straight at it
    private FPCameraController fp = new FPCameraController(-29f, -53f, -98f);
    // Scene manager to handle rendering of objects in the scene
    private Scene scene; 

    private DisplayMode displayMode;

    // method: start
    // purpose: this method sets up the window and OpenGL, creates the
    // scene, and runs the camera loop
    public void start() {
        try {
            createWindow();
            initGL();
            scene = new Scene();

            fp.gameLoop(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method: createWindow
    // purpose: this method finds a 640x480 32-bit display mode and
    // creates the window
    private void createWindow() throws Exception {
        Display.setFullscreen(false);

        DisplayMode[] d = Display.getAvailableDisplayModes();

        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == WIDTH
                    && d[i].getHeight() == HEIGHT
                    && d[i].getBitsPerPixel() == 32) {

                displayMode = d[i];
                break;
            }
        }

        if (displayMode == null) {
            throw new Exception("640x480, 32-bit display mode not found");
        }

        Display.setDisplayMode(displayMode);
        Display.setTitle("Semester Project (3D Rendering)");
        Display.create();
    }

    // method: initGL
    // purpose: this method turns on depth testing, textures, and the
    // vertex/color/texture arrays, then sets up the perspective
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        GLU.gluPerspective(
                100.0f,
                (float) displayMode.getWidth()
                        / (float) displayMode.getHeight(),
                0.1f,
                300.0f);

        glMatrixMode(GL_MODELVIEW);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    // method: main
    // purpose: this method is the program entry point
    public static void main(String[] args) {
        Main basic = new Main();
        basic.start();
    }
}