/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.eiafr.cojac.unit.replace;

import java.util.Arrays;
import static junit.framework.Assert.assertEquals;
import org.junit.Assert;

/**
 *
 * @author romain
 */
public class FloatProxy {
	
    public static void staticFieldDoubleAccess() throws Exception {
		FloatProxyNotInstrumented.staticDouble = 25.5;
		double r = FloatProxyNotInstrumented.staticDouble;
		assertEquals(25.5, r);
    }
	
	public static void staticFieldFloatAccess() throws Exception {
		FloatProxyNotInstrumented.staticFloat = 64.6f;
		float r = FloatProxyNotInstrumented.staticFloat;
		assertEquals(64.6f, r);
    }
	
	public static void instanceFieldDoubleAccess() throws Exception{
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented();
		assertEquals(0.0, fpni.doubleField);
		fpni.doubleField = 25.8;
		assertEquals(25.8, fpni.doubleField);
	}
	
	public static void instanceFieldFloatAccess() throws Exception{
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented();
		assertEquals(0.0f, fpni.floatField);
		fpni.floatField = 25.8f;
		assertEquals(25.8f, fpni.floatField);
	}
	
	public static void objectConstructor() throws Exception{
		double d1 = 5.4;
		long l1 = 512;
		float f1 = 432.2f;
		double d2 = 423.78;
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented(d1, l1, f1, d2);
		assertEquals(d1, fpni.d1);
		assertEquals(l1, fpni.l1);
		assertEquals(f1, fpni.f1);
		assertEquals(d2, fpni.d2);
	}
	
	public static void instanceMethod() throws Exception{
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented();
		short s1 = 5;
		long l1 = 5423;
		double d1 = 54.5;
		double d2 = 876.9;
		byte b1 = 2;
		char c1 = 'A';
		float f1 = 243.2f;
		fpni.instanceMethod(s1, l1, d1, d2, b1, true, c1, f1);
		assertEquals(d1, fpni.d1);
		assertEquals(l1, fpni.l1);
		assertEquals(f1, fpni.f1);
		assertEquals(d2, fpni.d2);
	}
	
	public static void staticMethod() throws Exception{
		short s1 = 5;
		long l1 = 5423;
		double d1 = 54.5;
		double d2 = 876.9;
		byte b1 = 2;
		char c1 = 'A';
		float f1 = 243.2f;
		FloatProxyNotInstrumented.staticMethod(l1, d1, s1, d2, b1, true, c1, f1);
		assertEquals(d1, FloatProxyNotInstrumented.static_d1);
		assertEquals(l1, FloatProxyNotInstrumented.static_l1);
		assertEquals(f1, FloatProxyNotInstrumented.static_f1);
		assertEquals(d2, FloatProxyNotInstrumented.static_d2);
	}
	
	public static void oneDimArrayPassingByMethod() throws Exception{ // TODO - double
		float array[] = new float[] {12.413f, 6.5f, 8.12f, 654.5f};
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented();
		float r[] = fpni.oneDimArrayPassing(array);
		Assert.assertTrue(Arrays.equals(r, array));
	}
	
	public static void multiDimArrayPassingByMethod() throws Exception{ // TODO - double
		float array[][] = new float[][] {{12.413f, 6.5f}, {54.212f, 53.123f}};
		FloatProxyNotInstrumented fpni = new FloatProxyNotInstrumented();
		float[][] r = fpni.multiDimArrayPassing(array);
		Assert.assertTrue(Arrays.deepEquals(r, array));
	}
	
	public static void oneDimArrayField() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void multiDimArrayField() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void castedNumberPassingByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void castedNumberReturningByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void castedObjectPassingByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void castedObjectReturningByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void oneDimeArrayCastedObjectPassingByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public static void oneDimeArrayCastedObjectReturningByMethod() throws Exception{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
}