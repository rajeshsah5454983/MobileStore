<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="./WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="About Us" />
</jsp:include>

<style>
    .about-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 2rem 1rem;
        font-family: 'Arial', sans-serif;
    }

    .about-header {
        text-align: center;
        margin-bottom: 3rem;
    }

    .about-header h1 {
        color: #333;
        font-size: 2.5rem;
        margin-bottom: 1rem;
    }

    .about-header p {
        color: #666;
        font-size: 1.1rem;
        line-height: 1.6;
        max-width: 800px;
        margin: 0 auto;
    }

    .team-section {
        margin-top: 3rem;
    }

    .team-section h2 {
        text-align: center;
        color: #333;
        margin-bottom: 2rem;
        font-size: 2rem;
    }

    .team-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 2rem;
    }

    .team-member {
        background-color: #f9f9f9;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease;
        text-align: center;
    }

    .team-member:hover {
        transform: translateY(-10px);
    }

    .member-img {
        width: 100%;
        height: 200px;
        object-fit: cover;
        border-bottom: 3px solid #3498db;
    }

    .member-info {
        padding: 1.5rem;
    }

    .member-info h3 {
        margin: 0;
        font-size: 1.5rem;
        color: #3498db;
    }

    .member-info p.role {
        color: #777;
        font-style: italic;
        margin: 0.5rem 0 1rem;
    }

    .member-info p.bio {
        font-size: 0.9rem;
        line-height: 1.5;
        color: #555;
    }

    .social-links {
        display: flex;
        justify-content: center;
        gap: 1rem;
        margin-top: 1rem;
    }

    .social-links a {
        color: #3498db;
        font-size: 1.2rem;
        transition: color 0.3s ease;
    }

    .social-links a:hover {
        color: #2980b9;
    }

    .store-info {
        margin-top: 4rem;
        background-color: #f5f5f5;
        padding: 2rem;
        border-radius: 10px;
        text-align: center;
    }

    .store-info h2 {
        color: #333;
        margin-bottom: 1rem;
    }

    .store-info p {
        line-height: 1.6;
        color: #555;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
        .team-grid {
            grid-template-columns: 1fr;
        }

        .about-header h1 {
            font-size: 2rem;
        }
    }
</style>

<div class="about-container">
    <div class="about-header">
        <h1>About Our Mobile Store</h1>
        <p>Welcome to our premium mobile store, where we offer the latest smartphones, accessories, and exceptional customer service. Our team is dedicated to providing you with the best mobile technology solutions for your needs.</p>
    </div>

    <div class="team-section">
        <h2>Meet Our Team</h2>
        <div class="team-grid">
            <!-- Team Member 1 -->
            <div class="team-member">
                <img src="/api/placeholder/400/300" alt="Team Member 1" class="member-img">
                <div class="member-info">
                    <h3>Team Member 1</h3>
                    <p class="role">Project Manager</p>
                    <p class="bio">Responsible for overseeing the project, coordinating tasks, and ensuring timely delivery of goals.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-linkedin"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 2 -->
            <div class="team-member">
                <img src="/api/placeholder/400/300" alt="Team Member 2" class="member-img">
                <div class="member-info">
                    <h3>Team Member 2</h3>
                    <p class="role">Frontend Developer</p>
                    <p class="bio">Creates beautiful and responsive user interfaces to ensure an exceptional user experience.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-linkedin"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 3 -->
            <div class="team-member">
                <img src="/api/placeholder/400/300" alt="Team Member 3" class="member-img">
                <div class="member-info">
                    <h3>Team Member 3</h3>
                    <p class="role">Backend Developer</p>
                    <p class="bio">Develops robust server-side logic and ensures smooth data flow throughout the application.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-linkedin"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 4 -->
            <div class="team-member">
                <img src="/api/placeholder/400/300" alt="Team Member 4" class="member-img">
                <div class="member-info">
                    <h3>Team Member 4</h3>
                    <p class="role">UI/UX Designer</p>
                    <p class="bio">Creates engaging designs and ensures the application is intuitive and user-friendly.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-linkedin"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="store-info">
        <h2>Our Mobile Store</h2>
        <p>Founded in 2023, our mobile store aims to provide cutting-edge mobile technology to our customers. We offer a wide range of smartphones, tablets, accessories, and repair services at competitive prices.</p>
        <p>Our mission is to help customers find the perfect mobile devices that match their needs and lifestyle, backed by exceptional customer service and technical support.</p>
    </div>
</div>

<jsp:include page="./WEB-INF/views/common/footer.jsp" />