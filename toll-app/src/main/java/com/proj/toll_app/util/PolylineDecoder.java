package com.proj.toll_app.util;

import com.proj.toll_app.service.MapServiceClient.Coordinates;
import java.util.ArrayList;
import java.util.List;

public class PolylineDecoder {

    /**
     * Decodes an encoded polyline string into a list of coordinates.
     * @param encoded The encoded polyline string from Google Directions API.
     * @return A list of Coordinates.
     */
    public static List<Coordinates> decode(final String encoded) {
        List<Coordinates> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Coordinates p = new Coordinates((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }
}