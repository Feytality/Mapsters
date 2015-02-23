package delta.soen390.mapsters.GeometryTests;

import android.test.InstrumentationTestCase;

/**
 * Created by Amanda on 2015-02-22.
 * Making sure all functions in Vector2D class function correctly.
 */
public class Vector2DTest extends InstrumentationTestCase{

    private Vector2D mVector2D;

    public void testDefaultConstructorVector2D() throws Exception{
        mVector2D = new Vector2D();
        assertEquals(0,mVector2D.x,mVector2D.y);
    }

    public void testAdd() throws Exception{
        Vector2D mVector = new Vector2D(3, 7);
        Vector2D mVector2 = new Vector2D(7, 3);
        Vector2D mVector3 = new Vector2D(10, 10);
        Vector2D mVectorSum = mVector.add(mVector2);
        assertEquals(mVector3.x, mVectorSum.x);
        assertEquals(mVector3.y, mVectorSum.y);
    }



}
