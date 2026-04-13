INSERT INTO tests (title, description) 
VALUES ('Основы SQL', 'Тест на знание базового синтаксиса SQL')
RETURNING *;

-- Предположим, созданный тест получил id = 10
INSERT INTO questions (test_id, question_text, position) 
VALUES 
    (10, 'Что означает аббревиатура SQL?', 1),
    (10, 'Какая команда используется для выборки данных?', 2),
    (10, 'Какой тип данных хранит только дату?', 3)
RETURNING *;

-- Для вопроса 1 (id = 25) - 4 варианта
INSERT INTO options (question_id, option_text, is_correct, position) 
VALUES 
    (25, 'Structured Query Language', true, 1),
    (25, 'Simple Query Language', false, 2),
    (25, 'Standard Query Language', false, 3),
    (25, 'Strong Question Language', false, 4);

-- Для вопроса 2 (id = 26) - 3 варианта
INSERT INTO options (question_id, option_text, is_correct, position) 
VALUES 
    (26, 'SELECT', true, 1),
    (26, 'INSERT', false, 2),
    (26, 'UPDATE', false, 3);

-- Для вопроса 3 (id = 37) - 2 варианта
INSERT INTO options (question_id, option_text, is_correct, position) 
VALUES 
    (27, 'DATE', true, 1),
    (27, 'TIMESTAMP', false, 2);

/*
-- Попытка создать тест без названия
INSERT INTO tests (title) VALUES (NULL);
-- Ошибка: null value in column "title" violates not-null constraint

-- Попытка создать вопрос с пустым текстом
INSERT INTO questions (test_id, question_text, position) 
VALUES (1, '', 4);
-- Ошибка: new row for relation "questions" violates check constraint "questions_question_text_check"

-- Попытка добавить второй правильный ответ к вопросу 1
INSERT INTO options (question_id, option_text, is_correct, position) 
VALUES (1, 'Another correct', true, 5);
-- Ошибка: duplicate key value violates unique constraint "one_correct_option_per_question"

-- Попытка создать вопрос для несуществующего теста
INSERT INTO questions (test_id, question_text, position) 
VALUES (999, 'Несуществующий тест', 1);
-- Ошибка: insert or update on table "questions" violates foreign key constraint "fk_questions_test"
*/
