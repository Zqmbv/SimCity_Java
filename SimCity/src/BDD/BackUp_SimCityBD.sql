--
-- PostgreSQL database dump
--

\restrict hf5gbSyGFXT02kHcSSwMb638tcNzVqXMVH8MOvDcpaY0ak36s67pqezZU5GjMHq

-- Dumped from database version 16.13
-- Dumped by pg_dump version 16.13

-- Started on 2026-03-30 12:18:30

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16453)
-- Name: alcaldes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alcaldes (
    id integer NOT NULL,
    nombre character varying(64) NOT NULL,
    apellido character varying(64) NOT NULL,
    dni integer NOT NULL,
    genero character varying(16) NOT NULL,
    clave character varying(32) NOT NULL,
    CONSTRAINT validar_genero CHECK (((genero)::text = ANY ((ARRAY['Masculino'::character varying, 'Femenino'::character varying, 'Otro'::character varying])::text[])))
);


ALTER TABLE public.alcaldes OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16452)
-- Name: alcaldes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alcaldes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.alcaldes_id_seq OWNER TO postgres;

--
-- TOC entry 4790 (class 0 OID 0)
-- Dependencies: 215
-- Name: alcaldes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alcaldes_id_seq OWNED BY public.alcaldes.id;


--
-- TOC entry 4634 (class 2604 OID 16456)
-- Name: alcaldes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alcaldes ALTER COLUMN id SET DEFAULT nextval('public.alcaldes_id_seq'::regclass);


--
-- TOC entry 4784 (class 0 OID 16453)
-- Dependencies: 216
-- Data for Name: alcaldes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alcaldes (id, nombre, apellido, dni, genero, clave) FROM stdin;
\.


--
-- TOC entry 4791 (class 0 OID 0)
-- Dependencies: 215
-- Name: alcaldes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alcaldes_id_seq', 19, true);


--
-- TOC entry 4637 (class 2606 OID 16461)
-- Name: alcaldes alcaldes_dni_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alcaldes
    ADD CONSTRAINT alcaldes_dni_key UNIQUE (dni);


--
-- TOC entry 4639 (class 2606 OID 16459)
-- Name: alcaldes alcaldes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alcaldes
    ADD CONSTRAINT alcaldes_pkey PRIMARY KEY (id);


-- Completed on 2026-03-30 12:18:31

--
-- PostgreSQL database dump complete
--

\unrestrict hf5gbSyGFXT02kHcSSwMb638tcNzVqXMVH8MOvDcpaY0ak36s67pqezZU5GjMHq

