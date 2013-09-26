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

package georegression.fitting.sphere;

import georegression.struct.point.Point3D_F32;
import georegression.struct.shapes.Sphere3D_F32;
import org.ddogleg.fitting.modelset.ModelFitter;
import org.ddogleg.optimization.FactoryOptimization;
import org.ddogleg.optimization.UnconstrainedLeastSquares;

import java.util.List;

/**
 * {@link UnconstrainedLeastSquares} fitting of 3D points to a sphere.
 *
 * @author Peter Abeles
 */
public class FitSphereToPoints_F32 implements ModelFitter<Sphere3D_F32,Point3D_F32> {

	// functions used by non-linear least squares solver
	private SphereToPointSignedDistance_F32 function = new SphereToPointSignedDistance_F32();
	private SphereToPointSignedDistanceJacobian_F32 jacobian = new SphereToPointSignedDistanceJacobian_F32();

	// The solver
	private UnconstrainedLeastSquares optimizer;

	// need to convert sphere to float[]
	private /**/double[] param = new /**/double[4];

	// maximum number of iterations
	private int maxIterations;

	// tolerances for optimization
	private /**/double ftol;
	private /**/double gtol;

	// used to convert float[] into shape parameters
	private CodecSphere3D_F32 codec = new CodecSphere3D_F32();

	/**
	 * Constructor which provides access to all tuning parameters
	 *
	 * @param optimizer Optimization algorithm
	 * @param maxIterations Maximum number of iterations that the optimizer can perform. Try 100
	 * @param ftol Convergence tolerance. See {@link UnconstrainedLeastSquares}.
	 * @param gtol Convergence tolerance. See {@link UnconstrainedLeastSquares}.
	 */
	public FitSphereToPoints_F32(UnconstrainedLeastSquares optimizer,
								 int maxIterations, /**/double ftol, /**/double gtol) {
		this.optimizer = optimizer;
		this.maxIterations = maxIterations;
		this.ftol = ftol;
		this.gtol = gtol;
	}

	/**
	 * Simplified constructor.  Only process access to the maximum number of iterations.
	 * @param maxIterations Maximum number of iterations.  Try 100
	 */
	public FitSphereToPoints_F32( int maxIterations ) {
		this(FactoryOptimization.leastSquaresLM(1e-3,false),maxIterations,1e-12,0);
	}

	@Override
	public Sphere3D_F32 createModelInstance() {
		return new Sphere3D_F32();
	}

	@Override
	public boolean fitModel(List<Point3D_F32> dataSet, Sphere3D_F32 initial, Sphere3D_F32 found) {

		codec.encode(initial,param);

		function.setPoints(dataSet);
		jacobian.setPoints(dataSet);

		optimizer.setFunction(function,jacobian);
		optimizer.initialize(param, ftol, gtol);

		for( int i = 0; i < maxIterations; i++ ) {
			if( optimizer.iterate() )
				break;
		}

		codec.decode(optimizer.getParameters(),found);

		return true;
	}
}