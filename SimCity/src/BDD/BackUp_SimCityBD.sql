--
-- PostgreSQL database dump
--

\restrict xBVMYvTFchM61f8qk5eZe1JL2JZMbbemv9wIZMLWTRQVnuWAVVpg5hvKcuPXq4J

-- Dumped from database version 16.13
-- Dumped by pg_dump version 16.13

-- Started on 2026-04-04 01:18:08

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
-- TOC entry 4806 (class 0 OID 16899)
-- Dependencies: 216
-- Data for Name: alcaldes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alcaldes (id, nombre, apellido, dni, genero, clave) FROM stdin;
2	Pedro Roberto	Pérez Suarez	987313	Masculino	987313prps!A
1	Ana Maria	Rojas Martinez	1212749	Femenino	121274AMRM!a
3	Eliyht Bose	Smith Morales	1010109	Otro	101010EBSM!a
5	Mario Eduardo	Sanchez Colina	12345678	Masculino	12345678!1aA
\.


--
-- TOC entry 4808 (class 0 OID 16909)
-- Dependencies: 218
-- Data for Name: ciudades; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ciudades (id, nombre, x, y, poblacion, descripcion, fechacreado, idalcalde) FROM stdin;
1	El Cairo	256	256	0	Piramides	2026-04-04 00:59:59.634732-04	1
2	New York USA	64	64	0	Time Square!!	2026-04-04 01:03:19.732787-04	1
3	Maracay	32	32	0	Hola Mundo	2026-04-04 01:03:53.458243-04	1
4	Maracaibo	256	256	0	Best Ciudad	2026-04-04 01:04:25.407654-04	3
\.


--
-- TOC entry 4810 (class 0 OID 16925)
-- Dependencies: 220
-- Data for Name: tiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tiles (id, idciudad, tipo, posx, posy, dimension, rotacion) FROM stdin;
\.


--
-- TOC entry 4820 (class 0 OID 0)
-- Dependencies: 215
-- Name: alcaldes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alcaldes_id_seq', 4, true);


--
-- TOC entry 4821 (class 0 OID 0)
-- Dependencies: 217
-- Name: ciudades_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ciudades_id_seq', 7, true);


--
-- TOC entry 4822 (class 0 OID 0)
-- Dependencies: 219
-- Name: tiles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tiles_id_seq', 1, false);


-- Completed on 2026-04-04 01:18:09

--
-- PostgreSQL database dump complete
--

\unrestrict xBVMYvTFchM61f8qk5eZe1JL2JZMbbemv9wIZMLWTRQVnuWAVVpg5hvKcuPXq4J

