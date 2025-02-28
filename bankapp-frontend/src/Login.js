import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    


      try {
        // Send a request to get customer details
        const response = await axios.get(`http://localhost:8081/api/customer/getCustomerDetails`, {
          params: { customerEmail: email }
        });

        // Handle the response (display details or redirect)
        if (response.data) {
          console.log("Customer details fetched:", response.data);
          navigate("/dashboard", { state: { customer: response.data } });
        } else {
          console.log("No customer found");
        }
      } catch (error) {
        console.error("Error fetching customer details:", error);
        alert("Error fetching details.");
      }
    
  };

  return (
    <div>
      <form onSubmit={handleLogin}>
        <input 
          type="email" 
          placeholder="Email" 
          value={email} 
          onChange={(e) => setEmail(e.target.value)} 
          required 
        />
        <input 
          type="password" 
          placeholder="Password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
          required 
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
