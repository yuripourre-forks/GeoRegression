/*
 * Copyright (C) 2011-2020, Peter Abeles. All Rights Reserved.
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

package georegression.metric;

import georegression.geometry.ConvertRotation3D_F64;
import georegression.geometry.GeometryMath_F64;
import georegression.geometry.UtilPlane3D_F64;
import georegression.misc.GrlConstants;
import georegression.struct.EulerType;
import georegression.struct.line.LineParametric3D_F64;
import georegression.struct.line.LineSegment3D_F64;
import georegression.struct.plane.PlaneGeneral3D_F64;
import georegression.struct.plane.PlaneNormal3D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.se.Se3_F64;
import georegression.struct.shapes.Box3D_F64;
import georegression.struct.shapes.BoxLength3D_F64;
import georegression.struct.shapes.Sphere3D_F64;
import georegression.struct.shapes.Triangle3D_F64;
import georegression.transform.se.SePointOps_F64;
import org.ddogleg.struct.FastArray;
import org.ddogleg.struct.FastQueue;
import org.ejml.UtilEjml;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Peter Abeles
 */
public class TestIntersection3D_F64 {

	@Test
	void intersect_planenorm_linepara() {
		// simple case with a known solution
		PlaneNormal3D_F64 plane = new PlaneNormal3D_F64(2,1,0,2,0,0);
		LineParametric3D_F64 line = new LineParametric3D_F64(0,0,0,3,0,0);

		Point3D_F64 found = new Point3D_F64();
		assertTrue(Intersection3D_F64.intersect(plane, line, found));

		assertEquals(2,found.x, GrlConstants.TEST_F64);
		assertEquals(0,found.y, GrlConstants.TEST_F64);
		assertEquals(0,found.z, GrlConstants.TEST_F64);
	}

	@Test
	void intersect_planegen_linepara() {
		// simple case with a known solution
		PlaneNormal3D_F64 plane = new PlaneNormal3D_F64(2,1,0,2,0,0);
		LineParametric3D_F64 line = new LineParametric3D_F64(0,0,0,3,0,0);
		PlaneGeneral3D_F64 general = UtilPlane3D_F64.convert(plane,(PlaneGeneral3D_F64)null);

		Point3D_F64 found = new Point3D_F64();
		assertTrue(Intersection3D_F64.intersect(general, line, found));

		assertEquals(2,found.x, GrlConstants.TEST_F64);
		assertEquals(0,found.y, GrlConstants.TEST_F64);
		assertEquals(0,found.z, GrlConstants.TEST_F64);
	}

	@Test
	void intersect_plane_plane() {
		PlaneGeneral3D_F64 a = new PlaneGeneral3D_F64(3,-4,0.5,6);
		PlaneGeneral3D_F64 b = new PlaneGeneral3D_F64(1.5,0.95,-4,-2);

		LineParametric3D_F64 line = new LineParametric3D_F64();

		Intersection3D_F64.intersect(a,b,line);

		// see if the origin of the line lies on both planes
		assertEquals(0, UtilPlane3D_F64.evaluate(a,line.p), GrlConstants.TEST_F64);
		assertEquals(0, UtilPlane3D_F64.evaluate(b,line.p), GrlConstants.TEST_F64);

		// now try another point on the line
		double x = line.p.x + line.slope.x;
		double y = line.p.y + line.slope.y;
		double z = line.p.z + line.slope.z;
		Point3D_F64 p = new Point3D_F64(x,y,z);

		assertEquals(0, UtilPlane3D_F64.evaluate(a,p), GrlConstants.TEST_F64);
		assertEquals(0, UtilPlane3D_F64.evaluate(b,p), GrlConstants.TEST_F64);

	}

