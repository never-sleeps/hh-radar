CREATE TABLE DICTIONARY (
                            id BIGSERIAL,
                            type VARCHAR(100) NOT NULL,
                            param VARCHAR(100) NOT NULL,
                            title VARCHAR(100) NOT NULL,

                            PRIMARY KEY (id)
);

INSERT INTO DICTIONARY (type, param, title) VALUES ('experience', 'noExperience', 'Нет опыта');
INSERT INTO DICTIONARY (type, param, title) VALUES ('experience', 'between1And3', 'От 1 до 3 лет');
INSERT INTO DICTIONARY (type, param, title) VALUES ('experience', 'between3And6', 'От 3 до 6 лет');
INSERT INTO DICTIONARY (type, param, title) VALUES ('experience', 'moreThan6', '> 6 лет');

INSERT INTO DICTIONARY (type, param, title) VALUES ('employment', 'full', 'Полная занятость');
INSERT INTO DICTIONARY (type, param, title) VALUES ('employment', 'part', 'Частичная занятость');
INSERT INTO DICTIONARY (type, param, title) VALUES ('employment', 'project', 'Проектная работа');
INSERT INTO DICTIONARY (type, param, title) VALUES ('employment', 'volunteer', 'Волонтерство');
INSERT INTO DICTIONARY (type, param, title) VALUES ('employment', 'probation', 'Стажировка');

INSERT INTO DICTIONARY (type, param, title) VALUES ('schedule', 'fullDay', 'Полный день');
INSERT INTO DICTIONARY (type, param, title) VALUES ('schedule', 'shift', 'Сменный график');
INSERT INTO DICTIONARY (type, param, title) VALUES ('schedule', 'flexible', 'Гибкий график');
INSERT INTO DICTIONARY (type, param, title) VALUES ('schedule', 'remote', 'Удаленная работа');
INSERT INTO DICTIONARY (type, param, title) VALUES ('schedule', 'flyInFlyOut', 'Вахтовый метод');