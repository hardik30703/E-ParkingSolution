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
            <a class="navbar-brand" href="/AdminDashboard">E - Parking Solution</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/ViewOwners">VIEW ALL OWNERS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ViewDrivers">VIEW ALL DRIVERS</a>
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
        <h2><b>View All Drivers</b></h2><br>

        <form id="searchForm" action="/ViewDrivers" method="get">
            <input list="usernames" type="text" id="username" name="username" placeholder="Search by Username">
            <datalist id="usernames"></datalist>
            <button type="submit">Search</button>
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Username</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${drivers}" var="driver">
                <tr>
                    <td>${driver.firstname} ${driver.lastname}</td>
                    <td>${driver.email}</td>
                    <td>${driver.username}</td>
                    <td><a href="/ViewVehicle/${driver.driverID}" class="btn btn-info">View Vehicles</a> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        $('#username').on('input', function() {
            var input = $(this).val();
            if(input.length > 0) {
                $.get('/SearchUsernames?username=' + input, function(data) {
                    var options = '';
                    for(var i=0; i<data.length; i++) {
                        options += '<option value="' + data[i] + '">';
                    }
                    $('#usernames').html(options);
                });
            }
        });

        $('#searchForm').on('submit', function(event) {
            event.preventDefault();
            var input = $('#username').val();
            window.location.href = '/ViewDrivers?username=' + input;
        });
    });
</script>

</body>
</html>
