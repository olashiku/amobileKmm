package com.exquisite.a_mobile_kmm.core.screenUtils

/**
 * Centralized strings resource class for the Dripp Fashion app.
 * This provides a single source of truth for all text content,
 * making it easier to maintain and prepare for internationalization.
 */
object Strings {

    // Splash Screen
    object Splash {
        const val APP_NAME = "Dripp Fashion"
        const val TAGLINE = "Bespoke Fashion, Perfectly Tailored"
        const val LOGO_CONTENT_DESCRIPTION = "Dripp Fashion logo"
    }

    // Authentication
    object Auth {
        const val LETS_CREATE="Let's create something extraordinary together"
        const val SIGN_IN = "Sign in to continue to Dripp Fashion"
        const val SIGNIN = "Sign in"
        const val GET_STARTED = "Get Started"
        const val BACK_TO_SIGNIN = "Back to Sign in"
        const val NO_ACCOUNT = "Don't have an account? "
        const val SIGNUP = "Sign Up"
        const val REMEMBER_ME = "Remember me"
        const val EMAIL = "Email Address"
        const val ENTER_EMAIL = "Enter your Email Address"
        const val PASSWORD = "Password"
        const val CONFIRM_PASSWORD = "Confirm Password"
        const val CREATE_PASSWORD = "Create a  strong password"
        const val ENTER_PASSWORD = "Enter your Password"
        const val RE_CONFIRM_PASSWORD = "Re-enter your password"
        const val FORGOT_PASSWORD = "Forgot Password?"
        const val FORGOT_PASSWORD_TEXT = "Enter your email address and we'll send you a verification code"
        const val VERIFY_CODE = "Verify Code"
        const val RESET_PASSWORD = "Reset Password"

        const val WELCOME_BACK = "Welcome Back"
        const val CREATE_ACCOUNT = "Create Account"
        const val JOIN_US = "Join us at Dripp Fashion"
        const val FULL_NAME = "Full Name"
        const val ENTER_FULL_NAME = "Enter your full name"
        const val PHONE_NUMBER = "Phone Number"
        const val ENTER_PHONE_NUMBER = "Enter your phone number"
        const val VERIFY_YOUR_EMAIL = "Verify Your Email"
        const val VERIFY_EMAIL = "Verify Email"

        const val VERIFY_EMAIL_6_DIGITS = "We've sent a 6-digit verification code to"
        const val DIDNT_RECIEVE_THE_CODE = "Didn't receive the code?"
        const val RESEND_CODE = "Resend Code"

        const val CREATE_PASSW0RD = "Create Password"
        const val SECURE_ACCOUNT = "Secure your Dripp Fashion account"
        const val CREATE_NEW_PASSWORD = "Create a new password for your account"
        const val SEND_VERIFICATION_CODE = "Send Verification Code"
        const val FASHION_REQUEST = "Fashion Requests"

        const val ENTER_CONFIRM_PASSWORD = "Confirm your Password"

    }

    // Common/General
    object Common {
        const val LOADING = "Loading..."
        const val ERROR = "Error"
        const val RETRY = "Retry"
        const val CANCEL = "Cancel"
        const val SAVE = "Save"
        const val DELETE = "Delete"
        const val EDIT = "Edit"
        const val DONE = "Done"
        const val NEXT = "Next"
        const val PREVIOUS = "Previous"
        const val CONTINUE = "Continue"
        const val SKIP = "Skip"
        const val BACK = "Back"
    }

    // Fashion/Shopping specific
    object Fashion {
        const val ADD_TO_CART = "Add to Cart"
        const val VIEW_CART = "View Cart"
        const val CHECKOUT = "Checkout"
        const val SIZE_GUIDE = "Size Guide"
        const val PRODUCT_DETAILS = "Product Details"
        const val REVIEWS = "Reviews"
        const val FAVORITES = "Favorites"
        const val COLLECTIONS = "Collections"
        const val NEW_ARRIVALS = "New Arrivals"
        const val TRENDING = "Trending"
    }


    // Error Messages
    object Errors {
        const val NETWORK_ERROR = "Network connection error. Please check your internet connection."
        const val GENERIC_ERROR = "Something went wrong. Please try again."
        const val LOGIN_FAILED = "Login failed. Please check your credentials."
        const val SIGNUP_FAILED = "Account creation failed. Please try again."
    }
}