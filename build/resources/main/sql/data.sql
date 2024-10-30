INSERT INTO users(username, password, description, tel, email) VALUES ('keith', '{noop}keithpw', 'Keith description', '12345678', 'lklee@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');
INSERT INTO users(username, password, description, tel, email) VALUES ('john', '{noop}johnpw', 'John description', '12345678', 'jktchui@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');
INSERT INTO users(username, password, description, tel, email) VALUES ('mary', '{noop}marypw', 'Mary description', '12345678', 'mary@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');