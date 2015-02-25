package delta.soen390.mapsters.Geometry;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BoundingBox2D {

	private Vector2D mMinimumPoint;
	private Vector2D mMaximumPoint;

	private ArrayList<LineSegment> mLineSegments;

	public BoundingBox2D(ArrayList<LatLng> boundingPoints)
	{
		//Need at least 2 points to have a boundingBox
		if(boundingPoints.size() < 3) {
            return;
        }

		mLineSegments = new ArrayList<>();

		double initialLatitude = boundingPoints.get(0).latitude;
		double initialLongitude = boundingPoints.get(0).longitude;
		mMinimumPoint = new Vector2D(initialLatitude,initialLongitude);
		mMaximumPoint = new Vector2D(initialLatitude,initialLongitude);

		constructLineSegments(boundingPoints);
	}

    private void constructLineSegments(ArrayList<LatLng> boundingPoints) {
        for(int i = 0 ; i< boundingPoints.size(); ++i) {
            Vector2D point1 = new Vector2D(
                    boundingPoints.get(i).latitude,
                    boundingPoints.get(i).longitude);
            Vector2D point2 = new Vector2D(
                    boundingPoints.get((i + 1) % boundingPoints.size()).latitude,
                    boundingPoints.get((i + 1) % boundingPoints.size()).longitude);

            //Get maximum in minimum point for optimized Hit Testing
            if(mMinimumPoint.x > point1.x) {
                mMinimumPoint.x = point1.x;
            } else if(mMaximumPoint.x < point1.x) {
                mMaximumPoint.x = point1.x;
            }

            if(mMinimumPoint.y > point1.y) {
                mMinimumPoint.y = point1.y;
            } else if(mMaximumPoint.y < point1.y) {
                mMaximumPoint.y = point1.y;
            }

            mLineSegments.add(
                    new LineSegment(point1, point2));
        }
    }

	public boolean isPointInsideBoundingBox(Vector2D point)
	{
		Log.i("Pos",point.x + " " + point.y);
		//Check if point is within the polygon bounds
		if(!isPointWithinBound(point)) {
            return false;
        }

		int winding = 0;

		for(int i =0 ; i < mLineSegments.size(); ++i) {
			LineSegment lineSegment = mLineSegments.get(i);
			Vector2D point1 = mLineSegments.get(i).getPoint1();
			Vector2D point2 = mLineSegments.get(i).getPoint2();

			if(point1.y <= point.y) {
				if(point2.y > point.y) {
					if(isLeft(lineSegment,point) > 0) {
                        winding++;
                    }
				}
			}
			else {
				if( point2.y <= point.y) {
                    if (isLeft(lineSegment, point) < 0) {
                        winding--;
                    }
                }
			}
		}

		return winding != 0;
	}

	private boolean isPointWithinBound(Vector2D point)
	{
		return  point.x < mMaximumPoint.x
			    &&  point.x > mMinimumPoint.x
				&&  (point.y > mMinimumPoint.y &&  point.y < mMaximumPoint.y);
	}

	private boolean isLineIntersect(LineSegment segment,  Vector2D point)
	{
		double lowerBound = segment.getPoint1().x;
		double upperBound = segment.getPoint2().x;

		//upwinding means point1 is lower than point 2, hence swap if not upwinding
		if(!segment.isUpWinding()) {
			lowerBound = segment.getPoint2().x;
			upperBound = segment.getPoint1().x;
		}

		return lowerBound < point.x
				&& upperBound > point.x;
	}

	//Returns whether passed point is on left of segment
	private double isLeft(LineSegment segment, Vector2D point)
	{
		Vector2D point1 = segment.getPoint1();
		Vector2D point2 = segment.getPoint2();

		Vector2D v1 = point1.substract(point);
		Vector2D v2 = point2.substract(point);

		Vector2D P1 = segment.getPoint2();

		return (v1.x  * v2.y) - (v1.y * v2.x);
	}

    private class LineSegment
    {
        private Vector2D mPoint1;
        private Vector2D mPoint2;
        private boolean mIsUpWinding;

        LineSegment(Vector2D point1, Vector2D point2)
        {
            mPoint1 = point1;
            mPoint2 = point2;

            //Check if leftWinding
            if (mPoint1.x < mPoint2.x) {
                mIsUpWinding = true;
            } else {
                mIsUpWinding = false;
            }
        }

        public Vector2D getPoint1()
        {
            return mPoint1;
        }

        public Vector2D getPoint2()
        {
            return mPoint2;
        }

        public boolean isUpWinding()
        {
            return mIsUpWinding;
        }
    }
}
