/*
 * Copyright (C) 2011-2014, Peter Abeles. All Rights Reserved.
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

package georegression.struct;

/**
 * Matrix with a fixed size of 3 by 3.
 *
 * @author Peter Abeles
 */
public class Matrix3x3_F64 {
	public double a11,a12,a13;
	public double a21,a22,a23;
	public double a31,a32,a33;

	public void zero() {
		a11=a12=a13=a21=a22=a23=a31=a32=a33=0;
	}

	public void set( Matrix3x3_F64 b ) {
		a11=b.a11; a12=b.a12; a13=b.a13;
		a21=b.a21; a22=b.a22; a23=b.a23;
		a31=b.a31; a32=b.a32; a33=b.a33;
	}

	public void set( double a11, double a12, double a13 ,
					 double a21, double a22, double a23 ,
					 double a31, double a32, double a33 ) {
		this.a11=a11;
		this.a12=a12;
		this.a13=a13;
		this.a21=a21;
		this.a22=a22;
		this.a23=a23;
		this.a31=a31;
		this.a32=a32;
		this.a33=a33;
	}

	public void scale( double value ) {
		a11 *= value;
		a12 *= value;
		a13 *= value;
		a21 *= value;
		a22 *= value;
		a23 *= value;
		a31 *= value;
		a32 *= value;
		a33 *= value;
	}
}
