<!DOCTYPE html>
<html>
<head>
    <title>E - Parking Solution</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f0f8ff;
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
        margin-bottom: 40px;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        background-color: #fff; /* White background for info sections */
    }

    .info-section h2 {
        font-size: 35px;
        margin-bottom: 15px;
        color: #4CAF50; /* Green headings */
    }

    .info-section h3 {
        font-size: 28px;
        margin-bottom: 15px;
        color: #4CAF50; /* Green headings */
    }

    .info-section p {
        font-size: 16px;
        line-height: 1.5;
        color: blue; /* Dark gray text */
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
<main>
    <section class="info-section">
        <h2>Welcome to your Driver Dashboard!</h2>
        <p>From here, you can manage all aspects of your parking experience. Whether you need to find a parking spot,
            manage your vehicles, or view your parking history, we've got you covered.</p>
    </section>
    <section class="info-section">
        <h3>FIND PARKING</h3>
        <p>Looking for a parking spot? Use our search feature to find available parking in your area.</p>

        <h3>MY VEHICLES</h3>
        <p>Manage your vehicles here. You can add new vehicles or remove vehicles that you no longer own.</p>

        <h3>PARKING HISTORY</h3>
        <p>View your parking history to keep track of where and when you've parked in the past and make cancellations.</p>
    </section>
</main>
</body>
</html>