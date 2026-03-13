-- Получить тест со всеми вопросами и ответами
SELECT 
    t.id AS test_id,
    t.title AS test_title,
    q.id AS question_id,
    q.question_text,
    q.position AS question_pos,
    o.id AS option_id,
    o.option_text,
    o.is_correct,
    o.position AS option_pos
FROM tests t
LEFT JOIN questions q ON t.id = q.test_id
LEFT JOIN options o ON q.id = o.question_id
WHERE t.id = 10
ORDER BY q.position, o.position;

-- Только правильные ответы для проверки
SELECT 
    t.title AS test,
    q.question_text,
    o.option_text AS correct_answer
FROM tests t
JOIN questions q ON t.id = q.test_id
JOIN options o ON q.id = o.question_id
WHERE o.is_correct = true
ORDER BY t.id, q.position;

-- Количество вопросов в тесте
SELECT 
    t.title,
    COUNT(q.id) AS questions_count
FROM tests t
LEFT JOIN questions q ON t.id = q.test_id
GROUP BY t.id, t.title;

-- Количество вариантов у каждого вопроса
SELECT 
    q.id AS question_id,
    q.question_text,
    COUNT(o.id) AS options_count
FROM questions q
LEFT JOIN options o ON q.id = o.question_id
GROUP BY q.id, q.question_text
ORDER BY q.id;
