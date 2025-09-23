
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

INSERT INTO scopes(key, name)
    VALUES('0ebfaea3-4ffd-9913-b576-47c3cc5c8c1e', 'task.delete'),
    ('ea1103c9-43df-ea19-fc61-0a813220588b', 'task.write'),
    ('05bad623-ade2-3e2c-a518-9dac5c0652e1', 'task.read'),
    ('f07a880c-d115-2589-3475-3afa4955ca1d', 'file.write'),
    ('5e271a1d-244c-989c-5f0c-3f442b9a6bfa', 'file.create'),
    ('ac2ad67e-41f6-5bd9-96f0-1b4bff52565d', 'file.read');

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
