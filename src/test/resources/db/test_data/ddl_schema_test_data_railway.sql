--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 14.5

-- Started on 2022-10-16 20:19:24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3422 (class 1262 OID 17471)
-- Name: railway; Type: DATABASE; Schema: -; Owner: postgres
--

-- CREATE DATABASE railway WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


-- ALTER DATABASE railway OWNER TO postgres;

-- \connect railway

-- SET statement_timeout = 0;
-- SET lock_timeout = 0;
-- SET idle_in_transaction_session_timeout = 0;
-- SET client_encoding = 'UTF8';
-- SET standard_conforming_strings = on;
-- SELECT pg_catalog.set_config('search_path', '', false);
-- SET check_function_bodies = false;
-- SET xmloption = content;
-- SET client_min_messages = warning;
-- SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS public;

CREATE ROLE postgres;

ALTER SCHEMA public OWNER TO postgres;

CREATE EXTENSION pg_trgm SCHEMA public;

--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 259 (class 1259 OID 17732)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

-- CREATE TABLE public.flyway_schema_history (
--     installed_rank integer NOT NULL,
--     version character varying(50),
--     description character varying(200) NOT NULL,
--     type character varying(20) NOT NULL,
--     script character varying(1000) NOT NULL,
--     checksum integer,
--     installed_by character varying(100) NOT NULL,
--     installed_on timestamp without time zone DEFAULT now() NOT NULL,
--     execution_time integer NOT NULL,
--     success boolean NOT NULL
-- );


-- ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 17552)
-- Name: meanings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.meanings (
    id integer NOT NULL,
    meanings_jsonb jsonb
);


ALTER TABLE public.meanings OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 17550)
-- Name: meanings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.meanings ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.meanings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 257 (class 1259 OID 17617)
-- Name: missing_words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.missing_words (
    id integer NOT NULL,
    word_name character varying,
    date_time_of_receiving timestamp with time zone
);


ALTER TABLE public.missing_words OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 17625)
-- Name: missing_words_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.missing_words ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.missing_words_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 256 (class 1259 OID 17602)
-- Name: translation_word; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.translation_word (
    word_id integer NOT NULL,
    translation_id integer NOT NULL
);


ALTER TABLE public.translation_word OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 17578)
-- Name: translations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.translations (
    id integer NOT NULL,
    name character varying(256)
);


ALTER TABLE public.translations OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 17576)
-- Name: translations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.translations ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.translations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 251 (class 1259 OID 17568)
-- Name: word_forms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_forms (
    id integer NOT NULL,
    word_forms_jsonb jsonb
);


ALTER TABLE public.word_forms OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 17566)
-- Name: word_forms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.word_forms ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.word_forms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 255 (class 1259 OID 17587)
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words (
    id integer NOT NULL,
    name character varying(256),
    word_forms_id integer,
    meaning_id integer
);


ALTER TABLE public.words OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 17585)
-- Name: words_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.words ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.words_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 260 (class 1259 OID 17797)
-- Name: work_states; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.work_states (
    chat_id bigint NOT NULL,
    work_state character varying NOT NULL
);


ALTER TABLE public.work_states OWNER TO postgres;

--
-- TOC entry 3268 (class 2606 OID 17740)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

