import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		
		int kOrder = 15;
		int kTime = 40;
		
		Filter fir = new Filter(kOrder, kTime, FilterInfo.koeffs);
		
		fir.quantizeCoefficients();
		fir.step(2, kTime);
		fir.quantizeSamples();
		fir.filter();
//		fir.output();
//		fir.filter2();
//		fir.getSamples();
		
		
		
		
	}

}
