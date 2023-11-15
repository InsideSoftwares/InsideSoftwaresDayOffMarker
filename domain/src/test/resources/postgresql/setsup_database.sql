DELETE FROM DOM_CONFIGURATION;
INSERT INTO DOM_CONFIGURATION (configuration_id,configuration_key,description,configuration_value,default_value) VALUES
	 ('34674a21-8c0e-4e03-825d-3997f1e5d8dc','LIMIT_FORWARD_DAYS_YEARS','Indicates the Limit for creating the days forward in years','30','30'),
	 ('7bc863f1-ee11-4cc4-8e22-4277d2fe2733','LIMIT_BACK_DAYS_YEARS','Indicates the Limit to create the days back in years','2','2'),
	 ('a5438218-45d8-47bb-8bd7-889f9ad99a0a','COUNTRY_DEFAULT','Default Application Country','BRAZIL','BRAZIL');

DELETE FROM DOM_FIXED_HOLIDAY;
INSERT INTO DOM_FIXED_HOLIDAY (fixed_holiday_id,"name",description,day_holiday,month_holiday,optional,from_time,"enable") VALUES
	 ('966eb9f5-36f8-4e38-a063-8765c2690ad6','Confraternização universal','Confraternização Universal',1,1,false,NULL,true),
	 ('4e196264-c59f-4d96-935b-07dffacff502','Tiradentes','Tiradentes',21,4,false,NULL,true),
	 ('ee1d4916-1407-4773-9c3b-93cd3bdcb032','Dia do Trabalho','Dia do Trabalho',1,5,false,NULL,true),
	 ('d070bd46-d241-4ef0-b4ee-753e0ea87035','Dia da Independência do Brasil','Dia da Independência do Brasil - 7 de Setembro',7,9,false,NULL,true),
	 ('35f855ea-4542-417c-982a-4c3d33bc2780','Nossa Senhora Aparecida','Nossa Senhora Aparecida',12,10,false,NULL,true),
	 ('076e8824-3d1e-493b-a58d-84d2c1f1ed0e','Finados','Finados',2,11,false,NULL,true),
	 ('17e53613-caa3-412c-a5ce-8a26cbf8d81b','Proclamação da República','Proclamação da República',15,11,false,NULL,true),
	 ('06743372-b861-49b8-a87a-63c154a0efac','Véspera de Natal','Véspera de Natal',24,12,true,NULL,false),
	 ('a64338d6-5111-4628-8803-a650f976b6b8','Natal','Natal',25,12,false,NULL,true),
	 ('8e515377-5f5b-43b2-8ed3-79babfbf88fb','Véspera de Ano-Novo','Véspera de Ano-Novo',31,12,true,NULL,false);

INSERT INTO DOM_COUNTRY (COUNTRY_ID, NAME, ACRONYM, CODE) VALUES('2596e281-4dc1-40bf-afb5-08b245de54a6', 'Brasil', 'BRS', 'BR01');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('938b4593-6eb5-4a3c-a504-eacaca04d893', 'Minas Gerais', 'MG', 'MG01', '2596e281-4dc1-40bf-afb5-08b245de54a6');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('b96fd571-e370-4d6a-a59b-cba58f93e980', 'Barbacena', 'BARB', 'BARB01', '938b4593-6eb5-4a3c-a504-eacaca04d893');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('4d2925fe-f58c-4821-accf-bd67b3b7a58d', 'Montes Claros', 'MTCS', 'MTCS01', '938b4593-6eb5-4a3c-a504-eacaca04d893');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('9f6507c5-f31f-4512-ac71-7ed708eeea42', 'Vespasiano', 'VESP', 'VESP01', '938b4593-6eb5-4a3c-a504-eacaca04d893');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('65af072d-dcbe-47be-b7a8-c54a57ab43dd', 'São Paulo', 'SP', 'SP01', '2596e281-4dc1-40bf-afb5-08b245de54a6');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('bea4a208-cd61-424c-b5e9-d5a93d9f39bd', 'Embu das Artes', 'EBAR', 'EBAR01', '65af072d-dcbe-47be-b7a8-c54a57ab43dd');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('c15b8719-a711-4687-8af0-dbf56625c17d', 'Águas de Lindóia', 'AGLD', 'AGLD01', '65af072d-dcbe-47be-b7a8-c54a57ab43dd');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('b04962ba-fbd7-4974-b921-df21c4cd507b', 'Botucatu', 'BOTU', 'BOTU01', '65af072d-dcbe-47be-b7a8-c54a57ab43dd');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('f7485292-7c4d-48c8-b46e-00efda156c45', 'Rio de Janeiro', 'RJ', 'RJ01', '2596e281-4dc1-40bf-afb5-08b245de54a6');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('97832307-11ed-4da8-8812-adb4c322f697', 'BELFORD ROXO', 'BEXO', 'BEXO01', 'f7485292-7c4d-48c8-b46e-00efda156c45');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('f30fe5bf-e14e-44d3-b4bb-be80eaa1fc59', 'PETRÓPOLIS', 'PELIS', 'PELIS01', 'f7485292-7c4d-48c8-b46e-00efda156c45');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('9fbf986e-3abe-4c87-aadf-65280fbcc48d', 'TERESÓPOLIS', 'TELIS', 'TELIS01', 'f7485292-7c4d-48c8-b46e-00efda156c45');

INSERT INTO DOM_COUNTRY (COUNTRY_ID, NAME, ACRONYM, CODE) VALUES('f79fe4a0-707f-4c51-a052-efac3e6b5d2e', 'Estados Unidos', 'EUA', 'EUA01');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('bf495081-7eca-4dab-9083-64a49d4273be', 'Texas', 'TX', 'TX01', 'f79fe4a0-707f-4c51-a052-efac3e6b5d2e');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('c7a9a1e4-9d6e-46fc-8e58-758ac901e3b9', 'Austin', 'AUIN', 'AUIN01', 'bf495081-7eca-4dab-9083-64a49d4273be');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('5e2beefd-ecec-454c-a8bd-dbdc4c85b923', 'Texas City', 'TXCT', 'TXCT01', 'bf495081-7eca-4dab-9083-64a49d4273be');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('c4a8d3c5-d15c-46d5-b48a-47a5de651965', 'Brownsville', 'BROW', 'BROW01', 'bf495081-7eca-4dab-9083-64a49d4273be');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('cd229a54-2b2e-4955-9138-8b7854398d5a', 'Ohio', 'OH', 'OH01', 'f79fe4a0-707f-4c51-a052-efac3e6b5d2e');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('72f47d56-b89a-40b7-b6c0-d37134ec8da1', 'Cincinnati', 'CITI', 'CITI01', 'cd229a54-2b2e-4955-9138-8b7854398d5a');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('2e94e812-7a1f-445a-a8a7-3aeecdef67ef', 'Toledo', 'TOLE', 'TOLE01', 'cd229a54-2b2e-4955-9138-8b7854398d5a');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('469938e2-7226-4ada-85c4-cce29068fa40', 'Chillicothe', 'CHITH', 'CHITH01', 'cd229a54-2b2e-4955-9138-8b7854398d5a');
INSERT INTO DOM_STATE (STATE_ID, NAME, ACRONYM, CODE, COUNTRY_ID) VALUES ('5379de3a-15e8-41c1-a09d-09324f0fec47', 'Oklahoma', 'OK', 'OK01', 'f79fe4a0-707f-4c51-a052-efac3e6b5d2e');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('b7066ed0-bbb8-4b16-a224-fc6ce74a3eae', 'Tulsa', 'TUL', 'TUL01', '5379de3a-15e8-41c1-a09d-09324f0fec47');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('c3a47d8a-2895-49d4-b162-e14f81d6da30', 'Broken Arrow', 'BRAW', 'BRAW01', '5379de3a-15e8-41c1-a09d-09324f0fec47');
INSERT INTO DOM_CITY (CITY_ID, NAME, ACRONYM, CODE, STATE_ID) VALUES('003b2f48-c279-42a3-b707-4772f41e45d6', 'Yukon', 'YUKN', 'YUKN01', '5379de3a-15e8-41c1-a09d-09324f0fec47');

INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('7d091330-29a8-46b4-bcf4-9715008db557','2023-09-01',false,false,'FRIDAY',244),
	 ('0b9b335a-a18b-4d6d-977b-febebde0dc42','2023-09-02',true,false,'SATURDAY',245),
	 ('e59d876d-3aff-4d94-a9b3-ecb332742017','2023-09-03',true,false,'SUNDAY',246),
	 ('d584acf9-d71d-4b35-b2dd-7a7e3587375b','2023-09-04',false,false,'MONDAY',247),
	 ('61c7ffda-d192-4a74-8158-8c659dba2638','2023-09-05',false,false,'TUESDAY',248),
	 ('d0098b52-0fb2-4d4c-890c-445b3a04cd2b','2023-09-06',false,false,'WEDNESDAY',249),
	 ('203746dc-0eaf-45f9-80d0-730b74dc35eb','2023-09-07',false,false,'THURSDAY',250),
	 ('e1541ce0-a95d-4d89-8c18-2ffd97ac58e3','2023-09-08',false,false,'FRIDAY',251),
	 ('b58de2d3-0991-4e3a-ba90-6692ed06829d','2023-09-09',true,false,'SATURDAY',252),
	 ('8683e4ca-9bca-41c4-bc1c-258e85b9d47d','2023-09-10',true,false,'SUNDAY',253);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('1f6813af-098c-4475-a55e-3f03dd720e40','2023-09-11',false,false,'MONDAY',254),
	 ('e02cff79-fa03-4881-b660-9ab8684bd09d','2023-09-12',false,false,'TUESDAY',255),
	 ('036b56e0-0a1d-4223-9c4a-6821a02a872f','2023-09-13',false,false,'WEDNESDAY',256),
	 ('91cdbd77-1546-4da8-b8f1-15997e95c539','2023-09-14',false,false,'THURSDAY',257),
	 ('c26d0814-b278-4cd2-81de-c688cf94f9ca','2023-09-15',false,false,'FRIDAY',258),
	 ('5b200685-b97a-4942-9149-34ccb82d1795','2023-09-16',true,false,'SATURDAY',259),
	 ('a8386a79-4873-4847-8789-48cefe5e56d0','2023-09-17',true,false,'SUNDAY',260),
	 ('b7fbe387-ada6-4404-b248-9c368769b94a','2023-09-18',false,false,'MONDAY',261),
	 ('eb95395a-d07d-45a1-a105-fa7fa6e658eb','2023-09-19',false,false,'TUESDAY',262),
	 ('7819c658-53bc-4477-8d80-1c82d3222290','2023-09-20',false,false,'WEDNESDAY',263);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('d494dff9-be30-4de2-9326-653c908813f6','2023-09-21',false,false,'THURSDAY',264),
	 ('20ca87c2-617b-4335-a99d-31efded14f63','2023-09-22',false,false,'FRIDAY',265),
	 ('b67806d4-cc2a-4be8-bea0-17b1c284b633','2023-09-23',true,false,'SATURDAY',266),
	 ('755cee78-037c-416c-b5b9-d1e421d76f21','2023-09-24',true,false,'SUNDAY',267),
	 ('abfeb980-aa24-4758-b72a-742031a6fb2c','2023-09-25',false,false,'MONDAY',268),
	 ('b7801dc6-c9e1-4d76-a94f-8b1241bdbea3','2023-09-26',false,false,'TUESDAY',269),
	 ('c925ed17-9aac-4d5b-93de-957567ffa4b4','2023-09-27',false,false,'WEDNESDAY',270),
	 ('74d205a8-d576-48e6-995a-a2fb85b4e90d','2023-09-28',false,false,'THURSDAY',271),
	 ('7e5a64a3-f763-497b-b130-10597732eea6','2023-09-29',false,false,'FRIDAY',272),
	 ('7cc8ffb2-e235-4be0-be04-151747738595','2023-09-30',true,false,'SATURDAY',273);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('266a95ea-3823-4784-b99e-930d77c4c163','2023-05-01',false,true,'MONDAY',121),
	 ('aa142a7e-d35d-4412-b95d-09150dc8a065','2023-05-02',false,false,'TUESDAY',122),
	 ('a11196ef-b230-4237-8769-9626574bcf6f','2023-05-03',false,false,'WEDNESDAY',123),
	 ('9b7a16b4-9483-4780-9998-b6f23006ff06','2023-05-04',false,false,'THURSDAY',124),
	 ('822220d9-18de-4e02-b8f8-950a1a007af9','2023-05-05',false,false,'FRIDAY',125),
	 ('7e508794-adf2-430f-ade2-068a83dead69','2023-05-06',true,false,'SATURDAY',126),
	 ('f0b7bc0b-6df2-418c-a572-7212853d2100','2023-05-07',true,false,'SUNDAY',127),
	 ('be90a9a2-fe46-4e4b-878c-da671f4cb97e','2023-05-08',false,false,'MONDAY',128),
	 ('f35421a2-caec-4669-a132-ae27c9928235','2023-05-09',false,false,'TUESDAY',129),
	 ('98b0c522-3509-42c9-a6d9-c03e89b0bb13','2023-05-10',false,false,'WEDNESDAY',130);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('98788252-e35c-4551-a6c0-6463a8d1668c','2023-05-11',false,false,'THURSDAY',131),
	 ('0db3c28a-9d70-4e8c-bec4-6e7e76163d34','2023-05-12',false,false,'FRIDAY',132),
	 ('fe2112d4-ec91-4744-b160-df51b833be49','2023-05-13',true,false,'SATURDAY',133),
	 ('3750932a-2e81-45d6-bd66-3c96c601d897','2023-05-14',true,false,'SUNDAY',134),
	 ('7b9b77e9-49dc-4e0d-8de7-2a42120329c6','2023-05-15',false,false,'MONDAY',135),
	 ('3f0a0e6a-1fa2-400c-aae7-c10e044e8ff0','2023-05-16',false,false,'TUESDAY',136),
	 ('f75c18d9-39a5-4a23-9125-caf87182ae45','2023-05-17',false,false,'WEDNESDAY',137),
	 ('0f6e477e-f4bc-4aed-8901-152abefdbb0c','2023-05-18',false,false,'THURSDAY',138),
	 ('151dd84a-1559-4666-9aa9-b5c581f77658','2023-05-19',false,false,'FRIDAY',139),
	 ('c10084c7-fd60-4323-870f-86105cd33c13','2023-05-20',true,false,'SATURDAY',140);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('eddfa983-48a3-4fe2-a73c-2fbeb5e5bc36','2023-05-21',true,false,'SUNDAY',141),
	 ('281c4868-1c27-4bc0-a10a-fb80de9fcdaf','2023-05-22',false,false,'MONDAY',142),
	 ('cafb54ab-d43d-4c0f-b10b-01977191bb61','2023-05-23',false,false,'TUESDAY',143),
	 ('021101b3-7dc1-47f8-8a94-6874fc4a2b0a','2023-05-24',false,false,'WEDNESDAY',144),
	 ('04375bd4-2a1b-4f1e-a315-a4216d34e418','2023-05-25',false,false,'THURSDAY',145),
	 ('db7fecb1-4bfb-4a83-b756-ee52fcc33351','2023-05-26',false,false,'FRIDAY',146),
	 ('0d5cb689-f9d3-4d78-90e0-8883da7df3d7','2023-05-27',true,false,'SATURDAY',147),
	 ('5891a6b9-ae72-482f-bd30-19994bc74fa3','2023-05-28',true,false,'SUNDAY',148),
	 ('ac37dc8d-3afb-4f1b-92b0-85bf4b92e6d1','2023-05-29',false,false,'MONDAY',149),
	 ('bb90a3ab-60a2-4db5-aeaf-7a13b5f640d2','2023-05-30',false,false,'TUESDAY',150);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('2822b728-9b55-447a-9662-fbd3bd2783af','2023-05-31',false,false,'WEDNESDAY',151),
	 ('20a6b5e5-48bf-4db7-8bcc-90a32a0d731b','2023-04-16',true,false,'SUNDAY',106),
	 ('08aa1d87-2787-42f8-88d3-2083e8d6d82e','2023-04-17',false,false,'MONDAY',107),
	 ('4140fbb7-3a55-4a1c-8677-7fd4e79ed13f','2023-04-18',false,false,'TUESDAY',108),
	 ('0cfda4f4-996b-4afe-9f63-774659c1aa6f','2023-04-19',false,false,'WEDNESDAY',109),
	 ('cd178566-a178-455c-a5d3-d266295a9400','2023-04-20',false,false,'THURSDAY',110),
	 ('20a17d8b-7d6c-4668-848a-cf2fbea18858','2023-04-21',false,true,'FRIDAY',111),
	 ('c12c1092-8606-43fa-9256-33ed437a125c','2023-04-22',true,false,'SATURDAY',112),
	 ('e1987b8e-de8b-4759-b5b2-f8b406b725aa','2023-04-23',true,false,'SUNDAY',113),
	 ('66cefe1a-9e12-4464-b8bf-e24f352b95e3','2023-04-24',false,false,'MONDAY',114);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('4b5925f6-9a6f-4df9-9ca1-4f79bd97f685','2023-04-25',false,false,'TUESDAY',115),
	 ('6d0de890-9dbd-4585-8dd7-e843a4996450','2023-04-26',false,false,'WEDNESDAY',116),
	 ('7ca19986-d2bf-4926-bcd1-ad852bf23672','2023-04-27',false,false,'THURSDAY',117),
	 ('38437180-f16e-4cf2-a49f-6748d9bc3dd7','2023-04-28',false,false,'FRIDAY',118),
	 ('d9f9aa39-1817-404e-b035-f6111863d784','2023-04-29',true,false,'SATURDAY',119),
	 ('75321e66-a77e-4b6e-ae96-dc30e3d0628c','2023-04-30',true,false,'SUNDAY',120),
	 ('1938f92d-50e9-436d-bdac-626b85b69111','2023-07-01',true,false,'SATURDAY',182),
	 ('1439c094-a5d6-4aee-ae1e-82626c630997','2023-07-02',true,false,'SUNDAY',183),
	 ('eb8eabf1-3d00-42c0-a5a8-37590b45850a','2023-07-03',false,false,'MONDAY',184),
	 ('49c107bf-fcd9-4d35-b147-f9feea2499a1','2023-07-04',false,false,'TUESDAY',185);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('d4a14448-7323-4c52-aee9-23a26a905063','2023-07-05',false,false,'WEDNESDAY',186),
	 ('1f970540-736d-4765-a4d8-269edb7149d9','2023-07-06',false,false,'THURSDAY',187),
	 ('dd58e922-18f1-4d94-bf60-55f1c029bbd6','2023-07-07',false,false,'FRIDAY',188),
	 ('4d624bcd-9e7a-4af8-890f-dab10df9fc68','2023-07-08',true,false,'SATURDAY',189),
	 ('8fa8da4f-8699-4171-ba5f-5e9a6d18d908','2023-07-09',true,false,'SUNDAY',190),
	 ('c8321c21-752d-49fa-9d0b-c03d5c914e44','2023-07-10',false,false,'MONDAY',191),
	 ('65f0601e-dac9-437f-b63b-0aaf0d07a85d','2023-07-11',false,false,'TUESDAY',192),
	 ('0ca17d3e-60a1-4709-a695-616b6377efd3','2023-07-12',false,false,'WEDNESDAY',193),
	 ('d09d26df-b65e-46f6-9d69-d36f60b33657','2023-07-13',false,false,'THURSDAY',194),
	 ('404d5e14-f540-4523-8d07-1509830231a1','2023-07-14',false,false,'FRIDAY',195);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('d7249eb6-60a3-4bb1-8798-b47273e85b57','2023-07-15',true,false,'SATURDAY',196),
	 ('b9525020-6c4a-4457-95ee-c01ca6430f18','2023-07-16',true,false,'SUNDAY',197),
	 ('477997ed-29c1-47bf-a0e7-c23ccb5b9b26','2023-07-17',false,false,'MONDAY',198),
	 ('571933e4-5db8-4130-83f8-a746119cb5ea','2023-07-18',false,false,'TUESDAY',199),
	 ('083849e8-9961-449d-8d22-34a8e9af663f','2023-07-19',false,false,'WEDNESDAY',200),
	 ('911a0686-aa5d-49c9-b47f-7189f09b6ec0','2023-07-20',false,false,'THURSDAY',201),
	 ('068328e4-9ab1-4e6f-8992-f22183e59e61','2023-07-21',false,false,'FRIDAY',202),
	 ('03b3c400-5262-4e88-9784-085d7b602c2f','2023-07-22',true,false,'SATURDAY',203),
	 ('2821c5c9-d35e-4f66-a38f-25d2b4dffbf8','2023-07-23',true,false,'SUNDAY',204),
	 ('f0c91917-4c65-43b3-b0a6-ef2c215f0549','2023-07-24',false,false,'MONDAY',205);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('5d6dd06b-a9cd-4ed0-9756-4979cc71c51a','2023-07-25',false,false,'TUESDAY',206),
	 ('005aa398-1148-468f-8fbb-0375ef0ef8fc','2023-07-26',false,false,'WEDNESDAY',207),
	 ('114d2299-45cf-4077-9ea5-66bbe0918566','2023-07-27',false,false,'THURSDAY',208),
	 ('598efa5a-b2c5-4f24-9ff9-fecd2b9a494f','2023-07-28',false,false,'FRIDAY',209),
	 ('1ab084f5-3e07-4fe5-8312-308d844a940d','2023-07-29',true,false,'SATURDAY',210),
	 ('f3432268-a733-484d-92f7-444b9bf1993f','2023-07-30',true,false,'SUNDAY',211),
	 ('7bfe771b-2294-4b89-a446-84cce26589ee','2023-07-31',false,false,'MONDAY',212),
	 ('a5c5959c-37dc-47a3-a86c-ad33ac8fc9a4','2023-03-17',false,false,'FRIDAY',76),
	 ('0f2f0201-093a-4e5b-9408-9b068260165c','2023-03-18',true,false,'SATURDAY',77),
	 ('50896650-51ef-4dc9-9dfc-725d9a83c147','2023-03-19',true,false,'SUNDAY',78);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('97c8ef61-53a3-4d2d-ac10-c3c7f62ee88b','2023-03-20',false,false,'MONDAY',79),
	 ('4fe86f36-f416-4198-980a-3a21709c44e5','2023-03-21',false,false,'TUESDAY',80),
	 ('86efeef2-49c2-47d5-8f80-49cdb633749e','2023-03-22',false,false,'WEDNESDAY',81),
	 ('3fad9ffa-e1e6-43ae-b206-34d3bfa48e98','2023-03-23',false,false,'THURSDAY',82),
	 ('2857af94-b3e2-4a1b-b3a4-c205c968066a','2023-03-24',false,false,'FRIDAY',83),
	 ('b04a55c7-8153-468e-a3a2-126e1b27ea15','2023-03-25',true,false,'SATURDAY',84),
	 ('483f58dd-3bf9-4b6e-9d81-0a1621474cd9','2023-03-26',true,false,'SUNDAY',85),
	 ('90da659e-d455-4d5e-81b1-f85973d15492','2023-03-27',false,false,'MONDAY',86),
	 ('09bdb851-9518-449a-a634-245bf55b9e69','2023-03-28',false,false,'TUESDAY',87),
	 ('94b62ec7-2fde-4103-897f-1b2506989ea2','2023-03-29',false,false,'WEDNESDAY',88);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('9f22f904-da12-4e2b-92a5-48ca3b61b2ec','2023-03-30',false,false,'THURSDAY',89),
	 ('e1f43f17-25a9-495f-8735-c52b3bd8b749','2023-03-31',false,false,'FRIDAY',90),
	 ('e1368a09-3b73-4ec0-b6e1-f2671998695a','2023-02-14',false,false,'TUESDAY',45),
	 ('a0247b11-295e-415d-b888-832b113de9e8','2023-02-15',false,false,'WEDNESDAY',46),
	 ('e1980764-b2b2-4c18-9af4-578c6324e732','2023-02-16',false,false,'THURSDAY',47),
	 ('34b6ccdc-9bfe-451f-aa87-15832f2ab1a3','2023-02-17',false,false,'FRIDAY',48),
	 ('b63fee77-8db5-4347-adc4-0060becf0562','2023-02-18',true,false,'SATURDAY',49),
	 ('10f0599f-9970-457e-938e-80f920fbe2cb','2023-02-19',true,false,'SUNDAY',50),
	 ('55922431-192e-4643-b326-668debb1621b','2023-02-20',false,false,'MONDAY',51),
	 ('18044052-080a-4002-ad0b-fba63da1fad5','2023-02-21',false,false,'TUESDAY',52);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('1c35fa28-6836-43c9-8204-03cb0fd5ca59','2023-02-22',false,false,'WEDNESDAY',53),
	 ('c03f6a87-2699-450e-89a0-394c41ede064','2023-02-23',false,false,'THURSDAY',54),
	 ('46a512b3-2585-47c7-b898-79054cb33134','2023-02-24',false,false,'FRIDAY',55),
	 ('9b491842-723d-42f0-ad08-4fc7882b81f2','2023-02-25',true,false,'SATURDAY',56),
	 ('05de794b-ed74-45fd-ab80-a5720e211c91','2023-02-26',true,false,'SUNDAY',57),
	 ('8c6b9757-00ff-4a37-a8be-83eedf22346a','2023-02-27',false,false,'MONDAY',58),
	 ('f80b5e1f-2365-4119-85de-40b9829cc771','2023-02-28',false,false,'TUESDAY',59),
	 ('f6e69b3f-7f42-4d5b-89cb-77466c0cb7de','2023-04-01',true,false,'SATURDAY',91),
	 ('73dda573-b199-44cc-ab10-d0dacd2d7aae','2023-04-02',true,false,'SUNDAY',92),
	 ('674983a1-6c47-43e5-bdc5-aba5f4be4556','2023-04-03',false,false,'MONDAY',93);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('354f35e2-f3fb-49ca-a55c-73ea880a9b29','2023-04-04',false,false,'TUESDAY',94),
	 ('a1232dd4-f54c-40a5-a535-ea949ec58480','2023-04-05',false,false,'WEDNESDAY',95),
	 ('ff7f257f-0174-43b1-95a5-352d04a55d3c','2023-04-06',false,false,'THURSDAY',96),
	 ('1b6c855a-e6e8-4117-a7db-03989136c130','2023-04-07',false,false,'FRIDAY',97),
	 ('3de83c1b-4d81-46f5-999f-0cfd25fb1231','2023-04-08',true,false,'SATURDAY',98),
	 ('6fb1a7dd-b56a-4887-bf5c-de2c2841532f','2023-04-09',true,false,'SUNDAY',99),
	 ('abf9f15d-1936-40dc-89f0-bf3961d0cf08','2023-04-10',false,false,'MONDAY',100),
	 ('c6eb83a5-3207-4510-b78d-e915a5c35053','2023-04-11',false,false,'TUESDAY',101),
	 ('522671f7-3e26-4e42-835f-d4999ad5f419','2023-04-12',false,false,'WEDNESDAY',102),
	 ('e6acaf37-c1fb-488a-a060-18e2eee40ad3','2023-04-13',false,false,'THURSDAY',103);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('b4bc894d-28b9-47e4-aaa7-16d363ded668','2023-04-14',false,false,'FRIDAY',104),
	 ('f6e0b08a-0b2c-490a-b7c0-9dbfbe932e5d','2023-04-15',true,false,'SATURDAY',105),
	 ('f09bec86-c6e8-479e-b538-c5f4dcccdbd2','2023-03-01',false,false,'WEDNESDAY',60),
	 ('4ba888d3-0a7a-445d-85dd-6fddad2a3a84','2023-03-02',false,false,'THURSDAY',61),
	 ('48ce8fb8-afdd-49d7-86c4-c3c0ad9d61bd','2023-03-03',false,false,'FRIDAY',62),
	 ('ab00437b-9751-4af8-b44c-d65f9e1000df','2023-03-04',true,false,'SATURDAY',63),
	 ('23cbf4fb-2b9f-4fb1-b72a-807906534ec9','2023-03-05',true,false,'SUNDAY',64),
	 ('43895626-23a9-441f-8143-464783b4810b','2023-03-06',false,false,'MONDAY',65),
	 ('3784ab2d-5eb9-4778-879b-a626a53dc772','2023-03-07',false,false,'TUESDAY',66),
	 ('f09f4e12-fd8d-4ef5-a115-670cc24e009c','2023-03-08',false,false,'WEDNESDAY',67);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('3efd370c-048e-4e65-8d95-a16d9a5957a0','2023-03-09',false,false,'THURSDAY',68),
	 ('d3e7bd2e-63a6-4eb7-b0d9-6d0f44f92fc8','2023-03-10',false,false,'FRIDAY',69),
	 ('f4bd25f2-cae6-4eaf-a639-f76a93b0378d','2023-03-11',true,false,'SATURDAY',70),
	 ('a9365857-55c3-4caa-8ba6-44b56067a1c8','2023-03-12',true,false,'SUNDAY',71),
	 ('4828c431-34ca-4347-ba25-3852781b8331','2023-03-13',false,false,'MONDAY',72),
	 ('a2ef14c5-7635-4ce7-9590-767b29f08784','2023-03-14',false,false,'TUESDAY',73),
	 ('819488c0-5f30-410a-9355-e0e6629af1ad','2023-03-15',false,false,'WEDNESDAY',74),
	 ('818e1113-d40d-4047-8e11-ab9a28679ea7','2023-03-16',false,false,'THURSDAY',75),
	 ('9fceb80c-fa64-4464-84f3-74b921e032ae','2023-01-01',true,true,'SUNDAY',1),
	 ('d5afcd1c-a0cb-4f65-9458-2c8669553a1c','2023-01-02',false,false,'MONDAY',2);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('fe5c5749-df7f-48dd-9ad7-97b5e188629a','2023-01-03',false,false,'TUESDAY',3),
	 ('06eaeedf-4313-44b3-afd3-3a0c72cda3e1','2023-01-04',false,false,'WEDNESDAY',4),
	 ('ef31f7cb-2a41-43fb-882f-555b478fe6ec','2023-01-05',false,false,'THURSDAY',5),
	 ('902ec833-cf23-44f1-b827-8f4dddc6136c','2023-01-06',false,false,'FRIDAY',6),
	 ('36046c3d-8975-4fbc-9f9d-cac99ec11b58','2023-01-07',true,false,'SATURDAY',7),
	 ('ee21a65b-c5b0-49d6-be44-6fe1a4118ec2','2023-01-08',true,false,'SUNDAY',8),
	 ('74a8d853-7430-4ba6-b93a-11e6d4bf07a1','2023-01-09',false,false,'MONDAY',9),
	 ('ef6f94b3-dc5e-438d-801f-6a7763791896','2023-01-10',false,false,'TUESDAY',10),
	 ('f214be53-118c-44b2-ba18-f09bc6c3b625','2023-01-11',false,false,'WEDNESDAY',11),
	 ('8464b37b-37df-4e59-baf9-315b97a0eca1','2023-01-12',false,false,'THURSDAY',12);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('435aeeae-6872-4671-b534-d2aa42aa7315','2023-01-13',false,false,'FRIDAY',13),
	 ('3e0ad694-fbca-467e-b50a-85a46d21e9b8','2023-01-14',true,false,'SATURDAY',14),
	 ('8c4a503c-6c3f-4679-8b43-5979b0f39cce','2023-01-15',true,false,'SUNDAY',15),
	 ('7fd58ad4-14ce-4fa3-864c-3b75a2b5e6f9','2023-01-16',false,false,'MONDAY',16),
	 ('60940e21-42fa-4c08-9195-112ab60660d7','2023-01-17',false,false,'TUESDAY',17),
	 ('794a2e74-b621-4b02-894f-026130f1ab52','2023-01-18',false,false,'WEDNESDAY',18),
	 ('168b956e-cd4a-41e6-9c1c-5d55b4d9a057','2023-01-19',false,false,'THURSDAY',19),
	 ('ca81eac2-b4da-4be7-a676-1907f943738b','2023-01-20',false,false,'FRIDAY',20),
	 ('20bb969b-1067-4f28-92f3-3ae148d9d2c3','2023-01-21',true,false,'SATURDAY',21),
	 ('d6270ca0-3b60-4bb3-bcb1-244dc6ad9dbd','2023-01-22',true,false,'SUNDAY',22);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('c20d23ea-947f-4af7-a333-03fd86151d1c','2023-01-23',false,false,'MONDAY',23),
	 ('5be3813c-25b4-4cec-8664-b85dfe109d10','2023-01-24',false,false,'TUESDAY',24),
	 ('beb42358-be2a-4509-b632-65bc20e94af7','2023-01-25',false,false,'WEDNESDAY',25),
	 ('956d7ae5-67d3-4bee-9c2a-d4ae6d4ba761','2023-01-26',false,false,'THURSDAY',26),
	 ('93136ad4-4404-486b-b68a-acf47d2cc0e4','2023-01-27',false,false,'FRIDAY',27),
	 ('19326192-9441-4909-b85a-9e88ceeebc48','2023-01-28',true,false,'SATURDAY',28),
	 ('f24c2cbb-5f8d-4408-814d-4052f94af2b0','2023-01-29',true,false,'SUNDAY',29),
	 ('ac989048-bddc-4007-9124-cb973fedb340','2023-01-30',false,false,'MONDAY',30),
	 ('4377792d-6d45-4c2e-9a81-f3d357efeac7','2023-01-31',false,false,'TUESDAY',31),
	 ('7103656b-b14d-47ea-ab95-4064a15d2764','2023-06-01',false,false,'THURSDAY',152);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('5cf89b92-9b98-40e1-9a1e-c32244c14926','2023-06-02',false,false,'FRIDAY',153),
	 ('60962646-3733-413a-bfab-1b9bb87c2eef','2023-06-03',true,false,'SATURDAY',154),
	 ('414222b2-0726-40c6-8197-c3d279b07d50','2023-06-04',true,false,'SUNDAY',155),
	 ('ac4d907f-5f9e-42fb-864c-1ef6e3356eea','2023-06-05',false,false,'MONDAY',156),
	 ('e5454e1b-c61c-475c-9b72-48e0448df2bf','2023-06-06',false,false,'TUESDAY',157),
	 ('7b495610-05bd-4b2e-9415-524648e315c0','2023-06-07',false,false,'WEDNESDAY',158),
	 ('cbfc2889-5ea4-484b-948b-6c35dbc154de','2023-06-08',false,false,'THURSDAY',159),
	 ('d184505c-f59c-4976-b863-a477f74aaea8','2023-06-09',false,false,'FRIDAY',160),
	 ('6a1db6a7-61c2-4305-a73f-fe9f6cea0e62','2023-06-10',true,false,'SATURDAY',161),
	 ('18390471-e4d9-4d24-952d-0c11b8b724e1','2023-06-11',true,false,'SUNDAY',162);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('83fee1ba-a136-43f2-a928-c39763dbb0ea','2023-06-12',false,false,'MONDAY',163),
	 ('658e8131-0c9a-4069-af85-adde32dfde8c','2023-06-13',false,false,'TUESDAY',164),
	 ('db55e3d1-cce4-4d61-b916-ae080e7d0272','2023-06-14',false,false,'WEDNESDAY',165),
	 ('97140fc4-5515-4b3c-af22-81cdcdfbab80','2023-06-15',false,false,'THURSDAY',166),
	 ('bb166021-280f-4545-9806-9715f0ef0b30','2023-06-16',false,false,'FRIDAY',167),
	 ('437f8269-2afb-4877-bf45-8bfd658af4a5','2023-06-17',true,false,'SATURDAY',168),
	 ('d8c8e455-1d2e-48db-8ce4-c902c11cf7c6','2023-06-18',true,false,'SUNDAY',169),
	 ('cb5a2745-acec-439f-9e4f-67201aff854e','2023-06-19',false,false,'MONDAY',170),
	 ('5c58683e-ee2a-4a55-899c-498a2ee84b66','2023-06-20',false,false,'TUESDAY',171),
	 ('e3dbe218-9e54-4e3f-b3bb-91044455b152','2023-06-21',false,false,'WEDNESDAY',172);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('eb21193c-06f3-4283-9319-f0a021009b01','2023-06-22',false,false,'THURSDAY',173),
	 ('633b5c26-e2c5-4240-81db-622cdb7cf017','2023-06-23',false,false,'FRIDAY',174),
	 ('2333956c-004a-45de-ba0b-e7831d014e11','2023-06-24',true,false,'SATURDAY',175),
	 ('4f022118-9cae-4a6b-b67d-080280274968','2023-06-25',true,false,'SUNDAY',176),
	 ('411bccfd-195f-473c-b02e-62b7e09b9466','2023-06-26',false,false,'MONDAY',177),
	 ('1b514ba4-b09c-4e9e-9ff2-c5e706f20f6f','2023-06-27',false,false,'TUESDAY',178),
	 ('b41d4232-1778-48c5-b5bd-1cc9bd861f37','2023-06-28',false,false,'WEDNESDAY',179),
	 ('c6c2ed6d-1bcd-44d8-9142-2400bb1f360f','2023-06-29',false,false,'THURSDAY',180),
	 ('aabb1fc0-5d72-4e9d-9db5-aaead9e59a12','2023-06-30',false,false,'FRIDAY',181),
	 ('67d2872c-a765-44cd-abe6-a73dfa47f69b','2023-02-01',false,false,'WEDNESDAY',32);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('dabf1da2-2ba3-4c3c-b5b1-4c3647985c8c','2023-02-02',false,false,'THURSDAY',33),
	 ('e43a4d6e-2937-4058-8409-cae4f222174f','2023-02-03',false,false,'FRIDAY',34),
	 ('dae6430e-da38-4089-98d4-647b9f83479d','2023-02-04',true,false,'SATURDAY',35),
	 ('7ba449ec-d84d-4b50-9355-686a6ac2b11e','2023-02-05',true,false,'SUNDAY',36),
	 ('5eec9513-cb7d-4dc6-a416-3e7aaade1c42','2023-02-06',false,false,'MONDAY',37),
	 ('421d2a8d-f6e2-42a1-9eb4-7544d648ba66','2023-02-07',false,false,'TUESDAY',38),
	 ('d54647c1-b00f-4895-8bf2-69948f34cd23','2023-02-08',false,false,'WEDNESDAY',39),
	 ('d2af86d6-9f37-4df3-80fa-f59cc69dfe31','2023-02-09',false,false,'THURSDAY',40),
	 ('ab287106-1c8d-4bbf-a3fb-bf0af4cce9ae','2023-02-10',false,false,'FRIDAY',41),
	 ('4048a670-21ed-4f8c-a6df-ce98358512ed','2023-02-11',true,false,'SATURDAY',42);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('686abbcb-1919-4eee-ad4a-cb213031d41b','2023-02-12',true,false,'SUNDAY',43),
	 ('a0e4934f-a42f-4687-a094-76cebe6edfba','2023-02-13',false,false,'MONDAY',44),
	 ('7020f099-7d77-4630-895e-756cfbdc9fec','2023-12-01',false,false,'FRIDAY',335),
	 ('4697dbd4-cd7e-441a-ac25-ddf4889d6243','2023-12-02',true,false,'SATURDAY',336),
	 ('f210340d-c3d9-4e72-bcce-4c6a04400f7a','2023-12-03',true,false,'SUNDAY',337),
	 ('2bd10d2c-7853-47d8-a3b4-ed093acfe660','2023-12-04',false,false,'MONDAY',338),
	 ('a38ae297-0d61-4d06-9df5-e62bad0ad677','2023-12-05',false,false,'TUESDAY',339),
	 ('74b43182-0a48-4758-b7d2-f541c03210d8','2023-12-06',false,false,'WEDNESDAY',340),
	 ('1517018f-afb6-448a-b124-87d843a25db4','2023-12-07',false,false,'THURSDAY',341),
	 ('a2585ae3-508a-4332-84af-0c0062b5aab7','2023-12-08',false,true,'FRIDAY',342);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('c2192e06-60fc-4f0c-bb7b-0d5e918e7ea8','2023-12-09',true,false,'SATURDAY',343),
	 ('9402c91e-99c0-4248-b7fb-4919802347a2','2023-12-10',true,false,'SUNDAY',344),
	 ('6d806d8f-8875-4da2-9862-df4e3b15f252','2023-12-11',false,false,'MONDAY',345),
	 ('85d5b3a4-94aa-46e0-af2a-fe8d3a969919','2023-12-12',false,false,'TUESDAY',346),
	 ('93b992d8-aa50-441d-b0ee-8e107e62f379','2023-12-13',false,false,'WEDNESDAY',347),
	 ('541f4b61-d0bf-4981-a782-b5173689371f','2023-12-14',false,false,'THURSDAY',348),
	 ('6a3c27ca-5c64-4a49-9258-e1722c32929b','2023-12-15',false,false,'FRIDAY',349),
	 ('31283943-011e-4704-89e9-1b0dc4ca8f74','2023-12-16',true,false,'SATURDAY',350),
	 ('d82a45a4-2fc1-4cba-bf1e-da454e32c676','2023-12-17',true,false,'SUNDAY',351),
	 ('ccb593e1-10c0-47e2-88be-8a3da600d362','2023-12-18',false,false,'MONDAY',352);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('1aec496f-af04-4756-84b5-4ddea5ef7a22','2023-12-19',false,false,'TUESDAY',353),
	 ('fba66caf-eb59-40d5-a4b6-05e41967dc56','2023-12-20',false,false,'WEDNESDAY',354),
	 ('c5a3f19a-229b-4d24-97e9-78c90ff92721','2023-12-21',false,false,'THURSDAY',355),
	 ('e3df6784-59e2-4aa3-9321-3d880d2af53f','2023-12-22',false,false,'FRIDAY',356),
	 ('8453af91-bdd6-4b4a-ae00-7d61a07705fc','2023-12-23',true,false,'SATURDAY',357),
	 ('1f79b919-99ea-4db7-a976-0dd2fc9f27b3','2023-12-24',true,false,'SUNDAY',358),
	 ('06937ba7-188f-46aa-bc8e-38a04f71af87','2023-12-25',false,true,'MONDAY',359),
	 ('e998bd6c-10e7-40a6-aa28-659d4dca3d1d','2023-12-26',false,false,'TUESDAY',360),
	 ('01abf84e-1297-46c3-a9a6-9eb346fc9d49','2023-12-27',false,false,'WEDNESDAY',361),
	 ('51908574-8ef5-472d-a6d6-b9b0f7d34727','2023-12-28',false,false,'THURSDAY',362);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('38f62bd8-97f2-4d6c-ad30-b8103e1d2031','2023-12-29',false,false,'FRIDAY',363),
	 ('40470274-1d2d-466f-aba0-bd5e1171889b','2023-12-30',true,false,'SATURDAY',364),
	 ('3a6aea78-4a1d-405f-bc04-c14aca89cfc9','2023-12-31',true,false,'SUNDAY',365),
	 ('28f5064a-be60-474b-99cc-35fdbe3d2cdd','2023-10-01',true,false,'SUNDAY',274),
	 ('a8d25d3d-3a0a-4ada-ab89-4b4c5afdd327','2023-10-02',false,false,'MONDAY',275),
	 ('a9311ce5-ffc0-4cab-8ed5-dbadf16caf2d','2023-10-03',false,false,'TUESDAY',276),
	 ('d8cad86e-9e83-46e5-9187-1d02f1849733','2023-10-04',false,false,'WEDNESDAY',277),
	 ('4a37d3c9-ab26-4208-a56f-2acdf50a3817','2023-10-05',false,false,'THURSDAY',278),
	 ('b6dd6fe8-9eac-4887-ab34-774c8d6d38bd','2023-10-06',false,false,'FRIDAY',279),
	 ('aea0e94b-f42c-4399-a385-20f6a7a5a71b','2023-10-07',true,false,'SATURDAY',280);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('23fc7af3-1e96-4e81-bbd6-c7034ee95a76','2023-10-08',true,false,'SUNDAY',281),
	 ('7dfeb1da-4944-4f18-860f-5077295cae5c','2023-10-09',false,false,'MONDAY',282),
	 ('7a3dff4e-8740-45a8-baa0-7f6a52732275','2023-10-10',false,false,'TUESDAY',283),
	 ('3fb0d0b2-32a2-4925-bc61-501c694203a6','2023-10-11',false,false,'WEDNESDAY',284),
	 ('061a5a70-d03c-4f48-b20e-136d61a55398','2023-10-12',false,false,'THURSDAY',285),
	 ('3af1e09b-a4ef-4091-941d-f93d13ba4b9f','2023-10-13',false,false,'FRIDAY',286),
	 ('4dd8c460-ce4f-4139-a8f5-55e4de42bedc','2023-10-14',true,false,'SATURDAY',287),
	 ('cc0d5086-1fc2-43ca-93ec-de06c01875e3','2023-10-15',true,false,'SUNDAY',288),
	 ('e1ed65ba-261c-44cd-9929-c736a3f0fc25','2023-10-16',false,false,'MONDAY',289),
	 ('4286be91-b8ed-4017-8a15-6193b2a1b77b','2023-10-17',false,false,'TUESDAY',290);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('2baba11c-c04b-4c64-9792-9d34f06eec3c','2023-10-18',false,false,'WEDNESDAY',291),
	 ('0e8661b1-2421-465f-a408-4309ad8020a8','2023-10-19',false,false,'THURSDAY',292),
	 ('ffe5d0b8-f107-4209-b351-050b11eb2c15','2023-10-20',false,false,'FRIDAY',293),
	 ('e6c6a3fe-ca44-4440-80a2-9b6033f578b2','2023-10-21',true,false,'SATURDAY',294),
	 ('1917a77d-5f6d-4067-a6ac-6fdb31a15862','2023-10-22',true,false,'SUNDAY',295),
	 ('71d64cf3-ce55-4380-bc8a-f92c3122e2f4','2023-10-23',false,false,'MONDAY',296),
	 ('fe627ccb-87f8-4a29-b500-31b7592b505f','2023-10-24',false,false,'TUESDAY',297),
	 ('cb9a02af-154d-4380-8b25-03a96c7a9f6d','2023-10-25',false,false,'WEDNESDAY',298),
	 ('b1cc528b-aa94-4a0d-ba02-00623fe9b60e','2023-10-26',false,false,'THURSDAY',299),
	 ('5b3d2ca7-d1ca-4399-bd58-b21ebacf86a5','2023-10-27',false,false,'FRIDAY',300);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('9bb44ee2-c3e6-463b-9bdc-8cb19f5d32f2','2023-10-28',true,false,'SATURDAY',301),
	 ('4dd9ea34-aa20-4b0a-bba6-3aff6e99ece6','2023-10-29',true,false,'SUNDAY',302),
	 ('bf3962d4-1886-4f1b-b577-6ac17dee4baf','2023-10-30',false,false,'MONDAY',303),
	 ('0e8ad8a6-5186-4684-8e70-afa018fd589d','2023-10-31',false,false,'TUESDAY',304),
	 ('34549722-814b-4728-ad8f-c2b5358bcaf7','2023-11-01',false,false,'WEDNESDAY',305),
	 ('f449cc6b-01d1-496d-8229-e081a2ad67af','2023-11-02',false,true,'THURSDAY',306),
	 ('b3087f6b-173e-4a0f-bf44-25748152bce1','2023-11-03',false,false,'FRIDAY',307),
	 ('38590cff-5a89-4901-ac83-a8e0f044b602','2023-11-04',true,false,'SATURDAY',308),
	 ('33867445-d9ea-45dd-9c87-010df7c3d09d','2023-11-05',true,false,'SUNDAY',309),
	 ('646340f2-6370-4330-ac9c-f85857c7550e','2023-11-06',false,false,'MONDAY',310);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('c7007864-599b-4e54-9d70-6132a374b0ea','2023-11-07',false,false,'TUESDAY',311),
	 ('3ae2c5de-e781-47ab-937e-45a9caccfea9','2023-11-08',false,false,'WEDNESDAY',312),
	 ('5f2ec850-3611-43be-ae0c-0fb8d08bf514','2023-11-09',false,false,'THURSDAY',313),
	 ('03d6d0be-f7db-49b3-905a-72b34d6ad67f','2023-11-10',false,false,'FRIDAY',314),
	 ('ebf9265f-f2c3-4efa-9e75-6473810f7bec','2023-11-11',true,false,'SATURDAY',315),
	 ('5e52b7b5-4c61-4c84-b7d8-4a4be47c7ee7','2023-11-12',true,false,'SUNDAY',316),
	 ('8dfa7def-6328-47f9-a925-a8e73f8d512b','2023-11-13',false,false,'MONDAY',317),
	 ('40f003ec-1988-440d-ad19-d46603b404c3','2023-11-14',false,false,'TUESDAY',318),
	 ('0a48889a-6ad3-45ef-b4ff-0e175514f890','2023-11-15',false,true,'WEDNESDAY',319),
	 ('58ff087c-2212-4378-b8e9-15e991e7c720','2023-11-16',false,false,'THURSDAY',320);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('34814886-f95a-4baa-8cb5-e68102055da2','2023-11-17',false,false,'FRIDAY',321),
	 ('d79c2741-2e0e-4b24-943e-456aecb72329','2023-11-18',true,false,'SATURDAY',322),
	 ('a83a32b8-7e08-4147-979d-e502123c9557','2023-11-19',true,false,'SUNDAY',323),
	 ('35f09006-9fd1-4ff8-91df-23029429498e','2023-11-20',false,false,'MONDAY',324),
	 ('5c006aa1-84f1-4bc5-b8bf-fbaa6f998b3d','2023-11-21',false,false,'TUESDAY',325),
	 ('3869b6d7-5014-49a8-be14-4de3aaa9f1af','2023-11-22',false,false,'WEDNESDAY',326),
	 ('370beab8-9136-4655-989d-c93df1258fd0','2023-11-23',false,false,'THURSDAY',327),
	 ('36bce9e4-ebdc-46e8-8571-027a4047f19c','2023-11-24',false,false,'FRIDAY',328),
	 ('d618a015-e29c-421a-87d1-1f5677dc603e','2023-11-25',true,false,'SATURDAY',329),
	 ('89f7238d-608c-463c-b370-f20b77ca1b54','2023-11-26',true,false,'SUNDAY',330);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('4fec0e7d-ce82-4de4-a76a-238971215291','2023-11-27',false,false,'MONDAY',331),
	 ('39e1a3da-8fa6-4a7e-a4aa-4ee1a662ab19','2023-11-28',false,false,'TUESDAY',332),
	 ('6dbe1465-65c2-49bd-a41b-106c46ffa61b','2023-11-29',false,false,'WEDNESDAY',333),
	 ('b09ca6fa-03dd-4ce0-aeef-96539adc2352','2023-11-30',false,false,'THURSDAY',334),
	 ('2b70dd62-be89-40f6-930e-83e84248c866','2023-08-01',false,false,'TUESDAY',213),
	 ('cc362d4f-8e5c-4438-8118-144e2d6c2e17','2023-08-02',false,false,'WEDNESDAY',214),
	 ('f10c2c0d-be21-43f7-a165-b2a9bafc7620','2023-08-03',false,false,'THURSDAY',215),
	 ('068e8e87-4df8-49ea-972f-8c591b0b3ea4','2023-08-04',false,false,'FRIDAY',216),
	 ('a0231c9d-f352-4100-b7c8-25d39acdc057','2023-08-05',true,false,'SATURDAY',217),
	 ('f066cd6c-47ad-4ddc-ad60-ac7709192d0e','2023-08-06',true,false,'SUNDAY',218);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('f7bc56a3-d09d-43b5-a517-332adbaa1635','2023-08-07',false,false,'MONDAY',219),
	 ('b0d8fad1-0d3a-4a59-9cc0-fbcbc4b92b39','2023-08-08',false,false,'TUESDAY',220),
	 ('96f358a1-1c56-433c-b61e-35b596d520d9','2023-08-09',false,false,'WEDNESDAY',221),
	 ('17dc3427-195b-4ab2-b58e-1a5d8e97a178','2023-08-10',false,false,'THURSDAY',222),
	 ('c957d9c7-9871-4451-a2f4-5f13d1d44f0a','2023-08-11',false,false,'FRIDAY',223),
	 ('88e1a0b1-3943-49f0-a41a-e5a41a150329','2023-08-12',true,false,'SATURDAY',224),
	 ('806de4e3-17cc-439b-9fcd-f66de9a644f2','2023-08-13',true,false,'SUNDAY',225),
	 ('b6515e5c-bdeb-4340-b54f-bfcad76c89c1','2023-08-14',false,false,'MONDAY',226),
	 ('a69280d5-c853-4be7-9c0d-aa7b5c4dabb0','2023-08-15',false,false,'TUESDAY',227),
	 ('8ec65463-06fd-417b-aeec-d61f753aa7b5','2023-08-16',false,false,'WEDNESDAY',228);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('467ac05d-aa1a-42ac-babc-0a4328fa2bab','2023-08-17',false,false,'THURSDAY',229),
	 ('f8ffbb80-da1b-43db-8cc2-c915fdee67f3','2023-08-18',false,false,'FRIDAY',230),
	 ('d9dff4c5-ee7b-4294-b832-4db8b747d4e6','2023-08-19',true,false,'SATURDAY',231),
	 ('48f9bbb3-ca1a-44e4-8929-f94d1bcd4135','2023-08-20',true,false,'SUNDAY',232),
	 ('a025ba2b-fd13-48c1-8aa5-f8c1e3acc2b9','2023-08-21',false,false,'MONDAY',233),
	 ('bb8978ab-ad72-4212-9314-f3af1d42c29d','2023-08-22',false,false,'TUESDAY',234),
	 ('f580793a-1235-4ddb-9cf5-1575a12d1f11','2023-08-23',false,false,'WEDNESDAY',235),
	 ('a71ce80a-0fbe-4944-9fd4-799d1562b610','2023-08-24',false,false,'THURSDAY',236),
	 ('aa4427e6-d799-4cd0-b578-f330a24b852f','2023-08-25',false,false,'FRIDAY',237),
	 ('1ce98d64-ae6b-426c-9140-f2192fd7d3a7','2023-08-26',true,false,'SATURDAY',238);