	@Test
	void intersection_triangle_ls() {
		LineSegment3D_F64 ls = new LineSegment3D_F64();
		Point3D_F64 p = new Point3D_F64();

		// degenerate triangle
		Triangle3D_F64 triangle = new Triangle3D_F64(1,1,1,2,2,2,3,3,3);
		assertEquals(-1,Intersection3D_F64.intersect(triangle,ls,p));

		// no intersection
		triangle.set(1,0,0,  3,0,0,  3,2,0);
		ls.set(0,0,0,  0,0,10); // completely miss
		assertEquals(0, Intersection3D_F64.intersect(triangle, ls, p));
		ls.set(0,0,0,  0,0,10); // hits the plain but not the triangle
		assertEquals(0, Intersection3D_F64.intersect(triangle, ls, p));
		ls.set(2,0.5,-1,  2,0.5,-0.5); // would hit, but is too short
		assertEquals(0, Intersection3D_F64.intersect(triangle, ls, p));
		ls.set(2,0.5,-0.5,  2,0.5,-1); // would hit, but is too short
		assertEquals(0,Intersection3D_F64.intersect(triangle,ls,p));

		// unique intersection
		ls.set(2,0.5,1,  2,0.5,-1);
		assertEquals(1,Intersection3D_F64.intersect(triangle,ls,p));
		assertEquals(0,p.distance(new Point3D_F64(2,0.5,0)),GrlConstants.TEST_F64);

		// infinite intersections
		ls.set(0, 0, 0, 4, 0, 0);
		assertEquals(2,Intersection3D_F64.intersect(triangle, ls, p));
	}

	@Test
	void intersection_triangle_line() {
		LineParametric3D_F64 line = new LineParametric3D_F64();
		Point3D_F64 p = new Point3D_F64();


		// degenerate triangle
		Triangle3D_F64 triangle = new Triangle3D_F64(1,1,1,2,2,2,3,3,3);
		assertEquals(-1,Intersection3D_F64.intersect(triangle,line,p));

		// no intersection
		triangle.set(1,0,0,  3,0,0,  3,2,0);
		line.set(0,0,0,  0,0,10); // completely miss
		assertEquals(0, Intersection3D_F64.intersect(triangle, line, p));
		line.set(0,0,0,  0,0,10); // hits the plain but not the triangle
		assertEquals(0, Intersection3D_F64.intersect(triangle, line, p));

		// unique intersection - positive
		line.set(2,0.5,1,  0,0,-2);
		assertEquals(1,Intersection3D_F64.intersect(triangle,line,p));
		assertEquals(0,p.distance(new Point3D_F64(2,0.5,0)),GrlConstants.TEST_F64);
		// unique intersection - negative
		line.set(2,0.5,-1,  0,0,2);
		assertEquals(1,Intersection3D_F64.intersect(triangle,line,p));
		assertEquals(0,p.distance(new Point3D_F64(2,0.5,0)),GrlConstants.TEST_F64);

		// infinite intersections
		line.set(0, 0, 0, 4, 0, 0);
		assertEquals(2,Intersection3D_F64.intersect(triangle, line, p));
	}

	@Test
	void intersect_poly_line() {
		// Simple test cases
		FastArray<Point3D_F64> polygon = new FastArray<>(Point3D_F64.class);

		polygon.add( new Point3D_F64(-1,-1,2));
		polygon.add( new Point3D_F64(-1,1,2));
		polygon.add( new Point3D_F64(1,1,2));
		polygon.add( new Point3D_F64(1,-1,2));

		// hit two different parts of the polygon
		LineParametric3D_F64 line = new LineParametric3D_F64(-0.5,0.5,0,0,0,1);
		Point3D_F64 found = new Point3D_F64();
//		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
//		assertTrue(found.distance(-0.5,0.5,2) <= GrlConstants.TEST_F64);
		line.p.set(0.5,-0.5,0);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertTrue(found.distance(0.5,-0.5,2) <= GrlConstants.TEST_F64);
		// point the line in the other direction
		line.slope.set(0,0,-1);
		assertEquals(3,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertTrue(found.distance(0.5,-0.5,2) <= GrlConstants.TEST_F64);
		// miss the target entirely
		line.slope.set(0,50,0.001);
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));

		// rotate everything
		Se3_F64 se = new Se3_F64();
		se.T.set(-4,2,1.2);
		ConvertRotation3D_F64.eulerToMatrix(EulerType.XYZ,1.2,0.5,3.4,se.R);

		line.p.set(0.5,-0.5,0);
		line.slope.set(0,0,1);
		SePointOps_F64.transform(se,line.p,line.p);
		GeometryMath_F64.mult(se.R,line.slope,line.slope);

