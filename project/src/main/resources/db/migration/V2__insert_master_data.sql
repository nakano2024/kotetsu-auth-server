
INSERT INTO user_roles(key, name)
    VALUES('021f1888-d72d-fe62-b8cc-4543ddcaa3ba', 'admin'),
    ('47bf8aa7-1122-dac8-3820-aae0282ad149', 'general');

INSERT INTO access_types(name) 
    VALUES('offline'),
    ('online');

INSERT INTO grant_types(name) 
    VALUES('authorization_code'),
    ('refresh_token');

INSERT INTO scopes(key, name, description)
    VALUES('2dd0280c-300a-e8ad-dbc5-db202beb34a0', 'openid', '認証情報の取得');
