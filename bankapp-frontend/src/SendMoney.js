import React, { useState } from 'react';
import { TextField, Button, Box, CircularProgress, Typography } from '@mui/material';
import axios from 'axios';

const SendMoney = ({ customerId }) => {
  const [step, setStep] = useState(1);
  const [receiverCustomerId, setReceiverCustomerId] = useState('');
  const [amount, setAmount] = useState('');
  const [senderBankId, setSenderBankId] = useState('');
  const [receiverBankId, setReceiverBankId] = useState('');
  const [isProcessing, setIsProcessing] = useState(false);
  const [error, setError] = useState('');

  // Validate inputs before proceeding to the next step
  const validateStep = () => {
    switch (step) {
      case 1:
        return receiverCustomerId.trim() !== '';
      case 2:
        return !isNaN(amount) && amount > 0;
      case 3:
        return senderBankId.trim() !== '' && receiverBankId.trim() !== '';
      default:
        return true;
    }
  };

  // Proceed to the next step after validation
  const nextStep = () => {
    if (validateStep()) {
      setError('');
      setStep(prevStep => prevStep + 1);
    } else {
      setError('Please fill in all fields correctly.');
    }
  };

  // Handle payment creation (POST request)
  const handlePaymentCreation = async () => {
    setIsProcessing(true);
    try {
      const response = await axios.post('http://localhost:8081/api/payments/create', {
        userId: customerId,
        receiverCustomerId: receiverCustomerId,
        amount: amount,
        senderBankId: senderBankId,
        receiverBankId: receiverBankId
      });
      setIsProcessing(false);
      alert('Payment created successfully!');
      // Reset the form or handle response as needed
    } catch (error) {
      setIsProcessing(false);
      setError('There was an error processing the payment. Please try again.');
    }
  };

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4">Send Money</Typography>
      {step === 1 && (
        <div>
          <TextField
            label="Receiver Customer ID"
            value={receiverCustomerId}
            onChange={(e) => setReceiverCustomerId(e.target.value)}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <Button variant="contained" color="primary" onClick={nextStep} fullWidth>
            Next
          </Button>
        </div>
      )}

      {step === 2 && (
        <div>
          <TextField
            label="Amount"
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <Button variant="contained" color="primary" onClick={nextStep} fullWidth>
            Next
          </Button>
        </div>
      )}

      {step === 3 && (
        <div>
          <TextField
            label="Sender Bank ID"
            value={senderBankId}
            onChange={(e) => setSenderBankId(e.target.value)}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <TextField
            label="Receiver Bank ID"
            value={receiverBankId}
            onChange={(e) => setReceiverBankId(e.target.value)}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <Button variant="contained" color="primary" onClick={handlePaymentCreation} fullWidth>
            {isProcessing ? <CircularProgress size={24} color="inherit" /> : 'Create Payment'}
          </Button>
        </div>
      )}

      {error && (
        <Typography color="error" sx={{ marginTop: 2 }}>
          {error}
        </Typography>
      )}
    </Box>
  );
};

export default SendMoney;
