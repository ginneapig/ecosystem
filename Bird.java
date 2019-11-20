
public class Bird extends Animal {
    private int steps;
    private int progress; // reset every time number of steps is reached
    private String nextMove;

    public Bird(int row, int col, String sex, String type, int steps) {
        super(row, col, sex, type);
        this.steps = steps;
        this.progress = 0;
        this.nextMove = "a";
    }

    // Changes the labeled position of the Bird instance. Keep track of
    // how many steps taken in one direction and reset progress every time
    // object reaches full length in one direction.
    public void move() {
        if (this.nextMove.equals("a")) { // move down
            this.row = row + 1;
            progress += 1;
            if (progress == steps) {
                nextMove = "b";
                progress = 0;
            }
        } else if (this.nextMove.equals("b")) { // move right
            this.col = col + 1;
            progress += 1;
            if (progress == steps) {
                nextMove = "c";
                progress = 0;
            }
        } else if (this.nextMove.equals("c")) { // move up
            this.row = row - 1;
            progress += 1;
            if (progress == steps) {
                nextMove = "a";
                progress = 0;
            }
        }
    }

    public Boolean reproduce(Animal two) {
        if (this.type().equals(two.type())) {
            Bird newTwo = (Bird) two;
            return !this.sex.equals(newTwo.sex);
        }
        return false;
    }
}
