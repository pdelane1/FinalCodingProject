package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;
import rocketDomain.RateDomainModel;


public class RocketHub extends Hub {

	private static final RateDomainModel RateDomainModel = null;
	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
		
			//	TODO - RocketHub.messageReceived

			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			int CreditScore = lq.getiCreditScore();
			int years = lq.getiTerm();
			double presentValue = lq.getdAmount();
			double futureValue = 0;
			boolean payment = true;
			double Rate = RateBLL.getRate(CreditScore);
			try{
				lq.setdRate(Rate);
				if (lq.getdRate() == 0.0){
					throw new RateException(RateDomainModel);
				}
			}
			catch(RateException e){
				e.printStackTrace();
			}
			finally{
				
			}
			double period = 12*years;
			RateBLL.getPayment(Rate, period, presentValue, futureValue, payment);
			
			sendToAll(lq);
		}
	}
}
