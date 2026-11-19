/***************************************************************
* file: Light.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 3
* date last modified: 11/18/2026
*
* purpose: This class is the light source for the world. It uses
* the "Bringing it to Code" setup from the Lighting slides: the
* position and color buffers are built in initLightArrays, the
* light is turned on in init, and apply puts the light back in its
* place each frame. The light stays at a fixed spot in the world,
* so the half of the terrain near it is brightly lit and the half
* far from it is dimly lit.
*
****************************************************************/
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Light {

    // where the light sits in the world: off the left edge of the terrain
    // and above it (the terrain spans x=0 to 58 and z=0 to -60)
    private static final float LIGHT_X = 0.0f;
    private static final float LIGHT_Y = 75.0f;
    private static final float LIGHT_Z = -30.0f;

    // how much ambient light the dim half of the world still gets
    private static final float DIM_LEVEL = 0.3f;

    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    private FloatBuffer dimLight;

    // method: Light
    // purpose: this constructor builds the position and color buffers
    public Light() {
        initLightArrays();
    }

    // method: initLightArrays
    // purpose: this method fills the buffers for the light's position, its
    // white (diffuse and specular) color, and its dim (ambient) color. The
    // last position value stays at 1.0f so OpenGL treats it as a position
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(LIGHT_X).put(LIGHT_Y).put(LIGHT_Z).put(1.0f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        dimLight = BufferUtils.createFloatBuffer(4);
        dimLight.put(DIM_LEVEL).put(DIM_LEVEL).put(DIM_LEVEL).put(1.0f).flip();
    }

    // method: init
    // purpose: this method sets the light's position and colors, then turns
    // on lighting and light 0. GL_COLOR_MATERIAL makes the lit surfaces use
    // the color array (white), so the textures keep their normal colors
    public void init() {
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light's position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, dimLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0
        glEnable(GL_COLOR_MATERIAL);
    }

    // method: apply
    // purpose: this method sends the light's position to OpenGL again. It is
    // called every frame right after the camera is applied, so the light stays
    // fixed in the world instead of moving with the camera
    public void apply() {
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
}