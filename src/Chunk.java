/***************************************************************
* file: Chunk.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 2
* date last modified: 11/5/2026
*
* purpose: This class holds a 30x30x30 chunk of blocks and draws
* it with three vertex buffer objects (positions, colors, and
* texture coordinates). Textures come from the terrain.png atlas,
* which is a 16x16 grid of tiles.
*
****************************************************************/
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunk {

    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;

    // The atlas (terrain.png) is 16x16 tiles, so one tile is 1/16 of the image
    private static final float TILE = 1f / 16f;

    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int VBOTextureHandle;
    private Texture texture;
    private int StartX, StartY, StartZ;
    private Random r;

    // method: render
    // purpose: this method binds the buffers and texture, then draws
    // the whole chunk with one draw call
    public void render() {
        glPushMatrix();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        glDrawArrays(GL_QUADS, 0,
                CHUNK_SIZE * CHUNK_SIZE
                * CHUNK_SIZE * 24);
        glPopMatrix();
    }

    // method: rebuildMesh
    // purpose: this method fills the position, color, and texture
    // buffers for every block and uploads them to the graphics card
    public void rebuildMesh(
            float startX, float startY, float startZ) {
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();

        FloatBuffer VertexPositionData
                = BufferUtils.createFloatBuffer(
                        (CHUNK_SIZE * CHUNK_SIZE
                        * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData
                = BufferUtils.createFloatBuffer(
                        (CHUNK_SIZE * CHUNK_SIZE
                        * CHUNK_SIZE) * 6 * 12);
        // 6 faces * 4 vertices * 2 floats (u, v) per cube
        FloatBuffer VertexTextureData
                = BufferUtils.createFloatBuffer(
                        (CHUNK_SIZE * CHUNK_SIZE
                        * CHUNK_SIZE) * 6 * 8);

        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                for (float y = 0; y < CHUNK_SIZE; y++) {
                    VertexPositionData.put(
                            createCube((float) (startX + x
                                    * CUBE_LENGTH),
                                    (float) (y * CUBE_LENGTH
                                    + (int) (CHUNK_SIZE * .8)),
                                    (float) (startZ + z
                                    * CUBE_LENGTH)));
                    VertexColorData.put(
                            createCubeVertexCol(
                                    getCubeColor(
                                            Blocks[(int) x]
                                            [(int) y]
                                            [(int) z])));
                    VertexTextureData.put(
                            createTexCube(
                                    Blocks[(int) x]
                                    [(int) y]
                                    [(int) z]));
                }
            }
        }
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();

        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    // method: createCubeVertexCol
    // purpose: this method repeats one color for all 24 vertices of a cube
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i
                    % CubeColorArray.length];
        }
        return cubeColors;
    }

    // method: createCube
    // purpose: this method returns the 24 vertex positions of a cube in
    // this order: top, bottom, front, back, left, right
    public static float[] createCube(float x, float y,
            float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[]{
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z};
    }

    // method: getCubeColor
    // purpose: this method returns white so the color array does not
    // tint the texture
    private float[] getCubeColor(Block block) {
        return new float[]{1, 1, 1};
    }

    // method: quadUV
    // purpose: this method returns the u, v coordinates for one face of
    // the atlas tile at (col, row); "start" picks which corner the first
    // vertex uses
    private static float[] quadUV(int col, int row, int start) {
        float u0 = col * TILE;
        float v0 = row * TILE;
        float u1 = u0 + TILE;
        float v1 = v0 + TILE;
        float[] cornerU = {u0, u1, u1, u0};
        float[] cornerV = {v0, v0, v1, v1};

        float[] uv = new float[8];
        for (int i = 0; i < 4; i++) {
            int c = (start + i) % 4;
            uv[i * 2] = cornerU[c];
            uv[i * 2 + 1] = cornerV[c];
        }
        return uv;
    }

    // method: createTexCube
    // purpose: this method returns the 48 texture coordinates for a cube
    // in the same face order as createCube, using the tiles for the
    // block's type
    public static float[] createTexCube(Block block) {
        int[] top, side, bottom; // each is {column, row} in the atlas

        switch (block.GetID()) {
            case 0: // grass
                top = new int[]{0, 0};
                side = new int[]{3, 0};
                bottom = new int[]{2, 0};
                break;
            case 1: // sand
                top = side = bottom = new int[]{2, 1};
                break;
            case 2: // water
                top = side = bottom = new int[]{13, 12};
                break;
            case 3: // dirt
                top = side = bottom = new int[]{2, 0};
                break;
            case 4: // stone
                top = side = bottom = new int[]{1, 0};
                break;
            default: // 5 = bedrock
                top = side = bottom = new int[]{1, 1};
                break;
        }

        float[] uv = new float[48];
        System.arraycopy(quadUV(top[0], top[1], 0), 0, uv, 0, 8);        // top
        System.arraycopy(quadUV(bottom[0], bottom[1], 0), 0, uv, 8, 8);  // bottom
        System.arraycopy(quadUV(side[0], side[1], 0), 0, uv, 16, 8);     // front
        System.arraycopy(quadUV(side[0], side[1], 2), 0, uv, 24, 8);     // back (flipped start)
        System.arraycopy(quadUV(side[0], side[1], 0), 0, uv, 32, 8);     // left
        System.arraycopy(quadUV(side[0], side[1], 0), 0, uv, 40, 8);     // right
        return uv;
    }

    // method: Chunk
    // purpose: this constructor loads the texture atlas, fills the chunk
    // with blocks, and builds the mesh
    public Chunk(int startX, int startY, int startZ) {
        try {
            // GL_NEAREST keeps the pixels sharp and stops tiles blurring together
            texture = TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream("terrain.png"), GL_NEAREST);
        } catch (Exception e) {
            System.out.println("Could not load terrain.png");
            e.printStackTrace();
        }

        r = new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    // TEMPORARY: random type so all 6 textures show up.
                    // Replace with noise terrain and layering later.
                    Blocks[x][y][z] = new Block(
                            Block.BlockType.values()[r.nextInt(6)]);
                }
            }
        }
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
}