ALTER TABLE users
ADD COLUMN confirmation_code VARCHAR(16),
ADD COLUMN is_confirmed BOOLEAN;