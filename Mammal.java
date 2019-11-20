
public class Mammal extends Animal {
    private String dir;
    private String nextMove;
    private int numReproduce;

    public Mammal(int row, int col, String sex, String type, String dir) {
        super(row, col, sex, type);
        // below: specified dir. animal moves in (left & up vs. right & down)
        this.dir = dir;
        this.nextMove = "VERT"; // reset to keep track of zig or zag
        this.numReproduce = 5;
    }

    // Changes the labeled position of the Bird instance.
    public void move() {
        if (nextMove.equals("HORI")) {
            if (dir.equals("left")) {
                // move left
                this.col = col - 1;
            } else {
                // move right
                this.col = col + 1;
            }
            nextMove = "VERT"; // prep for next move
        } else { // move vertically
            if (dir.equals("left")) {
                // move up
                this.row = row - 1;
            } else {
                // move down
                this.row = row + 1;
            }
            nextMove = "HORI"; // prep
        }
    }

    // consider both mammals, same species, opp genders, num reproduce
    public Boolean reproduce(Animal two) {
        if (this.type().equals(two.type())) {
            Mammal newTwo = (Mammal) two;
            this.numReproduce -= 1;
            newTwo.numReproduce -= 1;
            if (this.sex.equals(two.sex) == false && this.numReproduce >= 0
                    && newTwo.numReproduce >= 0) {
                return true;
            }
        }
        return false;
    }
}
