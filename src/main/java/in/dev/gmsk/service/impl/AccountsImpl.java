package in.dev.gmsk.service.impl;

import in.dev.gmsk.service.AccountService;
import in.dev.gmsk.util.JDBCConnection;
import in.dev.gmsk.util.model.Accounts;
import in.dev.gmsk.util.model.FeeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class AccountsImpl implements AccountService {

    @Override
    public Stream<Accounts> findByAll(JDBCConnection jdbcConnection) {
        try {
            List<Accounts> accountsList = new ArrayList<>();
            String query = "SELECT av.`locationId`, av.`vouchertype`, ah.`accountsheadingname`, av.`voucherdate`, avh.`amount` FROM `accounts_heading` ah LEFT JOIN `accounts_voucher_heading`  avh ON avh.`headingId` = ah.`id` JOIN `accounts_voucher` av ON av.`id` = `voucherId` ORDER BY ah.`id`";
            Connection connection = JDBCConnection.getConnection(jdbcConnection);
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Accounts accounts = new Accounts();
                accounts.setLocationId(rs.getString("locationId"));
                accounts.setVoucherType(rs.getString("vouchertype"));
                accounts.setAccountsHeadingName(rs.getString("accountsheadingname"));
                accounts.setVoucherDate(LocalDate.parse(rs.getString("voucherdate")));
                accounts.setAmount(rs.getDouble("amount"));
                accountsList.add(accounts);
            }
            rs.close();
            stmt.close();
            connection.close();

            return accountsList.parallelStream().distinct();
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occer \{e}");
        }
    }

    private List<FeeModel> getStudentConcessionDetails(FeeModel model, Connection connection) {
        List<FeeModel> returnList = new ArrayList<>();
        try {
            PreparedStatement concessionStmt = connection.prepareStatement("");
            concessionStmt.setString(1, model.getStudentId());
            concessionStmt.setString(2, model.getLocationId());
            concessionStmt.setString(3, model.getAccYearId());
            ResultSet concessionRS = concessionStmt.executeQuery();
            while (concessionRS.next()) {
                FeeModel concessionData = new FeeModel();
                concessionData.setConcessionId(concessionRS.getInt("id"));
                concessionData.setConcessionTypeId(concessionRS.getInt("concessionTypeId"));
                concessionData.setConcessionPercentage(concessionRS.getDouble("concessionpercentage"));
                returnList.add(concessionData);
            }
            concessionRS.close();
            concessionRS.close();
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occer \{e}");
        }
        return returnList;
    }

    ;

    private List<FeeModel> getStudentRespitesDetails(FeeModel model, List<FeeModel> list, Connection connection) {
        try {
            PreparedStatement recpstmt = connection.prepareStatement("");
            recpstmt.setString(1, model.getLocationId());
            recpstmt.setString(2, model.getAccYearId());
            recpstmt.setString(3, model.getStudentId());
            ResultSet recrs = recpstmt.executeQuery();
            while (recrs.next()) {
                FeeModel feeModel = new FeeModel();
                if (recrs.getString(1) != null) {
                    feeModel.setReceiptId(recrs.getString(1));
                }
                list.add(feeModel);
            }
            recrs.close();
            recpstmt.close();
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occer \{e}");
        }
        return list;
    }

    private List<FeeModel> getDefaulterFeeCalculation(FeeModel model, List<FeeModel> dataList, JDBCConnection jdbcConnection) {
        List<FeeModel> list = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT stu.`id`,scd.`classid`,scd.`specializationid`,cls.`position`,scd.`sectionid`,stu.`firstname`,stu.`lastname`,CONCAT(stu.`firstname`,' ',stu.`lastname`) AS studentname,stu.`admissionno`,stu.`admissiondate`,stu.`registrationno`,stu.`gender`,stu.`dateofbirth`,stu.`bloodgroup`,stu.`casteandcategory`,stu.`studentmobileno`,stu.`studentemailid`,stu.`mothertongue`,stu.`aadharno`,stu.`nationality`,stu.`studenttype`,scd.`status`,CASE WHEN scd.`rollno`=0 THEN '' ELSE scd.`rollno` END AS rollno,cls.`classname`,CASE WHEN spec.`specializationname` IS NULL THEN '' ELSE spec.`specializationname` END AS specializationname,sec.`sectionname`,CASE WHEN scd.`image` IS NULL THEN '' ELSE scd.`image` END AS image,spc.`relationship`,sp.`fathername`,sp.`fathermobileno`,sp.`fatheremailid`,sp.`fatheroccupation`,sp.`fatherannualincome`,sp.`mothername`,sp.`mothermobileno`,sp.`motheremailid`,sp.`motheroccupation`,sp.`motherannualincome`,sp.`guardianname`,sp.`guardianmobileno`,sp.`guardianemailid`,sp.`guardianoccupation`,sp.`guardianannualincome`,sp.`permanentaddress`,sp.`presentaddress`,sa.`acadamic_year`,sr.`religionname`,sl.`Location_Name`, CASE WHEN ts.`studentId` IS NULL THEN 'N' ELSE ts.`studentId` END AS transportReq, scd.`locationid`, scd.`accyearid`, stu.`status` FROM `student_classdetails` scd JOIN `student_registration` stu ON stu.`id`=scd.`studentid` JOIN `student_parent_childrenrelationship` spc ON spc.`studentid`=scd.`studentid` JOIN `student_parent` sp ON sp.`id`=spc.`parentid` JOIN `setup_acadamicyear` sa ON sa.`acadamic_id`=scd.`accyearid` JOIN `setup_religion` sr ON sr.`id`=stu.`religionid` JOIN `setup_location` sl ON sl.`Location_Id`=scd.`locationid` JOIN `setup_class` cls ON cls.`id`=scd.`classid` AND cls.`locationId`=scd.`locationid` LEFT JOIN `setup_specialization` spec ON spec.`id`=scd.`specializationid` AND spec.`locationId`=scd.`locationid` JOIN `setup_section` sec ON sec.`id`=scd.`sectionid` AND sec.`locationId`=stu.`locationid` LEFT JOIN `transport_student` ts ON ts.`studentId`=scd.`studentid` AND ts.`locationId`=scd.`locationid` AND ts.`accyearId`=scd.`accyearid` ORDER BY cls.`position`, specializationname, studentname";
            Connection connection = JDBCConnection.getConnection(jdbcConnection);
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("id");
                String classId = rs.getString("classid");
                String specId = rs.getString("specializationid");

                var columnName = rs.getString("studenttype").trim().equalsIgnoreCase("New") ? "newFeeAmount" : "feeAmount";
                model.setStudentId(rs.getString("id"));

                int concessionType = 0, concessionId = 0;
                double concessionPercentage = 0.0;
                String receiptId = dataList.get(1).getReceiptId();
                for (FeeModel i : dataList) {
                    concessionType = i.getConcessionTypeId();
                    concessionId = i.getConcessionId();
                    concessionPercentage = i.getConcessionPercentage();
                    // receiptId = i.getReceiptId();
                }

                extracted(model, concessionType, studentId, columnName, classId, specId, connection, concessionId, concessionPercentage, receiptId, rs, list);
            }
        } catch (Exception e) {
            throw new RuntimeException(STR."Error occurred = \{e}");
        }
        return list;
    }

    private void extracted(FeeModel model, int concessionType, String studentId, String columnName, String classId, String specId, Connection connection, int concessionId, double concessionPercentage, String receiptId, ResultSet rs, List<FeeModel> list) throws SQLException {
        if (concessionType != 1) {
            FeeModel noofobj = getFeeTermAmountDetails(model.getTermId(), studentId, columnName, classId, specId, model.getAccYearId(), model.getLocationId(), connection, concessionId, concessionType, concessionPercentage, receiptId);

            if (!noofobj.getFeeList().isEmpty()) {
                FeeModel obj = new FeeModel();
                obj.setAccYearName(rs.getString("acadamic_year"));
                obj.setLocationName(rs.getString("Location_Name"));
                obj.setStudentName(rs.getString("studentname"));
                obj.setAdmissionNo(rs.getString("admissionno"));
                obj.setRegistrationNo(rs.getString("registrationno"));
                obj.setClassName(rs.getString("classname"));
                obj.setSpecName(rs.getString("specializationname"));
                obj.setSectionName(rs.getString("sectionname"));
                obj.setTotalFee(noofobj.getTotalFee());
                obj.setTotalConcession(noofobj.getTotalConcession());
                obj.setTotalCollection(noofobj.getTotalCollection());
                obj.setTotalBalance(noofobj.getTotalBalance());
                obj.setTotalDeduction(noofobj.getTotalDeduction());

                double totalBalanceAmount = noofobj.getFeeList().parallelStream().mapToDouble(num -> Double.parseDouble(num.getBalanceAmount())).sum();

                if (totalBalanceAmount != 0x0.0p0) { // 0.0E00 -> sci,  0x0.0p0 -> hex
                    obj.setFeeList(noofobj.getFeeList());
                    list.add(obj);
                }
            }
        }
    }

    @Override
    public Stream<FeeModel> getDefaulterFeeList(FeeModel model, JDBCConnection jdbcConnection) {
        try {
            CompletableFuture<List<FeeModel>> future = CompletableFuture
                    .supplyAsync(() -> getStudentConcessionDetails(model, JDBCConnection.getConnection(jdbcConnection)))
                    .thenApplyAsync((concessionList) -> getStudentRespitesDetails(model, concessionList, JDBCConnection.getConnection(jdbcConnection)))
                    .thenApplyAsync((list) -> (getDefaulterFeeCalculation(model, list, jdbcConnection)));

            return future.get().parallelStream().distinct();
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occurred.../{e}");
        }
    }

    private FeeModel getFeeTermAmountDetails(String termId, String studentId, String columnname, String classId, String specId, String accYearId, String locationId, Connection connection, int concessionid, int concessiontype, double concessionpercentage, String receiptId) {

        FeeModel obj = new FeeModel();
        List<FeeModel> list1 = new ArrayList<>();
        double totalfee = 0.0, totalconcession = 0.0, totalcollection = 0.0, totalbalance = 0.0, totaldeduction = 0.0;

        try {
            PreparedStatement pstmt = connection.prepareStatement(STR."SELECT cfs.`id`,cfs.`termId` FROM `fee_class_feesetup` cfs WHERE cfs.`locationId`=? AND cfs.`accyearId`=? AND cfs.`classId`=? AND cfs.`specId`=? AND `termId` IN('\{termId.replaceAll(",", "','")}')");
            pstmt.setString(1, locationId);
            pstmt.setString(2, accYearId);
            pstmt.setString(3, classId);
            pstmt.setString(4, specId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                FeeModel obj1 = new FeeModel();
                obj1.setFeeAmount(String.format("%.2f", rs.getDouble("feeAmount")));
                totalfee += rs.getDouble("feeAmount");

                if (concessiontype == 1) {

                    totalconcession += rs.getDouble("feeAmount");
                    obj1.setConcessionAmount(String.format("%.2f", rs.getDouble("feeAmount")));

                } else if (concessiontype == 2) {

                    double amount = rs.getDouble("feeAmount");
                    double finalamount = (amount * (concessionpercentage / 100));
                    obj1.setConcessionAmount(String.format("%.2f", finalamount));
                    totalconcession += finalamount;

                } else if (concessiontype == 3) {
                    double amount = rs.getDouble("concessionamount");
                    obj1.setConcessionAmount(String.format("%.2f", amount));
                    totalconcession += amount;

                } else {
                    obj1.setConcessionAmount("0");
                }

                double collectionamount = rs.getDouble("collectionamount");
                obj1.setCollectionAmount(String.format("%.2f", collectionamount));
                totalcollection += collectionamount;

                double deductionamount = rs.getDouble("deductionamount");
                obj1.setConcessionDeduction(String.format("%.2f", deductionamount));
                totaldeduction += deductionamount;

                double balanceamount = 0.0;

                balanceamount = rs.getDouble("feeAmount") - Double.parseDouble(obj1.getConcessionAmount()) - collectionamount;
                totalbalance += balanceamount;

                obj1.setBalanceAmount(String.format("%.2f", balanceamount));
                obj1.setPayingAmount(String.format("%.2f", balanceamount));

                list1.add(obj1);
            }

            obj.setTotalFee(String.format("%.2f", totalfee));
            obj.setTotalConcession(String.format("%.2f", totalconcession));
            obj.setTotalCollection(String.format("%.2f", totalcollection));
            obj.setTotalBalance(String.format("%.2f", totalbalance));
            obj.setTotalDeduction(String.format("%.2f", totaldeduction));
            obj.setFeeList(list1);
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occurred.../{e}");
        }
        return obj;
    }
}
