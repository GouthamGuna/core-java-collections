package in.dev.gmsk.util.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class FeeModel {

    private String sno;
    private String locationId;
    private String accYearId;
    private String feeTypeId;
    private String feeTypeName;
    private String feeId;
    private String feeName;
    private String termId;
    private String feeAmount;
    private String newFeeAmount;
    private String deleteId;
    private String classId;
    private String className;
    private String specId;
    private String specName;
    private String sectionId;
    private String sectionName;
    private String studentId;
    private String studentName;
    private String admissionNo;
    private String registrationNo;
    private String feeConcessionId;
    private double concessionPercentage;
    private String file;
    private int concessionId;
    private int concessionTypeId;
    private String hConcessionTypeId;
    private String concessionDetailsId;
    private String concessionAmount;
    private String gender;
    private String image;
    private String totalFee;
    private String totalConcession;
    private String balanceAmount;
    private String payingAmount;
    private String totalCollection;
    private String totalBalance;
    private String paidExcessAmount;
    private String totalTermAmount;
    private String discountAmount;
    private String grandTotal;
    private String cashAmount;
    private String cardAmount;
    private String receiptNo;
    private String openingBalance;
    private String excessDeduction;
    private String deductionReceiptNo;
    private String paidDate;
    private String collectionAmount;
    private String concessionDeduction;
    private String totalDeduction;
    private String totalOpeningBalance;
    private String paidOpeningBalance;
    private String receiptId;
    private String fatherName;
    private String accYearName;
    private String username;
    private String remainingOpeningBalance;
    private String primaryId;
    private double totalAmount;
    private String locationName;
    private List<FeeModel> feeList;
}
