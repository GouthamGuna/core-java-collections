package in.dev.gmsk.util.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class Accounts {

    private String locationId;
    private String voucherType;
    private String accountsHeadingName;
    private LocalDate voucherDate;
    private double amount;
}
