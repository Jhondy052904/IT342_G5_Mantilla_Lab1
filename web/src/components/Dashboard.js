import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Dashboard.css';

const Dashboard = () => {
  const { user, logout, getAuthHeader } = useAuth();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const API_URL = process.env.REACT_APP_API_URL;

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        setLoading(true);
        const headers = { ...getAuthHeader(), 'Content-Type': 'application/json' };
        const response = await fetch(`${API_URL}/user/me`, { headers });

        if (!response.ok) {
          throw new Error('Failed to fetch user details');
        }

        const data = await response.json();
        setUserDetails(data);
      } catch (err) {
        setError(err.message || 'Failed to load user details');
      } finally {
        setLoading(false);
      }
    };

    if (user) {
      fetchUserDetails();
    }
  }, [user, getAuthHeader, API_URL]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (loading) {
    return (
      <div className="dashboard-container">
        <div className="dashboard-loading">
          <div className="spinner"></div>
          <p>Loading profile...</p>
        </div>
      </div>
    );
  }

  const displayUser = userDetails || user;

  return (
    <div className="dashboard-container">
      <nav className="navbar">
        <div className="navbar-content">
          <h2 className="navbar-brand">IT342 Dashboard</h2>
          <button onClick={handleLogout} className="btn-logout">
            Logout
          </button>
        </div>
      </nav>

      <div className="dashboard-content">
        <div className="profile-card">
          <div className="profile-header">
            <div className="profile-avatar">
              {displayUser?.firstName?.charAt(0).toUpperCase()}
              {displayUser?.lastName?.charAt(0).toUpperCase()}
            </div>
            <h1>Welcome, {displayUser?.firstName}!</h1>
          </div>

          {error && <div className="error-message">{error}</div>}

          <div className="profile-section">
            <h2>Profile Information</h2>
            <div className="profile-details">
              <div className="detail-item">
                <label>First Name</label>
                <p>{displayUser?.firstName}</p>
              </div>
              <div className="detail-item">
                <label>Last Name</label>
                <p>{displayUser?.lastName}</p>
              </div>
              <div className="detail-item">
                <label>Email</label>
                <p>{displayUser?.email}</p>
              </div>
              <div className="detail-item">
                <label>User ID</label>
                <p>#{displayUser?.id || displayUser?.userId}</p>
              </div>
            </div>
          </div>

          <div className="profile-section">
            <h2>Account Status</h2>
            <div className="status-badge">
              <span className="status-indicator active"></span>
              <span>Active</span>
            </div>
          </div>

          <button onClick={handleLogout} className="btn-logout-full">
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
