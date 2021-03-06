/*
 * Copyright (C) 2011-2015, Peter Abeles. All Rights Reserved.
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

package georegression.struct.shapes;

import georegression.struct.point.Point3D_F64;

import java.io.Serializable;

/**
 * Triangle in 3D space.  Described by 3 vertices/points.
 *
 * @author Peter Abeles
 */
public class Triangle3D_F64 implements Serializable {
	public Point3D_F64 v0 = new Point3D_F64();
	public Point3D_F64 v1 = new Point3D_F64();
	public Point3D_F64 v2 = new Point3D_F64();

	public Triangle3D_F64(Point3D_F64 v0, Point3D_F64 v1, Point3D_F64 v2) {
		this.v0.set(v0);
		this.v1.set(v1);
		this.v2.set(v2);
	}

	public Triangle3D_F64(double x0, double y0, double z0,
						  double x1, double y1, double z1,
						  double x2, double y2, double z2) {
		this.v0.set(x0,y0,z0);
		this.v1.set(x1,y1,z1);
		this.v2.set(x2,y2,z2);
	}

	public Triangle3D_F64( Triangle3D_F64 orig ) {
		set(orig);
	}

	public Triangle3D_F64() {
	}

	public void set( Triangle3D_F64 orig ) {
		v0.set(orig.v0);
		v1.set(orig.v1);
		v2.set(orig.v2);
	}

	public void set(double x0, double y0, double z0,
					double x1, double y1, double z1,
					double x2, double y2, double z2) {
		this.v0.set(x0,y0,z0);
		this.v1.set(x1,y1,z1);
		this.v2.set(x2,y2,z2);
	}

	public Point3D_F64 getV0() {
		return v0;
	}

	public void setV0(Point3D_F64 v0) {
		this.v0 = v0;
	}

	public Point3D_F64 getV1() {
		return v1;
	}

	public void setV1(Point3D_F64 v1) {
		this.v1 = v1;
	}

	public Point3D_F64 getV2() {
		return v2;
	}

	public void setV2(Point3D_F64 v2) {
		this.v2 = v2;
	}

	public Triangle3D_F64 copy() {
		return new Triangle3D_F64(this);
	}
}
