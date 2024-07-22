import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Author: Kyle Lewis
 * Date: 07-23-2024
 *
 * This program accepts user inputs for various costs related to a road trip.
 * The application utilizes a simple GUI for ease of use.
 * When the Calculate button is pressed, the total cost of the trip is displayed.
 *
 * Some costs, such as distance, gasoline cost, and gas mileage, support imperial and metric units.
 * Necessary unit conversions are performed automatically when calculating the total trip cost.
 */

public class TripCostCalculator extends Application
{
    //Constants
    final float KILOMETERS_PER_MILE = 1.609347f;
    final float LITERS_PER_GALLON = 3.78541178f;

    //Properties
    GridPane gridPane = new GridPane();
    Button btCalculate = new Button("Calculate");
    TextField tfDistance = new TextField();
    TextField tfGasolineCost = new TextField();
    TextField tfGasMileage = new TextField();
    TextField tfNumberOfDays = new TextField();
    TextField tfHotelCostPerDay = new TextField();
    TextField tfFoodCostPerDay = new TextField();
    TextField tfAttractions = new TextField();
    TextField tfTotalTripCost = new TextField();
    ComboBox<String> cboDistance = new ComboBox<>();
    ComboBox<String> cboGasolineCost = new ComboBox<>();
    ComboBox<String> cboGasMileage = new ComboBox<>();

    @Override //Override the start method in the Application class
    public void start(Stage primaryStage)
    {
        //Set gridPane properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Set up choices for combo boxes
        initializeComboBoxes();

        //Place nodes in the pane
        placeNodes();

        //Add listener to btCalculate
        btCalculate.setOnAction(e -> displayTotalTripCost());

        //Scene setup
        Scene scene = new Scene(gridPane);//Place the grid pane in the scene
        primaryStage.setTitle("Trip Cost Calculator"); //Set the stage title
        primaryStage.setScene(scene); //Place the scene in the stage
        primaryStage.sizeToScene(); //Fit window size to content
        primaryStage.show(); //Display the stage
    }

    /** Adds selection items to combo boxes and set their initial values. */
    private void initializeComboBoxes()
    {
        ObservableList<String> distanceUnits = FXCollections.observableArrayList("Miles", "Kilometers");
        cboDistance.getItems().addAll(distanceUnits);
        cboDistance.setValue("Miles");

        ObservableList<String> gasCostUnits = FXCollections.observableArrayList("Dollars/Gallon", "Dollars/Liter");
        cboGasolineCost.getItems().addAll(gasCostUnits);
        cboGasolineCost.setValue("Dollars/Gallon");

        ObservableList<String> gasMileageUnits = FXCollections.observableArrayList("Miles/Gallon", "Kilometers/Liter");
        cboGasMileage.getItems().addAll(gasMileageUnits);
        cboGasMileage.setValue("Miles/Gallon");
    }

    /** Places labels, text fields, combo boxes, and button in a grid layout. */
    private void placeNodes()
    {
        gridPane.add(new Label("Distance:"), 0, 0);
        gridPane.add(tfDistance, 1, 0);
        tfDistance.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(cboDistance, 2, 0);

        gridPane.add(new Label("Gasoline Cost:"), 0, 1);
        gridPane.add(tfGasolineCost, 1, 1);
        tfGasolineCost.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(cboGasolineCost, 2, 1);

        gridPane.add(new Label("Gas Mileage:"), 0, 2);
        gridPane.add(tfGasMileage, 1, 2);
        tfGasMileage.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(cboGasMileage, 2, 2);

        gridPane.add(new Label("Number of Days:"), 0, 3);
        gridPane.add(tfNumberOfDays, 1, 3);
        tfNumberOfDays.setAlignment(Pos.CENTER_RIGHT);

        gridPane.add(new Label("Hotel Cost:"), 0, 4);
        gridPane.add(tfHotelCostPerDay, 1, 4);
        tfHotelCostPerDay.setAlignment(Pos.CENTER_RIGHT);

        gridPane.add(new Label("Food Cost:"), 0, 5);
        gridPane.add(tfFoodCostPerDay, 1, 5);
        tfFoodCostPerDay.setAlignment(Pos.CENTER_RIGHT);

        gridPane.add(new Label("Attractions:"), 0, 6);
        gridPane.add(tfAttractions, 1, 6);
        tfAttractions.setAlignment(Pos.CENTER_RIGHT);

        //Stretch button to cell size
        gridPane.add(btCalculate, 1, 7);
        GridPane.setHgrow(btCalculate, Priority.ALWAYS);
        GridPane.setVgrow(btCalculate, Priority.ALWAYS);
        btCalculate.setMaxWidth(Double.MAX_VALUE);
        btCalculate.setMaxHeight(Double.MAX_VALUE);

        gridPane.add(new Label("Total Trip Cost"), 0, 8);
        gridPane.add(tfTotalTripCost, 1, 8);
        tfTotalTripCost.setAlignment(Pos.CENTER_RIGHT);
    }

    /** Calculates and displays the trip cost based on text field inputs. Called from Calculate button. */
    private void displayTotalTripCost()
    {
        //Perform unit conversions tp Imperial measurements where applicable
        float distance = toMiles(Float.parseFloat(tfDistance.getText()));
        float gasolineCost = toDollarsPerGallon(Float.parseFloat(tfGasolineCost.getText()));
        float gasMileage = toMilesPerGallon(Float.parseFloat(tfGasMileage.getText()));

        int numberOfDays = Integer.parseInt(tfNumberOfDays.getText());
        float hotelCostPerDay = Float.parseFloat(tfHotelCostPerDay.getText());
        float foodCostPerDay = Float.parseFloat(tfFoodCostPerDay.getText());
        float attractions = Float.parseFloat(tfAttractions.getText());

        //Package raw data into TripCost object
        TripCost totalTripCost = new TripCost(distance, gasolineCost, gasMileage, numberOfDays, hotelCostPerDay, foodCostPerDay, attractions);

        tfTotalTripCost.setText(String.format("$%.2f", totalTripCost.calculateCost()));
    }

    /** Converts kilometers to miles, if applicable. */
    private float toMiles(float distance)
    {
        if(cboDistance.getValue().equals("Kilometers"))
        {
            return distance / KILOMETERS_PER_MILE;
        }
        //Return original value if already in miles
        else return distance;
    }

    /** Converts Dollars/Liter to Dollars/Gallon, if applicable. */
    private float toDollarsPerGallon(float gasolineCost)
    {
        if(cboGasolineCost.getValue().equals("Dollars/Liter"))
        {
            return gasolineCost * LITERS_PER_GALLON;
        }
        //Return original value if already in Dollars/Gallon
        else return gasolineCost;
    }

    /** Converts Kilometers/Liter to Miles/Gallon, if applicable. */
    private float toMilesPerGallon(float gasMileage)
    {
        if(cboGasMileage.getValue().equals("Kilometers/Liter"))
        {
            return gasMileage * LITERS_PER_GALLON / KILOMETERS_PER_MILE;
        }
        //Return original value if already in Miles/Gallon
        else return gasMileage;
    }

     /** The main method is only needed for IDEs with limited JavaFX support.
     It is not needed for running from the command line. */
    public static void main(String[] args)
    {
        launch(args);
    }
}