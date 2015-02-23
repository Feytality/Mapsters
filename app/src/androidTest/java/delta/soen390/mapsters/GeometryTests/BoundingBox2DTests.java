/*import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import delta.soen390.mapsters.GeometryTests.BoundingBox2D;
import delta.soen390.mapsters.GeometryTests.Vector2D;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import delta.soen390.mapsters.GeometryTests.BoundingBox2D;
import delta.soen390.mapsters.GeometryTests.Vector2D;

package delta.soen390.mapsters.GeometryTests;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Created by Amanda on 2015-02-23.
 * Making sure all functions in Vector2D class function correctly.
 */
/*
public class BoundingBox2DTests extends InstrumentationTestCase {
    private Vector2D mPoint1;
    private Vector2D mPoint2;
    private Vector2D mMinPoint;
    private Vector2D mMaxPoint;
    private BoundingBox2D box;
    private boolean mUpWinding;
    private ArrayList mList = new ArrayList<LatLng>(2);

    public void setUp(){
        mList.add(new LatLng(12.0, 14.0));
        mList.add(new LatLng(8.0, 10.0));
        mList.add(new LatLng(6.0, 8.0));
        box = new BoundingBox2D(mList);
        mPoint1 = new Vector2D(1.0, 1.0);
        mPoint2 = new Vector2D(2.0, 2.0);
    }

    public void testLineSegmentDefaultConstructor() throws Exception{
        Class<?> LineSegment = Class.forName("delta.soen390.mapsters.GeometryTests$LineSegement");
        Constructor<?> constructor = LineSegment.getDeclaredConstructor(BoundingBox2D.class);
        constructor.setAccessible(true);
        Object mLineSegment = constructor.newInstance(box);
        mLineSegment = constructor.newInstance(mPoint1, mPoint2);

        //assertTrue(mLineSegment.isUpWinding);

    }

       public void testBoundingBox2DConstructorLessThan3() throws Exception{
           boolean check;
           mList.remove(0);
           box = new BoundingBox2D(mList);
           for(int i = 0; i < mList.size(); i++)
           {
               //if(mList.get(i).equals(bo
           }
          // assertTrue()


       }



}
*/