package delta.soen390.mapsters.GeometryTests;

import android.test.InstrumentationTestCase;

/**
 * Created by Amanda on 2015-02-22.
 * Making sure all functions in Vector2D class function correctly.
 */
public class Vector2DTests extends InstrumentationTestCase{

    private Vector2D mVector2D;

    public void testDefaultConstructorVector2D() throws Exception{
        mVector2D = new Vector2D();
        assertEquals(0,mVector2D.x,mVector2D.y);
    }

    public void testConstructorVector2D() throws Exception{
        mVector2D = new Vector2D(1.0, 4.0);
        assertEquals(1.0, mVector2D.x);
        assertEquals(4.0, mVector2D.y);
    }

    public void testAdd() throws Exception{
        Vector2D mVector = new Vector2D(3.0, 7.0);
        Vector2D mVector2 = new Vector2D(7.0, 3.0);
        Vector2D mVector3 = new Vector2D(10.0, 10.0);
        Vector2D mVectorSum = mVector.add(mVector2);
        assertEquals(mVector3.x, mVectorSum.x);
        assertEquals(mVector3.y, mVectorSum.y);
    }

    public void testSubtract() throws Exception{
        Vector2D mVector = new Vector2D(10.0, 10);
        Vector2D mVector2 = new Vector2D(5.0, 5.0);
        Vector2D mVector3 = new Vector2D(5.0, 5.0);
        Vector2D mVectorSum = mVector.substract(mVector2);
        assertEquals(mVector3.x, mVectorSum.x);
        assertEquals(mVector3.y, mVectorSum.y);
    }

    public void testCrossProduct() throws Exception{
        Vector2D mVector = new Vector2D(10.0, 10.0);
        Vector2D mVector2 = new Vector2D(1.0, 5.0);
        assertEquals(40.0,mVector.CrossProduct(mVector2));
    }
}
