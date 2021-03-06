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

package georegression.struct.point;

import georegression.misc.GrlConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestPoint2D_I32 {

	@Test
	void distance2() {
		Point2D_I32 a = new Point2D_I32(1,2);
		Point2D_I32 b = new Point2D_I32(3,5);

		assertEquals(4+9,a.distance2(b));
	}

	@Test
	void distance() {
		Point2D_I32 a = new Point2D_I32(1,2);
		Point2D_I32 b = new Point2D_I32(3,5);

		assertEquals(Math.sqrt(2*2 + 3*3),a.distance(b), GrlConstants.TEST_F64);
	}

	@Test
	void copy() {
		Point2D_I32 a = new Point2D_I32(1,2);
		Point2D_I32 b = a.copy();

		assertEquals(a.x,b.x);
		assertEquals(a.y,b.y);
	}

	@Test
	void setTo() {
		Point2D_I32 a = new Point2D_I32(1,2);
		Point2D_I32 b = new Point2D_I32();
		b.setTo(a);

		assertEquals(a.x,b.x);
		assertEquals(a.y,b.y);
	}
}
