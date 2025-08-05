-- Delete column account_type from tb_user table
ALTER TABLE tb_user DROP COLUMN account_type;

-- Add new columns to tb_user table
ALTER TABLE tb_user
ADD COLUMN avatar LONGBLOB NULL,
ADD COLUMN gender VARCHAR(1) NULL,
ADD COLUMN birthday DATE NULL,
ADD COLUMN tax_number VARCHAR(14) NULL;

-- Create new tb_user_financial_profile table
CREATE TABLE tb_user_financial_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    occupation VARCHAR(50) NULL,
    monthly_income DECIMAL(10, 2) NULL,
    monthly_expenses_limit DECIMAL(10, 2) NULL,
    goals VARCHAR(100) NULL,
    FOREIGN KEY (user_id) REFERENCES tb_user(id)
);