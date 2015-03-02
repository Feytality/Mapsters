package delta.soen390.mapsters.Data;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;


/**
 * Created by Niofire on 2/15/2015.
 */
public class JsonReader {
	public static JSONObject ReadJsonFromFile(Context context, String file)
	{
		String jsonString = null;
		JSONObject jsonObject;
		try {

			InputStream inputStream = context.getAssets().open(file);

			//the estimated number of bytes that can be read from the stream
			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);

			inputStream.close();

			jsonString = new String(buffer, "UTF-8");

			jsonObject = new JSONObject(jsonString);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return jsonObject;


	}
}
