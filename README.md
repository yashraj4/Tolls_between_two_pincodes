# Tolls_between_two_pincodes

This is a Spring Boot application that provides a REST API to find toll plazas located on the route between two Indian pincodes.

---
## Features
- **Route-based Toll Search**: Finds toll plazas specifically on the driving route.
- **Dynamic Geocoding**: Converts any valid Indian pincode to geographic coordinates.
- **External API Integration**: Uses the Google Maps API for geocoding and directions.
- **Caching**: Caches results for repeated requests to improve performance.
- **Robust Error Handling**: Provides clear error messages for invalid input or server issues.

---
## Tech Stack
- **Java 17+**
- **Spring Boot 3**
- **Apache Maven**
- **Google Maps API** (Geocoding & Directions)
- **Caffeine Cache**
- **OpenCSV**

---
## Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher.
- **Apache Maven**: To build the project.
- **Google Maps API Key**: A valid API key from the Google Cloud Platform.

---
## ⚙️ Setup and Configuration

1.  **Clone the Repository**
    ```bash
    git clone <[your-repository-url](https://github.com/yashraj4/Tolls_between_two_pincodes.git)>
    cd toll-app
    ```

2.  **Google Maps API Key**
    - Go to the [Google Cloud Console](https://console.cloud.google.com/).
    - Enable the **Geocoding API** and the **Directions API** for your project.
    - Make sure **Billing** is enabled for your project.
    - Create an API key.

3.  **Configure the Application**
    - Rename `application.properties.example` file to `application.properties`.
    - Add your Google Maps API key:
      ```properties
      google.api.key=YOUR_API_KEY
      ```

4.  **Toll Plaza Data**
    - You can place your `toll_plaza_list.csv` file inside the `src/main/resources/` directory as well if you want to use non-Indian geography. The CSV must have the columns: `Name,Latitude,Longitude`.

---
## 🚀 How to Run

1.  **Build the Project**
    - Open a terminal in the project's root directory and run:
    ```bash
    mvn clean install
    ```

2.  **Run the Application**
    ```bash
    java -jar target/toll-app-0.0.1-SNAPSHOT.jar
    ```
    The application will start on `http://localhost:8080`.

---
## 📡 API Usage

Send a `POST` request to the following endpoint.

- **Endpoint**: `POST /api/v1/toll-plazas`
- **Headers**: `Content-Type: application/json`

#### Sample Request (`cURL`)
```bash
curl -X POST http://localhost:8080/api/v1/toll-plazas \
-H "Content-Type: application/json" \
-d '{
    "sourcePincode": "110001",
    "destinationPincode": "560001"
}'
```

#### Sample Success Response
JSON

```{
    "sourcePincode": "110001",
    "destinationPincode": "560001",
    "distanceInKm": 2174.5,
    "tollPlazas": [
        {
            "name": "Toll Plaza Name 1",
            "latitude": 28.123,
            "longitude": 77.456,
            "distanceFromSource": 50.5
        }
    ]
}
```

#### Sample Error Response
JSON

```
{
    "error": "Invalid source or destination pincode"
}
```

## Visualizing the Route
You can see the exact route the application is using to find tolls.

#### Method 1: Console Logging
1.  In `TollService.java`, find the `findTollsOnRoute` method.
2.  Uncomment the following line to print the route coordinates to your console:
    ```java
    // System.out.println("Route Polyline Coordinates: " + routePolyline);
    ```
3.  Run a query. The console where your app is running will output a list of latitude and longitude pairs.

#### Method 2: Using visualizer.html (Easier)
A simple HTML file is provided to easily visualize the route and the tolls on a map.

1.  **Add Your API Key**: Open `visualizer.html` and replace **`YOUR_GOOGLE_API_KEY`** with your actual Google Maps API key in the `<script>` tag URL.
3.  **Get Coordinates**: Run a query in your application (with the `System.out.println` from Method 1 uncommented) to get the `routeCoords` and the `tolls` data from your console.
4.  **Paste Data**: Replace the sample data in the `routeCoords` and `tolls` arrays with the data you copied from your console.
5.  **View**: Open the `visualizer.html` file in your web browser to see the route and toll plazas plotted on the map.
