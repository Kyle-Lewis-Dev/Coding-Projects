import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Author: Kyle Lewis
 * Date: 08-06-2024
 *
 * This program tests the relationships between time intervals using a simple GUI.
 *
 * The Interval<T extends Comparable<T>> utility class is generic, and could be used to deal with intervals other than
 * time intervals.
 *
 * It supports comparisons between 2 time intervals, as well as the option to see if either
 * or both intervals contain a third time.
 */

public class TimeIntervalChecker extends Application
{
    //Properties
    String title = "Time Interval Checker";
    GridPane gridPane = new GridPane();
    Label lbStartTime = new Label("Start Time");
    Label lbEndTime = new Label("End Time");
    TextField tfStartTime1 = new TextField();
    TextField tfEndTime1 = new TextField();
    TextField tfStartTime2 = new TextField();
    TextField tfEndTime2 = new TextField();
    TextField tfTimeToCheck = new TextField();
    TextField tfOutput = new TextField();
    Button btCompare = new Button("Compare Intervals");
    Button btCheckTime = new Button("Check Time");

    @Override
    public void start(Stage primaryStage)
    {
        //Set gridPane properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Place nodes in the pane
        placeNodes();

        //Add listeners to buttons
        btCompare.setOnAction(e -> compareIntervals());
        btCheckTime.setOnAction(e -> checkTime());

        //Scene setup
        Scene scene = new Scene(gridPane);//Place the grid pane in the scene
        primaryStage.setTitle(title); //Set the stage title
        primaryStage.setScene(scene); //Place the scene in the stage
        primaryStage.sizeToScene(); //Fit window size to content
        primaryStage.show(); //Display the stage
    }

    /** Places labels, text fields, combo boxes, and button in a grid layout. */
    private void placeNodes()
    {
        //Row 0
        gridPane.add(lbStartTime, 1, 0);
        GridPane.setHalignment(lbStartTime, HPos.CENTER);
        gridPane.add(lbEndTime, 2, 0);
        GridPane.setHalignment(lbEndTime, HPos.CENTER);

        //Row 1
        gridPane.add(new Label("Time Interval 1"), 0, 1);
        gridPane.add(tfStartTime1, 1, 1);
        tfStartTime1.setAlignment(Pos.CENTER);
        gridPane.add(tfEndTime1, 2, 1);
        tfEndTime1.setAlignment(Pos.CENTER);

        //Row 2
        gridPane.add(new Label("Time Interval 2"), 0, 2);
        gridPane.add(tfStartTime2, 1, 2);
        tfStartTime2.setAlignment(Pos.CENTER);
        gridPane.add(tfEndTime2, 2, 2);
        tfEndTime2.setAlignment(Pos.CENTER);

        //Row 3
        gridPane.add(btCompare, 0, 3);
        GridPane.setColumnSpan(btCompare, 3);
        GridPane.setHgrow(btCompare, Priority.ALWAYS);
        GridPane.setVgrow(btCompare, Priority.ALWAYS);
        btCompare.setMaxWidth(Double.MAX_VALUE);
        btCompare.setMaxHeight(Double.MAX_VALUE);

        //Row 4
        gridPane.add(new Label("Time to Check"), 0, 4);
        gridPane.add(tfTimeToCheck, 1, 4);
        tfTimeToCheck.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(tfTimeToCheck, 2);

        //Row 5
        gridPane.add(btCheckTime, 0, 5);
        GridPane.setColumnSpan(btCheckTime, 3);
        GridPane.setHgrow(btCheckTime, Priority.ALWAYS);
        GridPane.setVgrow(btCheckTime, Priority.ALWAYS);
        btCheckTime.setMaxWidth(Double.MAX_VALUE);
        btCheckTime.setMaxHeight(Double.MAX_VALUE);

        //Row 6
        gridPane.add(tfOutput, 0, 6);
        GridPane.setColumnSpan(tfOutput, 3);
    }

    /** Tests the provided time against the 2 time intervals.
     *
     * Possible conditions:
     * Both intervals contain the time HH:MM AM.
     * Only interval 1 contains the time HH:MM AM.
     * Only interval 2 contains the time HH:MM AM.
     * Neither interval contains the time HH:MM AM.
     */
    private void checkTime()
    {
        Interval<Time> interval1;
        Interval<Time> interval2;
        Time toCheck;

        //Handle InvalidTime input errors
        try
        {
            interval1 = new Interval<>(new Time(tfStartTime1.getText()), new Time(tfEndTime1.getText()));
            interval2 = new Interval<>(new Time(tfStartTime2.getText()), new Time(tfEndTime2.getText()));
            toCheck = new Time(tfTimeToCheck.getText());
        }
        catch(InvalidTime e)
        {
            tfOutput.setText(e.getMessage());
            return;
        }

        //Check time against intervals
        if(interval1.within(toCheck))
        {
            if(interval2.within(toCheck))
            {
                tfOutput.setText("Both intervals contain the time " + toCheck.toString());
                return;
            }
            else
            {
                tfOutput.setText("Only interval 1 contains the time " + toCheck.toString());
                return;
            }
        }
        else if(interval2.within(toCheck))
        {
            tfOutput.setText("Only interval 2 contains the time " + toCheck.toString());
            return;
        }
        tfOutput.setText("Neither interval contains the time " + toCheck.toString());
    }

    /** Tests various relationships between 2 intervals.
     *
     * Possible conditions:
     * Interval 1 is a sub-interval of interval 2.
     * Interval 2 is a sub-interval of interval 1.
     * The intervals overlap.
     * The intervals are disjointed.
     */
    private void compareIntervals()
    {
        Interval<Time> interval1;
        Interval<Time> interval2;

        //Handle InvalidTime input errors
        try
        {
            interval1 = new Interval<>(new Time(tfStartTime1.getText()), new Time(tfEndTime1.getText()));
            interval2 = new Interval<>(new Time(tfStartTime2.getText()), new Time(tfEndTime2.getText()));
        }
        catch(InvalidTime e)
        {
            tfOutput.setText(e.getMessage());
            return;
        }

        //Test if a subinterval exists
        if(interval1.subinterval(interval2))
        {
            tfOutput.setText("Interval 1 is a sub-interval of interval 2");
            return;
        }
        else if(interval2.subinterval(interval1))
        {
            tfOutput.setText("Interval 2 is a sub-interval of interval 1");
            return;
        }

        //Test if the intervals overlap
        if(interval1.overlaps(interval2))//Condition not being met
        {
            tfOutput.setText("The intervals overlap");
            return;
        }

        //No previous conditions met
        tfOutput.setText("The intervals are disjointed");
    }

    /** The main method is only needed for IDEs with limited JavaFX support.
     It is not needed for running from the command line. */
    public static void main(String[] args)
    {
        launch(args);
    }
}