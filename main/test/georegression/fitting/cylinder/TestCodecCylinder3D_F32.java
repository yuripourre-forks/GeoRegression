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

import georegression.misc.GrlConstants;
import georegression.struct.shapes.Cylinder3D_F32;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestCodecCylinder3D_F32 {

	@Test
	public void basicCheck() {
		Cylinder3D_F32 cylinder = new Cylinder3D_F32(1,2,3,4,5,6,7);
		Cylinder3D_F32 found = new Cylinder3D_F32();
		/**/double param[] = new /**/double[ 7 ];

		CodecCylinder3D_F32 alg = new CodecCylinder3D_F32();

		alg.encode(cylinder,param);
		alg.decode(param,found);

		assertEquals(0, cylinder.line.p.distance(found.line.p), GrlConstants.TEST_F32);
		assertEquals(0, cylinder.line.slope.distance(found.line.slope), GrlConstants.TEST_F32);
		assertEquals(cylinder.radius,found.radius, GrlConstants.TEST_F32);
	}

}
