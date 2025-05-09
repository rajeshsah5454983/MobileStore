// Main JavaScript file for the Phone Store

document.addEventListener('DOMContentLoaded', function() {
    // Initialize all components
    initQuantityControls();
    initFormValidation();
    initMobileMenu();
});

// Mobile menu toggle
function initMobileMenu() {
    const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
    const nav = document.querySelector('nav ul');

    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener('click', function() {
            nav.classList.toggle('show');
            mobileMenuToggle.classList.toggle('active');
        });
    }
}

// Quantity controls for product details and cart
function initQuantityControls() {
    const quantityInputs = document.querySelectorAll('.quantity-input');

    quantityInputs.forEach(input => {
        const minusBtn = input.parentElement.querySelector('.quantity-minus');
        const plusBtn = input.parentElement.querySelector('.quantity-plus');

        if (minusBtn) {
            minusBtn.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('Minus button clicked');
                let value = parseInt(input.value);
                if (value > 1) {
                    input.value = value - 1;

                    // If in cart, update the cart
                    if (input.hasAttribute('data-cart-item-id')) {
                        updateCartItem(input);
                    }
                }
            });
        }

        if (plusBtn) {
            plusBtn.addEventListener('click', function(e) {
                console.log('Minus button clicked');
                e.preventDefault();
                let value = parseInt(input.value);
                let max = input.hasAttribute('max') ? parseInt(input.getAttribute('max')) : 99;

                if (value < max) {
                    input.value = value + 1;

                    // If in cart, update the cart
                    if (input.hasAttribute('data-cart-item-id')) {
                        updateCartItem(input);
                    }
                }
            });
        }

        // Update cart on input change
        if (input.hasAttribute('data-cart-item-id')) {
            input.addEventListener('change', function() {
                updateCartItem(input);
            });
        }
    });
}

// Update cart item quantity
function updateCartItem(input) {
    const cartItemId = input.getAttribute('data-cart-item-id');
    const quantity = input.value;

    // Create form and submit
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = contextPath + '/cart/update';

    const cartItemIdInput = document.createElement('input');
    cartItemIdInput.type = 'hidden';
    cartItemIdInput.name = 'cartItemId';
    cartItemIdInput.value = cartItemId;

    const quantityInput = document.createElement('input');
    quantityInput.type = 'hidden';
    quantityInput.name = 'quantity';
    quantityInput.value = quantity;

    form.appendChild(cartItemIdInput);
    form.appendChild(quantityInput);

    document.body.appendChild(form);
    form.submit();
}

// Form validation
function initFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');

    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!validateForm(form)) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        });
    });
}

// Validate form
function validateForm(form) {
    let isValid = true;

    // Validate required fields
    const requiredFields = form.querySelectorAll('[required]');
    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            showError(field, 'This field is required');
        } else {
            clearError(field);
        }
    });

    // Validate email fields
    const emailFields = form.querySelectorAll('[type="email"]');
    emailFields.forEach(field => {
        if (field.value.trim() && !validateEmail(field.value)) {
            isValid = false;
            showError(field, 'Please enter a valid email address');
        }
    });

    // Validate password fields
    const passwordField = form.querySelector('[name="password"]');
    const confirmPasswordField = form.querySelector('[name="confirmPassword"]');

    if (passwordField && confirmPasswordField) {
        if (passwordField.value !== confirmPasswordField.value) {
            isValid = false;
            showError(confirmPasswordField, 'Passwords do not match');
        }
    }

    return isValid;
}

// Validate email
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Show error message
function showError(field, message) {
    const errorDiv = field.nextElementSibling;
    if (errorDiv && errorDiv.classList.contains('error-message')) {
        errorDiv.textContent = message;
    } else {
        const div = document.createElement('div');
        div.className = 'error-message';
        div.textContent = message;
        field.parentNode.insertBefore(div, field.nextSibling);
    }
}

// Clear error message
function clearError(field) {
    const errorDiv = field.nextElementSibling;
    if (errorDiv && errorDiv.classList.contains('error-message')) {
        errorDiv.textContent = '';
    }
}

// Set context path for use in JavaScript
let contextPath = document.querySelector('meta[name="contextPath"]')?.getAttribute('content') || '';
