<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="./WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="About Us" />
</jsp:include>

<style>
    :root {
        --primary-color: #4361ee;
        --secondary-color: #3a0ca3;
        --accent-color: #4cc9f0;
        --text-dark: #2b2d42;
        --text-light: #8d99ae;
        --bg-light: #f8f9fa;
        --bg-dark: #212529;
    }

    .about-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 3rem 1rem;
        font-family: 'Poppins', sans-serif;
        color: var(--text-dark);
    }

    .about-header {
        text-align: center;
        margin-bottom: 4rem;
        position: relative;
        padding-bottom: 2rem;
    }

    .about-header::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 100px;
        height: 4px;
        background: linear-gradient(to right, var(--primary-color), var(--accent-color));
        border-radius: 2px;
    }

    .about-header h1 {
        color: var(--primary-color);
        font-size: 3rem;
        margin-bottom: 1.5rem;
        font-weight: 700;
        letter-spacing: -0.5px;
        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    }

    .about-header p {
        color: var(--text-light);
        font-size: 1.2rem;
        line-height: 1.8;
        max-width: 800px;
        margin: 0 auto;
    }

    .team-section {
        margin-top: 4rem;
        padding: 2rem 0;
    }

    .team-section h2 {
        text-align: center;
        color: var(--secondary-color);
        margin-bottom: 3rem;
        font-size: 2.2rem;
        font-weight: 600;
        position: relative;
        padding-bottom: 1rem;
    }

    .team-section h2::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 60px;
        height: 3px;
        background-color: var(--accent-color);
        border-radius: 2px;
    }

    .team-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 2.5rem;
    }

    .team-member {
        background-color: var(--bg-light);
        border-radius: 16px;
        overflow: hidden;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
        transition: all 0.4s ease;
        text-align: center;
        position: relative;
        z-index: 1;
    }

    .team-member::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
        opacity: 0;
        z-index: -1;
        transition: opacity 0.4s ease;
        border-radius: 16px;
    }

    .team-member:hover {
        transform: translateY(-15px);
    }

    .team-member:hover::before {
        opacity: 0.95;
    }

    .team-member:hover .member-info h3,
    .team-member:hover .member-info p.role,
    .team-member:hover .member-info p.bio {
        color: white;
    }

    .team-member:hover .social-links a {
        color: white;
        border-color: rgba(255, 255, 255, 0.6);
    }

    .member-img {
        width: 100%;
        height: 650px;
        object-fit: cover;
        border-bottom: 5px solid var(--accent-color);
        transition: transform 0.5s ease;
    }

    .team-member:hover .member-img {
        transform: scale(1.05);
    }

    .member-info {
        padding: 2rem 1.5rem;
        transition: all 0.3s ease;
    }

    .member-info h3 {
        margin: 0;
        font-size: 1.8rem;
        color: var(--primary-color);
        font-weight: 600;
        transition: color 0.3s ease;
    }

    .member-info p.role {
        color: var(--text-light);
        font-style: italic;
        margin: 0.8rem 0 1.2rem;
        font-size: 1rem;
        transition: color 0.3s ease;
    }

    .member-info p.bio {
        font-size: 0.95rem;
        line-height: 1.6;
        color: var(--text-dark);
        transition: color 0.3s ease;
    }

    .social-links {
        display: flex;
        justify-content: center;
        gap: 1.2rem;
        margin-top: 1.5rem;
    }

    .social-links a {
        color: var(--primary-color);
        width: 38px;
        height: 38px;
        border-radius: 50%;
        border: 1px solid rgba(67, 97, 238, 0.3);
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.3s ease;
    }

    .social-links a:hover {
        background-color: rgba(255, 255, 255, 0.2);
        transform: translateY(-5px);
    }

    .store-info {
        margin-top: 5rem;
        background: linear-gradient(45deg, #f3f4f6, #ffffff);
        padding: 3rem 2rem;
        border-radius: 20px;
        text-align: center;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.05);
        position: relative;
        overflow: hidden;
    }

    .store-info::before {
        content: '';
        position: absolute;
        width: 200px;
        height: 200px;
        background: radial-gradient(circle, var(--accent-color) 0%, rgba(76, 201, 240, 0) 60%);
        top: -100px;
        left: -100px;
        opacity: 0.3;
    }

    .store-info::after {
        content: '';
        position: absolute;
        width: 200px;
        height: 200px;
        background: radial-gradient(circle, var(--primary-color) 0%, rgba(67, 97, 238, 0) 60%);
        bottom: -100px;
        right: -100px;
        opacity: 0.3;
    }

    .store-info h2 {
        color: var(--secondary-color);
        margin-bottom: 1.5rem;
        font-size: 2rem;
        font-weight: 600;
        position: relative;
        display: inline-block;
        padding-bottom: 0.5rem;
    }

    .store-info h2::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 25%;
        width: 50%;
        height: 3px;
        background-color: var(--accent-color);
        border-radius: 2px;
    }

    .store-info p {
        line-height: 1.8;
        color: var(--text-dark);
        font-size: 1.05rem;
        max-width: 900px;
        margin: 0 auto 1rem;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
        .team-grid {
            grid-template-columns: 1fr;
        }

        .about-header h1 {
            font-size: 2.2rem;
        }

        .about-header p {
            font-size: 1.1rem;
        }

        .member-img {
            height: 300px;
        }
    }
