import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Login from "./Login"; // Create a Login.js file
import Register from "./Register"; // Create a Register.js file
import Dashboard from "./Dashboard"; // Create a Dashboard.js file
import SendMoney from "./SendMoney";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/SendMoney" element={<SendMoney/>} />
      </Routes>
    </Router>
  );
};

export default App;
