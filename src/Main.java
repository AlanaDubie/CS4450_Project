import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class Main {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private FPCameraController fp = new FPCameraController(0f, 0f, 0f);

    // Scene manager to handle rendering of objects in the scene
    private Scene scene = new Scene();

    private DisplayMode displayMode;

    public void start() {
        try {
            createWindow();
            initGL();

            fp.gameLoop(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glEnable(GL_DEPTH_TEST);

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

    public static void main(String[] args) {
        Main basic = new Main();
        basic.start();
    }
}
