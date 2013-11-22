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

package georegression.metric;

import georegression.geometry.UtilLine3D_F64;
import georegression.geometry.UtilPlane3D_F64;
import georegression.misc.GrlConstants;
import georegression.struct.line.LineParametric3D_F64;
import georegression.struct.line.LineSegment3D_F64;
import georegression.struct.plane.PlaneGeneral3D_F64;
import georegression.struct.plane.PlaneNormal3D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.point.Vector3D_F64;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Peter Abeles
 */
public class TestClosestPoint3D_F64 {
	/**
	 * Compute truth from 3 random points then see if the 3rd point is found again.
	 */
	@Test
	public void closestPoint_line() {
		Point3D_F64 a = new Point3D_F64( 1, 1, 1 );
		Point3D_F64 b = new Point3D_F64( 1.5, -2.5, 9 );
		Point3D_F64 c = new Point3D_F64( 10.1, 6, -3 );

		Vector3D_F64 va = new Vector3D_F64( a, b );
		Vector3D_F64 vc = new Vector3D_F64( c, b );

		LineParametric3D_F64 lineA = new LineParametric3D_F64( a, va );
		LineParametric3D_F64 lineB = new LineParametric3D_F64( c, vc );

		Point3D_F64 foundB = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);

		assertTrue( b.isIdentical( foundB, GrlConstants.DOUBLE_TEST_TOL ) );
		checkIsClosest(foundB,lineA,lineB);

