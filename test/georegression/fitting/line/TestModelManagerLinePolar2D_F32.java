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

package georegression.fitting.line;

import georegression.misc.GrlConstants;
import georegression.struct.line.LinePolar2D_F32;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestModelManagerLinePolar2D_F32 {

	@Test
	public void createModelInstance() {
		ModelManagerLinePolar2D_F32 alg = new ModelManagerLinePolar2D_F32();

		assertTrue( alg.createModelInstance() != null);
	}

	@Test
	public void copyModel() {
		ModelManagerLinePolar2D_F32 alg = new ModelManagerLinePolar2D_F32();

		LinePolar2D_F32 model = new LinePolar2D_F32(1,2);
		LinePolar2D_F32 found = new LinePolar2D_F32();

		alg.copyModel(model,found);

		assertEquals(model.angle,found.angle, GrlConstants.FLOAT_TEST_TOL);
		assertEquals(model.distance,found.distance, GrlConstants.FLOAT_TEST_TOL);
	}

}
