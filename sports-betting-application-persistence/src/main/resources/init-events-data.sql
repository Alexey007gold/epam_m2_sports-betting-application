CREATE TABLE sportsbetting_oleksii_kovetskyi.bet_id (
  id INT
);
CREATE TABLE sportsbetting_oleksii_kovetskyi.outcome_id (
  id INT
);

INSERT INTO sportsbetting_oleksii_kovetskyi.sport_event (id, event_type, title, start_date, end_date) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), 'F', 'Southampton v Bournemoth', '2019-10-27T19:00', '2019-10-27T21:00');
UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;


    INSERT INTO sportsbetting_oleksii_kovetskyi.bet_id VALUES((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence));
    UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
    INSERT INTO sportsbetting_oleksii_kovetskyi.bet (id, sport_event_id, description, bet_type) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), (SELECT id FROM sportsbetting_oleksii_kovetskyi.sport_event WHERE title = 'Southampton v Bournemoth'), '', 'W');

        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_id VALUES((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence));
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), 'Southampton');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 4.0, '2018-09-23T19:00', '2018-09-30T18:59');
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 5.0, '2018-09-30T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), 'Bournemoth');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.7, '2018-09-23T19:00', '2018-09-30T18:59');
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.5, '2018-09-30T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), 'Draw');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.7, '2018-09-23T19:00', '2018-09-30T18:59');
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.5, '2018-09-30T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;



    UPDATE sportsbetting_oleksii_kovetskyi.bet_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
    UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
    INSERT INTO sportsbetting_oleksii_kovetskyi.bet (id, sport_event_id, description, bet_type) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), (SELECT id FROM sportsbetting_oleksii_kovetskyi.sport_event WHERE title = 'Southampton v Bournemoth'), '', 'G');

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '0');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.75, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '1');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.25, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '>=2');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.05, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;


    UPDATE sportsbetting_oleksii_kovetskyi.bet_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
    UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
    INSERT INTO sportsbetting_oleksii_kovetskyi.bet (id, sport_event_id, description, bet_type) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), (SELECT id FROM sportsbetting_oleksii_kovetskyi.sport_event WHERE title = 'Southampton v Bournemoth'), 'scores of Victor Wanyama', 'S');

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '0');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.05, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '1');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.8, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), '2');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 3.15, '2018-09-23T19:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;



UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
INSERT INTO sportsbetting_oleksii_kovetskyi.sport_event (id, event_type, title, start_date, end_date) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), 'T', 'Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round', '2019-10-10T19:00', '2019-10-10T22:00');
UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

    UPDATE sportsbetting_oleksii_kovetskyi.bet_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
    UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
    INSERT INTO sportsbetting_oleksii_kovetskyi.bet (id, sport_event_id, description, bet_type) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), (SELECT id FROM sportsbetting_oleksii_kovetskyi.sport_event WHERE title = 'Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round'), '', 'W');

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), 'Rafael Nadal');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.01, '2018-01-01T00:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;

        UPDATE sportsbetting_oleksii_kovetskyi.outcome_id SET id = (SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence);
        UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
        INSERT INTO sportsbetting_oleksii_kovetskyi.outcome (id, bet_id, value) VALUES ((SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), (SELECT id from sportsbetting_oleksii_kovetskyi.bet_id), 'Alexander Zverev');

            INSERT INTO sportsbetting_oleksii_kovetskyi.outcome_odd (id, outcome_id, value, valid_from, valid_to) VALUES ((SELECT next_val FROM sportsbetting_oleksii_kovetskyi.hibernate_sequence), (SELECT id from sportsbetting_oleksii_kovetskyi.outcome_id), 1.7, '2018-01-01T00:00', NULL);
            UPDATE sportsbetting_oleksii_kovetskyi.hibernate_sequence SET next_val = next_val + 1;
            
            
            
DROP TABLE sportsbetting_oleksii_kovetskyi.bet_id;
DROP TABLE sportsbetting_oleksii_kovetskyi.outcome_id;