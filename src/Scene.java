import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private Chunk chunk = new Chunk(0, 0, 0);
    public void render() 
    { 
        chunk.render(); 
    }
}