INSERT INTO users(name, password, surname, email, address, deleted, rol, createdate, city) VALUES ('Marcos', 123, 'Solaz', 'marcos@gmail.com', 'Benedicto xvi', false, 'ADMIN', '2015-02-04', 'Valencia');
INSERT INTO users(name, password, surname, email, address, deleted, rol, createdate, city) VALUES ('Pepe', 123, 'Gonzalez', 'pepe@gmail.com', 'de la paz 1', true, 'USER', '2013-02-04', 'Madrid');
INSERT INTO users(name, password, surname, email, address, deleted, rol, createdate, city) VALUES ('Fede', 123, 'Lorca', 'fede@hotmail.com', 'Plaza España 2', false, 'USER', '2018-02-04', 'Bilbao');

INSERT INTO services(name, description, price, city, available) VALUES ('Fix Furniture', 'Check and repair furnitures', 35.20, 'Valencia', true);
INSERT INTO services(name, description, price, city, available) VALUES ('Fix TV', 'Check and repair TV', 43.60, 'Madrid', true);
INSERT INTO services(name, description, price, city, available) VALUES ('Fix Toilet', 'Check and repair toilet', 35.20, 'Valencia', true);


INSERT INTO orders(user_id, service_id, price, createdate, estimatedarrive) VALUES (1, 2, 50, '2018-02-04', '2018-02-04');
INSERT INTO orders(user_id, service_id, price, createdate, estimatedarrive) VALUES (1, 1, 40, '2018-02-04', '2018-02-04');
INSERT INTO orders(user_id, service_id, price, createdate, estimatedarrive) VALUES (3, 2, 50, '2018-02-04', '2018-02-04');
