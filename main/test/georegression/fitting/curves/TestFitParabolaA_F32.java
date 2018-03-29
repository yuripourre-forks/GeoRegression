/*
 * Copyright (C) 2011-2018, Peter Abeles. All Rights Reserved.
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

package georegression.fitting.curves;

import georegression.misc.GrlConstants;
import georegression.struct.curve.Parabola_F32;
import georegression.struct.point.Point2D_F32;
import org.ejml.UtilEjml;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Peter Abeles
 */
public class TestFitParabolaA_F32 {

	/**
	 * Fit to the minimum possible while testing several specific geometric configurations
	 */
	@Test
	public void fit_points_3() {
		FitParabolaA_F32 fitter = new FitParabolaA_F32();
		Parabola_F32 found = new Parabola_F32();

		List<Point2D_F32> points = new ArrayList<>();

		points.add( new Point2D_F32(1,1));
		points.add( new Point2D_F32(4,2));
		points.add( new Point2D_F32(-1,8));

		assertTrue(fitter.process(points,found));

		// it should fit all the points perfectly
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a straight diagonal
		points.get(1).set(3,3);
		points.get(2).set(4,4);

		assertTrue(fitter.process(points,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		// it's a line so a2 needs to be zero
		assertEquals(0,found.A,GrlConstants.TEST_F32);
		assertEquals(0,found.D,GrlConstants.TEST_F32); // goes through the origin

		// it should fit all the points perfectly
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a a straight line along x-axis
		points.get(1).set(3,1);
		points.get(2).set(4,1);
		assertTrue(fitter.process(points,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		assertEquals(0,found.A,GrlConstants.TEST_F32);
		assertEquals(0,found.B,GrlConstants.TEST_F32);
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a a straight line along y-axis
		points.get(1).set(1,3);
		points.get(2).set(1,4);
		assertTrue(fitter.process(points,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		assertEquals(0,found.C,GrlConstants.TEST_F32);
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}
	}

	/**
	 * Same as above but with weights. Note that this really shouldn't change the results. Mostly a sanity check
	 * to see if the weights are completely messed up.
	 */
	@Test
	public void fit_points_3_weighted() {
		FitParabolaA_F32 fitter = new FitParabolaA_F32();
		float weights[] = {0.8f,1.2f,0.2f};
		Parabola_F32 found = new Parabola_F32();

		List<Point2D_F32> points = new ArrayList<>();

		points.add( new Point2D_F32(1,1));
		points.add( new Point2D_F32(4,2));
		points.add( new Point2D_F32(-1,8));

		assertTrue(fitter.process(points,weights,found));

		// it should fit all the points perfectly
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a straight diagonal
		points.get(1).set(3,3);
		points.get(2).set(4,4);

		assertTrue(fitter.process(points,weights,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		// it's a line so a2 needs to be zero
		assertEquals(0,found.A,GrlConstants.TEST_F32);
		assertEquals(0,found.D,GrlConstants.TEST_F32); // goes through the origin

		// it should fit all the points perfectly
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a a straight line along x-axis
		points.get(1).set(3,1);
		points.get(2).set(4,1);
		assertTrue(fitter.process(points,weights,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		assertEquals(0,found.A,GrlConstants.TEST_F32);
		assertEquals(0,found.B,GrlConstants.TEST_F32);
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}

		// Now see if it can fit a a straight line along y-axis
		points.get(1).set(1,3);
		points.get(2).set(1,4);
		assertTrue(fitter.process(points,weights,found));

		assertFalse(UtilEjml.isUncountable(found.A));
		assertFalse(UtilEjml.isUncountable(found.B));
		assertFalse(UtilEjml.isUncountable(found.C));
		assertFalse(UtilEjml.isUncountable(found.D));

		assertEquals(0,found.C,GrlConstants.TEST_F32);
		for( Point2D_F32 p : points ) {
			assertEquals(0,found.evaluate(p.x,p.y),GrlConstants.TEST_F32);
		}
	}

}