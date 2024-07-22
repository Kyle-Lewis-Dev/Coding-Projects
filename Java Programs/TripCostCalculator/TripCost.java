/**
 * Author: Kyle Lewis
 * Date: 07-23-2024
 *
 * This is a supporting class for the Trip Cost Calculator.
 * It stores various individual trip costs, and performs the calculation for the total cost of a trip.
 */

public class TripCost
{
    float distance;
    float gasolineCost;
    float gasMileage;
    int numberOfDays;
    float hotelCost;
    float foodCost;
    float attractions;
    public TripCost(float distance, float gasolineCost, float gasMileage, int numberOfDays, float hotelCost, float foodCost, float attractions)
    {
        this.distance = distance;
        this.gasolineCost = gasolineCost;
        this.gasMileage = gasMileage;
        this.numberOfDays = numberOfDays;
        this.hotelCost = hotelCost;
        this.foodCost = foodCost;
        this.attractions = attractions;
    }

    public float calculateCost()
    {
        float total = 0f;

        //Gas Cost
        total += distance * gasolineCost / gasMileage;

        //Per Day Costs
        total += numberOfDays * hotelCost;
        total += numberOfDays * foodCost;

        //Flat Costs
        total += attractions;

        return total;
    }
}