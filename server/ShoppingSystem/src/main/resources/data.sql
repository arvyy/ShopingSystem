-- Insert default user roles:
MERGE INTO ROLE VALUES (1, 'ROLE_USER')
MERGE INTO ROLE VALUES (2, 'ROLE_ADMIN')

-- Create admin user (guid, email, enabled, password) and add ROLE_ADMIN to it
MERGE INTO USER VALUES('03dcd982-7445-44d2-a27e-d5addac9754e', 'admin@gmail.com', TRUE,'$2a$04$DjA.eKOkokIEKD0Gfqy2x.ZQX44x8uFG1QYQQ4ztKxCgZYHuv4AFy')
MERGE INTO USER_ROLE VALUES('03dcd982-7445-44d2-a27e-d5addac9754e', '2')
