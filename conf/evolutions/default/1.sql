--- Created by Ebean DDL
--- To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table epoch_result (
  epoch_result_id               bigint auto_increment not null,
  epoch_number                  integer not null,
  score                         double not null,
  f1                            double not null,
  accuracy                      double not null,
  prec                          double not null,
  recall                        double not null,
  training_training_strategy_result_id bigint,
  constraint pk_epoch_result primary key (epoch_result_id)
);

create table nnmodel (
  model_id                      bigint auto_increment not null,
  name                          varchar(255),
  status                        integer,
  creation_date                 datetime(6),
  model_type                    integer,
  raw_model                     longtext,
  constraint ck_nnmodel_status check ( status in (0,1,2,3,4,5)),
  constraint ck_nnmodel_model_type check ( model_type in (0)),
  constraint pk_nnmodel primary key (model_id)
);

create table nntraining_strategy (
  strategy_id                   bigint auto_increment not null,
  name                          varchar(255),
  version                       integer not null,
  creation_date                 datetime(6),
  raw_strategy                  longtext,
  status                        integer,
  model_model_id                bigint,
  constraint ck_nntraining_strategy_status check ( status in (0,1)),
  constraint pk_nntraining_strategy primary key (strategy_id)
);

create table training_strategy_result (
  training_strategy_result_id   bigint auto_increment not null,
  version                       integer not null,
  execution_date                datetime(6),
  raw_strategy                  longtext,
  best_epoch                    integer not null,
  best_score                    double not null,
  savedfile                     longblob,
  strategy_strategy_id          bigint,
  constraint pk_training_strategy_result primary key (training_strategy_result_id)
);

create index ix_epoch_result_training_training_strategy_result_id on epoch_result (training_training_strategy_result_id);
alter table epoch_result add constraint fk_epoch_result_training_training_strategy_result_id foreign key (training_training_strategy_result_id) references training_strategy_result (training_strategy_result_id) on delete restrict on update restrict;

create index ix_nntraining_strategy_model_model_id on nntraining_strategy (model_model_id);
alter table nntraining_strategy add constraint fk_nntraining_strategy_model_model_id foreign key (model_model_id) references nnmodel (model_id) on delete restrict on update restrict;

create index ix_training_strategy_result_strategy_strategy_id on training_strategy_result (strategy_strategy_id);
alter table training_strategy_result add constraint fk_training_strategy_result_strategy_strategy_id foreign key (strategy_strategy_id) references nntraining_strategy (strategy_id) on delete restrict on update restrict;


# --- !Downs

alter table epoch_result drop foreign key fk_epoch_result_training_training_strategy_result_id;
drop index ix_epoch_result_training_training_strategy_result_id on epoch_result;

alter table nntraining_strategy drop foreign key fk_nntraining_strategy_model_model_id;
drop index ix_nntraining_strategy_model_model_id on nntraining_strategy;

alter table training_strategy_result drop foreign key fk_training_strategy_result_strategy_strategy_id;
drop index ix_training_strategy_result_strategy_strategy_id on training_strategy_result;

drop table if exists epoch_result;

drop table if exists nnmodel;

drop table if exists nntraining_strategy;

drop table if exists training_strategy_result;

