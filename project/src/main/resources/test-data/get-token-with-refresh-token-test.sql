
INSERT INTO files(key, url)
    VALUES(
        '1b210543-8f30-762f-6467-ea1aeca1fabc',
        'https://example.com/user/image/1b210543-8f30-762f-6467-ea1aeca1fabc'
    );

INSERT INTO users(key, name, email, password_hash, role_key, is_active)
    VALUES(
        'e3714a8a-16d6-e645-218b-4276371791c2',
        '田中太郎',
        'tanaka@example.com',
        '$2a$08$4p9u7J2OBNzu.PTf1ZB9peWx1AKCNkUifWRUwFzZr24Vgq95WyfJ2',
        '47bf8aa7-1122-dac8-3820-aae0282ad149',
        true
    );

INSERT INTO user_image_files(user_key, file_key)
    VALUES(
        'e3714a8a-16d6-e645-218b-4276371791c2',
        '1b210543-8f30-762f-6467-ea1aeca1fabc'
    );

INSERT INTO scopes(key, name, description)
    VALUES('0ebfaea3-4ffd-9913-b576-47c3cc5c8c1e', 'task.delete', 'タスクの削除'),
    ('ea1103c9-43df-ea19-fc61-0a813220588b', 'task.write', 'タスクの編集'),
    ('05bad623-ade2-3e2c-a518-9dac5c0652e1', 'task.read', 'タスクの読み取り'),
    ('f07a880c-d115-2589-3475-3afa4955ca1d', 'file.write', 'ファイルの編集'),
    ('5e271a1d-244c-989c-5f0c-3f442b9a6bfa', 'file.create', 'ファイルの作成'),
    ('ac2ad67e-41f6-5bd9-96f0-1b4bff52565d', 'file.read', 'ファイルの読み取り');

INSERT INTO resource_servers(key, name, url)
    VALUES(
        'd5e0bb5c-c1eb-f08a-e7ab-c2cdc01eaa4a',
        'タスク管理アプリ',
        'https://task-manager.example.com'
    ),
    (
        'd569e46c-a755-306f-47e5-6bc26be95dfa',
        'ファイル管理アプリ',
        'https://file-manager.example.com'
    );

INSERT INTO scope_audiences(scope_key, resource_server_key)
    VALUES(
        '0ebfaea3-4ffd-9913-b576-47c3cc5c8c1e',
        'd5e0bb5c-c1eb-f08a-e7ab-c2cdc01eaa4a'
    ),
    (
        'ea1103c9-43df-ea19-fc61-0a813220588b',
        'd5e0bb5c-c1eb-f08a-e7ab-c2cdc01eaa4a'
    ),
    (
        '05bad623-ade2-3e2c-a518-9dac5c0652e1',
        'd5e0bb5c-c1eb-f08a-e7ab-c2cdc01eaa4a'
    ),
    (
        'f07a880c-d115-2589-3475-3afa4955ca1d',
        'd569e46c-a755-306f-47e5-6bc26be95dfa'
    ),
    (
        '5e271a1d-244c-989c-5f0c-3f442b9a6bfa',
        'd569e46c-a755-306f-47e5-6bc26be95dfa'
    ),
    (
        'ac2ad67e-41f6-5bd9-96f0-1b4bff52565d',
        'd569e46c-a755-306f-47e5-6bc26be95dfa'
    );

INSERT INTO clients(key, client_id, client_secret_hash)
    VALUES(
        'a6c0c972-5517-c789-5aae-c526c9969974',
        '94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com',
        '$2a$08$XdggoeA6f2uvZ07PlhHtqeRq6f/PXB3WnFsWmlUp.DNlEKwDdzCEC'
    );

INSERT INTO client_redirects(client_key, redirect_uri)
    VALUES(
        'a6c0c972-5517-c789-5aae-c526c9969974',
        'https://client.example.com/callback'
    );

INSERT INTO client_permitted_scopes(client_key, scope_key)
    VALUES(
        'a6c0c972-5517-c789-5aae-c526c9969974',
        '0ebfaea3-4ffd-9913-b576-47c3cc5c8c1e'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        'ea1103c9-43df-ea19-fc61-0a813220588b'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        '05bad623-ade2-3e2c-a518-9dac5c0652e1'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        'f07a880c-d115-2589-3475-3afa4955ca1d'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        '5e271a1d-244c-989c-5f0c-3f442b9a6bfa'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        'ac2ad67e-41f6-5bd9-96f0-1b4bff52565d'
    ),
    (
        'a6c0c972-5517-c789-5aae-c526c9969974',
        '2dd0280c-300a-e8ad-dbc5-db202beb34a0'
    );

INSERT INTO access_token_cores(key, issuer, subject, client_id)
    VALUES(
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71',
        'https://auth.example.com',
        'e3714a8a-16d6-e645-218b-4276371791c2',
        '94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com'
    );

INSERT INTO access_token_core_scopes (access_token_core_key, scope_key)
    VALUES
    (
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71',
        '05bad623-ade2-3e2c-a518-9dac5c0652e1'
    ),
    (
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71',
        '5e271a1d-244c-989c-5f0c-3f442b9a6bfa'
    ),
    (
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71',
        '2dd0280c-300a-e8ad-dbc5-db202beb34a0'
    );

INSERT INTO id_token_cores(key, issuer, audience, subject, nonce)
    VALUES
    (
        '7f9a2b4c-3e1d-8f6a-9c5b-1a8e7d4f2b6c',
        'https://auth.example.com',
        '94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com',
        'e3714a8a-16d6-e645-218b-4276371791c2',
        'random_nonce_string_xyz123'
    );

INSERT INTO refresh_token_cores(key, access_token_core_key, id_token_core_key)
    VALUES
    (
        '9d8c7b6a-5f4e-3d2c-1b0a-9e8d7c6b5a4f',
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71',
        '7f9a2b4c-3e1d-8f6a-9c5b-1a8e7d4f2b6c'
    );

INSERT INTO access_tokens(value, issued_at, expired_at, access_token_core_key)
    VALUES
    (
        'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.wFOGcNAgstf0r4hCLBkVJ46DayBNc5AN0oHLm0FwYYPOFoFepe6c7cjV3KLGNexysXWo0h4SftwfMP0lJRUPLlgbWdV2IhwrrnXfQMEBY6QmYIqjKnXGRGlNUVvy4UGN',
        '2025-09-30 00:00:00+00',
        '2025-09-30 01:00:00+00',
        '3bc642a5-5a1e-709c-0f78-356ee9b89a71'
    );

INSERT INTO id_token_metas(issued_at, expired_at, id_token_core_key)
    VALUES
    (
        '2025-09-30 00:00:00+00',
        '2025-09-30 01:00:00+00',
        '7f9a2b4c-3e1d-8f6a-9c5b-1a8e7d4f2b6c'
    );

INSERT INTO refresh_tokens(value, issued_at, expired_at, refresh_token_core_key, grant_type_name)
    VALUES
    (
        'rt_XyZw9876543210abcdef1234567890',
        '2025-09-23 00:00:00+00',
        '2025-09-30 00:00:00+00',
        '9d8c7b6a-5f4e-3d2c-1b0a-9e8d7c6b5a4f',
        'refresh_token'
    );
