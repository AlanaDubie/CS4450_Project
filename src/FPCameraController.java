import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;

import static org.lwjgl.opengl.GL11.*;

public class FPCameraController {

    private static final class Vector3f {
        private float x;
        private float y;
        private float z;

        private Vector3f(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // 3D vector to store the camera's position
    private Vector3f position = null;
    private Vector3f lPosition = null;

    // Rotation around the Y axis
    private float yaw = 0.0f;

    // Rotation around the X axis
    private float pitch = 0.0f;

    // Constructor
    public FPCameraController(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);

        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }

    // Increment the camera's yaw rotation
    public void yaw(float amount) {
        yaw += amount;
    }

    // Increment the camera's pitch rotation
    public void pitch(float amount) {
        pitch -= amount;
    }

    // Move forward relative to the current yaw
    public void walkForward(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));

        position.x -= xOffset;
        position.z += zOffset;
    }

    // Move backward relative to the current yaw
    public void walkBackwards(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));

        position.x += xOffset;
        position.z -= zOffset;
    }

    // Strafe left relative to the current yaw
    public void strafeLeft(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));

        position.x -= xOffset;
        position.z += zOffset;
    }

    // Strafe right relative to the current yaw
    public void strafeRight(float distance) {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));

        position.x -= xOffset;
        position.z += zOffset;
    }

    // Move the camera up
    public void moveUp(float distance) {
        position.y -= distance;
    }

    // Move the camera down
    public void moveDown(float distance) {
        position.y += distance;
    }

    // Rotate and translate the view to look through the camera
    public void lookThrough() {
        // Rotate pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);

        // Rotate yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);

        // Translate to the camera position
        glTranslatef(position.x, position.y, position.z);
    }

    // Main camera loop
    public void gameLoop(Scene scene) {

        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; // Length of a frame
        float lastTime = 0.0f; // Time of the last frame
        long time = 0;

        float mouseSensitivity = 0.09f;
        float movementSpeed = 0.35f;

        // Hide and capture the mouse
        Mouse.setGrabbed(true);

        // Loop until the window closes or Escape is pressed
        while (!Display.isCloseRequested()
                && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {

            time = Sys.getTime();
            lastTime = time;

            // Control camera yaw using horizontal mouse movement
            this.yaw(dx * mouseSensitivity);

            // Control camera pitch using vertical mouse movement
            this.pitch(dy * mouseSensitivity);

            // Read mouse movement since the previous read
            dx = Mouse.getDX();
            dy = Mouse.getDY();

            // Move forward
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                this.walkForward(movementSpeed);
            }

            // Move backward
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                this.walkBackwards(movementSpeed);
            }

            // Strafe left
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                this.strafeLeft(movementSpeed);
            }

            // Strafe right
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                this.strafeRight(movementSpeed);
            }

            // Move up
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                this.moveUp(movementSpeed);
            }

            // Move down (the professor's slide uses E)
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                this.moveDown(movementSpeed);
            }

            // Reset the model-view matrix
            glLoadIdentity();

            // Apply the camera before drawing
            this.lookThrough();

            // Clear the color and depth buffers
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Draw the scene here with objects
            scene.render();

            // Display the rendered frame
            Display.update();
            Display.sync(60);
        }

        Mouse.setGrabbed(false);
        Display.destroy();
    }  
}
