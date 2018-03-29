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

package georegression.geometry;

import georegression.struct.curve.ParabolaGeneral_F32;
import georegression.struct.line.LineGeneral2D_F32;

/**
 * @author Peter Abeles
 */
public class UtilParabola_F32 {
	public static void axisOfSymmetry(ParabolaGeneral_F32 parabola , LineGeneral2D_F32 axis ) {
		float A = parabola.A;
		float C = parabola.C;
		float D = parabola.D;
		float E = parabola.E;

		axis.A = A;
		axis.B = C;
		axis.C = (A*D + C*E)/(2.0f*(A*A + C*C));
	}
}
