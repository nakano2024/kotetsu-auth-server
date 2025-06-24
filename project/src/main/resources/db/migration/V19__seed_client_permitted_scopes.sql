-- Seed data for client_permitted_scopes table
-- Note: Use actual UUIDs from V17__seed_scopes.sql and V18__seed_client_informations.sql

INSERT INTO client_permitted_scopes (client_information_code, scope_code) VALUES
-- Web App Client permissions (User Management, Document, File Storage, Chat APIs)
('8c4f2a6e-9d1b-4e8c-f2a6-9d1b8c4f2a6e', '2d6a9f3c-8e1b-4c5f-9a7d-4e2c6f9a3b8d'), -- user.all
('8c4f2a6e-9d1b-4e8c-f2a6-9d1b8c4f2a6e', '5c9a2f6e-1d4b-4e8c-f2a9-6d1c5f8a2e4b'), -- document.all
('8c4f2a6e-9d1b-4e8c-f2a6-9d1b8c4f2a6e', '2c8f5a1e-9d4b-4c2f-8a5e-1d9c8f5a2e4b'), -- file.all
('8c4f2a6e-9d1b-4e8c-f2a6-9d1b8c4f2a6e', 'a4e8c2f9-6d1b-4a4e-c8f2-9d6a4e8c2f1b'), -- chat.all

-- Mobile App iOS permissions (User Management, Document, Notification, Calendar - read/write only)
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', '4b8e2f5c-9a1d-4e7b-a3c6-8f4d1b5e9a2c'), -- user.write
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', '7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f'), -- document.read
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', '3a6f9c2e-8d1b-4f5a-9c7e-2f4a6c9e1b8d'), -- document.write
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', '8b4f9c1e-6a2d-4e8b-f4c9-1e6a8f4c2d9b'), -- notification.read
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', '8e2c9f4a-6d1b-4e8c-f9a4-6d1e8c9f4a2b'), -- calendar.read
('3e9b5c1f-7a4d-4e3b-9c5f-7a4d3e9b5c1f', 'c9f6a2e4-8d1b-4c9f-a6e2-4d8c9f6a2e1b'), -- calendar.write

-- Mobile App Android permissions (same as iOS)
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', '4b8e2f5c-9a1d-4e7b-a3c6-8f4d1b5e9a2c'), -- user.write
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', '7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f'), -- document.read
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', '3a6f9c2e-8d1b-4f5a-9c7e-2f4a6c9e1b8d'), -- document.write
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', '8b4f9c1e-6a2d-4e8b-f4c9-1e6a8f4c2d9b'), -- notification.read
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', '8e2c9f4a-6d1b-4e8c-f9a4-6d1e8c9f4a2b'), -- calendar.read
('6f2d8a4c-1e9b-4f6d-2a8c-1e9b6f2d8a4c', 'c9f6a2e4-8d1b-4c9f-a6e2-4d8c9f6a2e1b'), -- calendar.write

-- Admin Dashboard permissions (all.all for every resource)
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', '2d6a9f3c-8e1b-4c5f-9a7d-4e2c6f9a3b8d'), -- user.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', '5c9a2f6e-1d4b-4e8c-f2a9-6d1c5f8a2e4b'), -- document.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', '9e4f2c6a-1d8b-4a9e-f5c2-8d1a6f4e9c2b'), -- analytics.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', '6e8a1f4c-2d9b-4a6e-f1c8-9d4a2f6e8c1b'), -- payment.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', 'f2e9c4a6-1d8b-4f2e-c9a4-6d1f2e9c4a8b'), -- notification.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', '2c8f5a1e-9d4b-4c2f-8a5e-1d9c8f5a2e4b'), -- file.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', 'a4e8c2f9-6d1b-4a4e-c8f2-9d6a4e8c2f1b'), -- chat.all
('9a5e1c7f-3d6b-4a95-e1c7-3d6b9a5e1c7f', 'f2c6a9e4-8d1b-4f2c-a6e9-4d8f2c6a9e1b'), -- calendar.all

-- Third Party Integration permissions (read only)
('4d8c2f6a-9e1b-4d84-c2f6-9e1b4d8c2f6a', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('4d8c2f6a-9e1b-4d84-c2f6-9e1b4d8c2f6a', '7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f'), -- document.read
('4d8c2f6a-9e1b-4d84-c2f6-9e1b4d8c2f6a', 'f8c4a1d9-2e6b-4f3c-8a5d-9f1e4c8a6b2d'), -- analytics.read

-- Developer Console permissions (read access to most APIs, no payment)
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', '7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f'), -- document.read
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', '9e4f2c6a-1d8b-4a9e-f5c2-8d1a6f4e9c2b'), -- analytics.all
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', '5a8c2f9e-6d1b-4a5c-8f2e-9d6a2c8f5e1b'), -- file.read
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', '7d4a9f6c-1e8b-4d7a-f9c6-1e8d4a9f6c2b'), -- chat.read
('7b5f9a3e-2c8d-4b75-f9a3-2c8d7b5f9a3e', '8e2c9f4a-6d1b-4e8c-f9a4-6d1e8c9f4a2b'), -- calendar.read

-- Analytics Client permissions (Analytics all + User read)
('1e6c4a8f-5d9b-4e16-c4a8-5d9b1e6c4a8f', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('1e6c4a8f-5d9b-4e16-c4a8-5d9b1e6c4a8f', '9e4f2c6a-1d8b-4a9e-f5c2-8d1a6f4e9c2b'), -- analytics.all

-- Test Client permissions (limited read access)
('e2f8c4a6-9d1b-4e2f-8c4a-9d1be2f8c4a6', 'a7f4c9e2-3d8b-4f1a-9e6c-5b2f8a4d7e1c'), -- user.read
('e2f8c4a6-9d1b-4e2f-8c4a-9d1be2f8c4a6', '7e1c4f9a-5d8b-4a2e-9f6c-1b4d7e9a5c2f'); -- document.read