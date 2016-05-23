package rocket.app.view;



import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import eNums.eAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketCode.Action;
import rocketData.LoanRequest;


public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	@FXML
	private TextField txtIncome;
	@FXML
	private TextField txtExpenses;
	@FXML
	private TextField txtCreditScore;
	@FXML
	private TextField txtHouseCost;
	@FXML
	private Label Income;
	@FXML
	private Label Expenses;
	@FXML
	private Label CreditScore;
	@FXML
	private Label HouseCost;
	@FXML
	private Label lblMortgagePayment;
	@FXML
	private Label Term;
	@FXML
	private ComboBox cmbTerm;
	@FXML
	private Button btnPayment;
	@FXML
	private Label ErrorMessage;
	@FXML
	private Label Result;
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		ObservableList<String> terms = FXCollections.observableArrayList("15 Years of Payment", "30 Years of Payment");
		cmbTerm.setItems(terms);
		//Found an example of using ObservableStrings with ComboBox online
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		
		//Credit Score in double
		String strCreditScore = txtCreditScore.getText();
		int CreditScore = Integer.getInteger(strCreditScore);
		lq.setiCreditScore(CreditScore);
		
		//House Cost in douoble
		String strHouseCost = txtHouseCost.getText();
		double HouseCost = Double.parseDouble(strHouseCost);
		lq.setdAmount(HouseCost);
		
		//Term
		if (cmbTerm.getValue()=="15 years of payment"){
			lq.setiTerm(12*15);
		}
		else if(cmbTerm.getValue()=="30 years of payment"){
			lq.setiTerm(12*30);
		}
		//double income
		String strIncome = txtIncome.getText();
		double Income = Double.parseDouble(strIncome);
		//double expenses
		String strExpenses = txtExpenses.getText();
		double Expenses = Double.parseDouble(strExpenses);
		
		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
		
		HandleLoanRequestDetails(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		double dPayment = lRequest.getdPayment();
		double Income = lRequest.getdIncome();
		double Expenses = lRequest.getdExpenses();
		double MaximumMonthlyPayment;
		NumberFormat formatter = new DecimalFormat("#0.00");
		if(Income*.28 > Income*.36 - Expenses){
			MaximumMonthlyPayment = Income*.36 - Expenses;
			String MaximumMonthlyPaymentString = Double.toString(MaximumMonthlyPayment);
			Result.setText(formatter.format(MaximumMonthlyPaymentString));
		}
		else{
			MaximumMonthlyPayment = Income*.28;
			String MaximumMonthlyPaymentString = Double.toString(MaximumMonthlyPayment);
			Result.setText(formatter.format(MaximumMonthlyPaymentString));
		}
		
	}
}
