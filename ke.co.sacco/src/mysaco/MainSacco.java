package mysaco;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author momondi
 */
import java.util.Scanner;

public class MainSacco {

    public static void main(String args[]) {

        System.out.println("Tracom Sacco management System Menu.\n1. Update phone number.\n2. Check balance.\n3. Addmember.\n4. Delete member.\n5. Check loan limit.\n6. Request loan limit\n7. Allmembers.\n8. Deposit/save\n9. Exit");
        //SaccoManagement object
        SaccoManagement newsacco = new SaccoManagement();

        //connecting to the database
        newsacco.openConnection();

        //user menu choices
        Scanner input = new Scanner(System.in);

        int choice = input.nextInt();

        switch (choice) {
            case 1:
                choice = 1;
                newsacco.updateMember();
                break;
            case 2:
                choice = 2;
                newsacco.getBalance();
                break;
            case 3:
                choice = 3;
                newsacco.addMember();
                break;
            case 4:
                choice = 4;
                newsacco.deleteMember();
                break;
            case 5:
                choice = 5;
                newsacco.loanLimit();
                break;
            case 6:
                choice = 6;
                newsacco.requestLoan();
            case 7:
                choice = 7;
                newsacco.allMembers();
                break;
            case 8:
                choice = 8;
                newsacco.DepositMember();
            default:
                choice = 9;
                System.out.println("Thank you for your time, ");
        }

    }

}
