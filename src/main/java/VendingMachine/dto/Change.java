package VendingMachine.dto;

import VendingMachine.service.InsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static VendingMachine.dto.Coins.*;

/**
 * The {@code Change} class is responsible for calculating change
 * to be returned based on the BigDecimal amount that it is passed.
 * Changed is calculated based on the Coins enum.
 */
public class Change {
    // Declare and initialize a HashMap to store Coins and the number of each
    private static HashMap<Coins, Integer> coinChangeMap = new HashMap<>();

    /**
     * Constructor for Change class
     * @param change HashMap of change
     */
    public Change(HashMap<Coins, Integer> change) {
        coinChangeMap = change;
    }

    /**
     * Based on the BigDecimal amount passed, the number of coins needed
     * to equal that amount are calculated and added to the change map
     * @param funds balance passed to method
     * @return map of coins
     * @throws InsufficientFundsException if balance is $0.00 or less
     */
    public static HashMap<Coins,Integer> getChange(BigDecimal funds) throws
            InsufficientFundsException {
        // Throw InsufficientFundsException if balance is invalid ($0.00 or less0
        if (funds.compareTo(new BigDecimal("0.00")) < 1)
            throw new InsufficientFundsException(
                    "Unable to make change. Balance is less than $0.01.");

        // Calculate the max number of quarters that can be used to provide change for
        // the funds amount passed to method, add QUARTER and the number of them
        // calculated to the change map, then subtract the value of the QUARTERs
        // from the funds. Do the same for DIMEs, then NICKELs, then PENNIEs
        int quarters = funds.divide(QUARTER.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(QUARTER, quarters);
        funds = funds.subtract(QUARTER.getValue().multiply(BigDecimal.valueOf(quarters)));

        int dimes = funds.divide(DIME.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(DIME, dimes);
        funds = funds.subtract(DIME.getValue().multiply(BigDecimal.valueOf(dimes)));

        int nickels = funds.divide(NICKEL.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(NICKEL, nickels);
        funds = funds.subtract(NICKEL.getValue().multiply(BigDecimal.valueOf(nickels)));

        int pennies = funds.divide(PENNY.getValue(), RoundingMode.DOWN).intValue();
        coinChangeMap.put(PENNY, pennies);

        // Return map of Coins and the number of each
        return coinChangeMap;
    }
}
