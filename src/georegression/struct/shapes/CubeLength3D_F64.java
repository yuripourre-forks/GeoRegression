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

package georegression.struct.shapes;

import georegression.struct.point.Point3D_F64;

/**
 * An axis aligned cube in 3D that is specified by a point (x0,y0,z0), and its lengthX, lengthY, and lengthZ.
 * The point 'p' is the point which is closest to the corner.  The corner which is farthest away from 'p'
 * is (x0+lengthX , y0+lengthY, z0+lengthZ)
 */
public class CubeLength3D_F64 {
	/**
	 * Point which defines the corner closest to the origin  TODO this is incorrect
	 */
	public Point3D_F64 p = new Point3D_F64();
	/**
	 * The length of each size along their respective axises
	 */
	public double lengthX,lengthY,lengthZ;

	public CubeLength3D_F64(double x0, double y0, double z0, double lengthX, double lengthY, double lengthZ) {
		this.p.set(x0,y0,z0);
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
	}

	public CubeLength3D_F64(CubeLength3D_F64 orig) {
		set(orig);
	}

	public void set( CubeLength3D_F64 orig ) {
		set(orig.p.x,orig.p.y,orig.p.z,orig.lengthX,orig.lengthY,orig.lengthZ);
	}

	public CubeLength3D_F64() {
	}

	public void set(double x0, double y0, double z0, double lengthX, double lengthY, double lengthZ) {
		this.p.set(x0,y0,z0);
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
	}

	public double area() {
		return lengthX*lengthY*lengthZ;
	}

	public Point3D_F64 getP() {
		return p;
	}

	public double getLengthX() {
		return lengthX;
	}

	public double getLengthY() {
		return lengthY;
	}

	public double getLengthZ() {
		return lengthZ;
	}

	public void setP(Point3D_F64 p) {
		this.p.set(p);
	}

	public void setLengthX(double lengthX) {
		this.lengthX = lengthX;
	}

	public void setLengthY(double lengthY) {
		this.lengthY = lengthY;
	}

	public void setLengthZ(double lengthZ) {
		this.lengthZ = lengthZ;
	}

	public String toString() {
		return getClass().getSimpleName()+"P( "+p.x+" "+p.y+" "+p.z+" ) sides ( "+lengthX+" , "+lengthY+" , "+lengthZ+" )";
	}
}
