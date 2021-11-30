package algonquin.cst2335.zhao0211;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private String stringUrl = "https://api.openweathermap.org/data/2.5/weather?q=Los+Angeles&appid=7e943c97096a9784391a981c4d878b22&Unit=Metric";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText cityTextField = findViewById(R.id.cityTextField);
        Button btn = findViewById(R.id.forecastButton);

        btn.setOnClickListener((View v) -> {
            String cityName = cityTextField.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Getting forecast").setMessage("We're calling people in " + cityName +  " to look outside their windows and tell us what's the weather like over there.")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    // Los Angeles

                    stringUrl = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&unit=metric&mode=xml";

                    URL url = new URL(stringUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = httpURLConnection.getInputStream();

                    /*

                     <current>
                        <city id="5368361" name="Los Angeles">
                        <coord lon="-118.2437" lat="34.0522"/>
                        <country>US</country>
                        <timezone>-28800</timezone>
                        <sun rise="2021-11-26T14:36:26" set="2021-11-27T00:44:47"/>
                        </city>
                        <temperature value="289.49" min="285.53" max="292.5" unit="kelvin"/>
                        <feels_like value="288.22" unit="kelvin"/>
                        <humidity value="40" unit="%"/>
                        <pressure value="1017" unit="hPa"/>
                        <wind>
                        <speed value="0" unit="m/s" name="Setting"/>
                        <gusts/>
                        <direction/>
                        </wind>
                        <clouds value="1" name="clear sky"/>
                        <visibility value="10000"/>
                        <precipitation mode="no"/>
                        <weather number="800" value="clear sky" icon="01n"/>
                        <lastupdate value="2021-11-27T03:10:26"/>
                    </current>
                     */

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(in, "UTF-8");
                    String current = "";
                    String min = "";
                    String max = "";
                    String humidity = "";
                    String iconName = "";
                    String description = "";
                    while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                        switch (xpp.getEventType()) {
                            case XmlPullParser.START_TAG:
                                if (xpp.getName().equals("temperature")) {
                                    current = xpp.getAttributeValue(null, "value");  //this gets the current temperature
                                    min = xpp.getAttributeValue(null, "min"); //this gets the min temperature
                                    max = xpp.getAttributeValue(null, "max"); //this gets the max temperature
                                } else if (xpp.getName().equals("weather")) {
                                    description = xpp.getAttributeValue(null, "value");  //this gets the weather description
                                    iconName = xpp.getAttributeValue(null, "icon"); //this gets the icon name
                                } else if (xpp.getName().equals("humidity")) {
                                    humidity = xpp.getAttributeValue(null, "value");  //this gets the humidity value
                                }
                                break;

                            case XmlPullParser.END_TAG:
                                break;

                            case XmlPullParser.TEXT:
                                break;
                        }
                    }



                    Bitmap image = null;
                    File file = new File(getFilesDir(), iconName + ".png");
                    Log.e("TAG", "path = " + file.getAbsolutePath());
                    if (file.exists()) {
                        Log.e("TAG", "path = exists");
                        image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
                    } else {
                        Log.e("TAG", "path = download");
                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());
                        }
                    }

                    FileOutputStream fOut = null;
                    try {
                        fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bitmap finalImage = image;
                    String finalCurrent = current;
                    String finalMin = min;
                    String finalMax = max;
                    String finalHumidity = humidity;
                    String finalDescription = description;
                    runOnUiThread(() -> {
                        TextView tv = findViewById(R.id.temp);
                        tv.setText("The current temperature is " + finalCurrent);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.minTemp);
                        tv.setText("The min temperature is " + finalMin);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.maxTemp);
                        tv.setText("The max temperature is " + finalMax);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.humidity);
                        tv.setText("The humidity temperature is " + finalHumidity + "%");
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.description);
                        tv.setText(finalDescription);
                        tv.setVisibility(View.VISIBLE);

                        ImageView iv = findViewById(R.id.icon);
                        iv.setImageBitmap(finalImage);
                        iv.setVisibility(View.VISIBLE);

                        dialog.hide();
                    });
                } catch (IOException | XmlPullParserException ioe) {

                }
            });
        });
    }
}