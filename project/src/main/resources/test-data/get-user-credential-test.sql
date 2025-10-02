
INSERT INTO files(key, url)
    VALUES(
        '1b210543-8f30-762f-6467-ea1aeca1fabc',
        'https://file.example.com/1b210543-8f30-762f-6467-ea1aeca1fabc'
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
