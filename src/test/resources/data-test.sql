-- ===========================================
-- USERS - Jeu de données de test complet
-- ===========================================
-- 10 utilisateurs pour tester tous les endpoints
INSERT INTO users (first_name, last_name, email, password) VALUES
('Alice', 'Dupont', 'alice.dupont@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Bob', 'Martin', 'bob.martin@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Charlie', 'Bernard', 'charlie.bernard@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Diana', 'Petit', 'diana.petit@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Eve', 'Robert', 'eve.robert@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Frank', 'Richard', 'frank.richard@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Grace', 'Durand', 'grace.durand@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Henry', 'Leroy', 'henry.leroy@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Iris', 'Moreau', 'iris.moreau@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'), -- Password1234!
('Jack', 'Simon', 'jack.simon@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'); -- Password1234!

-- ===========================================
-- PRODUCTS - Jeu de données de test complet
-- ===========================================
-- 15 produits avec différents états et statuts pour tester tous les cas
INSERT INTO products (name, description, brand, state, size, category, season, score, provider_id, status) VALUES
-- Produits disponibles (pour tests de création de transactions)
('Nike Air Max 90', 'Chaussures de sport classiques', 'Nike', 'LIKE_NEW', '42', 'Sneakers', 'All', 95, 1, 'AVAILABLE'),
('Adidas Stan Smith', 'Chaussures en cuir blanc', 'Adidas', 'NEW', '43', 'Casual', 'Spring', 92, 1, 'AVAILABLE'),
('Puma RS-X3', 'Sneakers streetwear', 'Puma', 'GOOD', '41', 'Sneakers', 'Summer', 88, 2, 'AVAILABLE'),
('Veste Patagonia', 'Veste imperméable technique', 'Patagonia', 'NEW', 'M', 'Outdoor', 'Winter', 97, 2, 'AVAILABLE'),
('Converse Chuck 70', 'Baskets en toile haute', 'Converse', 'ACCEPTABLE', '42', 'Casual', 'All', 85, 3, 'AVAILABLE'),
('New Balance 574', 'Sneakers running/casual', 'New Balance', 'NEW', '40', 'Sneakers', 'All', 90, 4, 'AVAILABLE'),
('Vans Old Skool', 'Baskets skates classiques', 'Vans', 'GOOD', '44', 'Casual', 'All', 87, 5, 'AVAILABLE'),
('Reebok Classic', 'Sneakers vintage', 'Reebok', 'LIKE_NEW', '43', 'Casual', 'All', 89, 6, 'AVAILABLE'),
-- Produits en attente (pour tests de statuts)
('Jordan 1 Retro', 'Baskets basketball iconiques', 'Nike', 'NEW', '42', 'Sneakers', 'All', 98, 7, 'PENDING'),
('Yeezy Boost 350', 'Sneakers lifestyle', 'Adidas', 'LIKE_NEW', '44', 'Sneakers', 'All', 96, 8, 'PENDING'),
-- Produits vendus (pour tests de statuts)
('Air Force 1', 'Baskets blanches classiques', 'Nike', 'GOOD', '41', 'Sneakers', 'All', 91, 9, 'SOLD'),
('Superstar', 'Baskets en cuir', 'Adidas', 'ACCEPTABLE', '42', 'Casual', 'All', 86, 10, 'SOLD'),
-- Produits avec différents états
('Chaussures Running', 'Pour la course', 'Asics', 'POOR', '43', 'Sportswear', 'All', 75, 1, 'AVAILABLE'),
('Baskets Vintage', 'Style rétro', 'Fila', 'ACCEPTABLE', '45', 'Casual', 'All', 82, 2, 'AVAILABLE'),
('Sneakers Premium', 'Haut de gamme', 'Common Projects', 'LIKE_NEW', '42', 'Luxury', 'All', 99, 3, 'AVAILABLE');

