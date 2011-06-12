/*
 * Copyright (c) 2011, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Java Geometric Regression Library (JGRL).
 *
 * JGRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * JGRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JGRL.  If not, see <http://www.gnu.org/licenses/>.
 */

package jgrl.geometry;

import jgrl.struct.point.Point3D_F32;
import jgrl.struct.point.Vector2D_F32;
import jgrl.struct.point.Vector3D_F32;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.RandomMatrices;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * @author Peter Abeles
 */
public class TestGeometryMath_F32 {

	Random rand = new Random(0x33);

	@Test
	public void crossMatrix() {
		float a = 1.1f, b = -.5, c = 2.2f;

		Vector3D_F32 v = new Vector3D_F32(a, b, c);

		Vector3D_F32 x = new Vector3D_F32(7.6f, 2.9f, 0.5f);

		Vector3D_F32 found0 = new Vector3D_F32();
		Vector3D_F32 found1 = new Vector3D_F32();

		GeometryMath_F32.cross(v, x, found0);
		DenseMatrix64F V = GeometryMath_F32.crossMatrix(a, b, c, null);

		GeometryMath_F32.mult(V, x, found1);

		assertEquals(found0.x, found1.x, 1e-8);
		assertEquals(found0.y, found1.y, 1e-8);
		assertEquals(found0.z, found1.z, 1e-8);
	}

	@Test
	public void cross() {
		Vector3D_F32 a = new Vector3D_F32(1, 0, 0);
		Vector3D_F32 b = new Vector3D_F32(0, 1, 0);
		Vector3D_F32 c = new Vector3D_F32();

		GeometryMath_F32.cross(a, b, c);

		assertEquals(0, c.x, 1e-8);
		assertEquals(0, c.y, 1e-8);
		assertEquals(1, c.z, 1e-8);

		GeometryMath_F32.cross(b, a, c);

		assertEquals(0, c.x, 1e-8);
		assertEquals(0, c.y, 1e-8);
		assertEquals(-1, c.z, 1e-8);
	}

	@Test
	public void _rotate() {
		DenseMatrix64F M = RandomMatrices.createRandom(3, 3, rand);
		DenseMatrix64F P = RandomMatrices.createRandom(3, 1, rand);
		DenseMatrix64F PP = new DenseMatrix64F(3, 1);

		Point3D_F32 pt = new Point3D_F32(P.get(0, 0), P.get(1, 0), P.get(2, 0));

		CommonOps.mult(M, P, PP);
		GeometryMath_F32.rotate(M, pt, pt, true);

		assertEquals(PP.get(0, 0), pt.x, 1e-8);
		assertEquals(PP.get(1, 0), pt.y, 1e-8);
		assertEquals(PP.get(2, 0), pt.z, 1e-8);
	}


	@Test
	public void innerProd_3D() {
		Vector3D_F32 a = new Vector3D_F32(2, -2, 3);
		Vector3D_F32 b = new Vector3D_F32(4, 3, 2);
		DenseMatrix64F M = new DenseMatrix64F(3, 3, true, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		float found = GeometryMath_F32.innerProd(a, M, b);

		assertEquals(156, found, 1e-8);
	}

	@Test
	public void innerProdTranM() {
		Vector3D_F32 a = new Vector3D_F32(2, -2, 3);
		Vector3D_F32 b = new Vector3D_F32(4, 3, 2);
		DenseMatrix64F M = new DenseMatrix64F(3, 3, true, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		float found = GeometryMath_F32.innerProdTranM(a, M, b);

		assertEquals(126, found, 1e-8);
	}

	@Test
	public void innerProd_2D() {
		Vector2D_F32 a = new Vector2D_F32(2, -2);
		Vector2D_F32 b = new Vector2D_F32(4, 3);
		DenseMatrix64F M = new DenseMatrix64F(3, 3, true, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		float found = GeometryMath_F32.innerProd(a, M, b);

		assertEquals(13, found, 1e-8);
	}

	@Test
	public void dot() {
		Vector3D_F32 a = new Vector3D_F32(2, -2, 3);
		Vector3D_F32 b = new Vector3D_F32(4, 3, 2);
		float found = GeometryMath_F32.dot(a, b);

		assertEquals(8, found, 1e-8);
	}

	@Test
	public void scale() {
		Vector3D_F32 a = new Vector3D_F32(1, -2, 3);
		GeometryMath_F32.scale(a, 2);

		assertEquals(2, a.x, 1e-8);
		assertEquals(-4, a.y, 1e-8);
		assertEquals(6, a.z, 1e-8);
	}

	@Test
	public void changeSign() {
		Vector3D_F32 a = new Vector3D_F32(1, -2, 3);
		GeometryMath_F32.changeSign(a);

		assertEquals(-1, a.x, 1e-8);
		assertEquals(2, a.y, 1e-8);
		assertEquals(-3, a.z, 1e-8);
	}

	@Test
	public void addMult() {
		fail("Implement");
	}
}
