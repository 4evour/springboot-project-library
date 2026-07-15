INSERT INTO product_category (id, name, description)
SELECT 1, '新鲜蔬菜', '校园合作基地当季蔬菜'
WHERE NOT EXISTS (SELECT 1 FROM product_category WHERE id = 1);

INSERT INTO product_category (id, name, description)
SELECT 2, '时令水果', '本地果园精选水果'
WHERE NOT EXISTS (SELECT 1 FROM product_category WHERE id = 2);

INSERT INTO product_category (id, name, description)
SELECT 3, '粮油杂粮', '大米、玉米和粗粮产品'
WHERE NOT EXISTS (SELECT 1 FROM product_category WHERE id = 3);

INSERT INTO product (id, category_id, name, description, price, stock, image_url, status)
SELECT 1, 1, '有机番茄', '本地温室采摘的新鲜番茄，适合凉拌和炒菜。', 6.80, 120, 'https://images.unsplash.com/photo-1546470427-e26264be0b0d?auto=format&fit=crop&w=800&q=80', 'ON_SALE'
WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = 1);

INSERT INTO product (id, category_id, name, description, price, stock, image_url, status)
SELECT 2, 1, '脆嫩黄瓜', '口感清爽的黄瓜，适合凉拌、沙拉和日常配菜。', 4.20, 90, 'https://images.unsplash.com/photo-1604977042946-1eecc30f269e?auto=format&fit=crop&w=800&q=80', 'ON_SALE'
WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = 2);

INSERT INTO product (id, category_id, name, description, price, stock, image_url, status)
SELECT 3, 2, '红富士苹果', '周边果园精选苹果，果香浓郁，口感清甜。', 8.90, 80, 'https://images.unsplash.com/photo-1567306226416-28f0efdc88ce?auto=format&fit=crop&w=800&q=80', 'ON_SALE'
WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = 3);

INSERT INTO product (id, category_id, name, description, price, stock, image_url, status)
SELECT 4, 3, '甜玉米', '新鲜甜玉米，颗粒饱满，自带清甜口感。', 3.50, 150, 'https://images.unsplash.com/photo-1551754655-cd27e38d2076?auto=format&fit=crop&w=800&q=80', 'ON_SALE'
WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = 4);

INSERT INTO notice (id, title, content)
SELECT 1, '周末自提通知', '周五前提交的订单可在本周末到校园服务点自提。'
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE id = 1);

INSERT INTO notice (id, title, content)
SELECT 2, '新品上架通知', '新一批当季蔬菜已经上架，欢迎选购。'
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE id = 2);
