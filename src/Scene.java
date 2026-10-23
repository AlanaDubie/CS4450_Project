import static org.lwjgl.opengl.GL11.*;

public class Scene {

    private Cube cube = new Cube();

    // Render all objects in the scene
    public void render() {

        // Place the cube five units in front of the camera
        glPushMatrix();
        glTranslatef(0.0f, 0.0f, -5.0f);

        cube.render();

        glPopMatrix();
    }
}
