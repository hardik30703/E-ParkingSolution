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
<header class="bg-dark text-white">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/OwnerDashboard">E - Parking Solution</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/ManageParkingLot">MY PARKING LOTS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/AddParkingLot">ADD PARKING LOT</a>
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
                        <form action="<c:url value='/notifications/${notification.id}/read' />" method="post">
                            <input type="submit" value="Mark as Read">
                        </form>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>

    <section class="info-section">
        <h2><b>Manage Parking Lots</b></h2><br>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>Postcode</th>
                    <th>Price per Hour</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${approvedLots}" var="parkingLot">
                <tr>
                    <td>${parkingLot.name}</td>
                    <td>${parkingLot.address}</td>
                    <td>${parkingLot.city}</td>
                    <td>${parkingLot.postcode}</td>
                    <td>${parkingLot.price}</td>
                    <td><a href="/edit/${parkingLot.lotID}" class="btn btn-info">Edit</a> <a href="/delete/${parkingLot.lotID}" class="btn btn-danger">Remove</a>
                        <a href="/viewBookings/${parkingLot.lotID}" class="btn btn-info">View Bookings</a></td>
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
                    <th>Name</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>Postcode</th>
                    <th>Price per Hour</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${pendingLots}" var="parkingLot">
                <tr>
                    <td>${parkingLot.name}</td>
                    <td>${parkingLot.address}</td>
                    <td>${parkingLot.city}</td>
                    <td>${parkingLot.postcode}</td>
                    <td>${parkingLot.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