INSERT INTO dom_day (day_id,"date",weekend,holiday,day_of_week,day_of_year) VALUES
	 ('96deb5bf-7a09-48d4-b5ad-24a53caedf60','2023-08-27',true,false,'SUNDAY',239),
	 ('51fb7bc1-a359-44d9-90d5-22938edab59e','2023-08-28',false,false,'MONDAY',240),
	 ('c5a24d87-706f-48fb-b90f-ced9900833f9','2023-08-29',false,false,'TUESDAY',241),
	 ('a9ae3ad7-9f7f-4bc2-9715-28f66ea75610','2023-08-30',false,false,'WEDNESDAY',242),
	 ('5391ad50-5c73-4d98-a94e-2f9d8be7c417','2023-08-31',false,false,'THURSDAY',243);

INSERT INTO dom_holiday (holiday_id,"name",description,"type",from_time,optional,automatic_update,day_id,fixed_holiday_id,national_holiday) VALUES
	 ('aef7a0eb-9086-4fc7-a15f-76fb9f6e82a7','Dia da Independência do Brasil','Dia da Independência do Brasil - 7 de Setembro','MANDATORY',NULL,false,true,'203746dc-0eaf-45f9-80d0-730b74dc35eb','d070bd46-d241-4ef0-b4ee-753e0ea87035',true),
	 ('73651fb4-7bbb-4d95-b3a4-9c7ee737609a','Dia do Trabalho','Dia do Trabalho','MANDATORY',NULL,false,true,'266a95ea-3823-4784-b99e-930d77c4c163','ee1d4916-1407-4773-9c3b-93cd3bdcb032',true),
	 ('af1f57fb-7ef6-4bc1-a544-88ca0f8dde1d','Tiradentes','Tiradentes','MANDATORY',NULL,false,true,'20a17d8b-7d6c-4668-848a-cf2fbea18858','4e196264-c59f-4d96-935b-07dffacff502',true),
	 ('10e31d7a-1293-4096-a11a-4e1769d9eae0','Confraternização universal','Confraternização Universal','MANDATORY',NULL,false,true,'9fceb80c-fa64-4464-84f3-74b921e032ae','966eb9f5-36f8-4e38-a063-8765c2690ad6',true),
	 ('50339973-2a87-4ad6-9ca7-1702062e7acd','Véspera de Natal','Véspera de Natal','OPTIONAL',NULL,true,true,'1f79b919-99ea-4db7-a976-0dd2fc9f27b3','06743372-b861-49b8-a87a-63c154a0efac',true),
	 ('d4b6d032-fc27-4608-894c-7eea6bf3ce9f','Natal','Natal','MANDATORY',NULL,false,true,'06937ba7-188f-46aa-bc8e-38a04f71af87','a64338d6-5111-4628-8803-a650f976b6b8',true),
	 ('ada3572d-884f-4d16-a686-e14abaf5f5cc','Véspera de Ano-Novo','Véspera de Ano-Novo','OPTIONAL',NULL,true,true,'3a6aea78-4a1d-405f-bc04-c14aca89cfc9','8e515377-5f5b-43b2-8ed3-79babfbf88fb',true),
	 ('cebdce92-4f6c-446b-bcba-9fddad60e613','Nossa Senhora Aparecida','Nossa Senhora Aparecida','MANDATORY',NULL,false,true,'061a5a70-d03c-4f48-b20e-136d61a55398','35f855ea-4542-417c-982a-4c3d33bc2780',true),
	 ('11c39bb6-603f-4da2-9aa8-eee10386c8ab','Finados','Finados','MANDATORY',NULL,false,true,'f449cc6b-01d1-496d-8229-e081a2ad67af','076e8824-3d1e-493b-a58d-84d2c1f1ed0e',true),
	 ('692fbfa7-9d37-4431-8744-730fba834754','Dia da Consciência Negra','Dia da Consciência Negra','MANDATORY',NULL,false,true,'35f09006-9fd1-4ff8-91df-23029429498e', null ,true),
	 ('e4ca2ed1-b666-43f5-a335-d614ae9a946a','Proclamação da República','Proclamação da República','MANDATORY',NULL,false,true,'0a48889a-6ad3-45ef-b4ff-0e175514f890','17e53613-caa3-412c-a5ce-8a26cbf8d81b',true);

