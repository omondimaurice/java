package mysaco;

/**
 *
 * @author momondi
 */
import java.sql.*;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class SaccoManagement {

    //scanner utility object for getting user input
    Scanner userinput;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String firstname;
    String lastname;
    String gender;
    String mobile_no;
    String memeber_since;

    //account_tbl data
    int deposit;
    int deduction;
    int loan;
    String loanaward_date;
    int balance = 0;

    private Connection db_conn;
    private Statement db_statement;

    /**
     * openConnection
     *
     * @return
     */
    public Connection openConnection() {
        if (db_conn == null) {

            String url_path = "jdbc:mysql://localhost:3306/saccodatabase?zeroDateTimeBehavior=convertToNull";
            String database_name = "saccodatabase";

            String driver_name = "com.mysql.jdbc.Driver";
            String user_name = "root";
            String user_password = "Mamakwaru@1";

            try {
                Class.forName(driver_name);
                this.db_conn = (Connection) DriverManager.getConnection(url_path, user_name, user_password);
                System.out.println("Connection successful!");

            } catch (Exception e) {

                System.out.println("Incorrect password or username!");
            }
        }
        return db_conn;
    }

    public void DepositMember() {

        userinput = new Scanner(System.in);
        try {
            String insertQuery = "insert into account_tbl(deposit,deduction,balance)" + "values(?,?,?)";

            PreparedStatement insert = db_conn.prepareCall(insertQuery);

            //getting user input
            System.out.print("Enter todays savings amount : ");
            try {
                deposit = userinput.nextInt();
            } catch (Exception e) {
                System.out.println("Enter a valid value !");
            }

            System.out.print("Enter any deductions  0 if there is no deduction : ");
            try {
                deduction = userinput.nextInt();
            } catch (Exception e) {
                System.out.println(" enter '0' or valid amount !");
            }

            balance = balance + deposit - deduction;

            insert.setInt(1, deposit);
            insert.setInt(2, deduction);
            insert.setInt(3, balance);
            insert.executeUpdate();

            System.out.println("Your current tracom savings account  balance is Ksh. :" + balance);

        } catch (Exception e) {

            System.err.println("We are unable to process your request....! ");

            System.err.println(e.getMessage());

        }
    }

    /**
     * adds member to the sacco
     */
    public void addMember() {

        userinput = new Scanner(System.in);
        try {
            String insertQuery = "insert into members_tbl(firstname,lastname,gender,mobile_no,memeber_since)" + "values(?,?,?,?,?)";

            PreparedStatement insert = db_conn.prepareCall(insertQuery);

            // Statement statement = db_conn.createStatement();
            //getting user input
            System.out.print("Enter first name : ");
            firstname = userinput.next();
            System.out.print("Enter last name: ");
            lastname = userinput.next();
            System.out.print("gender : ");
            gender = userinput.next();
            System.out.println("your telephone numeber");
            mobile_no = userinput.next();
            System.out.print("Enter date of registration yyy-mm-dd : ");
            memeber_since = userinput.next();

            insert.setString(1, firstname);
            insert.setString(2, lastname);
            insert.setString(3, gender);
            insert.setString(4, mobile_no);
            insert.setString(5, memeber_since);
            insert.executeUpdate();

            System.out.println("Welcome to tracom sacco " + firstname + " your are now one of us!");

        } catch (Exception e) {

            System.err.println("An error occured! ");

            System.err.println(e.getMessage());

        }
    }

    /**
     * shows all members
     */
    public void allMembers() {

        try {

            String insertQuery = "select * from members_tbl";

            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            ResultSet rs = insert.executeQuery(insertQuery);

            // extract data from the ResultSet
            while (rs.next()) {
                int member_id = rs.getInt("member_id");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");
                gender = rs.getString("gender");
                mobile_no = rs.getString("mobile_no");
                memeber_since = rs.getString("memeber_since");

                System.out.format("%s, %s, %s, %s, %s,\n", member_id, firstname, lastname, mobile_no, memeber_since);
            }
        } catch (Exception e) {
            System.out.println("We are sorry you cant view all members....");
        }
    }

    /**
     * updates member detail
     *
     */
    public void updateMember() {
        userinput = new Scanner(System.in);
        System.out.println("Enter member id you want to update: ");
        int member_id = userinput.nextInt();
        System.out.println("Enter new mobile number : ");
        mobile_no = userinput.next();

        try {

            String insertQuery = "update members_tbl set mobile_no = ? where member_id = ?";
            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            insert.setString(1, mobile_no);
            insert.setInt(2, member_id);
            insert.executeUpdate();
        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     *
     */
    public void deleteMember() {
        System.out.println("Enter the id of member you want to delete: ");
        userinput = new Scanner(System.in);
        int member_id = userinput.nextInt();
        try {

            String insertQuery = "delete from members_tbl where member_id = ?";
            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            insert.setInt(1, member_id);
            int del = insert.executeUpdate();
            System.out.println("deleted record is  : " + del);

        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     *
     */
    public void requestLoan() {

        System.out.println("Enter member_id : ");
        userinput = new Scanner(System.in);
        int id_choice = userinput.nextInt();

        try {
            String insertQuery = "select *from account_tbl where account_id=?";

            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            insert.setInt(1, id_choice);
            ResultSet rs = insert.executeQuery();

            while (rs.next()) {
                int account_id = rs.getInt("account_id");
                deposit = rs.getInt("deposit");
                deduction = rs.getInt("deduction");
                balance = rs.getInt("balance");

                int loan = balance * 3;
                if (balance >= 5000) {
                    System.out.println("You have been awarded Tracom sacco loan of Ksh. " + loan + " on" + dtf.format(now));

                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     * checks loan limit
     *
     */
    public void loanLimit() {

        System.out.println("Enter member_id : ");
        userinput = new Scanner(System.in);
        int id_choice = userinput.nextInt();

        try {
            String insertQuery = "select *from account_tbl where account_id=?";

            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            insert.setInt(1, id_choice);
            ResultSet rs = insert.executeQuery();

            while (rs.next()) {
                int account_id = rs.getInt("account_id");
                deposit = rs.getInt("deposit");
                deduction = rs.getInt("deduction");
                balance = rs.getInt("balance");

                if (balance < 5000) {
                    System.out.println("Your loan limit is Ksh. 0 ");
                } else {
                    int loan_limit = balance * 3;
                    System.out.println(" your loan limit is Ksh. " + loan_limit + " on " + dtf.format(now));
                }

            }

        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     *
     */
    public void getBalance() {
        System.out.println("Enter member_id : ");
        userinput = new Scanner(System.in);
        int id_choice = userinput.nextInt();

        try {
            String insertQuery = "select *from account_tbl where account_id=?";

            PreparedStatement insert = db_conn.prepareStatement(insertQuery);
            insert.setInt(1, id_choice);
            ResultSet rs = insert.executeQuery();

            while (rs.next()) {
                int account_id = rs.getInt("account_id");
                deposit = rs.getInt("deposit");
                deduction = rs.getInt("deduction");
                balance = rs.getInt("balance");

                System.out.format(" your current balance is %s,\n", balance + " on " + dtf.format(now));

            }

        } catch (Exception e) {

            System.out.println("An error occurred");
        }
    }
}