-- ALTER TABLE ONLY public.flyway_schema_history
--     ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (102420, '{"сущ., род вещно-м.": ["(1.1) (от) земли"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (79308, '{"форма глагола": ["(2.1) (страдательное причастие прилагательного от::) беспокоиться"], "прилаг. качеств.": ["(1.1) тот, кто чем-то обеспокоен"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (97284, '{"аббрев. w funkcji rzeczownika rodzaju nijakiego, имя собств.": ["(1.1) = избирательная акция поляков в Литве;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (39233, '{"прилаг. относит.": ["(1.1) в отношении города Хельсинки"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (106493, '{"сущ., род ж.": ["(1.1) сходные, аналогичные признаки", "(1.2) перечень признаков (1.1)"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (98553, '{"сущ., род лично-м., имя собств.": ["(1.1) имя |польское |м;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (124856, '{"сущ., род ж.": ["(1.1) заботиться"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (91994, '{"przysłówek": ["(1.1) способом, напоминающим движение маятника"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (93528, '{"сущ., род ж., имя собств.": ["(1.1) (диалект: Верхняя Силезия) знакомая форма имени Юстина."]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (84888, '{"сущ., род ср.": ["(1.1) герундий от |не |подозревать"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (91813, '{"фраза-сущ., род нелично-м., мн. ч.": ["(1.1) средство от болей в животе;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (65546, '{"прилаг.": ["(1.1) грубый, грубый", "(1.2) неизящный, грубый"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (125987, '{"сущ., род ж., м.": ["(1.1) теория о том, что мир есть целое, которое не может быть сведено к сумме его частей", "(1.2) точка зрения, согласно которой явления образуют целые системы;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (101748, '{"fraza przyimkowa": ["(1.1) (выражение, указывающее, что является основанием для выполнения данной деятельности, работы, работы или предмета)"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (102760, '{"сущ., род вещно-м.": ["(1.1) (от) променад"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (89281, '{"прилаг. качеств.": ["(1.1) направленный против или враждебно настроенный по отношению к Бермудским островам и Бермудским островам", "(1.2) демонстрирующий антинемецкое отношение (1.1)"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (42129, '{"сущ., род ср., имя собств.": ["(1.1) город в Италии, Сицилия;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (58475, '{"сущ., род вещно-м.": ["(1.1) ранний тип проигрывателя с механическим приводом;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (67280, '{"глаг.": ["Застрять"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (121651, '{"сущ., род ж.": ["(1.1) исполнение произведений солистом или в один голос", "(1.2) наличие в произведении только одной мелодической линии"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (82135, '{"сущ., род ср.": ["(1.1) появляться"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (107753, '{"сущ., род ж.": ["(1.1) качество вязанки; черта педантичных"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (104489, '{"сущ., род ж.": ["(1.1) особенность совместного обучения"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (125590, '{"сущ., род ж.": ["(1.1) бутерброд из двух слоев хлеба"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (54601, '{"сущ., род ср.": ["(1.1) герань от |не |предоставлять"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (38216, '{"сущ., род животно-м.": ["(1.1) кто-то с удивительными навыками или удивительным поведением"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (93334, '{"сущ., род лично-м.": ["(1.1) тот, кто добровольно добровольно участвует в чем-либо"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (60095, '{"глаг.": ["(1.1) лишить кого-либо свободы передвижения"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (113911, '{"сущ., род ж.": ["(1.1) изменение физического состояния из газообразного в твердое, минуя жидкую фазу;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (87901, '{"прилаг. относит.": ["(1.1) связанные с концертом, касающиеся концерта, предназначенные для концерта"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (50943, '{"прилаг. относит.": ["(1.1) с четырьмя комнатами"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (50979, '{"глаг. перех. несоверш.": ["(1.1) вести начатую самим собой борьбу", "(1.2) действовать агрессивно против кого-либо или чего-либо", "(1.3) (о болезнях:) касаются определенного органа, системы", "(1.4) (о болезнях, вредителях и прочих напастях : ) распространяться в определенной среде", "(1.5) действовать деструктивно", "(1.6) предпринимать энергичные действия", "(1.7) обращаться к кому-либо", "(1.8) пытаться получить преимущество"], "глаг. возвр. несоверш. atakować się": ["(2.1) нападать (1.2) друг на друга", "(2.2) нападать (1.2) на себя"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (47078, '{"форма сущ-го": ["(3.1) множественное число (от :) крокодил"], "прилаг. dzierżawczy": ["(1.1) принадлежащий крокодилу"], "прилаг. качеств.": ["", "(2.1) характеристика крокодила, обладающая характеристиками крокодила"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (102447, '{"сущ., род вещно-м.": ["(1.1) (де) бойкот"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (36732, '{"сущ., род вещно-м.": ["(1.1) книга с описанием и изображением гербов", "(1.2) книга о травах"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (117778, '{"сущ., род ж.": ["(1.1) жительница Пененжно", "(1.2) женщина из Пененжно, родившаяся в Пененжно"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (88225, '{"сущ., род ж.": ["(1.1) особенность того, что является контркультурным"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (121429, '{"сущ., род вещно-м.": ["(1.1) достижение более ранней меры путем отращивания", "(1.2) отросшей части", "(1.3) отросшей отчетливой нижней части волоса, например, после окрашивания", "(1.4) части растения, которая отрастает от ствол или корень", "(1.5) процесс роста рыба", "(1.6) ветвь в роге"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (102449, '{"сущ., род вещно-м.": ["(1.1) (от) болт"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (51260, '{"пословица": ["(1.1) хорошо и мудро все начинания начинать с призыва помощи Божией"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (59201, '{"прилаг.": ["(1.1) относительно четвертичного периода, происходящего, образовавшегося в четвертичном периоде"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (121701, '{"глаг. возвр.": ["(1.1) (диалект: Тешинская Силезия) будь щедрым"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (76670, '{"сущ., род ср.": ["(1.1) герань от |не |оттирать |"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (122336, '{"сущ., род вещно-м.": ["(1.1) город в Польше, расположенный в Мазовецком воеводстве, уезд Легионово;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (46123, '{"сущ., род ж.": ["(1.1) хирургический метод удаления катаракты;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (85897, '{"сущ., род ср.": ["(1.1) отключение того, что было ранее установлено"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (87587, '{"глаг. неперех. соверш. (несоверш. вид: parskać)": ["(1.1) фыркнуть"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (124323, '{"фраза-сущ., род животно-м.": ["(1.1) сильная массивная лошадь со спокойным темпераментом;"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (119876, '{"сущ., род ж.": ["(1.1) черта балтийского, балтский характер чего-либо"]}');
INSERT INTO public.meanings OVERRIDING SYSTEM VALUE VALUES (56763, '{"прилаг.": ["(1.1) жаропонижающее, используемое для лечения лихорадки, для предотвращения лихорадки"]}');


--
-- Data for Name: translation_word; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (79308, 26466);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (39233, 22244);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (98553, 23422);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (93528, 23675);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (102760, 18544);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (42129, 24285);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (67280, 32467);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (93334, 33479);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (87901, 36511);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (50943, 27669);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (102447, 29905);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (36732, 20559);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (36732, 20560);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (36732, 20561);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (46123, 25882);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (87587, 15893);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (87587, 19720);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (56763, 29187);
INSERT INTO public.translation_word OVERRIDING SYSTEM VALUE VALUES (56763, 29188);


--
-- Data for Name: translations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (15893, 'смеяться');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (18544, 'пешеходная зона');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (19720, 'чихать');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (25882, 'факоэмульсификация');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (20559, 'гербарий');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (20560, 'гербовник');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (20561, 'гербарий , травник');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (22244, 'хельсинкский');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (23422, 'Валерий');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (23675, 'Юстина');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (24285, 'Палермо');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (26466, 'расстроенный');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (29187, 'противолихорадочный');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (29188, 'жаропонижающий');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (27669, 'четырёхкомнатный');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (29905, 'бойкот');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (32467, 'застрять');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (33479, 'доброволец');
INSERT INTO public.translations OVERRIDING SYSTEM VALUE VALUES (36511, 'концертный');


--
-- Data for Name: word_forms; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (102420, '{"(1.1)": {"PLURAL": {"BIERNIK": "grunciki", "WOŁACZ": "grunciki", "CELOWNIK": "gruncikom", "MIANOWNIK": "grunciki", "NARZĘDNIK": "gruncikami", "DOPEŁNIACZ": "gruncików", "MIEJSCOWNIK": "gruncikach"}, "SINGULAR": {"BIERNIK": "gruncik", "WOŁACZ": "grunciku", "CELOWNIK": "gruncikowi", "MIANOWNIK": "gruncik", "NARZĘDNIK": "gruncikiem", "DOPEŁNIACZ": "grunciku", "MIEJSCOWNIK": "grunciku"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (79308, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (97284, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (39233, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (106493, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (98553, '{"(1.1)": {"PLURAL": {"BIERNIK": "Waleriuszów", "WOŁACZ": "Waleriuszowie", "CELOWNIK": "Waleriuszom", "MIANOWNIK": "Waleriuszowie", "NARZĘDNIK": "Waleriuszami", "DOPEŁNIACZ": "Waleriuszów", "MIEJSCOWNIK": "Waleriuszach"}, "SINGULAR": {"BIERNIK": "Waleriusza", "WOŁACZ": "Waleriuszu", "CELOWNIK": "Waleriuszowi", "MIANOWNIK": "Waleriusz", "NARZĘDNIK": "Waleriuszem", "DOPEŁNIACZ": "Waleriusza", "MIEJSCOWNIK": "Waleriuszu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (124856, '{"(1.1)": {"PLURAL": {"BIERNIK": "piecze", "WOŁACZ": "piecze", "CELOWNIK": "pieczom", "MIANOWNIK": "piecze", "NARZĘDNIK": "pieczami", "DOPEŁNIACZ": "pieczy", "MIEJSCOWNIK": "pieczach"}, "SINGULAR": {"BIERNIK": "pieczę", "WOŁACZ": "pieczo", "CELOWNIK": "pieczy", "MIANOWNIK": "piecza", "NARZĘDNIK": "pieczą", "DOPEŁNIACZ": "pieczy", "MIEJSCOWNIK": "pieczy"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (91994, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (93528, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (84888, '{"(1.1)": {"SINGULAR": {"BIERNIK": "nieposądzanie", "WOŁACZ": "nieposądzanie", "CELOWNIK": "nieposądzaniu", "MIANOWNIK": "nieposądzanie", "NARZĘDNIK": "nieposądzaniem", "DOPEŁNIACZ": "nieposądzania", "MIEJSCOWNIK": "nieposądzaniu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (91813, '{"(1.1)": {"PLURAL": {"BIERNIK": "krople Inoziemcowa", "WOŁACZ": "krople Inoziemcowa", "CELOWNIK": "kroplom Inoziemcowa", "MIANOWNIK": "krople Inoziemcowa", "NARZĘDNIK": "kroplami Inoziemcowa", "DOPEŁNIACZ": "kropli Inoziemcowa / kropel Inoziemcowa", "MIEJSCOWNIK": "kroplach Inoziemcowa"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (65546, '{"(1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (125987, '{"(1.1-2)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (101748, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (102760, '{"(1.1)": {"PLURAL": {"BIERNIK": "deptaczki", "WOŁACZ": "deptaczki", "CELOWNIK": "deptaczkom", "MIANOWNIK": "deptaczki", "NARZĘDNIK": "deptaczkami", "DOPEŁNIACZ": "deptaczków", "MIEJSCOWNIK": "deptaczkach"}, "SINGULAR": {"BIERNIK": "deptaczek", "WOŁACZ": "deptaczku", "CELOWNIK": "deptaczkowi", "MIANOWNIK": "deptaczek", "NARZĘDNIK": "deptaczkiem", "DOPEŁNIACZ": "deptaczka", "MIEJSCOWNIK": "deptaczku"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (89281, '{"(1.1-2)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (42129, '{"(1.1)": {"SINGULAR": {"BIERNIK": "Palermo", "WOŁACZ": "Palermo", "CELOWNIK": "Palermo", "MIANOWNIK": "Palermo", "NARZĘDNIK": "Palermo", "DOPEŁNIACZ": "Palermo", "MIEJSCOWNIK": "Palermo"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (58475, '{"(1.1)": {"PLURAL": {"BIERNIK": "patefony", "WOŁACZ": "patefony", "CELOWNIK": "patefonom", "MIANOWNIK": "patefony", "NARZĘDNIK": "patefonami", "DOPEŁNIACZ": "patefonów", "MIEJSCOWNIK": "patefonach"}, "SINGULAR": {"BIERNIK": "patefon", "WOŁACZ": "patefonie", "CELOWNIK": "patefonowi", "MIANOWNIK": "patefon", "NARZĘDNIK": "patefonem", "DOPEŁNIACZ": "patefonu", "MIEJSCOWNIK": "patefonie"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (67280, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (121651, '{"(1.1-2)": {"PLURAL": {"BIERNIK": "jednogłosowości", "WOŁACZ": "jednogłosowości", "CELOWNIK": "jednogłosowościom", "MIANOWNIK": "jednogłosowości", "NARZĘDNIK": "jednogłosowościami", "DOPEŁNIACZ": "jednogłosowości", "MIEJSCOWNIK": "jednogłosowościach"}, "SINGULAR": {"BIERNIK": "jednogłosowość", "WOŁACZ": "jednogłosowości", "CELOWNIK": "jednogłosowości", "MIANOWNIK": "jednogłosowość", "NARZĘDNIK": "jednogłosowością", "DOPEŁNIACZ": "jednogłosowości", "MIEJSCOWNIK": "jednogłosowości"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (82135, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (107753, '{"(1.1)": {"PLURAL": {"BIERNIK": "pedalskości", "WOŁACZ": "pedalskości", "CELOWNIK": "pedalskościom", "MIANOWNIK": "pedalskości", "NARZĘDNIK": "pedalskościami", "DOPEŁNIACZ": "pedalskości", "MIEJSCOWNIK": "pedalskościach"}, "SINGULAR": {"BIERNIK": "pedalskość", "WOŁACZ": "pedalskości", "CELOWNIK": "pedalskości", "MIANOWNIK": "pedalskość", "NARZĘDNIK": "pedalskością", "DOPEŁNIACZ": "pedalskości", "MIEJSCOWNIK": "pedalskości"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (104489, '{"(1.1)": {"PLURAL": {"BIERNIK": "koedukacyjności", "WOŁACZ": "koedukacyjności", "CELOWNIK": "koedukacyjnościom", "MIANOWNIK": "koedukacyjności", "NARZĘDNIK": "koedukacyjnościami", "DOPEŁNIACZ": "koedukacyjności", "MIEJSCOWNIK": "koedukacyjnościach"}, "SINGULAR": {"BIERNIK": "koedukacyjność", "WOŁACZ": "koedukacyjności", "CELOWNIK": "koedukacyjności", "MIANOWNIK": "koedukacyjność", "NARZĘDNIK": "koedukacyjnością", "DOPEŁNIACZ": "koedukacyjności", "MIEJSCOWNIK": "koedukacyjności"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (125590, '{"(1.1)": {"PLURAL": {"BIERNIK": "klapsztule", "WOŁACZ": "klapsztule", "CELOWNIK": "klapsztulom", "MIANOWNIK": "klapsztule", "NARZĘDNIK": "klapsztulami", "DOPEŁNIACZ": "klapsztuli", "MIEJSCOWNIK": "klapsztulach"}, "SINGULAR": {"BIERNIK": "klapsztulę", "WOŁACZ": "klapsztula", "CELOWNIK": "klapsztuli", "MIANOWNIK": "klapsztula", "NARZĘDNIK": "klapsztulą", "DOPEŁNIACZ": "klapsztuli", "MIEJSCOWNIK": "klapsztuli"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (54601, '{"(1.1)": {"PLURAL": {"BIERNIK": "", "WOŁACZ": "", "CELOWNIK": "", "MIANOWNIK": "", "NARZĘDNIK": "", "DOPEŁNIACZ": "", "MIEJSCOWNIK": ""}, "SINGULAR": {"BIERNIK": "nieudzielanie", "WOŁACZ": "nieudzielanie", "CELOWNIK": "nieudzielaniu", "MIANOWNIK": "nieudzielanie", "NARZĘDNIK": "nieudzielaniem", "DOPEŁNIACZ": "nieudzielania", "MIEJSCOWNIK": "nieudzielaniu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (38216, '{"(1.1)": {"PLURAL": {"BIERNIK": "przechuje", "WOŁACZ": "przechuje", "CELOWNIK": "przechujom", "MIANOWNIK": "przechuje", "NARZĘDNIK": "przechujami", "DOPEŁNIACZ": "przechujów", "MIEJSCOWNIK": "przechujach"}, "SINGULAR": {"BIERNIK": "przechuja", "WOŁACZ": "przechuju", "CELOWNIK": "przechujowi", "MIANOWNIK": "przechuj", "NARZĘDNIK": "przechujem", "DOPEŁNIACZ": "przechuja", "MIEJSCOWNIK": "przechuju"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (93334, '{"(1.1)": {"PLURAL": {"BIERNIK": "ochotników", "WOŁACZ": "ochotnicy", "CELOWNIK": "ochotnikom", "MIANOWNIK": "ochotnicy", "NARZĘDNIK": "ochotnikami", "DOPEŁNIACZ": "ochotników", "MIEJSCOWNIK": "ochotnikach"}, "SINGULAR": {"BIERNIK": "ochotnika", "WOŁACZ": "ochotniku", "CELOWNIK": "ochotnikowi", "MIANOWNIK": "ochotnik", "NARZĘDNIK": "ochotnikiem", "DOPEŁNIACZ": "ochotnika", "MIEJSCOWNIK": "ochotniku"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (60095, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (113911, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (87901, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (50943, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (50979, '{"(1.1-8)": {}, "(2.1-2)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (47078, '{"(1.1)": {}, "(2.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (102447, '{"(1.1)": {"PLURAL": {"BIERNIK": "bojkociki", "WOŁACZ": "bojkociki", "CELOWNIK": "bojkocikom", "MIANOWNIK": "bojkociki", "NARZĘDNIK": "bojkocikami", "DOPEŁNIACZ": "bojkocików", "MIEJSCOWNIK": "bojkocikach"}, "SINGULAR": {"BIERNIK": "bojkocik", "WOŁACZ": "bojkociku", "CELOWNIK": "bojkocikowi", "MIANOWNIK": "bojkocik", "NARZĘDNIK": "bojkocikiem", "DOPEŁNIACZ": "bojkociku", "MIEJSCOWNIK": "bojkociku"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (36732, '{"(1.1-2)": {"PLURAL": {"BIERNIK": "herbarze", "WOŁACZ": "herbarze", "CELOWNIK": "herbarzom", "MIANOWNIK": "herbarze", "NARZĘDNIK": "herbarzami", "DOPEŁNIACZ": "herbarzy / herbarzów", "MIEJSCOWNIK": "herbarzach"}, "SINGULAR": {"BIERNIK": "herbarz", "WOŁACZ": "herbarzu", "CELOWNIK": "herbarzowi", "MIANOWNIK": "herbarz", "NARZĘDNIK": "herbarzem", "DOPEŁNIACZ": "herbarza", "MIEJSCOWNIK": "herbarzu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (117778, '{"(1.1-2)": {"PLURAL": {"BIERNIK": "pieniężnianki", "WOŁACZ": "pieniężnianki", "CELOWNIK": "pieniężniankom", "MIANOWNIK": "pieniężnianki", "NARZĘDNIK": "pieniężniankami", "DOPEŁNIACZ": "pieniężnianek", "MIEJSCOWNIK": "pieniężniankach"}, "SINGULAR": {"BIERNIK": "pieniężniankę", "WOŁACZ": "pieniężnianko", "CELOWNIK": "pieniężniance", "MIANOWNIK": "pieniężnianka", "NARZĘDNIK": "pieniężnianką", "DOPEŁNIACZ": "pieniężnianki", "MIEJSCOWNIK": "pieniężniance"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (88225, '{"(1.1)": {"SINGULAR": {"BIERNIK": "kontrkulturowość", "WOŁACZ": "kontrkulturowości", "CELOWNIK": "kontrkulturowości", "MIANOWNIK": "kontrkulturowość", "NARZĘDNIK": "kontrkulturowością", "DOPEŁNIACZ": "kontrkulturowości", "MIEJSCOWNIK": "kontrkulturowości"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (121429, '{"(1.1-6)": {"PLURAL": {"BIERNIK": "odrosty", "WOŁACZ": "odrosty", "CELOWNIK": "odrostom", "MIANOWNIK": "odrosty", "NARZĘDNIK": "odrostami", "DOPEŁNIACZ": "odrostów", "MIEJSCOWNIK": "odrostach"}, "SINGULAR": {"BIERNIK": "odrost", "WOŁACZ": "odroście", "CELOWNIK": "odrostowi", "MIANOWNIK": "odrost", "NARZĘDNIK": "odrostem", "DOPEŁNIACZ": "odrostu", "MIEJSCOWNIK": "odroście"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (102449, '{"(1.1)": {"PLURAL": {"BIERNIK": "bełciki", "WOŁACZ": "bełciki", "CELOWNIK": "bełcikom", "MIANOWNIK": "bełciki", "NARZĘDNIK": "bełcikami", "DOPEŁNIACZ": "bełcików", "MIEJSCOWNIK": "bełcikach"}, "SINGULAR": {"BIERNIK": "bełcik", "WOŁACZ": "bełciku", "CELOWNIK": "bełcikowi", "MIANOWNIK": "bełcik", "NARZĘDNIK": "bełcikiem", "DOPEŁNIACZ": "bełciku", "MIEJSCOWNIK": "bełciku"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (51260, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (59201, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (121701, '{}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (76670, '{"(1.1)": {"SINGULAR": {"BIERNIK": "nieścieranie", "WOŁACZ": "nieścieranie", "CELOWNIK": "nieścieraniu", "MIANOWNIK": "nieścieranie", "NARZĘDNIK": "nieścieraniem", "DOPEŁNIACZ": "nieścierania", "MIEJSCOWNIK": "nieścieraniu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (122336, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (46123, '{"(1.1)": {"PLURAL": {"BIERNIK": "fakoemulsyfikacje", "WOŁACZ": "fakoemulsyfikacje", "CELOWNIK": "fakoemulsyfikacjom", "MIANOWNIK": "fakoemulsyfikacje", "NARZĘDNIK": "fakoemulsyfikacjami", "DOPEŁNIACZ": "fakoemulsyfikacji", "MIEJSCOWNIK": "fakoemulsyfikacjach"}, "SINGULAR": {"BIERNIK": "fakoemulsyfikację", "WOŁACZ": "fakoemulsyfikacjo", "CELOWNIK": "fakoemulsyfikacji", "MIANOWNIK": "fakoemulsyfikacja", "NARZĘDNIK": "fakoemulsyfikacją", "DOPEŁNIACZ": "fakoemulsyfikacji", "MIEJSCOWNIK": "fakoemulsyfikacji"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (85897, '{"(1.1)": {"SINGULAR": {"BIERNIK": "odinstalowanie", "WOŁACZ": "odinstalowanie", "CELOWNIK": "odinstalowaniu", "MIANOWNIK": "odinstalowanie", "NARZĘDNIK": "odinstalowaniem", "DOPEŁNIACZ": "odinstalowania", "MIEJSCOWNIK": "odinstalowaniu"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (87587, '{"(1.1)": {}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (124323, '{"(1.1)": {"PLURAL": {"BIERNIK": "konie zimnokrwiste", "WOŁACZ": "konie zimnokrwiste", "CELOWNIK": "koniom zimnokrwistym", "MIANOWNIK": "konie zimnokrwiste", "NARZĘDNIK": "końmi zimnokrwistymi", "DOPEŁNIACZ": "koni zimnokrwistemu", "MIEJSCOWNIK": "koniach zimnokrwistych"}, "SINGULAR": {"BIERNIK": "konia zimnokrwistego", "WOŁACZ": "koniu zimnokrwisty", "CELOWNIK": "koniowi zimnokrwistemu", "MIANOWNIK": "koń zimnokrwisty", "NARZĘDNIK": "koniem zimnokrwistym", "DOPEŁNIACZ": "konia zimnokrwistego", "MIEJSCOWNIK": "koniu zimnokrwistym"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (119876, '{"(1.1)": {"PLURAL": {"BIERNIK": "bałtyckości", "WOŁACZ": "bałtyckości", "CELOWNIK": "bałtyckościom", "MIANOWNIK": "bałtyckości", "NARZĘDNIK": "bałtyckościami", "DOPEŁNIACZ": "bałtyckości", "MIEJSCOWNIK": "bałtyckościach"}, "SINGULAR": {"BIERNIK": "bałtyckość", "WOŁACZ": "bałtyckości", "CELOWNIK": "bałtyckości", "MIANOWNIK": "bałtyckość", "NARZĘDNIK": "bałtyckością", "DOPEŁNIACZ": "bałtyckości", "MIEJSCOWNIK": "bałtyckości"}}}');
INSERT INTO public.word_forms OVERRIDING SYSTEM VALUE VALUES (56763, '{"(1.1)": {}}');


--
-- Data for Name: words; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (89281, 'antybermudzki', 89281, 89281);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (85897, 'odinstalowanie', 85897, 85897);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (88225, 'kontrkulturowość', 88225, 88225);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (76670, 'nieścieranie', 76670, 76670);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (67280, 'zastrzęgnąć', 67280, 67280);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (93528, 'Justa', 93528, 93528);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (106493, 'paralela', 106493, 106493);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (107753, 'pedalskość', 107753, 107753);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (84888, 'nieposądzanie', 84888, 84888);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (121701, 'szarpnyć sie', 121701, 121701);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (50943, 'czteropokojowy', 50943, 50943);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (117778, 'pieniężnianka', 117778, 117778);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (47078, 'krokodyli', 47078, 47078);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (97284, 'AWPL', 97284, 97284);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (50979, 'atakować', 50979, 50979);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (56763, 'przeciwgorączkowy', 56763, 56763);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (102447, 'bojkocik', 102447, 102447);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (119876, 'bałtyckość', 119876, 119876);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (124323, 'koń zimnokrwisty', 124323, 124323);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (87901, 'koncertowy', 87901, 87901);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (38216, 'przechuj', 38216, 38216);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (79308, 'zmartwiony', 79308, 79308);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (121429, 'odrost', 121429, 121429);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (98553, 'Waleriusz', 98553, 98553);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (113911, 'resublimacja', 113911, 113911);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (91994, 'wahadłowo', 91994, 91994);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (54601, 'nieudzielanie', 54601, 54601);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (91813, 'krople Inoziemcowa', 91813, 91813);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (125590, 'klapsztula', 125590, 125590);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (102420, 'gruncik', 102420, 102420);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (82135, 'wyłonienie', 82135, 82135);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (121651, 'jednogłosowość', 121651, 121651);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (59201, 'czwartorzędowy', 59201, 59201);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (124856, 'piecza', 124856, 124856);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (65546, 'kostropaty', 65546, 65546);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (58475, 'patefon', 58475, 58475);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (101748, 'na podstawie', 101748, 101748);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (39233, 'helsiński', 39233, 39233);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (87587, 'parsknąć', 87587, 87587);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (122336, 'Serock', 122336, 122336);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (60095, 'obezwładniać', 60095, 60095);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (42129, 'Palermo', 42129, 42129);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (102449, 'bełcik', 102449, 102449);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (102760, 'deptaczek', 102760, 102760);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (36732, 'herbarz', 36732, 36732);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (104489, 'koedukacyjność', 104489, 104489);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (93334, 'ochotnik', 93334, 93334);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (46123, 'fakoemulsyfikacja', 46123, 46123);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (51260, 'kto z Bogiem zacznie, uczyni bacznie', 51260, 51260);
INSERT INTO public.words OVERRIDING SYSTEM VALUE VALUES (125987, 'holizm', 125987, 125987);


--
-- TOC entry 3247 (class 2606 OID 17559)
-- Name: meanings meanings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meanings
    ADD CONSTRAINT meanings_pkey PRIMARY KEY (id);


--
-- TOC entry 3266 (class 2606 OID 17624)
-- Name: missing_words missing_words_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.missing_words
    ADD CONSTRAINT missing_words_pk PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 17584)
-- Name: translations translations_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.translations
    ADD CONSTRAINT translations_name_key UNIQUE (name);


--
-- TOC entry 3255 (class 2606 OID 17582)
-- Name: translations translations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.translations
    ADD CONSTRAINT translations_pkey PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 17606)
-- Name: translation_word translations_words_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.translation_word
    ADD CONSTRAINT translations_words_pk PRIMARY KEY (word_id, translation_id);


--
-- TOC entry 3250 (class 2606 OID 17575)
-- Name: word_forms word_forms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_forms
    ADD CONSTRAINT word_forms_pkey PRIMARY KEY (id);


--
-- TOC entry 3260 (class 2606 OID 17591)
-- Name: words words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 17804)
-- Name: work_states work_states_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.work_states
    ADD CONSTRAINT work_states_pk PRIMARY KEY (chat_id);


--
-- TOC entry 3269 (class 1259 OID 17741)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

-- CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 3245 (class 1259 OID 17721)
-- Name: meanings_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX meanings_pk ON public.meanings USING btree (id);


--
-- TOC entry 3264 (class 1259 OID 17722)
-- Name: missing_word_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX missing_word_pk ON public.missing_words USING btree (id);


--
-- TOC entry 3261 (class 1259 OID 17723)
-- Name: translation_word_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX translation_word_pk ON public.translation_word USING btree (word_id, translation_id);


--
-- TOC entry 3253 (class 1259 OID 17724)
-- Name: translations_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX translations_pk ON public.translations USING btree (id);


--
-- TOC entry 3257 (class 1259 OID 17719)
-- Name: trgm_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX trgm_idx ON public.words USING gin (name public.gin_trgm_ops);


--
-- TOC entry 3256 (class 1259 OID 17720)
-- Name: trgm_idx_translations; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX trgm_idx_translations ON public.translations USING gin (name public.gin_trgm_ops);


--
-- TOC entry 3248 (class 1259 OID 17725)
-- Name: word_forms_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX word_forms_pk ON public.word_forms USING btree (id);


--
-- TOC entry 3258 (class 1259 OID 17726)
-- Name: words_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX words_pk ON public.words USING btree (id);


--
-- TOC entry 3272 (class 1259 OID 17805)
-- Name: work_states_pk_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX work_states_pk_index ON public.work_states USING btree (chat_id);


--
-- TOC entry 3275 (class 2606 OID 17612)
-- Name: translation_word translation_word_translation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.translation_word
    ADD CONSTRAINT translation_word_translation_id_fkey FOREIGN KEY (translation_id) REFERENCES public.translations(id) ON UPDATE CASCADE;


--
-- TOC entry 3276 (class 2606 OID 17607)
-- Name: translation_word translation_word_word_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.translation_word
    ADD CONSTRAINT translation_word_word_id_fkey FOREIGN KEY (word_id) REFERENCES public.words(id) ON UPDATE CASCADE;


--
-- TOC entry 3273 (class 2606 OID 17597)
-- Name: words words_meaning_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_meaning_id_fkey FOREIGN KEY (meaning_id) REFERENCES public.meanings(id) ON UPDATE CASCADE;


--
-- TOC entry 3274 (class 2606 OID 17592)
-- Name: words words_word_forms_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_word_forms_id_fkey FOREIGN KEY (word_forms_id) REFERENCES public.word_forms(id) ON UPDATE CASCADE;


-- Completed on 2022-10-16 20:19:44

--
-- PostgreSQL database dump complete
--

