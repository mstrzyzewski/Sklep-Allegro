package maksymilianstrzyzewski.com;

import java.io.Serializable;
import java.util.Locale;

public class Offer implements Comparable<Offer>, Serializable {

    private String id;
    private String name;
    private String thumbnailUrl;
    private String description;
    private String currency;
    private double amount;

    public Offer(String id, String name, String thumbnailUrl, String description, String currency, double amount) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.currency = currency;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Offer o) {
        return Double.compare(getAmount(), o.getAmount());
    }

    public String getAmountWithCurrency() {
        String amount = String.format(Locale.getDefault(), "%.2f", this.amount);
        amount = amount.replace(".", ",");
        return amount + " " + currency;
    }
}