-- ===========================================
-- MESSAGES - Jeu de données de test complet
-- ===========================================
-- 20 messages pour tester les endpoints de récupération par sender/receiver
INSERT INTO messages (content, timestamp, sender_id, receiver_id) VALUES
-- Messages entre user 1 et user 2
('Bonjour, je suis intéressé par votre produit', '2026-01-10T10:00:00', 1, 2),
('Bonjour, oui je suis disponible pour échanger', '2026-01-10T10:15:00', 2, 1),
('Parfait, quand pouvons-nous nous rencontrer?', '2026-01-10T10:30:00', 1, 2),
('Demain après-midi si cela vous convient', '2026-01-10T10:45:00', 2, 1),
-- Messages entre user 2 et user 3
('Salut, votre produit est toujours disponible?', '2026-01-10T11:00:00', 2, 3),
('Oui, il est toujours disponible', '2026-01-10T11:10:00', 3, 2),
('Super, je propose un échange', '2026-01-10T11:20:00', 2, 3),
-- Messages entre user 3 et user 4
('Bonjour, intéressé par votre veste', '2026-01-10T12:00:00', 3, 4),
('Oui, elle est en excellent état', '2026-01-10T12:15:00', 4, 3),
('Parfait, je vous contacte bientôt', '2026-01-10T12:30:00', 3, 4),
-- Messages entre user 4 et user 5
('Hello, votre produit me plaît', '2026-01-10T13:00:00', 4, 5),
('Merci, il est neuf', '2026-01-10T13:15:00', 5, 4),
-- Messages entre user 5 et user 6
('Salut, échange possible?', '2026-01-10T14:00:00', 5, 6),
('Oui bien sûr', '2026-01-10T14:10:00', 6, 5),
-- Messages entre user 6 et user 7
('Bonjour, votre produit est disponible?', '2026-01-10T15:00:00', 6, 7),
('Oui, toujours disponible', '2026-01-10T15:15:00', 7, 6),
-- Messages entre user 7 et user 8
('Intéressé par votre produit', '2026-01-10T16:00:00', 7, 8),
('Parfait, contactez-moi', '2026-01-10T16:15:00', 8, 7),
-- Messages entre user 8 et user 9
('Bonjour, échange possible?', '2026-01-10T17:00:00', 8, 9),
('Oui, avec plaisir', '2026-01-10T17:15:00', 9, 8),
-- Messages entre user 9 et user 10
('Salut, votre produit m''intéresse', '2026-01-10T18:00:00', 9, 10),
('Super, discutons-en', '2026-01-10T18:15:00', 10, 9);

-- ===========================================
-- TRANSACTIONS - Jeu de données de test complet
-- ===========================================
-- 10 transactions avec différents statuts pour tester tous les cas
INSERT INTO transactions (status, requester_id, recipient_id) VALUES
-- Transactions en attente
('PENDING', 2, 1),
('PENDING', 3, 2),
('PENDING', 4, 1),
-- Transactions acceptées
('ACCEPTED', 5, 3),
('ACCEPTED', 6, 4),
-- Transactions rejetées
('REJECTED', 7, 5),
-- Transactions en cours
('REQUESTER_COMPLETED', 8, 6),
('RECIPIENT_COMPLETED', 9, 7),
-- Transactions complétées
('COMPLETED', 10, 8),
-- Transactions annulées
('CANCELLED', 1, 9);

-- ===========================================
-- LIENS Transaction-Products (Offered Products)
-- ===========================================
INSERT INTO transactions_offered_products (jpa_transaction_entity_id, offered_products_id) VALUES
(1, 3),  -- Transaction 1: offre produit 3
(2, 5),  -- Transaction 2: offre produit 5
(3, 1),  -- Transaction 3: offre produit 1
(4, 7),  -- Transaction 4: offre produit 7
(5, 8),  -- Transaction 5: offre produit 8
(6, 9),  -- Transaction 6: offre produit 9
(7, 10), -- Transaction 7: offre produit 10
(8, 11), -- Transaction 8: offre produit 11
(9, 12), -- Transaction 9: offre produit 12
(10, 13); -- Transaction 10: offre produit 13

-- ===========================================
-- LIENS Transaction-Products (Requested Products)
-- ===========================================
INSERT INTO transactions_requested_products (jpa_transaction_entity_id, requested_products_id) VALUES
(1, 1),  -- Transaction 1: demande produit 1
(2, 2),  -- Transaction 2: demande produit 2
(3, 4),  -- Transaction 3: demande produit 4
(4, 6),  -- Transaction 4: demande produit 6
(5, 7),  -- Transaction 5: demande produit 7
(6, 8),  -- Transaction 6: demande produit 8
(7, 9),  -- Transaction 7: demande produit 9
(8, 10), -- Transaction 8: demande produit 10
(9, 11), -- Transaction 9: demande produit 11
(10, 12); -- Transaction 10: demande produit 12
