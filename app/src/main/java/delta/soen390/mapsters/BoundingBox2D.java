package delta.soen390.mapsters;

import android.graphics.Rect;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BoundingBox2D {


	private class LineSegment
	{
		private Vector2D _point1;
		private Vector2D _point2;
		private boolean _isUpWinding;

		LineSegment(Vector2D point1,Vector2D point2)
		{
			_point1 = point1;
			_point2 = point2;

			//Check if leftWinding
			if(_point1.x < _point2.x)
				_isUpWinding = true;
			else
				_isUpWinding = false;


		}

		public Vector2D getPoint1()
		{
			return _point1;
		}
		public Vector2D getPoint2()
		{
			return _point2;
		}

		public boolean isUpWinding()
		{
			return _isUpWinding;
		}
	};

	private Vector2D _minimumPoint;
	private Vector2D _maximumPoint;


	private ArrayList<LineSegment> _lineSegments;

	public BoundingBox2D(ArrayList<LatLng> boundingPoints)
	{
		//Need at least 2 points to have a boundingBox
		if(boundingPoints.size() <  3)
			return;

		_lineSegments = new ArrayList<LineSegment>();

		double initialLatitude = boundingPoints.get(0).latitude;
		double initialLongitude = boundingPoints.get(0).longitude;
		_minimumPoint = new Vector2D(initialLatitude,initialLongitude);
		_maximumPoint = new Vector2D(initialLatitude,initialLongitude);


		//Construct and load line segments
		for(int i =0 ; i< boundingPoints.size(); ++i)
		{
			Vector2D point1 = new Vector2D(
					boundingPoints.get(i).latitude,
					boundingPoints.get(i).longitude);
			Vector2D point2 = new Vector2D(
					boundingPoints.get((i + 1) % boundingPoints.size()).latitude,
					boundingPoints.get((i + 1) % boundingPoints.size()).longitude);

			//Get maximum in minimum point for optimized Hit Testing
			if(_minimumPoint.x > point1.x)
				_minimumPoint.x = point1.x;
			else if(_maximumPoint.x < point1.x)
				_maximumPoint.x = point1.x;
			if(_minimumPoint.y > point1.y)
				_minimumPoint.y = point1.y;
			else if(_maximumPoint.y < point1.y)
				_maximumPoint.y = point1.y;

			_lineSegments.add(
					new LineSegment(point1,point2));
		}
	}

	public boolean isPointInsideBoundingBox(Vector2D point)
	{
		Log.i("Pos",point.x + " " + point.y);
		//Check if point is within the polygon bounds
		if(!isPointWithinBound(point))
			return false;

		int winding = 0;


		for(int i =0 ; i < _lineSegments.size(); ++i)
		{
			LineSegment lineSegment = _lineSegments.get(i);
			Vector2D point1 = _lineSegments.get(i).getPoint1();
			Vector2D point2 = _lineSegments.get(i).getPoint2();

			if(point1.y <= point.y)
			{
				if(point2.y > point.y)
				{
					if(isLeft(lineSegment,point) > 0)
						winding++;
				}
			}
			else
			{
				if( point2.y <= point.y)
					if(isLeft(lineSegment,point) < 0)
						winding--;
			}
		}

		return winding != 0;

	}
	private boolean isPointWithinBound(Vector2D point)
	{
		return      point.x < _maximumPoint.x
				&&  point.x > _minimumPoint.x
				&&  (point.y > _minimumPoint.y
				&&  point.y < _maximumPoint.y);
	}
	private boolean isLineIntersect(LineSegment segment,  Vector2D point)
	{

		double lowerBound = segment.getPoint1().x;
		double upperBound = segment.getPoint2().x;

		//upwinding means point1 is lower than point 2, hence swap if not upwinding
		if (!segment.isUpWinding())
		{
			lowerBound = segment.getPoint2().x;
			upperBound = segment.getPoint1().x;
		}
		return  lowerBound < point.x
				&& upperBound > point.x;
	}

	//Returns whether passed point is on left of segment
	private double  isLeft(LineSegment segment, Vector2D point)
	{
		Vector2D point1 = segment.getPoint1();
		Vector2D point2 = segment.getPoint2();

		Vector2D v1 = point1.substract(point);
		Vector2D v2 = point2.substract(point);

		Vector2D P1 = segment.getPoint2();


		return (v1.x  * v2.y) - (v1.y * v2.x);
	}






}
