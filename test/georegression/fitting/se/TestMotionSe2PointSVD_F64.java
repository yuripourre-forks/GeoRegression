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

package georegression.fitting.se;

import georegression.geometry.UtilPoint2D_F64;
import georegression.misc.GrlConstants;
import georegression.misc.test.GeometryUnitTest;
import georegression.struct.point.Point2D_F64;
import georegression.struct.se.Se2_F64;
import georegression.transform.se.SePointOps_F64;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public class TestMotionSe2PointSVD_F64 {

	Random rand = new Random( 434324 );

	@Test
	public void noiseless() {
		Se2_F64 tran = new Se2_F64( 2, -4, 0.93 );

		List<Point2D_F64> from = UtilPoint2D_F64.random( -10, 10, 30, rand );
		List<Point2D_F64> to = new ArrayList<Point2D_F64>();
		for( Point2D_F64 p : from ) {
			to.add( SePointOps_F64.transform( tran, p, null ) );
		}

		MotionSe2PointSVD_F64 alg = new MotionSe2PointSVD_F64();

		assertTrue( alg.process( from, to ) );

		Se2_F64 tranFound = alg.getMotion();

//        tranFound.getTranslation().print();
//        tran.getTranslation().print();

		checkTransform( from, to, tranFound, GrlConstants.DOUBLE_TEST_TOL );
	}

	public static void checkTransform( List<Point2D_F64> from, List<Point2D_F64> to, Se2_F64 tranFound, double tol ) {
		Point2D_F64 foundPt = new Point2D_F64();
		for( int i = 0; i < from.size(); i++ ) {

			Point2D_F64 p = from.get( i );

			SePointOps_F64.transform( tranFound, p, foundPt );

			GeometryUnitTest.assertEquals( to.get( i ), foundPt, tol );
		}
	}
}
