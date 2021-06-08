CREATE SEQUENCE student_sequence START 1;

CREATE TABLE IF NOT EXISTS user_entity (
   id SERIAL PRIMARY KEY,
   created_at TIMESTAMP,
   last_modified_at TIMESTAMP,
   public_id UUID NOT NULL,
   email VARCHAR(255),
   email_verification_status BOOLEAN NOT NULL,
   email_verification_token VARCHAR(255),
   encrypted_password VARCHAR(255) NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   UNIQUE(email, public_id)
);
