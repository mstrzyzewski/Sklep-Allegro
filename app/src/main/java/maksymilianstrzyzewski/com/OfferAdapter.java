package maksymilianstrzyzewski.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> {

    private ArrayList<Offer> offersSet;
    private Context mContext;

    private static class ViewHolder {
        ImageView thumbnail;
        TextView txtName;
        TextView txtAmountWithCurrency;
    }

    public OfferAdapter(ArrayList<Offer> offers, Context context) {
        super(context, R.layout.row_item, offers);
        this.offersSet = offers;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Offer offer = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.thumbnail = convertView.findViewById(R.id.thumbnail);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtAmountWithCurrency = convertView.findViewById(R.id.price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert offer != null;
        Picasso.with(getContext()).load(offer.getThumbnailUrl()).into(viewHolder.thumbnail);
        viewHolder.txtName.setText(offer.getName());
        viewHolder.txtAmountWithCurrency.setText(offer.getAmountWithCurrency());

        return convertView;
    }
}

