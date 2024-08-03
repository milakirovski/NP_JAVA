package kolok1.zad14_MeteoroloshkaStanica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Measure implements Comparable<Measure>{
    private float degrees;
    private float humidity;
    private float wind;
    private float visibility;
    private Date dateTime;

    public Measure(float degrees, float humidity, float wind, float visibility, Date dateTime) {
        this.degrees = degrees;
        this.humidity = humidity;
        this.wind = wind;
        this.visibility = visibility;
        this.dateTime = dateTime;
    }

    public float getDegrees() {
        return degrees;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWind() {
        return wind;
    }

    public float getVisibility() {
        return visibility;
    }

    public Date getDate() {
        return dateTime;
    }

    @Override
    public String toString() {
        //24.6 80.2 km/h 28.7% 51.7 km Tue Dec 17 23:40:15 CET 2013
        //Format the date to GMT time zone
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = dateFormat.format(dateTime);

        // Format the rest of the data
        return String.format(Locale.US, "%.1f %.1f km/h %.1f%% %.1f km %s", degrees, humidity, wind, visibility, formattedDate);
    }

    @Override
    public int compareTo(Measure o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
