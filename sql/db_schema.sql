
drop table if exists job_submission;
drop table if exists customer;
drop table if exists position;
drop table if exists user;
drop table if exists applicant;

create table if not exists customer (
  id            varchar(255) not null,
  name          varchar(255) not null,
  description   text,
  is_active     boolean default true,
  last_updated  timestamp default now(),
  constraint pk_customer primary key (id)
);

create table if not exists user (
  customer_id      varchar(255) not null,
  id               varchar(255) not null,
  password         varchar(255),
  last_updated     timestamp default now(),
  is_active        boolean default true,
  constraint pk_user primary key (customer_id, id)
);

# --- !Ups
create table if not exists position (
  customer_id      varchar(255)  null,
  id               varchar(255) not null,
  title            varchar(255),
  description      text,
  location         varchar(128),
  status           tinyint,
  hiring_mgr       varchar(255) not null,
  recruiter        varchar(255),
  posted           datetime,
  last_updated     timestamp default now(),
  constraint pk_position primary key (customer_id, id)
);

create table if not exists applicant (
  customer_id      varchar(255) not null,
  id               varchar(255) not null,
  resume_url       varchar(255) not null,
  is_active        boolean,
  last_updated     timestamp default now(),
  constraint pk_applicant primary key (customer_id, id)
);

create table if not exists job_submission (
  customer_id      varchar(255) not null,
  position_id      varchar(255) not null,
  applicant_id     varchar(255) not null,
  recruiter        varchar(255) not null,
  hiring_mgr       varchar(255) not null,
  applied_on       timestamp,
  constraint pk_job_submission primary key (customer_id, position_id, applicant_id)
);