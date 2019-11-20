
abstract class Animal {
    public int row;
    public int col;
    public String sex;
    public String type;
    public Character typeChar;

    // Constructor class to be used in all classes extending
    // this abstract class. Cannot be constructed on its own.
    public Animal(int row, int col, String sex, String type) {
        this.row = row;
        this.col = col;
        this.sex = sex;
        this.type = type;
        this.typeChar = type.charAt(0);
    }

    public abstract void move();

    public abstract Boolean reproduce(Animal a);

    // Return sex of animal.
    public String sex() {
        return this.sex;
    }

    // Return full name of animal species.
    public String type() {
        return this.type;
    }

    // Return single character representation.
    public Character charRep() {
        return this.typeChar;
    }

    // Return row location.
    public int getRow() {
        return this.row;
    }

    // Return column location.
    public int getCol() {
        return this.col;
    }

    // Called by Ecosystem only if animal has fallen off grid.
    public void setRow(int newRow) {
        this.row = newRow;
    }

    // Called by Ecosystem only if animal has fallen off grid.
    public void setCol(int newCol) {
        this.col = newCol;
    }

}
