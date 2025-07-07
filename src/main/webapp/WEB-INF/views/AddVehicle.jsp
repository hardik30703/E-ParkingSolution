<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E Parking Solution</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
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
    <h2><b>Enter the details of your Vehicle</b></h2><br>

    <form action="/AddVehicle" method="post" class="row g-3">
        <div class="col-md-6">
            <label>Reg Number</label>
            <input type="text" class="form-control" id="regNumber" name="regNumber"required>
        </div>

        <div class="col-md-6">
            <label>Make</label>
            <input type="text" class="form-control" id="make" name="make"required>
        </div>

        <div class="col-md-6">
            <label>Colour</label>
            <input type="text" class="form-control" id="colour" name="colour"required>
        </div>

        <div class="col-12">
            <input type="submit" class="btn btn-primary" id="addParkingLot" value="Submit">
        </div>
    </form>

</main>
</body>
</html>
