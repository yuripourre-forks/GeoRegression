/*
 * Copyright (C) 2011-2019, Peter Abeles. All Rights Reserved.
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

package georegression.struct;

import georegression.struct.affine.Affine2D_F32;
import georegression.struct.affine.Affine2D_F64;
import georegression.struct.curve.EllipseQuadratic_F32;
import georegression.struct.curve.EllipseQuadratic_F64;
import georegression.struct.curve.EllipseRotated_F32;
import georegression.struct.curve.EllipseRotated_F64;
import georegression.struct.homography.Homography2D_F32;
import georegression.struct.homography.Homography2D_F64;
import georegression.struct.line.LineParametric2D_F32;
import georegression.struct.line.LineParametric2D_F64;
import georegression.struct.line.LineParametric3D_F32;
import georegression.struct.line.LineParametric3D_F64;
import georegression.struct.point.*;
import georegression.struct.se.Se2_F32;
import georegression.struct.se.Se2_F64;
import georegression.struct.se.Se3_F32;
import georegression.struct.se.Se3_F64;
import org.ejml.ops.ConvertMatrixData;

/**
 * Functions for converting between 32bit and 64bit structures
 *
 * @author Peter Abeles
 */
public class ConvertFloatType {

	public static Se3_F32 convert( Se3_F64 src , Se3_F32 dst ) {
		if( dst == null ) {
			dst = new Se3_F32();
		}

		ConvertMatrixData.convert(src.getR(), dst.getR());
		convert(src.T,dst.T);

		return dst;
	}

	public static Se3_F64 convert( Se3_F32 src , Se3_F64 dst ) {
		if( dst == null ) {
			dst = new Se3_F64();
		}

		ConvertMatrixData.convert(src.getR(), dst.getR());
		convert(src.T,dst.T);

		return dst;
	}

	public static Se2_F32 convert(Se2_F64 src , Se2_F32 dst ) {
		if( dst == null ) {
			dst = new Se2_F32();
		}

		dst.c = (float)src.c;
		dst.s = (float)src.s;
		convert(src.T,dst.T);

		return dst;
	}

	public static Se2_F64 convert( Se2_F32 src , Se2_F64 dst ) {
		if( dst == null ) {
			dst = new Se2_F64();
		}

		dst.c = src.c;
		dst.s = src.s;
		convert(src.T,dst.T);

		return dst;
	}

	public static Homography2D_F32 convert(Homography2D_F64 src , Homography2D_F32 dst ) {
		if( dst == null ) {
			dst = new Homography2D_F32();
		}

		ConvertMatrixData.convert(src, dst);

		return dst;
	}

	public static Homography2D_F64 convert(Homography2D_F32 src , Homography2D_F64 dst ) {
		if( dst == null ) {
			dst = new Homography2D_F64();
		}

		ConvertMatrixData.convert(src, dst);

		return dst;
	}

	public static Affine2D_F32 convert(Affine2D_F64 src , Affine2D_F32 dst ) {
		if( dst == null ) {
			dst = new Affine2D_F32();
		}

		dst.a11 = (float)src.a11; dst.a12 = (float)src.a12;
		dst.a21 = (float)src.a21; dst.a22 = (float)src.a22;

		dst.tx = (float)src.tx; dst.ty = (float)src.ty;

		return dst;
	}

	public static Affine2D_F64 convert(Affine2D_F32 src , Affine2D_F64 dst ) {
		if( dst == null ) {
			dst = new Affine2D_F64();
		}

		dst.a11 = src.a11; dst.a12 = src.a12;
		dst.a21 = src.a21; dst.a22 = src.a22;

		dst.tx = src.tx; dst.ty = src.ty;

		return dst;
	}

	public static Point3D_F32 convert( Point3D_F64 src , Point3D_F32 dst ) {
		if( dst == null ) {
			dst = new Point3D_F32();
		}

		dst.x = (float)src.x;
		dst.y = (float)src.y;
		dst.z = (float)src.z;

		return dst;
	}

	public static Point3D_F64 convert( Point3D_F32 src , Point3D_F64 dst ) {
		if( dst == null ) {
			dst = new Point3D_F64();
		}

		dst.x = src.x;
		dst.y = src.y;
		dst.z = src.z;

		return dst;
	}

	public static Point2D_F32 convert( Point2D_F64 src , Point2D_F32 dst ) {
		if( dst == null ) {
			dst = new Point2D_F32();
		}

		dst.x = (float)src.x;
		dst.y = (float)src.y;

		return dst;
	}

