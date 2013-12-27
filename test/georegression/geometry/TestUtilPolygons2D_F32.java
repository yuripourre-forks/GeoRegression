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

package georegression.geometry;

import georegression.misc.GrlConstants;
import georegression.struct.shapes.Quadrilateral_F32;
import georegression.struct.shapes.RectangleCorner2D_F32;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestUtilPolygons2D_F32 {

	@Test
	public void convert_rectcorner_quad() {
		RectangleCorner2D_F32 r = new RectangleCorner2D_F32(1,2,5,6);
		Quadrilateral_F32 q = new Quadrilateral_F32();

		UtilPolygons2D_F32.convert(r,q);

		assertEquals( 1,q.a.x, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 2,q.a.y, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 5,q.b.x, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 2,q.b.y, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 5,q.c.x, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 6,q.c.y, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 1,q.d.x, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 6,q.d.y, GrlConstants.FLOAT_TEST_TOL);
	}

	@Test
	public void bounding_quadrilateral() {
		Quadrilateral_F32 q = new Quadrilateral_F32(3,0,2,-3,-2,3,1,5);
		RectangleCorner2D_F32 out = new RectangleCorner2D_F32();

		UtilPolygons2D_F32.bounding(q,out);

		assertEquals(-2,out.x0, GrlConstants.FLOAT_TEST_TOL);
		assertEquals(-3,out.y0, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 3,out.x1, GrlConstants.FLOAT_TEST_TOL);
		assertEquals( 5,out.y1, GrlConstants.FLOAT_TEST_TOL);
	}
}
