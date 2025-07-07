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
</style>
<header class="bg-dark text-white">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">E - Parking Solution</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/SearchParking">SEARCH PARKING</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ManageVehicle">MANAGE VEHICLES</a>
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
    <h2><b>View all Parking Lots</b></h2><br>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Slot Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${parkingSlots}" var="parkingSlot">
            <tr>
                <td>${parkingSlot.startTime} - ${parkingSlot.endTime}</td>
                <td>
                    <c:choose>
                        <c:when test="${!parkingSlot.booked}">
                            <a href="/Book/${parkingSlot.slotID}" class="btn btn-info">Book</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-info" disabled >Booked</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</main>
</body>
</html>