		// check two arbitrary lines
		lineA = new LineParametric3D_F64( 2,3,-4,-9,3,6.7 );
		lineB = new LineParametric3D_F64( -0.4,0,1.2,-3.4,4,-5 );
		foundB = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);
		checkIsClosest(foundB,lineA,lineB);
	}

	@Test
	public void closestPoints_lines() {
		Point3D_F64 a = new Point3D_F64( 1, 1, 1 );
		Point3D_F64 b = new Point3D_F64( 1.5, -2.5, 9 );
		Point3D_F64 c = new Point3D_F64( 10.1, 6, -3 );

		Vector3D_F64 va = new Vector3D_F64( a, b );
		Vector3D_F64 vc = new Vector3D_F64( c, b );
		// normalize the vector so that the value 't' is euclidean distance
		va.normalize();
		vc.normalize();

		LineParametric3D_F64 lineA = new LineParametric3D_F64( a, va );
		LineParametric3D_F64 lineB = new LineParametric3D_F64( c, vc );

		double param[] = new double[2];

		assertTrue(ClosestPoint3D_F64.closestPoints(lineA, lineB, param));

		assertEquals( a.distance(b) , param[0] , GrlConstants.DOUBLE_TEST_TOL );
		assertEquals( c.distance(b) , param[1] , GrlConstants.DOUBLE_TEST_TOL );
	}

	@Test
	public void closestPoint_point() {
		Point3D_F64 a = new Point3D_F64( 1, 1, 1 );
		Point3D_F64 b = new Point3D_F64( 1.5, -2.5, 9 );
		Point3D_F64 c = new Point3D_F64( 10.1, 6, -3 );

		Vector3D_F64 va = new Vector3D_F64( a, b );

		LineParametric3D_F64 lineA = new LineParametric3D_F64( a, va );

		Point3D_F64 foundB = ClosestPoint3D_F64.closestPoint(lineA, c, null);

		Vector3D_F64 p = new Vector3D_F64( foundB, c );

		// see if they are perpendicular and therefor c foundB is the closest point
		double d = p.dot( va );

		assertEquals( 0, d, GrlConstants.DOUBLE_TEST_TOL );
	}

	@Test
	public void closestPoint_planeNorm_point() {
		Point3D_F64 found;

		PlaneNormal3D_F64 n = new PlaneNormal3D_F64(3,4,-5,3,4,-5);

		found = ClosestPoint3D_F64.closestPoint(n,new Point3D_F64(0,0,0),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);

		// move it closer, but the point shouldn't change
		Vector3D_F64 v = n.n;
		v.normalize();
		found = ClosestPoint3D_F64.closestPoint(n,new Point3D_F64(v.x,v.y,v.z),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);

		// other side of normal
		found = ClosestPoint3D_F64.closestPoint(n,new Point3D_F64(3+v.x,4+v.y,v.z-5),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);
	}

	@Test
	public void closestPoint_planeGen_point() {
		Point3D_F64 found;

		PlaneNormal3D_F64 n = new PlaneNormal3D_F64(3,4,-5,3,4,-5);
		PlaneGeneral3D_F64 g = UtilPlane3D_F64.convert(n, null);

		found = ClosestPoint3D_F64.closestPoint(g,new Point3D_F64(0,0,0),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);

		// move it closer, but the point shouldn't change
		Vector3D_F64 v = n.n;
		v.normalize();
		found = ClosestPoint3D_F64.closestPoint(g,new Point3D_F64(v.x,v.y,v.z),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);

		// other side of normal
		found = ClosestPoint3D_F64.closestPoint(g,new Point3D_F64(3+v.x,4+v.y,v.z-5),null);
		assertEquals(3,found.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(4,found.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(-5,found.z, GrlConstants.DOUBLE_TEST_TOL);
	}

	@Test
	public void closestPoint_lineSeg_pt() {
		// closest point is on the line
		LineSegment3D_F64 lineA = new LineSegment3D_F64(2,3,4,7,8,9);
		checkIsClosest(lineA,new Point3D_F64(2,3.5,3.5));

		// closest point is past a
		checkIsClosest(lineA,new Point3D_F64(1,1.95,3));

		// closest point is past b
		checkIsClosest(lineA, new Point3D_F64(8, 9, 10.1));
	}

	@Test
	public void closestPoint_lineSeg_lineSeg() {
		Point3D_F64 found;

		LineSegment3D_F64 lineA = new LineSegment3D_F64(2,3,4,7,8,9);
		LineSegment3D_F64 lineB = new LineSegment3D_F64(2,3,6,-3,8,1);

		// intersects in the middle
		found = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);
		checkIsClosest(found,lineA,lineB);

		lineA.set(-10,0,0,10,0,0);
		// misses start of lineA
		lineB.set(-100,0,20,-100,0,-20);
		found = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);
		checkIsClosest(found, lineA, lineB);
		// misses end of lineA
		lineB.set(100,0,20,100,0,-20);
		found = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);
		checkIsClosest(found, lineA, lineB);
		// same but for line B
		checkIsClosest(found, lineB, lineA);
		lineB.set(-100,0,20,-100,0,-20);
		found = ClosestPoint3D_F64.closestPoint(lineA, lineB, null);
		checkIsClosest(found, lineB, lineA);
	}

	private void checkIsClosest( LineSegment3D_F64 line ,  Point3D_F64 target  ) {

		Point3D_F64 pointOnLine = ClosestPoint3D_F64.closestPoint(line,target,null);

		LineParametric3D_F64 para = UtilLine3D_F64.convert(line,null);

		double t = UtilLine3D_F64.computeT(para,pointOnLine);

		double t0 = t - Math.sqrt(GrlConstants.DOUBLE_TEST_TOL);
		double t1 = t + Math.sqrt(GrlConstants.DOUBLE_TEST_TOL);

		if( t0 < 0 ) t0 = 0;
		if( t1 > 1 ) t1 = 1;

		Point3D_F64 work0 = para.getPointOnLine(t0);
		Point3D_F64 work1 = para.getPointOnLine(t1);

		double dist = pointOnLine.distance(target);
		double dist0 = work0.distance(target);
		double dist1 = work1.distance(target);

		assertTrue( dist <= dist0 );
		assertTrue( dist <= dist1 );
	}

	private void checkIsClosest( Point3D_F64 pt , LineSegment3D_F64 lineA , LineSegment3D_F64 lineB ) {
		double found = Distance3D_F64.distance(lineA,pt)+Distance3D_F64.distance(lineB,pt);

		Point3D_F64 work = pt.copy();

		for( int i = 0; i < 3; i++ ) {
			double orig = work.getIndex(i);
			work.setIndex(i, orig + Math.sqrt(GrlConstants.DOUBLE_TEST_TOL));
			double d = Distance3D_F64.distance(lineA,work)+Distance3D_F64.distance(lineB,work);
			assertTrue(found+" "+d,found < d);
			work.setIndex(i, orig - Math.sqrt(GrlConstants.DOUBLE_TEST_TOL));
			d = Distance3D_F64.distance(lineA,work)+Distance3D_F64.distance(lineB,work);
			assertTrue(found + " " + d, found < d);
			work.setIndex(i,orig);
		}
	}

	private void checkIsClosest( Point3D_F64 pt , LineParametric3D_F64 lineA , LineParametric3D_F64 lineB ) {
		double found = Distance3D_F64.distance(lineA,pt)+Distance3D_F64.distance(lineB,pt);

		Point3D_F64 work = pt.copy();

		for( int i = 0; i < 3; i++ ) {
			double orig = work.getIndex(i);
			work.setIndex(i,orig + Math.sqrt(GrlConstants.DOUBLE_TEST_TOL));
			double d = Distance3D_F64.distance(lineA,work)+Distance3D_F64.distance(lineB,work);
			assertTrue(found+" "+d,found < d);
			work.setIndex(i,orig - Math.sqrt(GrlConstants.DOUBLE_TEST_TOL));
			d = Distance3D_F64.distance(lineA,work)+Distance3D_F64.distance(lineB,work);
			assertTrue(found+" "+d,found < d);
			work.setIndex(i,orig);
		}
	}
}
