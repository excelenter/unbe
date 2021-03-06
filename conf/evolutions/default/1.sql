# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  content                   TEXT,
  is_published              tinyint(1) default 0,
  is_approved               tinyint(1) default 0,
  publish_date              datetime,
  base_rating               integer,
  rating                    integer,
  list_image_id             varchar(40),
  tmp_content               TEXT,
  parent_cycle_id           bigint,
  article_type_id           integer,
  article_area_id           integer,
  constraint pk_article primary key (id))
;

create table article_area (
  id                        integer auto_increment not null,
  title                     varchar(255),
  constraint pk_article_area primary key (id))
;

create table article_type (
  id                        integer auto_increment not null,
  title                     varchar(255),
  constraint pk_article_type primary key (id))
;

create table cycle (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  logo_id                   varchar(40),
  description               varchar(255),
  creator_id                bigint,
  constraint pk_cycle primary key (id))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  second_name               varchar(255),
  password                  varchar(255),
  register_date             datetime,
  where_from                varchar(255),
  is_active                 tinyint(1) default 0,
  user_avatar_id            varchar(40),
  status_points             integer,
  gender                    integer,
  user_status_id            integer,
  constraint ck_user_gender check (gender in (0,1,2)),
  constraint pk_user primary key (id))
;

create table user_status (
  id                        integer auto_increment not null,
  title                     varchar(255),
  constraint pk_user_status primary key (id))
;

alter table article add constraint fk_article_listImage_1 foreign key (list_image_id) references s3file (id) on delete restrict on update restrict;
create index ix_article_listImage_1 on article (list_image_id);
alter table article add constraint fk_article_parentCycle_2 foreign key (parent_cycle_id) references cycle (id) on delete restrict on update restrict;
create index ix_article_parentCycle_2 on article (parent_cycle_id);
alter table article add constraint fk_article_articleType_3 foreign key (article_type_id) references article_type (id) on delete restrict on update restrict;
create index ix_article_articleType_3 on article (article_type_id);
alter table article add constraint fk_article_articleArea_4 foreign key (article_area_id) references article_area (id) on delete restrict on update restrict;
create index ix_article_articleArea_4 on article (article_area_id);
alter table cycle add constraint fk_cycle_logo_5 foreign key (logo_id) references s3file (id) on delete restrict on update restrict;
create index ix_cycle_logo_5 on cycle (logo_id);
alter table cycle add constraint fk_cycle_creator_6 foreign key (creator_id) references user (id) on delete restrict on update restrict;
create index ix_cycle_creator_6 on cycle (creator_id);
alter table user add constraint fk_user_userAvatar_7 foreign key (user_avatar_id) references s3file (id) on delete restrict on update restrict;
create index ix_user_userAvatar_7 on user (user_avatar_id);
alter table user add constraint fk_user_userStatus_8 foreign key (user_status_id) references user_status (id) on delete restrict on update restrict;
create index ix_user_userStatus_8 on user (user_status_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table article;

drop table article_area;

drop table article_type;

drop table cycle;

drop table s3file;

drop table user;

drop table user_status;

SET FOREIGN_KEY_CHECKS=1;

