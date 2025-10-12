-- UUID拡張
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS trigger AS $$
BEGIN
  NEW.updated_at := current_timestamp;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- user_roles
CREATE TABLE IF NOT EXISTS user_roles (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name        varchar(32) NOT NULL,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_user_roles_updated_at
BEFORE UPDATE ON user_roles
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- users
CREATE TABLE IF NOT EXISTS users (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name        varchar(64) NOT NULL,
  email       varchar(128) NOT NULL,
  password_hash varchar(512) NOT NULL,
  role_key    uuid REFERENCES user_roles(key) ON DELETE CASCADE,
  is_active   boolean NOT NULL DEFAULT true,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp,
  CONSTRAINT uq_users_email UNIQUE (email)
);
CREATE TRIGGER trg_users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- files
CREATE TABLE IF NOT EXISTS files (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  url         text NOT NULL,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_files_updated_at
BEFORE UPDATE ON files
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- user_image_files
CREATE TABLE IF NOT EXISTS user_image_files (
  user_key    uuid NOT NULL REFERENCES users(key) ON DELETE CASCADE,
  file_key    uuid NOT NULL REFERENCES files(key) ON DELETE CASCADE,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (user_key, file_key)
);
CREATE TRIGGER trg_user_image_files_updated_at
BEFORE UPDATE ON user_image_files
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- clients
CREATE TABLE IF NOT EXISTS clients (
  key           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  client_id     varchar(128) NOT NULL,
  name varchar(128) NOT NULL,
  client_secret_hash varchar(128) NOT NULL,
  created_at    timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at    timestamptz NOT NULL DEFAULT current_timestamp,
  CONSTRAINT uq_clients_client_id UNIQUE (client_id)
);
CREATE TRIGGER trg_clients_updated_at
BEFORE UPDATE ON clients
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- client_redirects
CREATE TABLE IF NOT EXISTS client_redirects (
  key           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  client_key    uuid NOT NULL REFERENCES clients(key) ON DELETE CASCADE,
  redirect_uri  varchar(512) NOT NULL,
  created_at    timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at    timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_client_redirects_updated_at
BEFORE UPDATE ON client_redirects
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- access_types
CREATE TABLE IF NOT EXISTS access_types (
  name        varchar(64) PRIMARY KEY,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_access_types_updated_at
BEFORE UPDATE ON access_types
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- grant_types
CREATE TABLE IF NOT EXISTS grant_types (
  name        varchar(64) PRIMARY KEY,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_grant_types_updated_at
BEFORE UPDATE ON grant_types
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- resource_servers
CREATE TABLE IF NOT EXISTS resource_servers (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name        varchar(128) NOT NULL,
  url  varchar(512) NOT NULL,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_resource_servers_updated_at
BEFORE UPDATE ON resource_servers
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- scopes
CREATE TABLE IF NOT EXISTS scopes (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name        varchar(128) NOT NULL,
  description varchar(128) NOT NULL,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp,
  CONSTRAINT uq_scopes_name UNIQUE (name)
);
CREATE TRIGGER trg_scopes_updated_at
BEFORE UPDATE ON scopes
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- scope_audiences
CREATE TABLE IF NOT EXISTS scope_audiences (
  scope_key           uuid NOT NULL REFERENCES scopes(key) ON DELETE CASCADE,
  resource_server_key uuid NOT NULL REFERENCES resource_servers(key) ON DELETE CASCADE,
  created_at          timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at          timestamptz NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (scope_key, resource_server_key)
);
CREATE TRIGGER trg_scope_audiences_updated_at
BEFORE UPDATE ON scope_audiences
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- client_permitted_scopes
CREATE TABLE IF NOT EXISTS client_permitted_scopes (
  client_key  uuid NOT NULL REFERENCES clients(key) ON DELETE CASCADE,
  scope_key   uuid NOT NULL REFERENCES scopes(key) ON DELETE CASCADE,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (client_key, scope_key)
);
CREATE TRIGGER trg_client_permitted_scopes_updated_at
BEFORE UPDATE ON client_permitted_scopes
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- access_token_cores
CREATE TABLE IF NOT EXISTS access_token_cores (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  issuer      varchar(128) NOT NULL,
  subject     uuid REFERENCES users(key) ON DELETE CASCADE,
  client_id   varchar(128) NOT NULL REFERENCES clients(client_id) ON DELETE CASCADE,
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_access_token_cores_updated_at
BEFORE UPDATE ON access_token_cores
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- access_token_core_scopes
CREATE TABLE IF NOT EXISTS access_token_core_scopes (
  access_token_core_key uuid NOT NULL REFERENCES access_token_cores(key) ON DELETE CASCADE,
  scope_key             uuid NOT NULL REFERENCES scopes(key) ON DELETE CASCADE,
  created_at            timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at            timestamptz NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (access_token_core_key, scope_key)
);
CREATE TRIGGER trg_access_token_core_scopes_updated_at
BEFORE UPDATE ON access_token_core_scopes
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- id_token_cores
CREATE TABLE IF NOT EXISTS id_token_cores (
  key         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  issuer      varchar(256) NOT NULL,
  audience    varchar(256) NOT NULL REFERENCES clients(client_id) ON DELETE CASCADE,
  subject     uuid REFERENCES users(key) ON DELETE CASCADE,
  nonce       varchar(128),
  created_at  timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at  timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_id_token_cores_updated_at
BEFORE UPDATE ON id_token_cores
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- refresh_token_cores
CREATE TABLE IF NOT EXISTS refresh_token_cores (
  key                   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  access_token_core_key uuid REFERENCES access_token_cores(key) ON DELETE CASCADE,
  id_token_core_key     uuid REFERENCES id_token_cores(key)     ON DELETE CASCADE,
  created_at            timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at            timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_refresh_token_cores_updated_at
BEFORE UPDATE ON refresh_token_cores
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- authorization_codes
CREATE TABLE IF NOT EXISTS authorization_codes (
  key                    uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  value                  varchar(256) NOT NULL,
  challenge              varchar(256),
  expired_at             timestamptz NOT NULL,
  access_type_name       varchar(64) REFERENCES access_types(name),
  grant_type_name        varchar(64) REFERENCES grant_types(name),
  access_token_core_key  uuid REFERENCES access_token_cores(key),
  id_token_core_key      uuid REFERENCES id_token_cores(key),
  refresh_token_core_key uuid REFERENCES refresh_token_cores(key),
  created_at             timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at             timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_authorization_codes_updated_at
BEFORE UPDATE ON authorization_codes
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- access_tokens
CREATE TABLE IF NOT EXISTS access_tokens (
  key                   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  value                 varchar(256) NOT NULL,
  access_token_core_key uuid REFERENCES access_token_cores(key),
  issued_at             timestamptz NOT NULL,
  expired_at            timestamptz NOT NULL,
  created_at            timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at            timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_access_tokens_updated_at
BEFORE UPDATE ON access_tokens
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- id_token_metas
CREATE TABLE IF NOT EXISTS id_token_metas (
  key               uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  id_token_core_key uuid REFERENCES id_token_cores(key),
  issued_at         timestamptz NOT NULL,
  expired_at        timestamptz NOT NULL,
  created_at        timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at        timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_id_token_metas_updated_at
BEFORE UPDATE ON id_token_metas
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- refresh_tokens
CREATE TABLE IF NOT EXISTS refresh_tokens (
  key                    uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  value                  varchar(256) NOT NULL,
  refresh_token_core_key uuid REFERENCES refresh_token_cores(key),
  grant_type_name        varchar(64) REFERENCES grant_types(name),
  issued_at              timestamptz NOT NULL,
  expired_at             timestamptz NOT NULL,
  created_at             timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at             timestamptz NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER trg_refresh_tokens_updated_at
BEFORE UPDATE ON refresh_tokens
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();
