<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>E - Parking Solution</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
          integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==" crossorigin="" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
            integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==" crossorigin=""></script>
    <script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>
</head>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f0f8ff; /* Light blue background */
    }

    nav {
        padding: 20px;
    }

    main {
        padding: 20px;
    }

    .navbar-brand {
        font-size: 22px;
        font-weight: bold;
    }

    .nav-link {
        font-size: 16px;
    }

    h2 {
        color: #343a40;
    }

    p {
        color: #6c757d;
    }

    .info-section {
        margin-bottom: 30px;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        background-color: #fff; /* White background for info sections */
    }
</style>
<body>
<header class="bg-dark text-white">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/DriverDashboard">E - Parking Solution</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/SearchParking">FIND PARKING</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ManageVehicle">MY VEHICLES</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ParkingHistory">PARKING HISTORY</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/">LOGOUT</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container"><br>
    <section class="info-section">
<%--    <h2><b>Search Parking</b></h2>--%>
    <div id="map" style="width: 1050px; height: 500px"></div><br>
    </section>
</main>
<script>
    // Initialize the map
    const map = L.map('map')

    // Get the tile layer from OpenStreetMaps
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {

        // Specify the maximum zoom of the map
        //maxZoom: 19,

        // Set the attribution for OpenStreetMaps
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Set the view of the map
    // with the latitude, longitude and the zoom value
    //map.setView([48.8584, 2.2945], 16);

    // Set the map view to the user's location
    map.locate({setView: true, maxZoom: 15});

    //Search feature
    L.Control.geocoder({
        defaultMarkGeocode: true,
        placeholder: 'Search for a place...',
        errorMessage: 'Nothing found.',
    }).on('markgeocode', function(e) {
        map.fitBounds(e.geocode.bbox);
    }).addTo(map);


    // Fetch the parking lot data from the server
    $.get('/getParkingLots', function(parkingLots) {
        // Loop through the parking lots and add them to the map
        for (let i = 0; i < parkingLots.length; i++) {
            // Convert the postcode to coordinates
            $.get('https://api.postcodes.io/postcodes/' + (parkingLots[i].postcode), function(data) {
                // Check if the postcode was found
                if (data.status == 200) {
                    // Create a marker for the parking lot
                    const marker = L.marker([data.result.latitude, data.result.longitude]).addTo(map);

                    // Bind a popup to the marker
                    marker.bindPopup('<a href="/HoursBooking/' + parkingLots[i].lotID + '"><span style="font-family: Arial; font-size: 16px;">'
                        + parkingLots[i].name + '<br/>Price: &pound' + parkingLots[i].price + '<br/>Postcode: ' + parkingLots[i].postcode + '</span></a>');

                }
            });
        }
    });


</script>
</body>
</html>
