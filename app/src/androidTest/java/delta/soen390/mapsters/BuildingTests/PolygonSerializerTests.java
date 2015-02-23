package delta.soen390.mapsters.BuildingTests;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.GoogleMap;

import delta.soen390.mapsters.Buildings.PolygonSerializer;

/**
 * Created by Patrick on 15-02-23.
 *
 */
public class PolygonSerializerTests extends InstrumentationTestCase {

    private PolygonSerializer mPolygonSerializer;
    private GoogleMap mGoogleMap;

    public void testPolygonSerializer() throws Exception {
        mPolygonSerializer = new PolygonSerializer(mGoogleMap);
        // Although mGoogleMap might be null, mPolygonSerializer is not
        assertNotNull(mPolygonSerializer);
    }
}