	public static Point2D_F64 convert( Point2D_F32 src , Point2D_F64 dst ) {
		if( dst == null ) {
			dst = new Point2D_F64();
		}

		dst.x = src.x;
		dst.y = src.y;

		return dst;
	}

	public static Vector3D_F32 convert( Vector3D_F64 src , Vector3D_F32 dst ) {
		if( dst == null ) {
			dst = new Vector3D_F32();
		}

		dst.x = (float)src.x;
		dst.y = (float)src.y;
		dst.z = (float)src.z;

		return dst;
	}

	public static Vector3D_F64 convert( Vector3D_F32 src , Vector3D_F64 dst ) {
		if( dst == null ) {
			dst = new Vector3D_F64();
		}

		dst.x = src.x;
		dst.y = src.y;
		dst.z = src.z;

		return dst;
	}

	public static Vector2D_F32 convert( Vector2D_F64 src , Vector2D_F32 dst ) {
		if( dst == null ) {
			dst = new Vector2D_F32();
		}

		dst.x = (float)src.x;
		dst.y = (float)src.y;

		return dst;
	}

	public static Vector2D_F64 convert( Vector2D_F32 src , Vector2D_F64 dst ) {
		if( dst == null ) {
			dst = new Vector2D_F64();
		}

		dst.x = src.x;
		dst.y = src.y;

		return dst;
	}

	public static EllipseRotated_F32 convert(EllipseRotated_F64 src , EllipseRotated_F32 dst ) {
		if( dst == null ) {
			dst = new EllipseRotated_F32();
		}

		convert(src.center, dst.center);
		dst.a = (float)src.a;
		dst.b = (float)src.b;
		dst.phi = (float)src.phi;

		return dst;
	}

	public static EllipseRotated_F64 convert( EllipseRotated_F32 src , EllipseRotated_F64 dst ) {
		if( dst == null ) {
			dst = new EllipseRotated_F64();
		}

		convert(src.center, dst.center);
		dst.a = src.a;
		dst.b = src.b;
		dst.phi = src.phi;

		return dst;
	}

	public static EllipseQuadratic_F32 convert(EllipseQuadratic_F64 src , EllipseQuadratic_F32 dst ) {
		if( dst == null ) {
			dst = new EllipseQuadratic_F32();
		}

		dst.A = (float)src.A;
		dst.B = (float)src.B;
		dst.C = (float)src.C;
		dst.D = (float)src.D;
		dst.E = (float)src.E;
		dst.F = (float)src.F;

		return dst;
	}

	public static EllipseQuadratic_F64 convert( EllipseQuadratic_F32 src , EllipseQuadratic_F64 dst ) {
		if( dst == null ) {
			dst = new EllipseQuadratic_F64();
		}

		dst.A = src.A;
		dst.B = src.B;
		dst.C = src.C;
		dst.D = src.D;
		dst.E = src.E;
		dst.F = src.F;

		return dst;
	}

	public static LineParametric2D_F32 convert(LineParametric2D_F64 src , LineParametric2D_F32 dst ) {
		if( dst == null ) {
			dst = new LineParametric2D_F32();
		}

		dst.slope.x = (float)src.slope.x;
		dst.slope.y = (float)src.slope.y;
		dst.p.x = (float)src.p.x;
		dst.p.y = (float)src.p.y;

		return dst;
	}

	public static LineParametric2D_F64 convert(LineParametric2D_F32 src , LineParametric2D_F64 dst ) {
		if( dst == null ) {
			dst = new LineParametric2D_F64();
		}

		dst.slope.x = src.slope.x;
		dst.slope.y = src.slope.y;
		dst.p.x = src.p.x;
		dst.p.y = src.p.y;

		return dst;
	}

	public static LineParametric3D_F32 convert(LineParametric3D_F64 src , LineParametric3D_F32 dst ) {
		if( dst == null ) {
			dst = new LineParametric3D_F32();
		}

		dst.slope.x = (float)src.slope.x;
		dst.slope.y = (float)src.slope.y;
		dst.slope.z = (float)src.slope.z;
		dst.p.x = (float)src.p.x;
		dst.p.y = (float)src.p.y;
		dst.p.z = (float)src.p.z;

		return dst;
	}

	public static LineParametric3D_F64 convert(LineParametric3D_F32 src , LineParametric3D_F64 dst ) {
		if( dst == null ) {
			dst = new LineParametric3D_F64();
		}

		dst.slope.x = src.slope.x;
		dst.slope.y = src.slope.y;
		dst.slope.z = src.slope.z;
		dst.p.x = src.p.x;
		dst.p.y = src.p.y;
		dst.p.z = src.p.z;

		return dst;
	}

}
