package swingingAround;

public class Vec3 {

    public double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void addVec3ToThisVec3(Vec3 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public Vec3 multiplyVec3ByMatrix(dMatrix mat) {
        if ((mat.getHeight() != 3) || (mat.getWidth() != 3)) {
            System.out.println("Either the number of rows in the matrix is incorrect for Vec3 multiplication, " +
                               "\nor the number of columns is incorrect, and then the result vector wouldn't be a Vec3.");


            return null;
        }

        double newX, newY, newZ;
        newX = x * mat.getValueAt(0,0) + y * mat.getValueAt(0,1) + z * mat.getValueAt(0,2);
        newY = x * mat.getValueAt(1,0) + y * mat.getValueAt(1,1) + z * mat.getValueAt(1,2);
        newZ = x * mat.getValueAt(2,0) + y * mat.getValueAt(2,1) + z * mat.getValueAt(2,2);

        return new Vec3(newX,newY,newZ);
    }

    public double dotProduct(Vec3 v) {
        double product = 0;
        product += v.x * x + v.y * y + v.z * z;
        return product;
    }

    public void multiplyByNumber(double nr) {
        x *= nr;
        y *= nr;
        z *= nr;
    }

    public void printVec() {
        System.out.println(x + " " + y + " " + z);
    }
}
