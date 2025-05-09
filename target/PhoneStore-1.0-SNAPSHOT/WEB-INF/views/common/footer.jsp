    </main>
    <footer>
        <div class="container">
            <div class="footer-logo">
                <h2>Phone Store</h2>
                <p>Your one-stop shop for the latest smartphones and accessories. We offer a wide range of products at competitive prices.</p>
            </div>

            <div class="footer-links">
                <h3>Quick Links</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
                    <li><a href="${pageContext.request.contextPath}/cart">Cart</a></li>
                    <li><a href="${pageContext.request.contextPath}/orders">Orders</a></li>
                </ul>
            </div>

            <div class="footer-links">
                <h3>Categories</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/products?category=1">Smartphones</a></li>
                    <li><a href="${pageContext.request.contextPath}/products?category=2">Accessories</a></li>
                    <li><a href="${pageContext.request.contextPath}/products?category=3">Tablets</a></li>
                    <li><a href="${pageContext.request.contextPath}/products?category=4">Wearables</a></li>
                </ul>
            </div>

            <div class="footer-contact">
                <h3>Contact Us</h3>
                <p>123 Main Street, City, Country</p>
                <p>Email: info@phonestore.com</p>
                <p>Phone: +1 234 567 890</p>

                <div class="footer-social">
                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                    <a href="#"><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
        </div>

        <div class="footer-bottom">
            <div class="container">
                <p>&copy; 2023 Phone Store. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <!-- Custom JavaScript -->
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

    <!-- Set context path for JavaScript -->
    <meta name="contextPath" content="${pageContext.request.contextPath}">
</body>
</html>
