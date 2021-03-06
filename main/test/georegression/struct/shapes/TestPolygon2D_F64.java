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

package georegression.struct.shapes;

import georegression.struct.point.Point2D_F64;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestPolygon2D_F64 {
	@Test
	void convert() {
		Polygon2D_F64 polygon = new Polygon2D_F64(3);
		polygon.get(0).set(1,2);
		polygon.get(1).set(2,3);
		polygon.get(2).set(3,4);

		List<Point2D_F64> list = polygon.convert(null,false);
		assertEquals(3,list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) == polygon.get(i));
		}

		list = polygon.convert(null,true);
		assertEquals(3,list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) != polygon.get(i));
			assertTrue(list.get(i).equals(polygon.get(i)));
		}

		polygon.vertexes.grow().set(4,5);
		polygon.convert(list,true);
		assertEquals(4,list.size());
	}

	@Test
	void set_list() {
		List<Point2D_F64> list = new ArrayList<>();

		list.add( new Point2D_F64(2,3));
		list.add( new Point2D_F64(3,4));
		list.add( new Point2D_F64(4,5));

		Polygon2D_F64 polygon = new Polygon2D_F64(2);
		polygon.set(list);
		assertEquals(polygon.size(),list.size());
		for ( int i = 0; i < list.size(); i++ ) {
			assertTrue(list.get(i) != polygon.get(i));
			assertTrue(list.get(i).equals(polygon.get(i)));
		}
	}
}