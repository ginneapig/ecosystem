import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class PA9Main extends Application {
    /**
     * Annie Gao
     * CS 210, Spring 2019
     * PA9, PA9Main.java
     * Section: 001E
     * 
     * This program first creates a grid to store a number of animals
     * created through the input parameters. It implements an Ecosystem
     * object that will allow the storage of various specified animals.
     * 
     * Using JavaFX, this program also produces a graphic that
     * allows users to input different types of commands, such as CREATE,
     * MOVE, REPRODUCE for the specified animals. Each animal is represented
     * by a different color on the ecosystem picture.
     * 
     * Possible commands are:
     * 
     * CREATE (0,1) warbler female 2
     * CREATE (2,3) elephant male right
     * CREATE (3,4) bee male true
     * MOVE
     * REPRODUCE
     * 
     * Creating different animals requires setting different parameters,
     * and for mammals after specifying the gender, they are given a direction.
     * If given right, they move right and down; if given left, they move
     * left and up.
     * For birds, they take in a number of steps. This number of steps
     * specifies how many steps they take in a preset direction of down,
     * right, up, and repeat.
     * For insects, they move in a clockwise or counterclockwise fashion.
     * They are passed a boolean value, where true indicates CW and
     * false means CCW. The square pattern they follow grows bigger with each
     * revolution, in which they start with one step in each direction until
     * they end up at the same location they were created. The next revolution
     * means taking an extra step in each direction so that it is a bigger
     * square, until they return to their creation spot, and so on.
     * 
     * Moving animals off the grid mean they simply appear on the opposite side
     * such as falling off the right side and appearing on the left.
     * 
     * Animals reproduce if there are two animals of the same species in the
     * first two spots at the same location, and they are of opposite gender.
     * This is different for mosquitos, where they take in two additional
     * booleans to represent if the genes received from their parents
     * have been modified or not. A boolean of true means the gene is modified.
     * If a mosquito receives two true values, they cannot reproduce.
     * 
     * Both MOVE and REPRODUCE can be followed with a species or class
     * name if the user wants to only move or reproduce certain animals,
     * such as:
     * 
     * MOVE mammal
     * REPRODUCE bird
     **/

    private static int SIZE_ACROSS; // num rows
    private static int SIZE_DOWN; // num cols

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 40;

    // resource to find which animal is of what class
    private static List<String> MAMMALS = new ArrayList<String>(Arrays
            .asList("elephant", "rhinoceros", "lion", "giraffe", "zebra"));
    private static List<String> BIRDS = new ArrayList<String>(
            Arrays.asList("thrush", "owl", "warbler", "shrike"));
    private static List<String> INSECTS = new ArrayList<String>(
            Arrays.asList("mosquito", "bee", "fly", "ant"));

    // the Ecosystem object storing animals
    private static Ecosystem ANIMAL_GRID;

    public static void main(String[] args) {
        SIZE_ACROSS = Integer.parseInt(args[0]) * RECT_SIZE;
        SIZE_DOWN = Integer.parseInt(args[1]) * RECT_SIZE;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        TextField cmdIn = new TextField();


        Button button = new Button("Process");
        TextArea showCommands = new TextArea();

        GraphicsContext gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN,
                button, cmdIn, showCommands);

        button.setOnAction(new CommandHandler(gc, cmdIn, showCommands));

        primaryStage.setTitle("DIY Ecosystem! You can't ruin the planet"
                + " anymore than it already has been...");
        primaryStage.show();

        ANIMAL_GRID = new Ecosystem(SIZE_ACROSS, SIZE_DOWN);
    }


    // Takes in the line typed by the user and processes it
    // to appear on the visual ecosystem.
    class CommandHandler implements EventHandler<ActionEvent> {
        private TextField command;
        private GraphicsContext gc;
        private TextArea showComm;

        CommandHandler(GraphicsContext gc, TextField command,
                TextArea showCommands) {
            this.gc = gc;
            this.command = command;
            this.showComm = showCommands;
        }

        @Override
        public void handle(ActionEvent event) {
            // get string from textfield
            String stringCommand = command.getText();
            String[] splitComm = stringCommand.split(" ");
            String currCommand = splitComm[0].toUpperCase();
            if (currCommand.equals("CREATE")) {
                Integer row = Character.getNumericValue(splitComm[1].charAt(1));
                Integer col = Character.getNumericValue(splitComm[1].charAt(3));
                String sex = splitComm[3].toLowerCase();
                String type = splitComm[2].toLowerCase(); // specific species
                String dir = splitComm[4].toLowerCase();

                if (MAMMALS.contains(type)) {
                    ANIMAL_GRID.newMammal(row, col, sex, type, dir);

                } else if (BIRDS.contains(type)) {
                    int steps = Integer.parseInt(splitComm[4]);
                    ANIMAL_GRID.newBird(row, col, sex, type, steps);

                } else if (INSECTS.contains(type)) {
                    Boolean rotation = Boolean.parseBoolean(splitComm[4]);
                    if (type != "mosquito") {
                        ANIMAL_GRID.newInsect(row, col, sex, type, rotation);
                    } else { // mosquitos are special
                        Boolean momGene = Boolean.parseBoolean(splitComm[5]);
                        Boolean dadGene = Boolean.parseBoolean(splitComm[6]);
                        ANIMAL_GRID.newMosquito(row, col, sex, type, rotation,
                                momGene, dadGene);
                    }
                }

            } else if (currCommand.equals("MOVE")) {
                if (splitComm.length == 1) { // standard move for all animals
                    ANIMAL_GRID.move();
                } else { // move either specific species, class, or indices
                    if (splitComm[1].charAt(0) == '(') {
                        // must convert, otherwise will get ASCII values
                        int rowPos = Character
                                .getNumericValue(splitComm[1].charAt(1));
                        int colPos = Character
                                .getNumericValue(splitComm[1].charAt(3));
                        ANIMAL_GRID.move(rowPos, colPos);
                    } else { // move type
                        String specifiedAnimal = splitComm[1].substring(0, 1)
                                .toUpperCase()
                                + splitComm[1].substring(1).toLowerCase();
                        ANIMAL_GRID.move(specifiedAnimal);
                    }
                }

            } else if (currCommand.equals("REPRODUCE")) {
                if (splitComm.length == 1) { // standard move for all animals
                    ANIMAL_GRID.reproduce();
                } else { // reproduce specific place or specific
                         // class/species
                    if (splitComm[1].charAt(0) == '(') {
                        // must convert, otherwise will get ASCII values
                        int rowPos = Character
                                .getNumericValue(splitComm[1].charAt(1));
                        int colPos = Character
                                .getNumericValue(splitComm[1].charAt(3));
                        ANIMAL_GRID.reproduce(rowPos, colPos);
                    } else {
                        String specifiedAnimal = splitComm[1].substring(0, 1)
                                .toUpperCase()
                                + splitComm[1].substring(1).toLowerCase();
                        ANIMAL_GRID.reproduce(specifiedAnimal);
                    }
                }
            }
            ecosystemDraw(gc);

            this.showComm.appendText(stringCommand + "\n");
            command.clear();
        }
    }

    // Sets up a loop to display the first animal at each grid location,
    // represented by different color squares, or a green square
    // if no animals present.
    // PARAMS: GraphicsContext to draw ecosystem to.
    // RETURNS: None.
    private void ecosystemDraw(GraphicsContext gc) {
        List<ArrayList<ArrayList<Animal>>> stackedGrid = ANIMAL_GRID
                .getStackedGrid();
        for (int i = 0; i < stackedGrid.size(); i++) {
            for (int j = 0; j < stackedGrid.get(i).size(); j++) {
                if (!stackedGrid.get(i).get(j).isEmpty()) {
                    Animal currAnimal = stackedGrid.get(i).get(j).get(0);
                    if (currAnimal.type().equals("elephant")) {
                        gc.setFill(Color.GREY);
                    } else if (currAnimal.type().equals("rhinoceros")) {
                        gc.setFill(Color.BURLYWOOD);
                    } else if (currAnimal.type().equals("lion")) {
                        gc.setFill(Color.DARKORANGE);
                    } else if (currAnimal.type().equals("giraffe")) {
                        gc.setFill(Color.YELLOW);
                    } else if (currAnimal.type().equals("zebra")) {
                        gc.setFill(Color.WHITE);
                    } else if (currAnimal.type().equals("thrush")) {
                        gc.setFill(Color.LIGHTBLUE);
                    } else if (currAnimal.type().equals("owl")) {
                        gc.setFill(Color.BROWN);
                    } else if (currAnimal.type().equals("warbler")) {
                        gc.setFill(Color.DARKBLUE);
                    } else if (currAnimal.type().equals("shrike")) {
                        gc.setFill(Color.TURQUOISE);
                    } else if (currAnimal.type().equals("mosquito")) {
                        gc.setFill(Color.RED);
                    } else if (currAnimal.type().equals("bee")) {
                        gc.setFill(Color.PINK);
                    } else if (currAnimal.type().equals("fly")) {
                        gc.setFill(Color.BLACK);
                    } else if (currAnimal.type().equals("ant")) {
                        gc.setFill(Color.BURLYWOOD);
                    }
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(RECT_SIZE * j, RECT_SIZE * i, RECT_SIZE, RECT_SIZE);
            }
        }
    }

    /**
     * Sets up the application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, Button button, TextField cmd_in,
            TextArea command) {
        // ===== set up the scene with a text box and button for input
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        final int num_items = 2;
        HBox input_box = new HBox(num_items);
        input_box.getChildren().add(cmd_in);
        input_box.getChildren().add(button);
        // below: ensure it fills the whole width
        HBox.setHgrow(cmd_in, Priority.ALWAYS);
        HBox.setHgrow(button, Priority.ALWAYS);
        p.setBottom(input_box);

        // Canvas(pixels across, pixels down)
        Canvas canvas = new Canvas(canvas_width, canvas_height);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);


        // Place the canvas and command output areas in pane.
        p.setTop(canvas);
        p.setCenter(command);

        // Place the pane into the scene into the stage.
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }
}
