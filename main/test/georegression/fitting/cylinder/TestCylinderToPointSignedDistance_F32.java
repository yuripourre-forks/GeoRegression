/*
 * Copyright (C) 2011-2017, Peter Abeles. All Rights Reserved.
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

package georegression.fitting.cylinder;

import georegression.metric.Distance3D_F32;
import georegression.misc.GrlConstants;
import georegression.struct.point.Point3D_F32;
import georegression.struct.shapes.Cylinder3D_F32;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestCylinderToPointSignedDistance_F32 {

	@Test
	public void compareToDistance() {

		Cylinder3D_F32 cylinder = new Cylinder3D_F32(1,2,3,0,0,1,3);

		CylinderToPointSignedDistance_F32 alg = new CylinderToPointSignedDistance_F32();

		/**/double param[] = new /**/double[7];
		param[0] = cylinder.line.p.x;
		param[1] = cylinder.line.p.y;
		param[2] = cylinder.line.p.z;
		param[3] = cylinder.line.slope.x;
		param[4] = cylinder.line.slope.y;
		param[5] = cylinder.line.slope.z;
		param[6] = cylinder.radius;

		List<Point3D_F32> points = new ArrayList<Point3D_F32>();

		// inside, should be negative
		points.add(new Point3D_F32(3,2,3));
		// outside, should be positive
		points.add(new Point3D_F32(6,2,3));

		/**/double output[] = new /**/double[ points.size() ];

		alg.setPoints(points);
		alg.process(param,output);

		for( int i = 0; i < points.size(); i++ ) {
			float expected = Distance3D_F32.distance(cylinder, points.get(i));
			assertEquals(expected,(float) output[i], GrlConstants.TEST_F32);
		}
	}

	@Test
	public void getN_and_getM() {
		CylinderToPointSignedDistance_F32 alg = new CylinderToPointSignedDistance_F32();

		List<Point3D_F32> points = new ArrayList<Point3D_F32>();
		points.add(new Point3D_F32(1,2,3.5f));
		points.add(new Point3D_F32(1,2,3.5f));
		points.add(new Point3D_F32(1,2,3.5f));

		alg.setPoints(points);

		assertEquals(7,alg.getNumOfInputsN());
		assertEquals(points.size(), alg.getNumOfOutputsM());
	}

}
