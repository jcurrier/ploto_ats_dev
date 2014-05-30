
drop table if exists job_submission;
drop table if exists position;
drop table if exists user;
drop table if exists applicant;


# --- !Ups
create table if not exists position (
  id               varchar(255) not null,
  title            varchar(255),
  description      text,
  location         varchar(128),
  status           tinyint,
  posted           datetime,
  last_updated     timestamp default now(),
  constraint pk_position primary key (id)
);

create table if not exists user (
  email            varchar(255),
  password         varchar(255),
  last_updated     timestamp default now(),
  is_active        boolean default true,
  constraint pk_user primary key (email)
);

create table if not exists applicant (
  email            varchar(255) not null,
  resume_url       varchar(255) not null,
  is_active        boolean,
  last_updated     timestamp default now(),
  constraint pk_applicant primary key (email)
);

create table if not exists job_submission (
  position_id      varchar(255) not null,
  applicant_id     varchar(255) not null,
  recruiter        varchar(255) not null,
  hiring_mgr       varchar(255) not null,
  applied_on       timestamp,
  constraint pk_job_submission primary key (position_id, applicant_id),
  foreign key (position_id) references position (id),
  foreign key (applicant_id) references applicant (email),
  foreign key (recruiter) references user(email),
  foreign key (hiring_mgr) references user(email)
);