		for (int i = 0; i < polygon.size; i++) {
			SePointOps_F64.transform(se,polygon.get(i),polygon.get(i));
		}

		Point3D_F64 expected = new Point3D_F64(0.5,-0.5,2);
		SePointOps_F64.transform(se,expected,expected);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertTrue(found.distance(expected) <= GrlConstants.TEST_F64);
	}

	@Test
	void contained_boxLength_point() {
		BoxLength3D_F64 box = new BoxLength3D_F64(2,3,4,1,1.5,2.5);

		// point clearly inside the code
		assertTrue(Intersection3D_F64.contained(box,new Point3D_F64(2.1,3.1,4.1)));
		// point way outside
		assertFalse(Intersection3D_F64.contained(box,new Point3D_F64(-2,9,8)));

		// test edge cases
		assertTrue(Intersection3D_F64.contained(box,new Point3D_F64(2,3,4)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2+1, 3.1, 4.1)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2.1, 3+1.5, 4.1)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2.1, 3.1, 4+2.5)));
	}

	@Test
	void contained_box_point() {
		Box3D_F64 box = new Box3D_F64(2,3,4,3,4.5,6.5);

		// point clearly inside the code
		assertTrue(Intersection3D_F64.contained(box,new Point3D_F64(2.1,3.1,4.1)));
		// point way outside
		assertFalse(Intersection3D_F64.contained(box,new Point3D_F64(-2,9,8)));

		// test edge cases
		assertTrue(Intersection3D_F64.contained(box,new Point3D_F64(2,3,4)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2+1, 3.1, 4.1)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2.1, 3+1.5, 4.1)));
		assertFalse(Intersection3D_F64.contained(box, new Point3D_F64(2.1, 3.1, 4+2.5)));
	}

	@Test
	void contained2_box_point() {
		Box3D_F64 box = new Box3D_F64(2,3,4,3,4.5,6.5);

		// point clearly inside the code
		assertTrue(Intersection3D_F64.contained2(box, new Point3D_F64(2.1, 3.1, 4.1)));
		// point way outside
		assertFalse(Intersection3D_F64.contained2(box, new Point3D_F64(-2, 9, 8)));

		// test edge cases
		assertTrue(Intersection3D_F64.contained2(box, new Point3D_F64(2, 3, 4)));
		assertTrue(Intersection3D_F64.contained2(box, new Point3D_F64(2 + 1, 3.1, 4.1)));
		assertTrue(Intersection3D_F64.contained2(box, new Point3D_F64(2.1, 3 + 1.5, 4.1)));
		assertTrue(Intersection3D_F64.contained2(box, new Point3D_F64(2.1, 3.1, 4 + 2.5)));
	}

	@Test
	void contained_box_box() {
		Box3D_F64 box = new Box3D_F64(2,3,4,3,4.5,6.5);

		// identical
		assertTrue(Intersection3D_F64.contained(box,new Box3D_F64(2,3,4,3,4.5,6.5)));
		// smaller
		assertTrue(Intersection3D_F64.contained(box,new Box3D_F64(2.1,3.1,4.1,2.9,4.4,6.4)));

		// partial x-axis
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(1.9,3,4,3,4.5,6.5)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,3,4,3.1,4.5,6.5)));
		// partial y-axis
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,2.9,4,3,4.5,6.5)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,3,4,3,4.6,6.5)));
		// partial z-axis
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,3,3.9,3,4.5,6.5)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,3,4,3,4.5,6.6)));
	}

	@Test
	void intersect_box_box() {
		Box3D_F64 box = new Box3D_F64(2,3,4,3,4.5,6.5);

		// identical
		assertTrue(Intersection3D_F64.contained(box,new Box3D_F64(2,3,4,3,4.5,6.5)));
		// outside
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(10,10,10,12,12,12)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(10,3,4, 12,4.5,6.5)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,10,4, 3,12,6.5)));
		assertFalse(Intersection3D_F64.contained(box,new Box3D_F64(2,3,10, 3,4.5,12)));

		// assume the 1D tests are sufficient.  the above tests do check to see if each axis is handled
		// individually
	}

	@Test
	void intersect_1d() {
		// identical
		assertTrue(Intersection3D_F64.intersect(0,0,1,1));
		// bigger
		assertTrue(Intersection3D_F64.intersect(0,-1,1,2));
		assertTrue(Intersection3D_F64.intersect(-1,0,2,1));
		// shifted
		assertTrue(Intersection3D_F64.intersect(0,0.1,1,1.1));
		assertTrue(Intersection3D_F64.intersect(0,-0.1,1,0.9));
		assertTrue(Intersection3D_F64.intersect(0.1,0,1.1,1));
		assertTrue(Intersection3D_F64.intersect(-0.1,0,0.9,1));
		// graze
		assertFalse(Intersection3D_F64.intersect(0,1,1,2));
		assertFalse(Intersection3D_F64.intersect(1,0,2,1));
		// outside
		assertFalse(Intersection3D_F64.intersect(0,2,1,3));
	}

	@Test
	void intersect_line_sphere() {
		Point3D_F64 a = new Point3D_F64();
		Point3D_F64 b = new Point3D_F64();

		// test a negative case first
		assertFalse(Intersection3D_F64.intersect(
				new LineParametric3D_F64(0,0,-10,1,0,0),new Sphere3D_F64(0,0,0,2),a,b));

		// Now a positive case
		assertTrue(Intersection3D_F64.intersect(
				new LineParametric3D_F64(0,0,2,1,0,0),new Sphere3D_F64(0,0,2,2),a,b));
		assertTrue( a.distance(new Point3D_F64( 2,0,2)) <= GrlConstants.TEST_F64);
		assertTrue( b.distance(new Point3D_F64(-2,0,2)) <= GrlConstants.TEST_F64);
	}

	/**
	 * Check easy cases which can be computed by hand
	 */
	@Test
	void intersectConvex() {
		FastQueue<Point3D_F64> polygon = new FastQueue<>(Point3D_F64::new);
		polygon.grow().set(-1,-1.2,2);
		polygon.grow().set(-1,1.2,2);
		polygon.grow().set(1,1.2,2);
		polygon.grow().set(1,-1.2,2);

		LineParametric3D_F64 line = new LineParametric3D_F64();
		line.slope.set(0,0,1);

		Point3D_F64 found = new Point3D_F64();

		// test an easy intersection dead center
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(0,0,2), UtilEjml.TEST_F64);

		// negative intersection
		line.slope.set(0,0,-1);
		assertEquals(3,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(0,0,2), UtilEjml.TEST_F64);

		// should barely hit
		line.slope.set(-1,0,2.01);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(-1*(2.0/2.01),0,2), UtilEjml.TEST_F64);
		line.slope.set(1,0,2.01);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(1*(2.0/2.01),0,2), UtilEjml.TEST_F64);
		line.slope.set(0,-1.2,2.01);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(0,-1.2*(2.0/2.01),2), UtilEjml.TEST_F64);
		line.slope.set(0,1.2,2.01);
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(0,1.2*(2.0/2.01),2), UtilEjml.TEST_F64);

		// should barely miss
		line.slope.set(-1,0,1.99);
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));
		line.slope.set(1,0,1.99);
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));
		line.slope.set(0,-1.2,1.99);
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));
		line.slope.set(0,1.2,1.99);
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));

		// scale sanity check
		line.slope.set(-1,0,2.01);
		line.slope.normalize();
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(-1*(2.0/2.01),0,2), UtilEjml.TEST_F64);
		line.slope.set(-1,0,1.99);
		line.slope.normalize();
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));

		// see if polygon order matters
		polygon.get(0).set(1,-1.2,2);
		polygon.get(1).set(1,1.2,2);
		polygon.get(2).set(-1,1.2,2);
		polygon.get(3).set(-1,-1.2,2);

		line.slope.set(-1,0,2.01);
		line.slope.normalize();
		assertEquals(1,Intersection3D_F64.intersectConvex(polygon,line,found));
		assertEquals(0,found.distance(-1*(2.0/2.01),0,2), UtilEjml.TEST_F64);
		line.slope.set(-1,0,1.99);
		line.slope.normalize();
		assertEquals(0,Intersection3D_F64.intersectConvex(polygon,line,found));
	}
}
