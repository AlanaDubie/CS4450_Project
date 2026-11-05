/***************************************************************
* file: Scene.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 2
* date last modified: 11/5/2026
*
* purpose: This class holds everything that is drawn in the world.
* Right now that is a single chunk of blocks.
*
****************************************************************/
import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private Chunk chunk = new Chunk(0, 0, 0);

    // method: render
    // purpose: this method draws the chunk
    public void render() 
    { 
        chunk.render(); 
    }
}