import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import Chat from './ChatAI';  // Correct path (assuming Chat.js is in the same folder as Dashboard.js)
import { Button } from "@mui/material";
import SendMoney from "./SendMoney";  // Import SendMoney component

const Dashboard = () => {
  const location = useLocation();
  const { customer } = location.state || {}; // Extract the customer details passed from the login page
  
  // State for controlling the visibility of the chat and SendMoney components
  const [isChatVisible, setIsChatVisible] = useState(false);
  const [isSendMoneyVisible, setIsSendMoneyVisible] = useState(false);

  // Toggle chat visibility
  const toggleChat = () => {
    setIsChatVisible(prevVisibility => !prevVisibility);
  };

  // Toggle SendMoney visibility
  const toggleSendMoney = () => {
    setIsSendMoneyVisible(prevVisibility => !prevVisibility);
  };

  // Fallback if customer data is not found
  if (!customer) {
    return <div>No customer details found.</div>;
  }

  return (
    <div className="dashboard-container">
      <h1>Welcome, {customer.name}!</h1>

      {/* Button to toggle Chat */}
      <Button 
        variant="contained" 
        color="primary" 
        onClick={toggleChat} 
        style={{ marginBottom: '20px' }}
      >
        {isChatVisible ? 'Close Chat' : 'Chat with AI'}
      </Button>

      {/* Conditionally render the Chat component */}
      {isChatVisible && <Chat customerId={customer.userId} />} 

      {/* Button to toggle SendMoney */}
      <Button
        variant="contained"
        color="secondary"
        onClick={toggleSendMoney}
        style={{ marginTop: '80px' , marginLeft:'-130px'}}
      >
        {isSendMoneyVisible ? 'Cancel Money Transfer' : 'Send Money'}
      </Button>

      {/* Conditionally render SendMoney component */}
      {isSendMoneyVisible && <SendMoney customerId={customer.userId} />}

      <div className="customer-details">
        <p><strong>Customer ID:</strong> {customer.customerId}</p>
        <p><strong>Bank Balance:</strong> ${customer.bankBalance}</p>
        <p><strong>Bank Details:</strong> {customer.bankName}</p>
        <p><strong>Email:</strong> {customer.customerEmail}</p>
      </div>
    </div>
  );
};

export default Dashboard;
