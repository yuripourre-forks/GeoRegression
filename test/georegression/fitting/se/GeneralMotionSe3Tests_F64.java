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

import georegression.fitting.MotionTransformPoint;
import georegression.geometry.RotationMatrixGenerator;
import georegression.geometry.UtilPoint3D_F64;
import georegression.misc.GrlConstants;
import georegression.misc.test.GeometryUnitTest;
import georegression.struct.point.Point3D_F64;
import georegression.struct.point.Vector3D_F64;
import georegression.struct.se.Se3_F64;
import georegression.transform.se.SePointOps_F64;
import org.ejml.data.DenseMatrix64F;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * @author Peter Abeles
 */
public abstract class GeneralMotionSe3Tests_F64 {
	Random rand = new Random( 434324 );

	abstract MotionTransformPoint<Se3_F64, Point3D_F64> createAlg();

	@Test
	public void noiseless() {
		for( int i = 0; i < 100; i++ ) {
			DenseMatrix64F R = RotationMatrixGenerator.eulerXYZ(rand.nextGaussian(),
					rand.nextGaussian(), rand.nextGaussian(), null);
			Vector3D_F64 T = new Vector3D_F64( rand.nextGaussian(),
					rand.nextGaussian(), rand.nextGaussian() );

			Se3_F64 tran = new Se3_F64( R, T );

			List<Point3D_F64> from = UtilPoint3D_F64.random(-10, 10, 30, rand);
			List<Point3D_F64> to = new ArrayList<Point3D_F64>();
			for( Point3D_F64 p : from ) {
				to.add( SePointOps_F64.transform(tran, p, null) );
			}

			MotionTransformPoint<Se3_F64, Point3D_F64> alg = createAlg();

			assertTrue( alg.process( from, to ) );


			Se3_F64 tranFound = alg.getMotion();

			checkTransform( from, to, tranFound, GrlConstants.DOUBLE_TEST_TOL );
		}
	}

	@Test
	public void noiselessPlanar() {
		for( int i = 0; i < 100; i++ ) {
			DenseMatrix64F R = RotationMatrixGenerator.eulerXYZ( rand.nextGaussian(),
					rand.nextGaussian(),rand.nextGaussian(), null );
			Vector3D_F64 T = new Vector3D_F64( rand.nextGaussian(),
					rand.nextGaussian(), rand.nextGaussian() );

			Se3_F64 tran = new Se3_F64( R, T );

			List<Point3D_F64> from = createPlanar(30);
			List<Point3D_F64> to = new ArrayList<Point3D_F64>();
			for( Point3D_F64 p : from ) {
				to.add( SePointOps_F64.transform( tran, p, null ) );
			}

			MotionTransformPoint<Se3_F64, Point3D_F64> alg = createAlg();

			assertTrue( alg.process( from, to ) );

			Se3_F64 tranFound = alg.getMotion();
			
			R.print();
			tranFound.getR().print();
			
			checkTransform( from, to, tranFound, GrlConstants.DOUBLE_TEST_TOL );
		}
	}

	private List<Point3D_F64> createPlanar( int N ) {
		List<Point3D_F64> ret = new ArrayList<Point3D_F64>();

		for( int i = 0; i < N; i++ )
			ret.add( new Point3D_F64((double) (rand.nextGaussian()*2),(double)(rand.nextGaussian()*2),3));

		return ret;
	}

	public static void checkTransform( List<Point3D_F64> from, List<Point3D_F64> to, Se3_F64 tranFound, double tol ) {
		Point3D_F64 foundPt = new Point3D_F64();
		for( int i = 0; i < from.size(); i++ ) {

			Point3D_F64 p = from.get( i );

			SePointOps_F64.transform( tranFound, p, foundPt );

			GeometryUnitTest.assertEquals(to.get(i), foundPt, tol);
		}
	}
}
