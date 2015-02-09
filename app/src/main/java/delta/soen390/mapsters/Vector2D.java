package delta.soen390.mapsters;

/**
 * Created by Niofire on 2/7/2015.
 * Math specific, breaking coding standards
 */
public class Vector2D {

	public double x;
	public double y;

	public Vector2D(double x,double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2D()
	{
		x = 0;
		y = 0;
	}

	public Vector2D substract(Vector2D vector2D)
	{
		return new Vector2D(x - vector2D.x, y - vector2D.y);
	}

	public Vector2D add(Vector2D vector2D)
	{
		return new Vector2D(x + vector2D.x,y + vector2D.y);
	}

	public double CrossProduct(Vector2D vector2D)
	{
		return  (x * vector2D.y) - (y * vector2D.x);
	}


}
