import java.util.ArrayList;
import java.util.Arrays;

public class Filter {
	
	private int kOrder;
	private int kTime;
	private short[] quantizedCoefficients;
	private double[] coeffs;
	
	private final int kMaximum = 32768;
	private final float kImpulse = (float)(1 - Math.pow(2, -15));
	private final float kStep = 0.8f;
	
	private float[] m_samples;
	private short[] quantizedSamples;
	private float[] m_result;
	
	public Filter(int kOrder, int kTime, double[] coeffs) {
		this.kOrder = kOrder;
		this.kTime = kTime;
		this.coeffs = coeffs;
		quantizedCoefficients = new short[kOrder + 1];
		quantizedSamples = new short[kTime];
		this.m_result = new float[kOrder + kTime];
		
	}
	
	public void impulse(int zeros, int kTime) {
		m_samples = new float[kTime];
		m_samples[zeros] = this.kImpulse;
	}
	
	public void step(int zeros, int kTime) {
		m_samples = new float[kTime];
		for (int i = 0; i < zeros; i++)
			m_samples[i] = 0;
		for (int i = zeros; i < m_samples.length; i++)
			m_samples[i] = this.kStep;
	}
	
	public void output() {
		for (int i = 0; i < m_result.length; i++) {
			System.out.println(String.format("%.15f", (Float)m_result[i]));
		}
	}
	
	public void quantizeCoefficients() {
		for (int i = 0; i < this.kOrder + 1; i++) {
			quantizedCoefficients[i] = (short)(this.coeffs[i] * kMaximum);
		}
	}
	
	public void quantizeSamples() {
		for (int i = 0; i < this.kTime; i++) {
			quantizedSamples[i] = (short)(this.m_samples[i] * kMaximum);
		}
	}
	
	public void convertResults(short[] filteringResult) {
		for (int i = 0; i < this.kTime + this.kOrder; i++) {
			this.m_result[i] = (float)filteringResult[i] / kMaximum;
		}
	}
	
	public void filter() {
		int sum;
		short[] filteringResult = new short[this.kOrder + this.kTime];
		
	    for(int i = 0; i < this.kTime + this.kOrder; i++) {
	            sum = 0;
	            for(int j = 0; j < this.kOrder + 1; j++) {
	                if(i - j >= 0 && i - j < this.kTime) {
	                    sum += this.quantizedCoefficients[j] * this.quantizedSamples[i - j];
	                    int t1 = this.quantizedCoefficients[j] * this.quantizedSamples[i - j];
	                    int t2 = (int)this.quantizedCoefficients[j] * this.quantizedSamples[i - j];
	                    System.out.println(t1 == t2);
	                }
	            }
	            filteringResult[i] += (short)(sum / kMaximum); //делим на 6, чтобы не было перегруза на пересечении фильтров
	        }
	    convertResults(filteringResult);
	 }
	
	public void filter2() {
		float sum;
		float[] result = new float[5];
		float[] x = {0.1f, 0.2f, 0.3f};
		float[] h = {0.4f, 0.5f, 0.6f};
		
	    for(int i = 0; i < x.length + h.length - 1; i++) {
	            sum = 0;
	            for(int j = 0; j < h.length; j++) {
	                if(i - j >= 0 && i - j < x.length)
	                    sum += h[j] * x[i - j];
	            }
	            result[i] += sum; //делим на 6, чтобы не было перегруза на пересечении фильтров
	            System.out.println(result[i]);
	        }
	 }
	
//	public float[] getResult() {
//		return m_result;
//	}
	
	public void getSamples() {
		for (int i = 0; i < m_samples.length; i++) {
			System.out.println(String.format("%s", (Float)m_samples[i]));
		}
	}
}