</style>

<div class="about-container">
    <div class="about-header">
        <h1>Welcome to Mobile Store</h1>
        <p>Your premium destination for cutting-edge mobile technology. We offer the latest smartphones, accessories, and exceptional customer service with a team dedicated to providing you with the best mobile solutions tailored to your needs and lifestyle.</p>
    </div>

    <div class="team-section">
        <h2>Meet Our Team</h2>
        <div class="team-grid">
            <!-- Team Member 1 -->
            <div class="team-member">
                <img src="./assets/photos/Rajesh.jpg" alt="Rajesh Kumar Sah" class="member-img">
                <div class="member-info">
                    <h3>Rajesh Kumar Sah</h3>
                    <p class="role">Backend Developer</p>
                    <p class="bio">Talented backend developer who creates robust server-side architecture and ensures smooth data flow throughout the application.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 2 -->
            <div class="team-member">
                <img src="./assets/photos/sima.jpg" alt="Sima Limbu" class="member-img">
                <div class="member-info">
                    <h3>Sima Limbu</h3>
                    <p class="role">Frontend Developer</p>
                    <p class="bio">Creates beautiful and responsive user interfaces to ensure an exceptional user experience for our customers.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 3 -->
            <div class="team-member">
                <img src="./assets/photos/sabal.jpg" alt="Sabal Luital" class="member-img">
                <div class="member-info">
                    <h3>Sabal Luital</h3>
                    <p class="role">Project Manager</p>
                    <p class="bio">Responsible for overseeing the project, coordinating tasks, and ensuring timely delivery of goals for our mobile store.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                    </div>
                </div>
            </div>

            <!-- Team Member 4 -->
            <div class="team-member">
                <img src="./assets/photos/2.jpg" alt="Mingma Rinchhen Gurung" class="member-img">
                <div class="member-info">
                    <h3>Mingma Rinchhen Gurung</h3>
                    <p class="role">UI/UX Designer</p>
                    <p class="bio">Creates engaging designs and ensures the application is intuitive and user-friendly for all our customers.</p>
                    <div class="social-links">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-envelope"></i></a>
                        <a href="#"><i class="fa fa-github"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="store-info">
        <h2>Our Mobile Store Journey</h2>
        <p>Founded in 2023, Mobile Store was born from a passion to bridge the gap between cutting-edge mobile technology and everyday users. We carefully curate our product selection to offer the latest smartphones, tablets, accessories, and expert repair services at competitive prices.</p>
        <p>Our vision goes beyond just selling devices – we aim to enhance your digital lifestyle by providing personalized recommendations, ongoing technical support, and a commitment to customer satisfaction that extends long after your purchase.</p>
        <p>Visit us today and experience the perfect blend of technology, expertise, and exceptional service!</p>
    </div>
</div>

<jsp:include page="./WEB-INF/views/common/footer.jsp" />