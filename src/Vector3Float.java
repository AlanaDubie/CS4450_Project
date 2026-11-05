/***************************************************************
* file: Vector3Float.java
* author: Alana, Nirwisha, Aidan
* class: CS 4450
*
* assignment: Final Program - Checkpoint 2
* date last modified: 11/5/2026
*
* purpose: This class holds a 3D point or vector made of three
* floats. The camera uses it to store its position.
*
****************************************************************/
public class Vector3Float {

    private float x, y, z;

    // method: Vector3Float
    // purpose: this constructor sets the x, y, z values
    public Vector3Float(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // method: getX
    // purpose: this method returns the x value
    public float getX() {
        return x;
    }

    // method: getY
    // purpose: this method returns the y value
    public float getY() {
        return y;
    }

    // method: getZ
    // purpose: this method returns the z value
    public float getZ() {
        return z;
    }

    // method: setX
    // purpose: this method changes the x value
    public void setX(float x) {
        this.x = x;
    }

    // method: setY
    // purpose: this method changes the y value
    public void setY(float y) {
        this.y = y;
    }

    // method: setZ
    // purpose: this method changes the z value
    public void setZ(float z) {
        this.z = z;
    }
}