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

package georegression.struct.plane;

import georegression.struct.point.Point3D_F64;
import georegression.struct.point.Vector3D_F64;

import java.io.Serializable;
import java.util.Objects;

/**
 * Defines a plane using a point on the plane and the plane's normal.  N*(x-p0) = 0, where N is the plane's
 * normal, p0 is a point on the plane, and 'x' is another point on the plane.
 *
 * @author Peter Abeles
 */
public class PlaneNormal3D_F64 implements Serializable {
	/** An arbitrary point in the plane */
	public Point3D_F64 p = new Point3D_F64();
	/** The plane's normal */
	public Vector3D_F64 n = new Vector3D_F64();

	public PlaneNormal3D_F64(PlaneNormal3D_F64 o) {
		set(o);
	}

	public PlaneNormal3D_F64(Point3D_F64 point, Vector3D_F64 normal) {
		set(point,normal);
	}

	public PlaneNormal3D_F64( double px , double py , double pz , double nx , double ny, double nz ) {
		set(px,py,pz,nx,ny,nz);
	}

	public PlaneNormal3D_F64() {
	}

	public Point3D_F64 getP() {
		return p;
	}

	public void set( double px , double py , double pz , double nx , double ny, double nz ) {
		this.p.set(px,py,pz);
		this.n.set(nx,ny,nz);
	}

	public void set( Point3D_F64 point, Vector3D_F64 normal ) {
		this.p.set(point);
		this.n.set(normal);
	}

	public void set( PlaneNormal3D_F64 o ) {
		this.p.set(o.p);
		this.n.set(o.n);
	}

	public void setP(Point3D_F64 p) {
		this.p.set(p);
	}

	public Vector3D_F64 getN() {
		return n;
	}

	public void setN(Vector3D_F64 n) {
		this.n.set(n);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"{" +
				"p=" + p +
				", n=" + n +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlaneNormal3D_F64 that = (PlaneNormal3D_F64) o;
		return p.equals(that.p) && n.equals(that.n);
	}

	@Override
	public int hashCode() {
		return Objects.hash(p, n);
	}
}
