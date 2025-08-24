package com.proj.toll_app.service;

import java.util.List;

public interface MapServiceClient {
    // A simple record to hold coordinates
    record Coordinates(double lat, double lon) {}

    /**
     * Converts a pincode into geographic coordinates.
     * @param pincode The 6-digit Indian pincode.
     * @return The coordinates, or null if not found.
     */
    Coordinates getCoordinatesForPincode(String pincode);

    /**
     * Gets a route between two coordinates.
     * @param start The starting coordinates.
     * @param end The ending coordinates.
     * @return A list of coordinates forming the route's polyline.
     */
    List<Coordinates> getRoutePolyline(Coordinates start, Coordinates end);
}