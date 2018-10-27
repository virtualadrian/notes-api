-- we don't know how to generate schema notelerdb (class Schema) :(

create table account
(
  id bigint not null
    primary key,
  address_line_one varchar(255) null,
  address_line_three varchar(255) null,
  address_line_two varchar(255) null,
  city varchar(255) null,
  email varchar(255) null,
  first_name varchar(255) null,
  last_name varchar(255) null,
  middle_initial varchar(255) null,
  state varchar(255) null,
  username varchar(255) null,
  zip varchar(255) null
)
  engine=InnoDB
;

create table app_role
(
  id bigint auto_increment
    primary key,
  description varchar(255) null,
  role_name varchar(255) null
)
  engine=InnoDB
;

create table app_user
(
  id bigint auto_increment
    primary key,
  account_expiration_date datetime null,
  email_address varchar(255) null,
  enabled bit null,
  first_name varchar(255) null,
  last_name varchar(255) null,
  locked bit null,
  password varchar(255) null,
  password_expiration_date datetime null,
  username varchar(255) null
)
  engine=InnoDB
;

create table app_user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint FKfnlxi1bmv5ao8u3nf30ymq7xa
  foreign key (user_id) references app_user (id),
  constraint FK6hkq1uibwvjsusnnxrsm1gh83
  foreign key (role_id) references app_role (id)
)
  engine=InnoDB
;

create index FK6hkq1uibwvjsusnnxrsm1gh83
  on app_user_role (role_id)
;

create index FKfnlxi1bmv5ao8u3nf30ymq7xa
  on app_user_role (user_id)
;

create table card
(
  id bigint auto_increment
    primary key,
  account_id bigint null,
  card_answer longtext null,
  card_difficulty_id varchar(255) null,
  card_question varchar(255) null,
  card_rating_id varchar(255) null,
  created_ts datetime null,
  deck_id bigint null,
  original_note_id bigint null
)
  engine=InnoDB
;

create table card_deck
(
  id bigint auto_increment
    primary key,
  account_id bigint null,
  deck_name varchar(255) null,
  course_id varchar(255) null,
  created_ts datetime null,
  deck_description varchar(255) null
)
  engine=InnoDB
;

create table hibernate_sequence
(
  next_val bigint null
)
  engine=InnoDB
;

create table note
(
  id bigint auto_increment
    primary key,
  account_id bigint null,
  cloned_from_note_id bigint null,
  created_ts datetime null,
  is_private bit null,
  note_body longtext null,
  note_tags longtext null,
  note_title longtext null
)
  engine=InnoDB
;

create table oauth_client_details
(
  client_id varchar(256) not null
    primary key,
  resource_ids varchar(256) null,
  client_secret varchar(256) null,
  scope varchar(256) null,
  authorized_grant_types varchar(256) null,
  web_server_redirect_uri varchar(256) null,
  authorities varchar(256) null,
  access_token_validity int null,
  refresh_token_validity int null,
  additional_information varchar(4096) null,
  autoapprove varchar(256) null
)
  engine=InnoDB
;

create table profile
(
  id bigint auto_increment
    primary key,
  account_id bigint null,
  created_ts datetime null,
  profile_avatar varchar(255) null,
  profile_biography varchar(255) null,
  profile_company varchar(255) null,
  profile_facebook varchar(255) null,
  profile_google_plus varchar(255) null,
  profile_linked_in varchar(255) null,
  profile_location varchar(255) null,
  profile_twitter varchar(255) null,
  profile_url varchar(255) null
)
  engine=InnoDB
;

create table shared_note
(
  id bigint auto_increment
    primary key,
  note_created_ts datetime null,
  note_id bigint null,
  share_expires datetime null
)
  engine=InnoDB
;

create table verification
(
  id bigint not null
    primary key,
  token varchar(255) null,
  token_expiration_date datetime null,
  user_id bigint null
)
  engine=InnoDB
;

