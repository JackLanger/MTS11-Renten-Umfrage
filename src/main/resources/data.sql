-- questions
SET IDENTITY_INSERT dbo.t_question ON
INSERT INTO dbo.t_question (id, question_text, question_type)
-- allgemein
VALUES (1, N'Geschlecht', 3),
       (2, N'Familienstand', 3),
       (3, N'Anzahl Kinder', 3),
       (4, N'Höchster Abschluss', 3),
       (5, N'Berufsgruppe', 3),
       (6, N'Monatliche Bruttoeinkommen', 3),
       (7, N'In welchem Umfeld wurden Sie auf das Thema Rente aufmerksam geworden', 4),
       (8, N'In wieweit hat das Thema Rente Einfluss auf Ihre Familienplanung', 2),
       (9, N'Was glauben Sie wird das Renteneintrittsalter sein wenn Sie in Rente gehen?', 5),
       (10, N'Mit welchem Alter würden Sie gerne in Rente gehen?', 5),
       (11, N'Wie sehr haben Sie sich bisher mit dem Thema Rente beschäftigt?', 2),
       (12, N'Wie gut schätzen Sie Ihr Wissen über die gesetzliche Rentenversicherung ein?', 7),
       (13, N'„Denn eins ist sicher – die Rente“. Würden Sie dieser Aussage eines Wahlplakats von 1986 heute noch zustimmen?', 2),
       (14, N'Welche der folgenden Begriffe könnten Sie jemand anderem in einfachen Worten erklären?', 4),
       (15, N'Wie ausschlaggebend war/ist Ihre spätere Rente für Ihre Karriereentscheidung?', 2),
       (16, N'Für wie wahrscheinlich halten Sie es, dass Sie in Ihrem aktuellem Beruf bis zur Rente arbeiten werden?', 2),
       (17, N'In welchem Alter haben Sie angefangen Regelmäßig in die Gesetzliche RV einzuzahlen', 5),
       (18, N'Wie schätzen sie ihre finanzielle Absicherung im Alter durch die gesetzliche Rente ein?', 7),
       (19, N'Wie ausschlaggebend war die betriebliche Rente oder Möglichkeit zu vermögenswirksamen Leistungen o.Ä. für die Wahl Ihres Arbeitgebers?', 2),
       (20, N'Wie schätzen Sie die Auswirkungen durch die aktuellen Preissteigerungen auf Ihre Rentensituation ein?', 2),
       (21, N'Wie viel Aufwand haben Sie in die Planung Ihrer Altersvorsorge gesteckt?', 2),
       (22, N'Welche der folgenden Maßnahmen zur Sicherung Ihrer Rente haben Sie bereits getroffen?', 3),
       (23, N'Wie sicher fühlen Sie sich bezüglich ihrer Vorkehrungen', 6),
       (24, N'Wie viel Geld investieren Sie in etwa monatlich in Ihre private Altersvorsorge?', 3),
       (25, N'Hat diese Umfrage Sie angeregt für Ihre Rente Maßnahmen zu ergreifen?', 1)
SET IDENTITY_INSERT dbo.t_question OFF

INSERT INTO dbo.t_question_answer (question_p_question_id, answer_option, answer_value)
VALUES (1, N'männlich', 0),       -- geschlecht
       (1, N'weiblich', 1),
       (1, N'diverse', 2),
       (2, N'Ledig', 0),          -- familienstand
       (2, N'Verheiratet', 1),
       (2, N'Eingetragene Lebenspartnerschaft', 2),
       (2, N'Geschieden', 3),
       (2, N'Verwitwet', 4),
       (4, N'Ohne Abschluss', 0), -- abschluss
       (4, N'Hauptschulabschluss', 1),
       (4, N'Mittlere Reife/ Realschulabschluss', 2),
       (4, N'Hochschulreife', 3),
       (4, N'Nachschulischer Abschluss', 4),
       (4, N'Ausbildung', 5),
       (4, N'Studium', 6),
       (5, N'Schüler*in', 0),     -- berufsgruppe
       (5, N'Auszubildende*r', 1),
       (5, N'Student*in', 2),
       (5, N'Angestellte*r', 3),
       (5, N'Selbstständige*r', 4),
       (5, N'Beamte*r', 5),
       (5, N'Rentner*in/Pensionär*in', 6),
       (7, N'Familie', -1),       -- umfeld
       (7, N'Freunde', -1),
       (7, N'Beruf', -1),
       (7, N'Schule', -1),
       (7, N'Rentenbescheid', -1),
       (7, N'Werbekampagne', -1),
       (7, N'Politik', -1),
       (7, N'Soziale Medien', -1),
       (7, N'Sonstige', -1),
       (7, N'Rente? noch nie gehört.', -1),
       (22, N'Betriebliche Altersvorsorge', -1),
       (22, N'Private Rentenversicherung', -1),
       (22, N'Lebensversicherung Anlagen', -1),
       (22, N'Immobilien und Wertgegenstände', -1),
       (22, N'Andere', -1),
       (22, N'Keine', -1),
       (24, N'Nichts', 0),        -- income
       (24, N'weniger als 50€', 1),
       (24, N'50-99 €', 2),
       (24, N'100-199 €', 3),
       (24, N'200-299 €', 4),
       (24, N'300-499 €', 5),
       (24, N'500-999 €', 6),
       (24, N'mehr als 1000 €', 7)

