package VendingMachine.ui;

import java.math.BigDecimal;

public interface UserIO {
    void print(String message);

    void printf(int num, String str, BigDecimal bigDecimal);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);
}
