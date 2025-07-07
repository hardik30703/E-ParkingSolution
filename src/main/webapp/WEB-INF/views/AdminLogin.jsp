<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f0f8ff; /* Light blue background */
    }

    main {
        padding: 20px;
    }

    nav {
        background-color: #333; /* Dark gray navigation bar */
        padding: 20px;
    }

    .navbar-brand {
        font-size: 22px;
        font-weight: bold;
        color: white;
    }

    .nav-link {
        font-size: 16px;
    }

    h2 {
        font-size: 38px;
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

    footer {
        background-color: #222;
        color: #fff;
        padding: 20px;
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
    }
</style>
<body>
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
                        <a class="nav-link" href="/">HOME</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="container mt-5">
    <section class="info-section">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h2 class="mb-4"><b>Admin Login</b></h2>
                <form action="/AdminLogin" method="post">
                    <div class="mb-3">
                        <label>Username</label>
                        <input type="text" class="form-control" id="username" name="username" required><br>
                        <label>Password</label>
                        <input type="password" class="form-control" id="password" name="password" required><br>
                    </div>
                    <input type="submit" class="btn btn-primary" id="login" value="Login">
                </form>
                <br>
            </div>
        </div>
    </section>
</div>
<footer class="bg-dark text-white"></footer>
<script>
    <c:if test="${not empty errorMessage}">
    alert('${errorMessage}');
    </c:if>
</script>
</body>
</html>
