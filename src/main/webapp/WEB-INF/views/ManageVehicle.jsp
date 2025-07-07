<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <c:if test="${not empty notifications}">
        <section class="info-section">
            <div id="notifications">
                <h2><b>Notifications</b></h2>
                <c:forEach var="notification" items="${notifications}">
                    <div class="notification">
                        <p>${notification.message}</p>
                        <form action="<c:url value='/notification/${notification.id}/read' />" method="post">
                            <input type="submit" value="Mark as Read">
                        </form>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>
    <section class="info-section">
        <h2><b>Manage Vehicles</b></h2><br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Reg Number</th>
                <th>Make</th>
                <th>Colour</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${approvedVehicles}" var="vehicle">
                <tr>
                    <td>${vehicle.regNumber}</td>
                    <td>${vehicle.make}</td>
                    <td>${vehicle.colour}</td>
                    <td><a href="/deleteVehicle/${vehicle.vehicleID}" class="btn btn-danger">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
    <section class="info-section">
        <h2><b>Decision Pending</b></h2><br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Reg Number</th>
                <th>Make</th>
                <th>Colour</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pendingVehicles}" var="vehicles">
                <tr>
                    <td>${vehicles.regNumber}</td>
                    <td>${vehicles.make}</td>
                    <td>${vehicles.colour}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
    <a href="/AddVehicle" class="btn btn-info">Add New Vehicle</a>
</main>
</body>
</html>