-- Seed data for client_informations table

INSERT INTO client_informations (code, name, secret, redirect_uri, is_valid) VALUES
('8c4f2a6e-9d1b-4e8c-f2a6-9d1b8c4f2a6e', 'Web App Client', 'web_client_secret_12345', 'https://webapp.example.com/callback', true),
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', 'Mobile App iOS', 'ios_client_secret_67890', 'com.example.app://callback', true),
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', 'Mobile App Android', 'android_client_secret_abcde', 'com.example.android://callback', true),
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', 'Admin Dashboard', 'admin_client_secret_fghij', 'https://admin.example.com/auth/callback', true),
('4d8c2f6a-9e1b-4d84-c2f6-9e1b4d8c2f6a', 'Third Party Integration', 'third_party_secret_klmno', 'https://partner.example.com/oauth/callback', true),
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', 'Developer Console', 'dev_console_secret_pqrst', 'https://dev.example.com/oauth/return', true),
('1e6c4a8f-5d9b-4e16-c4a8-5d9b1e6c4a8f', 'Analytics Client', 'analytics_secret_uvwxy', 'https://analytics.example.com/auth', true),
('e2f8c4a6-9d1b-4e2f-8c4a-9d1be2f8c4a6', 'Test Client', 'test_client_secret_z1234', 'http://localhost:3000/callback', false);
