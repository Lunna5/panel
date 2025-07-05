CREATE TABLE allocations
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    node_id    BIGINT       NOT NULL,
    ip         VARCHAR(255) NOT NULL,
    ip_alias   VARCHAR(255) NULL,
    port       INT          NOT NULL,
    notes      VARCHAR(255) NULL,
    server_id  BIGINT NULL,
    is_primary BIT(1) DEFAULT 0 NULL,
    CONSTRAINT pk_allocations PRIMARY KEY (id)
);

CREATE TABLE group_permissions
(
    group_id      BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    CONSTRAINT pk_group_permissions PRIMARY KEY (group_id, permission_id)
);

CREATE TABLE group_users
(
    group_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT pk_group_users PRIMARY KEY (group_id, user_id)
);

CREATE TABLE locations
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    updated_at       datetime NULL,
    short_code       VARCHAR(255) NOT NULL,
    `description`    VARCHAR(255) NOT NULL,
    maintenance_mode BIT(1)       NOT NULL,
    enabled          BIT(1)       NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE nodes
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    uuid          BINARY(16)            NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    location_id   BIGINT       NOT NULL,
    fqdn          VARCHAR(191) NOT NULL,
    ssl_enabled   BIT(1)       NOT NULL,
    memory        INT          NOT NULL,
    disk          INT          NOT NULL,
    CONSTRAINT pk_nodes PRIMARY KEY (id)
);

CREATE TABLE organization_groups
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255) NOT NULL,
    `description`   VARCHAR(1000) NULL,
    organization_id BIGINT       NOT NULL,
    CONSTRAINT pk_organization_groups PRIMARY KEY (id)
);

CREATE TABLE organizations
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(1000) NULL,
    logo_url      VARCHAR(255) NULL,
    CONSTRAINT pk_organizations PRIMARY KEY (id)
);

CREATE TABLE permissions
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

CREATE TABLE role_permissions
(
    permission_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (permission_id, role_id)
);

CREATE TABLE roles
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE server_permissions
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(1000) NULL,
    CONSTRAINT pk_server_permissions PRIMARY KEY (id)
);

CREATE TABLE servers
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    created_at            datetime NULL,
    updated_at            datetime NULL,
    uuid                  BINARY(16)            NOT NULL,
    node_id               BIGINT       NOT NULL,
    name                  VARCHAR(255) NOT NULL,
    `description`         TEXT NULL,
    status                SMALLINT     NOT NULL,
    skip_scripts          BIT(1) NULL,
    owner_id              BIGINT NULL,
    organization_group_id BIGINT NULL,
    memory                INT          NOT NULL,
    swap                  INT          NOT NULL,
    disk                  INT          NOT NULL,
    io                    INT          NOT NULL,
    cpu                   INT          NOT NULL,
    threads               INT          NOT NULL,
    oom_disabled          BIT(1)       NOT NULL,
    main_allocation_id    BIGINT       NOT NULL,
    allocation_limit      INT NULL,
    database_limit        INT NULL,
    backup_limit          INT          NOT NULL,
    installed_at          datetime NULL,
    CONSTRAINT pk_servers PRIMARY KEY (id)
);

CREATE TABLE sessions
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    user_id    BIGINT NOT NULL,
    user_agent VARCHAR(255) NULL,
    ip_address VARCHAR(255) NULL,
    expires_at BIGINT NULL,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

CREATE TABLE user_organization
(
    organization_id BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    CONSTRAINT pk_user_organization PRIMARY KEY (organization_id, user_id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    updated_at  datetime NULL,
    email       VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NULL,
    password    VARCHAR(255) NULL,
    totp_secret VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE locations
    ADD CONSTRAINT uc_locations_shortcode UNIQUE (short_code);

ALTER TABLE nodes
    ADD CONSTRAINT uc_nodes_description UNIQUE (`description`);

ALTER TABLE nodes
    ADD CONSTRAINT uc_nodes_name UNIQUE (name);

ALTER TABLE nodes
    ADD CONSTRAINT uc_nodes_uuid UNIQUE (uuid);

ALTER TABLE permissions
    ADD CONSTRAINT uc_permissions_name UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE server_permissions
    ADD CONSTRAINT uc_server_permissions_name UNIQUE (name);

ALTER TABLE servers
    ADD CONSTRAINT uc_servers_uuid UNIQUE (uuid);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE allocations
    ADD CONSTRAINT FK_ALLOCATIONS_ON_NODE FOREIGN KEY (node_id) REFERENCES nodes (id);

ALTER TABLE allocations
    ADD CONSTRAINT FK_ALLOCATIONS_ON_SERVER FOREIGN KEY (server_id) REFERENCES servers (id);

ALTER TABLE nodes
    ADD CONSTRAINT FK_NODES_ON_LOCATION FOREIGN KEY (location_id) REFERENCES locations (id);

ALTER TABLE organization_groups
    ADD CONSTRAINT FK_ORGANIZATION_GROUPS_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organizations (id);

ALTER TABLE servers
    ADD CONSTRAINT FK_SERVERS_ON_MAIN_ALLOCATION FOREIGN KEY (main_allocation_id) REFERENCES allocations (id);

ALTER TABLE servers
    ADD CONSTRAINT FK_SERVERS_ON_NODE FOREIGN KEY (node_id) REFERENCES nodes (id);

ALTER TABLE servers
    ADD CONSTRAINT FK_SERVERS_ON_ORGANIZATION_GROUP FOREIGN KEY (organization_group_id) REFERENCES organization_groups (id);

ALTER TABLE servers
    ADD CONSTRAINT FK_SERVERS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE group_permissions
    ADD CONSTRAINT fk_groper_on_organization_group_model FOREIGN KEY (group_id) REFERENCES organization_groups (id);

ALTER TABLE group_permissions
    ADD CONSTRAINT fk_groper_on_server_permission_model FOREIGN KEY (permission_id) REFERENCES server_permissions (id);

ALTER TABLE group_users
    ADD CONSTRAINT fk_grouse_on_organization_group_model FOREIGN KEY (group_id) REFERENCES organization_groups (id);

ALTER TABLE group_users
    ADD CONSTRAINT fk_grouse_on_user_model FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_permission_model FOREIGN KEY (permission_id) REFERENCES permissions (id);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_role_model FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_organization
    ADD CONSTRAINT fk_useorg_on_organization_model FOREIGN KEY (organization_id) REFERENCES organizations (id);

ALTER TABLE user_organization
    ADD CONSTRAINT fk_useorg_on_user_model FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role_model FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user_model FOREIGN KEY (user_id) REFERENCES users (id);