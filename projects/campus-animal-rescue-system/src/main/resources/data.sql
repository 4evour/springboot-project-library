INSERT INTO animal (id, name, species, gender, age, location, health_status, description, image_url, status)
SELECT 1, '橘子', '猫', '雌性', '约 1 岁', '图书馆花园', '健康', '经常在图书馆附近活动，性格亲人。', 'https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=800&q=80', 'WAITING'
WHERE NOT EXISTS (SELECT 1 FROM animal WHERE id = 1);

INSERT INTO animal (id, name, species, gender, age, location, health_status, description, image_url, status)
SELECT 2, '来福', '狗', '雄性', '约 2 岁', '北操场', '已康复', '在操场附近救助的温顺小狗。', 'https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&w=800&q=80', 'WAITING'
WHERE NOT EXISTS (SELECT 1 FROM animal WHERE id = 2);

INSERT INTO animal (id, name, species, gender, age, location, health_status, description, image_url, status)
SELECT 3, '雪球', '猫', '未知', '约 6 个月', '宿舍区', '需要观察', '需要临时照护的白色幼猫。', 'https://images.unsplash.com/photo-1573865526739-10659fec78a5?auto=format&fit=crop&w=800&q=80', 'RESCUING'
WHERE NOT EXISTS (SELECT 1 FROM animal WHERE id = 3);

INSERT INTO notice (id, title, content)
SELECT 1, '志愿者招募通知', '愿意参与喂养、巡查和救助的同学，可到校园服务中心登记。'
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE id = 1);

INSERT INTO notice (id, title, content)
SELECT 2, '领养申请提醒', '请如实填写领养信息，管理员会逐一审核每一份申请。'
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE id = 2);

UPDATE animal
SET name = '橘子', species = '猫', gender = '雌性', age = '约 1 岁', location = '图书馆花园', health_status = '健康', description = '经常在图书馆附近活动，性格亲人。'
WHERE id = 1;

UPDATE animal
SET name = '来福', species = '狗', gender = '雄性', age = '约 2 岁', location = '北操场', health_status = '已康复', description = '在操场附近救助的温顺小狗。'
WHERE id = 2;

UPDATE animal
SET name = '雪球', species = '猫', gender = '未知', age = '约 6 个月', location = '宿舍区', health_status = '需要观察', description = '需要临时照护的白色幼猫。'
WHERE id = 3;

UPDATE notice
SET title = '志愿者招募通知', content = '愿意参与喂养、巡查和救助的同学，可到校园服务中心登记。'
WHERE id = 1;

UPDATE notice
SET title = '领养申请提醒', content = '请如实填写领养信息，管理员会逐一审核每一份申请。'
WHERE id = 2;
