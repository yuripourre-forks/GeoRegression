/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Geometric Regression Library (GeoRegression).
 *
 * GeoRegression is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * GeoRegression is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with GeoRegression.  If not, see <http://www.gnu.org/licenses/>.
 */

package georegression.geometry;

import georegression.struct.line.LineParametric3D_F32;
import georegression.struct.line.LineSegment3D_F32;
import georegression.struct.point.Point3D_F32;

/**
 * Various utilty functions related to lines in 3D
 *
 * @author Peter Abeles
 */
public class UtilLine3D_F32 {
	/**
	 * Converts a {@link LineSegment3D_F32} into {@link LineParametric3D_F32}.
	 *
	 * @param line Line segment
	 * @param output Storage for converted line.  If null new line will be declared.
	 * @return Line in parametric format
	 */
	public static LineParametric3D_F32 convert( LineSegment3D_F32 line , LineParametric3D_F32 output ) {
		if( output == null )
			output = new LineParametric3D_F32();

		output.p.set(line.a);
		output.slope.x = line.b.x - line.a.x;
		output.slope.y = line.b.y - line.a.y;
		output.slope.z = line.b.z - line.a.z;

		return output;
	}

	/**
	 * Computes the value of T for a point on the parametric line
	 *
	 * @param line The line
	 * @param pointOnLine Point on a line
	 * @return Value of T for the point
	 */
	public static float computeT( LineParametric3D_F32 line , Point3D_F32 pointOnLine ) {

		float dx = pointOnLine.x - line.p.x;
		float dy = pointOnLine.y - line.p.y;
		float dz = pointOnLine.z - line.p.z;

		float adx = (float)Math.abs(dx);
		float ady = (float)Math.abs(dy);
		float adz = (float)Math.abs(dz);

		float t;

		if( adx > ady ) {
			if( adx > adz ) {
				t = dx/line.slope.x;
			} else {
				t = dz/line.slope.z;
			}
		} else if( ady > adz ) {
			t = dy/line.slope.y;
		}  else {
			t = dz/line.slope.z;
		}
		return t;
	}
}
