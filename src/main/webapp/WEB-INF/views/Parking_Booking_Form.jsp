<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    .info-section {
        margin-bottom: 40px;
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
        <h2><b>Enter the details of your Booking</b></h2><br>

        <form action="/BookingDetails" method="post" class="row g-3">
            <div class="col-md-6">
                <label>Name of Parking Lot</label>
                <input type="text" class="form-control" id="name" name="name" value="${name}"required readonly>
            </div>

            <div class="col-md-6">
                <label>Postcode</label>
                <input type="text" class="form-control" id="postcode" name="postcode" value="${postcode}"required readonly>
            </div>

            <div class="col-md-6">
                <label>Car Registration Number</label>
                <select class="form-control" id="carregnumber" name="carregnumber">
                    <c:forEach var="vehicle" items="${vehicle}">
                        <option value="${vehicle.regNumber}">${vehicle.regNumber}</option>
                    </c:forEach>
                </select>
            </div>

            <input type="hidden" class="form-control" id="hours" name="hours" value="hours">

            <div class="col-md-6">
                <label>Time of Slot</label>
                <select class="form-control" id="slottime" name="slottime">
                    <c:forEach var="slot" items="${slots}">
                        <option value="${slot.slotID}">${slotTimes[slot.slotID]}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-6">
                <label>Amount</label>
                <input type="number" class="form-control" id="amount" name="amount" value="${amount}"required readonly>
            </div>

            <div class="col-md-6">
                <label hidden>Date</label>
                <input type="date" class="form-control" id="date" name="date" hidden>
            </div>

            <div class="col-12">
                <button type="submit" class="btn btn-primary">Next</button>
            </div>
        </form>
    </section>
</main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        var url = window.location.pathname;
        var hours = url.split('/')[3];
        $('#hours').val(hours);
    });
</script>
</body>
</html>
