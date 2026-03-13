-- 1. Таблица: Тесты
-- Хранит общую информацию о тесте.
CREATE TABLE IF NOT EXISTS tests (
    id BIGSERIAL PRIMARY KEY, 
    -- BIGSERIAL: Защита от переполнения счетчика (до 9 квинтиллионов записей).
    -- SERIAL лимитирован 2 млрд, что мало для крупных систем.
    
    title text NOT NULL, 
    -- text снимает искусственное ограничение длины varchar (255 символов часто мало).
    
    description text, 
    -- Может быть NULL, если описания нет.
    
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP
    -- timestamptz хранит время с учетом часового пояса, что важно для глобальных систем.
);

-- 2. Таблица: Вопросы
-- Связана с тестом через внешний ключ.
CREATE TABLE IF NOT EXISTS questions (
    id BIGSERIAL PRIMARY KEY,
    
    test_id bigint NOT NULL,
    -- Ссылка на родительский тест.
    
    question_text text NOT NULL CHECK (trim(question_text) <> ''),
    -- CHECK запрещает пустые строки ''. 
    
    position int NOT NULL DEFAULT 0,
    -- Позволяет вручную сортировать вопросы, игнорируя порядок создания (id).
    
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_questions_test 
        FOREIGN KEY (test_id) 
        REFERENCES tests(id) 
        ON DELETE CASCADE 
        -- CASCADE: При удалении теста все его вопросы удалятся автоматически.
);

-- 3. Таблица: Варианты ответов
-- Связана с вопросом.
CREATE TABLE IF NOT EXISTS options (
    id BIGSERIAL PRIMARY KEY,
    
    question_id bigint NOT NULL,
    
    option_text text NOT NULL CHECK (trim(option_text) <> ''),
    -- Аналогично вопросам: запрет пустых строк.
    
    is_correct boolean NOT NULL DEFAULT false,
    -- Флаг правильного ответа. По умолчанию false (безопаснее).
    
    position int NOT NULL DEFAULT 0,
    -- Порядок вывода вариантов (чтобы не всегда был алфавитный или по ID).
    
    CONSTRAINT fk_options_question 
        FOREIGN KEY (question_id) 
        REFERENCES questions(id) 
        ON DELETE CASCADE
        -- При удалении вопроса варианты удаляются автоматически.
);

-- 1. Индексы по внешним ключам (FK)
-- Ускоряют JOIN-запросы и ON DELETE CASCADE.
CREATE INDEX IF NOT EXISTS idx_questions_test_id ON questions(test_id);
CREATE INDEX IF NOT EXISTS idx_options_question_id ON options(question_id);

-- 2. Составные индексы для сортировки (Covering Index logic)
-- Индекс (test_id, position) позволяет БД сразу отдать данные в нужном порядке без дополнительной операции SORT.
CREATE INDEX IF NOT EXISTS idx_questions_test_position ON questions(test_id, position);
CREATE INDEX IF NOT EXISTS idx_options_question_position ON options(question_id, position);

-- 3. Уникальный частичный индекс (Partial Unique Index)
-- Гарантирует бизнес-правило: "У вопроса может быть только ОДИН правильный ответ".
CREATE UNIQUE INDEX IF NOT EXISTS one_correct_option_per_question 
ON options (question_id) 
WHERE is_correct = true;

-- Комментарии
COMMENT ON TABLE tests IS 'Хранит заголовки и мета-данные тестов';
COMMENT ON TABLE questions IS 'Вопросы, принадлежащие конкретному тесту';
COMMENT ON TABLE options IS 'Варианты ответов для вопросов';

-- Справка
/*
1. BIGSERIAL vs SERIAL:
   - SERIAL (INT): Лимит ~2.1 млрд записей. Занимает 4 байта.
   - BIGSERIAL (BIGINT): Лимит ~9*10^18 записей. Занимает 8 байт.
   - Разница в месте: На 1 млн строк разница всего ~4 МБ.

2. TEXT vs VARCHAR(N):
   - В PostgreSQL нет производительностной разницы.
   - TEXT гибче (не нужно гадать с длиной 255 или 500).

3. JSONB:
   Если требуется хранить форматирование (формулы, картинки), меняем:
     question_text text 
   на:
     content jsonb NOT NULL DEFAULT '{}'::jsonb
   
   Пример хранения в JSONB:
   {
     "type": "latex",
     "body": "Найдите корень $x^2$",
     "image": "/img/q1.png"
   }
*/
