package maksymilianstrzyzewski.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView offersList;

    public static final String ARRAY_NAME = "offers";
    public static final String API_URL = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
    public static final String KEY = "offer";

    /**
     * Offer fields
     */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String THUMBNAIL_URL = "thumbnailUrl";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        offersList = findViewById(R.id.offersList);
        mQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void jsonParse() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray(ARRAY_NAME);
                            addOffersToList(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mQueue.add(request);
    }

    private void addOffersToList(JSONArray jsonArray) throws JSONException {
        final ArrayList<Offer> offers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject price = jsonObject.getJSONObject(PRICE);

            if (price.getDouble(AMOUNT) < 50 || price.getDouble(AMOUNT) > 1000) {
                continue;
            }

            Offer offer = new Offer(
                    jsonObject.getString(ID),
                    jsonObject.getString(NAME),
                    jsonObject.getString(THUMBNAIL_URL),
                    jsonObject.getString(DESCRIPTION),
                    price.getString(CURRENCY),
                    price.getDouble(AMOUNT)
            );

            offers.add(offer);
        }

        Collections.sort(offers);

        OfferAdapter adapter = new OfferAdapter(offers, getApplicationContext());
        offersList.setAdapter(adapter);

        offersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer offer = offers.get(position);

                openOffer(offer);
            }
        });
    }

    private void openOffer(Offer offer) {
        Intent offerIntent = new Intent(MainActivity.this, OfferActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, offer);

        offerIntent.putExtras(bundle);
        startActivity(offerIntent);
    }
}
