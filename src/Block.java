/***************************************************************
* file: Block.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 2
* date last modified: 11/5/2026
*
* purpose: This class represents one voxel (a cube) in the world.
* It stores the block's type and whether the block is active
* (active blocks are the ones that get drawn).
*
****************************************************************/
public class Block {

    private boolean IsActive;
    private BlockType Type;
    private float x, y, z;

    public enum BlockType {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);

        private int BlockID;

        // method: BlockType
        // purpose: this constructor stores the numeric id of the block type
        BlockType(int i) {
            BlockID = i;
        }

        // method: GetID
        // purpose: this method returns the numeric id of the block type
        public int GetID() {
            return BlockID;
        }

        // method: SetID
        // purpose: this method changes the numeric id of the block type
        public void SetID(int i) {
            BlockID = i;
        }
    }

    // method: Block
    // purpose: this constructor creates a block of the given type
    public Block(BlockType type) {
        Type = type;
    }

    // method: setCoords
    // purpose: this method stores the block's x, y, z position
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // method: IsActive
    // purpose: this method returns true if the block should be drawn
    public boolean IsActive() {
        return IsActive;
    }

    // method: SetActive
    // purpose: this method turns the block on or off
    public void SetActive(boolean active) {
        IsActive = active;
    }

    // method: GetID
    // purpose: this method returns the numeric id of the block's type
    public int GetID() {
        return Type.GetID();
    }
}