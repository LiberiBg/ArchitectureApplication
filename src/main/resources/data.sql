-- ===========================================
-- USERS
-- ===========================================
INSERT INTO users (first_name, last_name, email, password) VALUES
('Jean', 'Dupont', 'jean.dupont@email.com', 'Password1234*'),
('Marie', 'Martin', 'marie.martin@email.com', 'Password1234*'),
('Luc', 'Roux', 'luc.roux@email.com', 'Password1234*'),
('Sophie', 'Leroy', 'sophie.leroy@email.com', 'Password1234*');

-- ===========================================
-- PRODUCTS
-- ===========================================
INSERT INTO products (name, description, brand, state, size, category, season, score, provider_id, status) VALUES
('Nike Air Max 90', 'Classique intemporelle', 'Nike', 'LIKE_NEW', '42', 'Sneakers', 'All', 95, 1, 'AVAILABLE'),
('Adidas Stan Smith', 'Cuir blanc iconique', 'Adidas', 'NEW', '43', 'Casual', 'Spring', 92, 1, 'AVAILABLE'),
('Puma RS-X3', 'Streetwear futuriste', 'Puma', 'GOOD', '41', 'Sneakers', 'Summer', 88, 2, 'AVAILABLE'),
('Veste Patagonia', 'Imperméable technique', 'Patagonia', 'NEW', 'M', 'Outdoor', 'Winter', 97, 2, 'AVAILABLE'),
('Converse Chuck 70', 'Toile haute classique', 'Converse', 'ACCEPTABLE', '42', 'Casual', 'All', 85, 3, 'AVAILABLE'),
('New Balance 574', 'Hybride running/casual', 'New Balance', 'NEW', '40', 'Sneakers', 'All', 90, 4, 'AVAILABLE');

-- ===========================================
-- MESSAGES
-- ===========================================
INSERT INTO messages (content, timestamp, sender_id, receiver_id) VALUES
('Test1', '2026-01-10T14:30:00', 1, 2),
('Test2', '2026-01-10T15:02:00', 2, 1),
('Test3', '2026-01-11T09:15:00', 2, 3),
('Test4', '2026-01-11T10:05:00', 3, 2),
('Test5', '2026-01-12T16:45:00', 4, 1),
('Test6', '2026-01-12T17:12:00', 1, 4);

-- ===========================================
-- TRANSACTIONS
-- ===========================================
INSERT INTO transactions (status, requester_id, recipient_id) VALUES
('PENDING', 2, 1),
('COMPLETED', 3, 2),
('PENDING', 4, 1);

-- ===========================================
-- LIENS Transaction-Products
-- ===========================================
INSERT INTO transactions_offered_products (jpa_transaction_entity_id, offered_products_id) VALUES (1, 3);
INSERT INTO transactions_requested_products (jpa_transaction_entity_id, requested_products_id) VALUES (1, 1);
INSERT INTO transactions_offered_products (jpa_transaction_entity_id, offered_products_id) VALUES (2, 5);
INSERT INTO transactions_requested_products (jpa_transaction_entity_id, requested_products_id) VALUES (2, 5);
INSERT INTO transactions_offered_products (jpa_transaction_entity_id, offered_products_id) VALUES (3, 1);
INSERT INTO transactions_requested_products (jpa_transaction_entity_id, requested_products_id) VALUES (3, 6);
