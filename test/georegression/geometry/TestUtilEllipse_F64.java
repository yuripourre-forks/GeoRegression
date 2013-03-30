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

import georegression.metric.UtilAngle;
import georegression.misc.GrlConstants;
import georegression.struct.point.Point2D_F64;
import georegression.struct.shapes.EllipseQuadratic_F64;
import georegression.struct.shapes.EllipseRotated_F64;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestUtilEllipse_F64 {

	Random rand = new Random(234);

	@Test
	public void convert_back_forth() {
		convert_back_forth(0,0,4.5,3,0);
		convert_back_forth(1,2,4.5,3,0);
		convert_back_forth(0,0,4.5,3,(double)Math.PI/4);
		convert_back_forth(0,0,4.5,3,0.1);
		convert_back_forth(-2,1.5,4.5,3,-0.1);

		convert_back_forth(1,2,3,1.5,0);
		convert_back_forth(1,2,3,1.5,1.5);

		// see if it can handle a circle
		convert_back_forth(0,0,3,3,0);
	}

	@Test
	public void convert_back_forth_random() {

		for( int i = 0; i < 100; i++ ) {
			double x = (rand.nextDouble()-0.5)*2;
			double y = (rand.nextDouble()-0.5)*2;
			double theta = (rand.nextDouble()-0.5)*(double)Math.PI;
			double b = rand.nextDouble()*2+0.1;
			double a = b+rand.nextDouble();

			convert_back_forth(x,y,a,b,theta);
		}
	}

	public void convert_back_forth( double x0 , double y0, double a, double b, double phi ) {
		// should be scale invariant
		convert_back_forth(x0,y0,a,b,phi,1);
		convert_back_forth(x0,y0,a,b,phi,-1);
	}

	public void convert_back_forth( double x0 , double y0, double a, double b, double phi , double scale ) {
		EllipseRotated_F64 rotated = new EllipseRotated_F64(x0,y0,a,b,phi);
		EllipseQuadratic_F64 quad = new EllipseQuadratic_F64();
		EllipseRotated_F64 found = new EllipseRotated_F64();

		UtilEllipse_F64.convert(rotated,quad);

		quad.a *= scale;
		quad.b *= scale;
		quad.c *= scale;
		quad.d *= scale;
		quad.e *= scale;
		quad.f *= scale;

		UtilEllipse_F64.convert(quad,found);

		assertEquals(rotated.center.x,found.center.x, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(rotated.center.y,found.center.y, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(rotated.a,found.a, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(rotated.b,found.b, GrlConstants.DOUBLE_TEST_TOL);
		assertEquals(rotated.phi,found.phi, GrlConstants.DOUBLE_TEST_TOL);
	}

	@Test
	public void convert_rotated_to_quad() {
		EllipseRotated_F64 rotated = new EllipseRotated_F64(1,2,4.5,3,0.2);

		Point2D_F64 p = UtilEllipse_F64.computePoint(0.45,rotated,null);

		double eval = UtilEllipse_F64.evaluate(p.x,p.y,rotated);
		assertEquals(1,eval, GrlConstants.DOUBLE_TEST_TOL);

		EllipseQuadratic_F64 quad = new EllipseQuadratic_F64();
		UtilEllipse_F64.convert(rotated,quad);
		eval = UtilEllipse_F64.evaluate(p.x,p.y,quad);
		assertEquals(0,eval, GrlConstants.DOUBLE_TEST_TOL);
	}

	/**
	 * Tests computePoint and evaluate(rotated) by computes points around the ellipse and seeing if they
	 * meet the expected results.
	 */
	@Test
	public void computePoint_evaluate_rotated() {
		EllipseRotated_F64 rotated = new EllipseRotated_F64(1,2,4.5,3,0.2);

		for( int i = 0; i < 100; i++ ) {
			double t = Math.PI*2*i/100.0;
			Point2D_F64 p = UtilEllipse_F64.computePoint(t,rotated,null);
			double eval = UtilEllipse_F64.evaluate(p.x,p.y,rotated);
			assertEquals(1,eval, GrlConstants.DOUBLE_TEST_TOL);
		}
	}

	@Test
	public void computePoint_evaluate_quadratic() {
		EllipseRotated_F64 rotated = new EllipseRotated_F64(1,2,4.5,3,0.2);
		EllipseQuadratic_F64 quad = new EllipseQuadratic_F64();
		UtilEllipse_F64.convert(rotated,quad);

		for( int i = 0; i < 100; i++ ) {
			double t = Math.PI*2*i/100.0;
			Point2D_F64 p = UtilEllipse_F64.computePoint(t,rotated,null);
			double eval = UtilEllipse_F64.evaluate(p.x,p.y,quad);
			assertEquals(0,eval, GrlConstants.DOUBLE_TEST_TOL);
		}
	}

	@Test
	public void computeAngle() {
		EllipseRotated_F64 rotated = new EllipseRotated_F64(1,2,4.5,3,0.2);

		for( int i = 0; i <= 100; i++ ) {
			double t = Math.PI*2*i/100.0 - Math.PI;
			Point2D_F64 p = UtilEllipse_F64.computePoint(t,rotated,null);
			double found = UtilEllipse_F64.computeAngle(p,rotated);
//			System.out.println(t+" "+found);
			assertTrue(UtilAngle.dist(t, found) <= GrlConstants.DOUBLE_TEST_TOL);
		}
	}

}
