package maksymilianstrzyzewski.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class OfferActivity extends AppCompatActivity {

    private ImageView image;
    private TextView amountWithCurrency;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Offer offer = (Offer) bundle.getSerializable(MainActivity.KEY);

            assert offer != null;
            setTitle(offer.getName());
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

            image = findViewById(R.id.image);
            amountWithCurrency = findViewById(R.id.amountWithCurrency);
            descriptionView = findViewById(R.id.description);

            setData(offer);
        }
    }

    private void setData(Offer offer) {
        Picasso.with(this).load(offer.getThumbnailUrl()).into(image);
        amountWithCurrency.setText(offer.getAmountWithCurrency());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            descriptionView.setText(Html.fromHtml(offer.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            descriptionView.setText(Html.fromHtml(offer.getDescription()));
        }
    }
}
