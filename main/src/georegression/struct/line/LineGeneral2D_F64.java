/*
 * Copyright (C) 2011-2019, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Geometric Regression Library (GeoRegression).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package georegression.struct.line;

import georegression.geometry.UtilLine2D_F64;
import org.ejml.FancyPrint;

import java.io.Serializable;

/**
 * <p>
 * Represents the line using three parameters such that any point on the line obeys the
 * following formula, A*x + B*y + C = 0.  Any 2D line can be represented using this notation.
 * This formulation is also known as standard and implicit.  The slope is -A/B.
 * </p>
 * <p>
 * If it is said the line is normalized that refers to it being scaled such that A*A + B*B = 1.  To
 * normalize a line call {@link #normalize()}.  After normalization several operations become less expensive.
 * </p>
 *
 * @see UtilLine2D_F64
 *
 * @author Peter Abeles
 */
public class LineGeneral2D_F64 implements Serializable {

	/**
	 * Coefficients which define the line.
	 */
	public double A,B,C;

	/**
	 * A*x + B*y + C = 0
	 *
	 * @param a parameter A
	 * @param b parameter B
	 * @param c parameter C
	 */
	public LineGeneral2D_F64(double a, double b, double c) {
		set(a,b,c);
	}

	public LineGeneral2D_F64(LineGeneral2D_F64 line ) {
		set(line);
	}

	public LineGeneral2D_F64() {
	}

	public double getA() {
		return A;
	}

	public double getB() {
		return B;
	}

	public double getC() {
		return C;
	}

	public void set( LineGeneral2D_F64 original ) {
		this.A = original.A;
		this.B = original.B;
		this.C = original.C;
	}

	/**
	 * A*x + B*y + C = 0
	 *
	 * @param a parameter A
	 * @param b parameter B
	 * @param c parameter C
	 */
	public void set(double a, double b, double c) {
		this.A = a;
		this.B = b;
		this.C = c;
	}

	public void setA(double a) {
		A = a;
	}

	public void setB(double b) {
		B = b;
	}

	public void setC(double c) {
		C = c;
	}

	/**
	 * Ensures that A*A + B*B == 1
	 */
	public void normalize() {
		double d = Math.sqrt(A*A + B*B);
		A /= d;
		B /= d;
		C /= d;
	}

	/**
	 * Returns the result of A*x + B*y + C.  If the line is normalized then this is also the signed distance
	 * away from the line.
	 *
	 * @param x x-coordinate of a point
	 * @param y y-coordinate of a point
	 * @return result of line equation
	 */
	public double evaluate( double x , double y ) {
		return A*x + B*y + C;
	}

	public LineGeneral2D_F64 copy() {
		return new LineGeneral2D_F64(this);
	}

	@Override
	public String toString() {
		FancyPrint f = new FancyPrint();
		return getClass().getSimpleName()+"{ A="+f.s(A)+" B="+f.s(B)+" C="+f.s(C)+" }";
	}

	@Override
	public boolean equals(Object obj) {
		try {
			LineGeneral2D_F64 o = (LineGeneral2D_F64) obj;
			return A==o.A && B ==o.B && C==o.C;
		} catch( RuntimeException e ) {
			return super.equals(obj);
		}
	}
}
