# IT342 Web Application - ReactJS

A modern React authentication application with protected routes, user registration, login, and profile pages.

## Features

- ✅ User Registration Page
- ✅ User Login Page  
- ✅ Protected Dashboard/Profile Page
- ✅ Logout Functionality
- ✅ Persistent Authentication (localStorage)
- ✅ Form Validation
- ✅ Error Handling
- ✅ Responsive Design
- ✅ Protected Routes

## Requirements

- Node.js 14+ and npm 6+
- React 18+
- React Router DOM 6+

## Installation

### 1. Install Dependencies

```bash
cd web
npm install
```

### 2. Configure API URL

Create a `.env` file in the `web` directory:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

### 3. Start Development Server

```bash
npm start
```

The application will open at `http://localhost:3000`

## Pages Overview

### Login Page (`/login`)
- Email and password input fields
- Form validation
- Error messages
- Link to registration page
- Redirects to dashboard on successful login

### Register Page (`/register`)
- First name and last name inputs
- Email input with validation
- Password confirmation
- Form validation
- Error and success messages
- Link to login page
- Auto-redirect to dashboard on successful registration

### Dashboard/Profile Page (`/dashboard`)
- Protected route (requires authentication)
- Displays user profile information
- Shows first name, last name, email, and user ID
- Account status indicator
- Logout button
- Navbar with app branding

## Authentication Flow

1. User registers or logs in with credentials
2. Credentials are stored in localStorage (Base64 encoded for Basic Auth)
3. User is redirected to dashboard
4. Protected routes check authentication status
5. Logout clears stored credentials and redirects to login

## Project Structure

```
web/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Login.js
│   │   ├── Login.css
│   │   ├── Register.js
│   │   ├── Register.css
│   │   ├── Dashboard.js
│   │   ├── Dashboard.css
│   │   ├── ProtectedRoute.js
│   │   └── ProtectedRoute.css
│   ├── context/
│   │   └── AuthContext.js
│   ├── App.js
│   ├── App.css
│   ├── index.js
│   └── index.css
├── .env
├── .gitignore
├── package.json
└── README.md
```

## State Management

The application uses React Context API for authentication state management:

- **AuthContext** - Manages user state, authentication functions (register, login, logout)
- **useAuth Hook** - Custom hook to access authentication context

## API Integration

The application communicates with the backend API at `http://localhost:8080/api`:

- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user
- `GET /user/me` - Get current user profile (protected)

## Authentication Method

- **HTTP Basic Authentication** - Email and password are Base64 encoded and sent in Authorization header
- **localStorage** - Credentials are persisted in browser storage for session management

## Form Validation

- Email validation with regex pattern
- Password confirmation matching
- Minimum password length (6 characters)
- Required field validation
- Real-time error message clearing

## Error Handling

- Network error handling
- API error messages displayed to user
- Field-level validation errors
- Connection timeout handling

## Responsive Design

- Mobile-friendly layouts
- Flexbox and Grid layouts
- Media queries for smaller screens
- Touch-friendly button sizes

## Available Scripts

### `npm start`
Runs the app in development mode.
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

### `npm build`
Builds the app for production to the `build` folder.

### `npm test`
Launches the test runner in interactive watch mode.

## Future Enhancements

- [ ] Password reset functionality
- [ ] User profile update
- [ ] Two-factor authentication
- [ ] User profile picture upload
- [ ] Email verification
- [ ] Account deletion
- [ ] Remember me functionality
- [ ] Dark mode toggle
