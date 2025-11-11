package com.proj.toll_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Toll Plaza Finder API</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            max-width: 800px;
                            margin: 50px auto;
                            padding: 20px;
                            background-color: #f5f5f5;
                        }
                        .container {
                            background: white;
                            padding: 30px;
                            border-radius: 8px;
                            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                        }
                        h1 {
                            color: #333;
                        }
                        .endpoint {
                            background: #f8f9fa;
                            padding: 15px;
                            border-left: 4px solid #007bff;
                            margin: 20px 0;
                        }
                        code {
                            background: #e9ecef;
                            padding: 2px 6px;
                            border-radius: 3px;
                            font-family: 'Courier New', monospace;
                        }
                        .example {
                            background: #fff3cd;
                            padding: 15px;
                            border-radius: 4px;
                            margin: 15px 0;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🚗 Toll Plaza Finder API</h1>
                        <p>Welcome! The API is running successfully.</p>
                        
                        <div class="endpoint">
                            <h3>Available Endpoint:</h3>
                            <p><strong>POST</strong> <code>/api/v1/toll-plazas</code></p>
                            <p>Find toll plazas between two pincodes</p>
                        </div>
                        
                        <div class="example">
                            <h4>Example Request:</h4>
                            <pre>
POST http://localhost:8080/api/v1/toll-plazas
Content-Type: application/json

{
    "sourcePincode": "560001",
    "destinationPincode": "411001"
}
                            </pre>
                        </div>
                        
                        <h4>Using cURL:</h4>
                        <code>
                            curl -X POST http://localhost:8080/api/v1/toll-plazas \\<br>
                            &nbsp;&nbsp;-H "Content-Type: application/json" \\<br>
                            &nbsp;&nbsp;-d '{"sourcePincode":"560001","destinationPincode":"411001"}'
                        </code>
                    </div>
                </body>
                </html>
                """;
    }
}