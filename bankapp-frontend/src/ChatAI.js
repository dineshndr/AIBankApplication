import React, { useState } from 'react';
import { TextField, Button, List, ListItem, ListItemText, Paper, Box } from '@mui/material';
import { styled } from '@mui/system';
import axios from 'axios';
import ChatIcon from '@mui/icons-material/Chat'; // Import the chat icon for the button
import CloseIcon from '@mui/icons-material/Close'; // Import the close icon for the close button

// Styled components for the chat UI
const ChatContainer = styled(Box)(() => ({
  display: 'flex',
  flexDirection: 'column',
  height: '100vh',
  width: '400px', // Sidebar width
  position: 'fixed',
  right: 0,
  top: 0,
  backgroundColor: 'white',
  boxShadow: '-2px 0 5px rgba(0, 0, 0, 0.3)',
  transform: 'translateX(100%)', // Initially off-screen
  transition: 'transform 0.3s ease', // Smooth transition
  zIndex: 1000,
}));

const ChatBox = styled(Paper)(() => ({
  overflowY: 'auto',
  flexGrow: 1,
  padding: '16px',
  marginBottom: '10px',
  maxHeight: '500px', // Optional: add a max height to the chat box
}));

const ChatInput = styled(TextField)(() => ({
  width: '100%',
}));

const SendButton = styled(Button)(() => ({
  marginLeft: '8px',
}));

// Styled round chat button
const ChatButton = styled(Button)(() => ({
  position: 'fixed',
  bottom: '20px',
  right: '20px',
  borderRadius: '50%',
  width: '60px',
  height: '60px',
  backgroundColor: '#007bff',
  color: 'white',
  boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
  '&:hover': {
    backgroundColor: '#0056b3',
  },
}));

// Styled Close button inside the chat box
const CloseButton = styled(Button)(() => ({
  position: 'absolute',
  top: '10px',
  right: '10px',
  minWidth: 'auto',
  padding: '12px', // Increased padding for larger touch area
  backgroundColor: 'transparent',
  color: '#ff0000',
  '&:hover': {
    backgroundColor: 'transparent',
  },
  fontSize: '24px', // Increase the size of the close icon
}));

// Chat Component
const Chat = ({ customerId }) => {
  const [userQuery, setUserQuery] = useState('');
  const [messages, setMessages] = useState([
    { sender: 'bot', text: 'How can I assist you today?' },
  ]);
  const [isChatVisible, setIsChatVisible] = useState(false);

  // Toggle chat visibility
  const toggleChat = () => {
    setIsChatVisible((prevVisibility) => !prevVisibility);
  };

  // Close chat when "X" button is clicked
  const closeChat = () => {
    setIsChatVisible(false);
  };

  // Handle submit (send the query)
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (userQuery.trim()) {
      setMessages((prevMessages) => [
        ...prevMessages,
        { sender: 'user', text: userQuery },
      ]);

      try {
        console.log("Customer ID" + customerId);
        // Send the request with customerId as a query parameter
        const response = await axios.post(
          `http://localhost:8081/api/customer/transaction/history?customerId=${customerId}`,
          { customerQuery: userQuery }
        );

        const botMessage = response.data.length > 0
          ? `Transaction History:\n\n${response.data.map(payment => `
            - Sender: ${payment.senderBank.bankName} (${payment.customer.name})
            Receiver: ${payment.receiverBank.bankName} (${payment.receiverCustomer.name})
            Amount: $${payment.amount}
            Status: ${payment.status}
            Created At: ${new Date(payment.createdAt).toLocaleString()}
          `).join("\n")}`
          : "Sorry, I couldn't find your transaction history.";

        setMessages((prevMessages) => [
          ...prevMessages,
          { sender: 'user', text: userQuery },
          { sender: 'bot', text: botMessage },
        ]);
      } catch (error) {
        console.error("Error details:", error); // Detailed error logging
        setMessages((prevMessages) => [
          ...prevMessages,
          { sender: 'user', text: userQuery },
          { sender: 'bot', text: "There was an error processing your request. Please try again." },
        ]);
      }

      setUserQuery(''); // Clear input field
    }
  };

  return (
    <div>
      {/* Round button to toggle chat */}
      <ChatButton
        variant="contained"
        onClick={toggleChat}
        startIcon={<ChatIcon />}
      />

      {/* Sidebar chat UI */}
      <ChatContainer style={{ transform: isChatVisible ? 'translateX(0)' : 'translateX(100%)' }}>
        {/* Close button to close chat */}
        <CloseButton onClick={closeChat}>
          <CloseIcon />
        </CloseButton>

        <ChatBox>
          <List>
            {messages.map((message, index) => (
              <ListItem key={index}>
                <ListItemText
                  primary={<pre style={{ whiteSpace: "pre-wrap", wordWrap: "break-word" }}>{message.text}</pre>}
                  style={{ textAlign: message.sender === 'bot' ? 'left' : 'right' }}
                />
              </ListItem>
            ))}
          </List>
        </ChatBox>

        <Box display="flex" justifyContent="space-between" alignItems="center">
          <ChatInput
            label="Ask me anything..."
            value={userQuery}
            onChange={(e) => setUserQuery(e.target.value)}
          />
          <SendButton variant="contained" color="primary" onClick={handleSubmit}>
            Send
          </SendButton>
        </Box>
      </ChatContainer>
    </div>
  );
};

export default Chat;