INSERT INTO dom_state_holiday (state_id,holiday_id,holiday) VALUES
	 ('65af072d-dcbe-47be-b7a8-c54a57ab43dd','692fbfa7-9d37-4431-8744-730fba834754',true),
	 ('938b4593-6eb5-4a3c-a504-eacaca04d893','692fbfa7-9d37-4431-8744-730fba834754',false);

INSERT INTO dom_tag (tag_id,description,code) VALUES
	 ('512eff83-3fcc-4892-8107-386955d22629','TAG_ONE','TONE'),
	 ('edcb6c0d-d14a-4ada-94b6-fdaedc2972c9','TAG_TWO','TTWO'),
	 ('69a66834-8d59-48b1-9210-c4d83e1ad80c','TAG_TAG','TTAG'),
	 ('87ef68d8-6007-419a-a334-81ddce453b79','TAG_RAVEL','RAVEL'),
	 ('89533fcc-6b11-42a6-8038-72c7e3ce2796','TAG_AKLA','AKLA'),
	 ('e92a9d90-4ad4-40f3-a231-74a0f958790b','TAG','TAG');

INSERT INTO dom_day_tag (day_id,tag_id)
	select day_id, '512eff83-3fcc-4892-8107-386955d22629' tag_id from dom_day where date_part('DAY', date) = 11;

INSERT INTO dom_day_tag (day_id,tag_id)
	select day_id, 'edcb6c0d-d14a-4ada-94b6-fdaedc2972c9' tag_id from dom_day where date_part('DAY', date) = 25;

INSERT INTO dom_day_tag (day_id,tag_id)
	select day_id, '69a66834-8d59-48b1-9210-c4d83e1ad80c' tag_id from dom_day where date_part('DAY', date) = 31;

INSERT INTO dom_day_tag (day_id,tag_id)
	select day_id, '87ef68d8-6007-419a-a334-81ddce453b79' tag_id from dom_day where date_part('DAY', date) = 29 and date_part('MONTH', date) = 2;

INSERT INTO dom_day_tag (day_id,tag_id)
	select day_id, '89533fcc-6b11-42a6-8038-72c7e3ce2796' tag_id from dom_day where date_part('MONTH', date) = 11;
