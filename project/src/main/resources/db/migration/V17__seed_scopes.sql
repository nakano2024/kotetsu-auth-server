-- Seed data for scopes table
-- Note: resource_server_code values must match the UUIDs from V16__seed_resource_servers.sql

INSERT INTO scopes (code, resource_server_code, name) VALUES
-- User Management API scopes
('a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c', '3f7a9b2e-8c1d-4f6a-9e3b-7c5d8f2a6b4e', 'user.read'),
('4b8e2f5c-9a1d-4e7b-a3c6-8f4d1b5e9a2c', '3f7a9b2e-8c1d-4f6a-9e3b-7c5d8f2a6b4e', 'user.write'),
('9c5f1a8e-6d2b-4f9c-8e1a-3f6b9c2d5a7e', '3f7a9b2e-8c1d-4f6a-9e3b-7c5d8f2a6b4e', 'user.delete'),
('2d6a9f3c-8e1b-4c5f-9a7d-4e2c6f9a3b8d', '3f7a9b2e-8c1d-4f6a-9e3b-7c5d8f2a6b4e', 'user.all'),

-- Document API scopes
('7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f', '9d4f2c8b-6e1a-4b9f-8c2d-5a7f3e9b6c1d', 'document.read'),
('3a6f9c2e-8d1b-4f5a-9c7e-2f4a6c9e1b8d', '9d4f2c8b-6e1a-4b9f-8c2d-5a7f3e9b6c1d', 'document.write'),
('8d2f5a9c-6e1b-4c8f-a5d2-9c4e8a1f6b3d', '9d4f2c8b-6e1a-4b9f-8c2d-5a7f3e9b6c1d', 'document.delete'),
('5c9a2f6e-1d4b-4e8c-f2a9-6d1c5f8a2e4b', '9d4f2c8b-6e1a-4b9f-8c2d-5a7f3e9b6c1d', 'document.all'),

-- Analytics API scopes
('f8c4a1d9-2e6b-4f3c-8a5d-9f1e4c8a6b2d', '6b8e3f1c-9a2d-4e7b-a1c5-8f9d2b4e6a3c', 'analytics.read'),
('1b6e9f4c-8a2d-4c1f-9e6b-4d8c1f9a2e5b', '6b8e3f1c-9a2d-4e7b-a1c5-8f9d2b4e6a3c', 'analytics.write'),
('6a2c8f1e-9d4b-4e6a-c8f1-2d9b6e4a1c8f', '6b8e3f1c-9a2d-4e7b-a1c5-8f9d2b4e6a3c', 'analytics.delete'),
('9e4f2c6a-1d8b-4a9e-f5c2-8d1a6f4e9c2b', '6b8e3f1c-9a2d-4e7b-a1c5-8f9d2b4e6a3c', 'analytics.all'),

-- Payment API scopes
('4c7f1a9e-6d2b-4e4c-9f8a-1e6c4f9a2d7b', '2e7c9f4a-5d1b-4a8e-9c3f-6b2d7a5f8e1c', 'payment.read'),
('2f8d6a1c-9e4b-4c2f-a7d9-6e1c8f4a2b9d', '2e7c9f4a-5d1b-4a8e-9c3f-6b2d7a5f8e1c', 'payment.write'),
('a9c2f6e1-4d8b-4f9a-c2e6-8d1f4a9c6e2b', '2e7c9f4a-5d1b-4a8e-9c3f-6b2d7a5f8e1c', 'payment.delete'),
('6e8a1f4c-2d9b-4a6e-f1c8-9d4a2f6e8c1b', '2e7c9f4a-5d1b-4a8e-9c3f-6b2d7a5f8e1c', 'payment.all'),

-- Notification API scopes
('8b4f9c1e-6a2d-4e8b-f4c9-1e6a8f4c2d9b', '8a5f2d9b-3e6c-4f1a-b7d9-4c8e1f5a2b6d', 'notification.read'),
('3d9e6f2a-8c1b-4d3f-9e6a-2c8b4f9e6a1d', '8a5f2d9b-3e6c-4f1a-b7d9-4c8e1f5a2b6d', 'notification.write'),
('c1a6f9e2-4d8b-4c1a-f6e9-8d2c1f6a9e4b', '8a5f2d9b-3e6c-4f1a-b7d9-4c8e1f5a2b6d', 'notification.delete'),
('f2e9c4a6-1d8b-4f2e-c9a4-6d1f2e9c4a8b', '8a5f2d9b-3e6c-4f1a-b7d9-4c8e1f5a2b6d', 'notification.all'),

-- File Storage API scopes
('5a8c2f9e-6d1b-4a5c-8f2e-9d6a2c8f5e1b', '1c6f9a4e-7b2d-4e5c-8f1a-9d3b6e4c7f2a', 'file.read'),
('e4b9c6a2-8d1f-4e4b-c9a6-2f8d1e4b9c6a', '1c6f9a4e-7b2d-4e5c-8f1a-9d3b6e4c7f2a', 'file.write'),
('9f1e4c8a-2d6b-4f9e-4c1a-8d6f1e4c8a2b', '1c6f9a4e-7b2d-4e5c-8f1a-9d3b6e4c7f2a', 'file.delete'),
('2c8f5a1e-9d4b-4c2f-8a5e-1d9c8f5a2e4b', '1c6f9a4e-7b2d-4e5c-8f1a-9d3b6e4c7f2a', 'file.all'),

-- Chat API scopes
('7d4a9f6c-1e8b-4d7a-f9c6-1e8d4a9f6c2b', '5d2a8f1c-4e9b-4c7f-a3d6-2f8b1e5c9a4d', 'chat.read'),
('b6c1f8a4-9e2d-4b6c-f1a8-4e9b6c1f8a2d', '5d2a8f1c-4e9b-4c7f-a3d6-2f8b1e5c9a4d', 'chat.write'),
('1f9c6a4e-8d2b-4f1c-9a6e-4d8f1c9a6e2b', '5d2a8f1c-4e9b-4c7f-a3d6-2f8b1e5c9a4d', 'chat.delete'),
('a4e8c2f9-6d1b-4a4e-c8f2-9d6a4e8c2f1b', '5d2a8f1c-4e9b-4c7f-a3d6-2f8b1e5c9a4d', 'chat.all'),

-- Calendar API scopes
('8e2c9f4a-6d1b-4e8c-f9a4-6d1e8c9f4a2b', 'f4c7a9d2-1e5b-4f8c-9a6d-3b7f4e2c8a5d', 'calendar.read'),
('c9f6a2e4-8d1b-4c9f-a6e2-4d8c9f6a2e1b', 'f4c7a9d2-1e5b-4f8c-9a6d-3b7f4e2c8a5d', 'calendar.write'),
('4a6e9c2f-1d8b-4a4e-c9f2-6d1a4e9c2f8b', 'f4c7a9d2-1e5b-4f8c-9a6d-3b7f4e2c8a5d', 'calendar.delete'),
('f2c6a9e4-8d1b-4f2c-a6e9-4d8f2c6a9e1b', 'f4c7a9d2-1e5b-4f8c-9a6d-3b7f4e2c8a5d', 'calendar.all');