package VendingMachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The {@code Coins} enum defines the names
 * of Coins and their BigDecimal values
 */
public enum Coins {
    QUARTER(new BigDecimal("0.25")),
    DIME(new BigDecimal("0.10")),
    NICKEL(new BigDecimal("0.05")),
    PENNY(new BigDecimal("0.01"));

    // Declare value variable
    private final BigDecimal value;

    /**
     * NoConstructor for Coins
     * @param value value of Coins
     */
    Coins(BigDecimal value) {
        this.value = value;
    }

    /**
     * Returns the value of a specific Coins enum
     * @return Coin value
     */
    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
