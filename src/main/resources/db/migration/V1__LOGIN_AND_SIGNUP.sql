-- Profile table creation (with Basic profile)
CREATE TABLE IF NOT EXISTS tb_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

INSERT INTO tb_profile (name)
VALUES ('Basic');

-- Role table creation (with CASH_FLOW_BASICS role)
CREATE TABLE IF NOT EXISTS tb_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_value VARCHAR(100) NOT NULL
);

INSERT INTO tb_role (role_value)
VALUES ('CASH_FLOW_BASICS');

-- Profile x Role table creation (with Basic -> CASH_FLOW_BASICS)
CREATE TABLE IF NOT EXISTS tb_profile_roles (
    profile_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (profile_id, role_id),
    FOREIGN KEY (profile_id) REFERENCES tb_profile(id),
    FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

INSERT INTO tb_profile_roles (profile_id, role_id)
VALUES (1, 1);

-- User table creation
CREATE TABLE IF NOT EXISTS tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NULL,
    account_type VARCHAR(15) NOT NULL,
    profile BIGINT NOT NULL,
    FOREIGN KEY (profile) REFERENCES tb_profile(id)
);
