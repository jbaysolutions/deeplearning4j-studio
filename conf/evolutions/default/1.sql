# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table nnmodel (
  model_id                      bigint auto_increment not null,
  name                          varchar(255),
  status                        integer,
  creation_date                 timestamp,
  model_type                    integer,
  raw_model                     clob,
  constraint ck_nnmodel_status check ( status in (0,1,2,3,4,5)),
  constraint ck_nnmodel_model_type check ( model_type in (0)),
  constraint pk_nnmodel primary key (model_id)
);

create table nntraining_strategy (
  strategy_id                   bigint auto_increment not null,
  name                          varchar(255),
  creation_date                 timestamp,
  raw_strategy                  clob,
  status                        integer,
  model_model_id                bigint,
  constraint ck_nntraining_strategy_status check ( status in (0,1)),
  constraint pk_nntraining_strategy primary key (strategy_id)
);

create index ix_nntraining_strategy_model_model_id on nntraining_strategy (model_model_id);
alter table nntraining_strategy add constraint fk_nntraining_strategy_model_model_id foreign key (model_model_id) references nnmodel (model_id) on delete restrict on update restrict;


# --- !Downs

alter table nntraining_strategy drop constraint if exists fk_nntraining_strategy_model_model_id;
drop index if exists ix_nntraining_strategy_model_model_id;

drop table if exists nnmodel;

drop table if exists nntraining_strategy;

