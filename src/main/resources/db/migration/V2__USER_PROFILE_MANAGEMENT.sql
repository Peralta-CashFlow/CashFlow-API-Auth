-- Delete column account_type from tb_user table
ALTER TABLE tb_user DROP COLUMN account_type;

-- Add new columns to tb_user table
ALTER TABLE tb_user
ADD COLUMN avatar LONGBLOB NULL,
ADD COLUMN gender VARCHAR(1) NULL,
ADD COLUMN birthday DATE NULL,
ADD COLUMN tax_number VARCHAR(14) NULL;
