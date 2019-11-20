import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ecosystem {
    private int rows;
    private int cols;
    private List<ArrayList<ArrayList<Animal>>> stackedGrid;

    // Constructor class. Creates empty grid to represent ecosystem.
    // PARAMS: Integer values of rows and columns to determine size of grid.
    // RETURNS: N/A.
    public Ecosystem(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        stackedGrid = new ArrayList<ArrayList<ArrayList<Animal>>>();
        for (int i = 0; i < rows; i++) {
            ArrayList<ArrayList<Animal>> nextRow = new ArrayList<ArrayList<Animal>>();
            stackedGrid.add(nextRow);
            for (int j = 0; j < cols; j++) {
                ArrayList<Animal> nextCol = new ArrayList<Animal>();
                nextRow.add(nextCol);
            }
        }
    }

    // Return 3D list for access to contents.
    public List<ArrayList<ArrayList<Animal>>> getStackedGrid() {
        return stackedGrid;
    }

    // Create new mammal object and directly add to ecosystem.
    // PARAMS: Integer values of specified row and column to place mammal,
    // followed by string values of sex and species of mammal and
    // direction mammal moves in.
    // RETURNS: None.
    public void newMammal(int row, int col, String sex, String type,
            String dir) {
        Animal m = new Mammal(row, col, sex, type, dir);
        stackedGrid.get(row).get(col).add(m);
    }

    // Create new bird object and directly add to ecosystem.
    // PARAMS: Integer values of specified row and column to place bird,
    // followed by string values of sex and species of bird and
    // number of steps indicating number of steps bird moves in one direction.
    // RETURNS: None.
    public void newBird(int row, int col, String sex, String type, int steps) {
        Animal b = new Bird(row, col, sex, type, steps);
        stackedGrid.get(row).get(col).add(b);
    }

    // Create new insect object and directly add to ecosystem.
    // PARAMS: Integer values of specified row and column to place insect,
    // followed by string values of sex and species of insect and boolean value
    // indicating way in which insect moves.
    // RETURNS: None.
    public void newInsect(int row, int col, String sex, String type,
            Boolean rotation) {
        Animal i = new Insect(row, col, sex, type, rotation);
        stackedGrid.get(row).get(col).add(i);
    }

    // Create new mosquito object and directly add to ecosystem.
    // PARAMS: Integer values of specified row and column to place insect,
    // followed by string values of sex and species of insect.
    // Following is a boolean value indicating way in which insect moves,
    // and two more booleans inherited from parents.
    // RETURNS: None.
    public void newMosquito(int row, int col, String sex, String type,
            Boolean rotation, Boolean momGene, Boolean dadGene) {
        Animal i = new Insect(row, col, sex, type, rotation);
        stackedGrid.get(row).get(col).add(i);
    }

    // Helper function for checking if animals are falling off
    // top or bottom sides during move.
    // PARAMS: Animal object of current animal being checked.
    // RETURNS: Integer representing new row.
    public int rowCheck(Animal currentAnimal) {
        int newRow = currentAnimal.getRow();
        // int newCol = currentAnimal.getCol();
        if (newRow > rows - 1) { // fell off bottom side
            currentAnimal.setRow(0);
            newRow = 0;
        } else if (newRow < 0) { // fell off top side
            currentAnimal.setRow(rows - 1);
            newRow = rows - 1;
        }
        return newRow;
    }

    // Helper function for checking if animals are falling off
    // left or right sides during move.
    // PARAMS: Animal object of current animal being checked.
    // RETURNS: Integer representing new column.
    public int colCheck(Animal currentAnimal) {
        int newCol = currentAnimal.getCol();
        if (newCol > cols - 1) { // fell off right side
            currentAnimal.setCol(0);
            newCol = 0;
        } else if (newCol < 0) { // fell off left side
            currentAnimal.setCol(cols - 1);
            newCol = cols - 1;
        }
        return newCol;
    }

    // Helper function for move: create new grid.
    // PARAMS: None.
    // RETURNS: 3D list.
    public List<ArrayList<ArrayList<Animal>>> newGrid() {
        List<ArrayList<ArrayList<Animal>>> newGrid = new ArrayList<ArrayList<ArrayList<Animal>>>();
        for (int i = 0; i < rows; i++) {
            ArrayList<ArrayList<Animal>> nextRow = new ArrayList<ArrayList<Animal>>();
            newGrid.add(nextRow);
            for (int j = 0; j < cols; j++) {
                ArrayList<Animal> nextCol = new ArrayList<Animal>();
                nextRow.add(nextCol);
            }
        }
        return newGrid;
    }

    // Move each animal in the ecosystem.
    // PARAMS: None.
    // RETURNS: None.
    public void move() {
        // first create new grid to store new placements
        List<ArrayList<ArrayList<Animal>>> newGrid = newGrid();

        // next check each animal and place into new grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < stackedGrid.get(i).get(j).size(); k++) {
                    Animal currentAnimal = stackedGrid.get(i).get(j).get(k);
                    currentAnimal.move();
                    int newRow = currentAnimal.getRow();
                    int newCol = currentAnimal.getCol();
                    newRow = rowCheck(currentAnimal);
                    newCol = colCheck(currentAnimal);
                    newGrid.get(newRow).get(newCol).add(currentAnimal);
                }
            }
        }
        stackedGrid = newGrid;
    }

    // Move animals only at a specific location.
    // PARAMS: Integer values of specified row and column to be moved.
    // RETURNS: None.
    public void move(int row, int col) {
        // ensures within grid size
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            // locate list of animals to move
            List<Animal> desiredMove = stackedGrid.get(row).get(col);

            // first create new grid to store new placements
            List<ArrayList<ArrayList<Animal>>> newGrid = newGrid();

            // add to new grid without moving
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    for (int k = 0; k < stackedGrid.get(i).get(j).size(); k++) {
                        if (i != row && j != col) {
                            Animal currentAnimal = stackedGrid.get(i).get(j)
                                    .get(k);
                            newGrid.get(i).get(j).add(currentAnimal);
                        }
                    }
                }
            }
            // move only these specified animals
            if (!desiredMove.isEmpty()) {
                for (int i = 0; i < desiredMove.size(); i++) {
                    Animal currentAnimal = desiredMove.get(i);
                    currentAnimal.move();
                    int newRow = currentAnimal.getRow();
                    int newCol = currentAnimal.getCol();
                    newRow = rowCheck(currentAnimal);
                    newCol = colCheck(currentAnimal);
                    newGrid.get(newRow).get(newCol).add(currentAnimal);
                }
            }
            stackedGrid = newGrid;
        }
    }

    // Moves animals of a specific class or, even narrower, a specific
    // species throughout ecosystem.
    // PARAMS: String that is either species or class name of animal.
    // RETURNS: None.
    public void move(String specifiedAnimal) {
        // first create new grid to store new placements
        List<ArrayList<ArrayList<Animal>>> newGrid = newGrid();

        // next check if each animal matches specified animal
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < stackedGrid.get(i).get(j).size(); k++) {
                    Animal currentAnimal = stackedGrid.get(i).get(j).get(k);
                    String className = currentAnimal.getClass().getSimpleName();
                    String typeName = currentAnimal.type();
                    if (specifiedAnimal.equals(className)
                            || specifiedAnimal.toLowerCase().equals(typeName)) {
                        currentAnimal.move();
                        int newRow = currentAnimal.getRow();
                        int newCol = currentAnimal.getCol();
                        newRow = rowCheck(currentAnimal);
                        newCol = colCheck(currentAnimal);
                        newGrid.get(newRow).get(newCol).add(currentAnimal);
                    } else { // not moved
                        newGrid.get(i).get(j).add(currentAnimal);
                    }
                }
            }
        }
        stackedGrid = newGrid;
    }

    // Helper function for reproduce: checks each class.
    // PARAMS: Integer values of row and column, two Animal objects
    // to possibly reproduce, and string name of the first animal's class.
    // RETURNS: None.
    public void whichClass(int row, int col, Animal a1, Animal a2,
            String currClass) {
        // Generate random number for determination of sex.
        int randNum = ThreadLocalRandom.current().nextInt(0, 2);
        String sex;
        if (randNum == 0) {
            sex = "male";
        } else {
            sex = "female";
        }
        if (currClass.equals("Mammal")) {
            // Generate random number for determination of direction.
            int randNum2 = ThreadLocalRandom.current().nextInt(0, 2);
            String dir;
            if (randNum2 == 0) {
                dir = "left";
            } else {
                dir = "right";
            }
            Mammal newA1 = (Mammal) a1;
            if (newA1.reproduce(a2)) {
                Animal baby = new Mammal(row, col, sex, newA1.type(), dir);
                stackedGrid.get(row).get(col).add(baby);
            }
        } else if (currClass.equals("Bird")) {
            Bird newA1 = (Bird) a1;
            if (newA1.reproduce(a2)) {
                Animal baby = new Bird(row, col, sex, newA1.type(), 5);
                stackedGrid.get(row).get(col).add(baby);
            }
        } else if (currClass.equals("Insect")) {
            // Generate random number for determination of rotation.
            int randNum3 = ThreadLocalRandom.current().nextInt(0, 2);
            boolean rot;
            if (randNum3 == 0) {
                rot = true;
            } else {
                rot = false;
            }
            if (a1.type() == "mosquito") {
                Mosquito newA1 = (Mosquito) a1;
                if (newA1.reproduce(a2)) {
                    Mosquito newA2 = (Mosquito) a2;
                    Animal baby = new Mosquito(row, col, sex, newA1.type(),
                            rot, newA1.editedGene(), newA2.editedGene());
                    stackedGrid.get(row).get(col).add(baby);
                }
            } else {
                Insect newA1 = (Insect) a1;
                if (newA1.reproduce(a2)) {
                    Animal baby = new Insect(row, col, sex, newA1.type(),
                            rot);
                    stackedGrid.get(row).get(col).add(baby);
                }
            }
        }
    }

    // Reproduces animals at each location if the first two animals
    // are of the same class and species as well as opposite gender.
    // Accounts for different requirements for each class as well.
    // PARAMS: None.
    // RETURNS: None.
    public void reproduce() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (stackedGrid.get(i).get(j).size() >= 2) {
                    Animal a1 = stackedGrid.get(i).get(j).get(0);
                    Animal a2 = stackedGrid.get(i).get(j).get(1);
                    String currClass = a1.getClass().getSimpleName();
                    whichClass(i, j, a1, a2, currClass);
                }
            }
        }
    }

    // Reproduces animals only at a specific location, ensuring
    // passed in values are within range and location has enough animals.
    // PARAMS: Integer values of specified row and column to check.
    // RETURNS: None.
    public void reproduce(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            if (stackedGrid.get(row).get(col).size() >= 2) {
                Animal a1 = stackedGrid.get(row).get(col).get(0);
                Animal a2 = stackedGrid.get(row).get(col).get(1);
                String currClass = a1.getClass().getSimpleName();
                whichClass(row, col, a1, a2, currClass);
            }
        }
    }

    // Reproduces animals only of a specific class or species, ensuring
    // passed in values are within range and location has enough animals.
    // PARAMS: String name of class or species, first letter capitalized only.
    // RETURNS: None.
    public void reproduce(String specifiedAnimal) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (stackedGrid.get(i).get(j).size() >= 2) {
                    Animal a1 = stackedGrid.get(i).get(j).get(0);
                    Animal a2 = stackedGrid.get(i).get(j).get(1);
                    // why isn't class name getting mosquito
                    String className = a1.getClass().getSimpleName();
                    String typeName = a1.type();
                    if (specifiedAnimal.equals(className)
                            || specifiedAnimal.toLowerCase().equals(typeName)) {
                        // reproduce all types of animals in specific class
                        whichClass(i, j, a1, a2, className);
                    }
                }
            }
        }
    }

    // Print out grid of location of animals, representing animals by
    // the first letter of their species name and only printing
    // the first arrival if multiple animals at one location. Empty locations
    // are represented by a "." character.
    // PARAMS: None.
    // RETURNS: None.
    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (stackedGrid.get(i).get(j).isEmpty()) {
                    System.out.print(".");
                } else {
                    System.out
                            .print(stackedGrid.get(i).get(j).get(0).charRep());
                }
                if (j == cols - 1) {
                    System.out.println();
                }
            }
            if (i == rows - 1) {
                System.out.println();
            }
        }
    }
}
