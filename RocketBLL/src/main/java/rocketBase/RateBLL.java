package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static final RateDomainModel RateDomainModel = null;
	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore)// throws RateException 
	{
		ArrayList<RateDomainModel> allRates = RateDAL.getAllRates();
		double interestRate = 0;
		//RateDomainModel e = new RateDomainModel();
		
		boolean isEmpty = true;
		/*for (RateDomainModel Rates : allRates){
			if (Rates.getiMinCreditScore() == GivenCreditScore){
				interestRate = Rates.getdInterestRate();
				isEmpty = false;
				break;
			}
		}*/
		try{
			//if (isEmpty == true){
				//throw new RateException(RateDomainModel);
			for (RateDomainModel Rates : allRates){
				if (Rates.getiMinCreditScore() == GivenCreditScore){
					interestRate = Rates.getdInterestRate();
					isEmpty = false;
					break;
				}
			}
			if (isEmpty == true){
				throw new RateException(RateDomainModel);
			}
		}catch(RateException e){
			System.out.println("No rate approved!");
		}
	
		return interestRate;
	}
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return FinanceLib.pmt(r, n, p, f, t);
	}
}
