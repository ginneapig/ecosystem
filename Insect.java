
public class Insect extends Animal {
    private Boolean rotation;
    private String nextMove;
    private int progress; // reset every time number of steps is reached
    private int totalSteps;

    // Initializes an insect object keeping track of various necessary aspects
    // for proper use of other methods.
    public Insect(int row, int col, String sex, String type, Boolean rotation) {
        super(row, col, sex, type);
        this.rotation = rotation;
        this.nextMove = "a";
        this.progress = 0;
        this.totalSteps = 1;
    }

    // Moves the insect in a clockwise or counter-clockwise movement in
    // ever-growing squares, always starting with a left movement.
    public void move() {
        if (rotation == true) { // move CW
            if (this.nextMove.equals("a")) { // move left
                this.col = col - 1;
                progress += 1;
                if (progress == totalSteps) {
                    this.nextMove = "b";
                    progress = 0;
                }
            } else if (this.nextMove.equals("b")) { // move up
                this.row = row - 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "c";
                    progress = 0;
                }
            } else if (this.nextMove.equals("c")) { // move right
                this.col = col + 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "d";
                    progress = 0;
                }
            } else if (this.nextMove.equals("d")) { // move down
                this.row = row + 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "a";
                    progress = 0;
                    totalSteps += 1;
                }
            }
        } else { // move CCW
            if (this.nextMove.equals("a")) { // move left
                this.col = col - 1;
                progress += 1;
                if (progress == totalSteps) {
                    this.nextMove = "b";
                    progress = 0;
                }
            } else if (this.nextMove.equals("b")) { // move down
                this.row = row + 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "c";
                    progress = 0;
                }
            } else if (this.nextMove.equals("c")) { // move right
                this.col = col + 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "d";
                    progress = 0;
                }
            } else if (this.nextMove.equals("d")) { // move up
                this.row = row - 1;
                progress += 1;
                if (progress == totalSteps) {
                    nextMove = "a";
                    progress = 0;
                    totalSteps += 1;
                }
            }
        }
    }

    // Checks if the two insects are same species and opposite gender
    // before reproducing.
    public Boolean reproduce(Animal two) {
        // checks for same class AND same species
        if (this.type().equals(two.type())) {
            Insect newTwo = (Insect) two;
            if (!this.sex.equals(newTwo.sex)) {
                return true;
            }
        }
        return false;
    }
}
