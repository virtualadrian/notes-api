create table card (
    id bigint generated by default as identity,
    account_id bigint,
    card_answer clob,
    card_difficulty_id varchar(255),
    card_question varchar(255),
    card_rating_id varchar(255),
    created_ts timestamp,
    deck_id bigint,
    original_note_id bigint,
    primary key (id)
);

create table shared_note (
    id bigint generated by default as identity,
    note_created_ts timestamp,
    note_id bigint,
    share_expires timestamp,
    primary key (id)
);

alter table `note` change column `note_created_ts` `created_ts` datetime;
alter table `note` add column `is_private` boolean after `created_ts`;
alter table `note` add column `note_tags` TEXT after note_body;
ALTER TABLE `notelerdb`.`note` CHANGE COLUMN `created_ts` `created_ts` DATETIME NULL DEFAULT NULL AFTER `account_id`,
ALTER TABLE `notelerdb`.`note` CHANGE COLUMN `is_private` `is_private` TINYINT(1) NULL DEFAULT NULL AFTER `created_ts`;