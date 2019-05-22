drop table if exists for_trading;
drop table if exists daily_info;
drop table if exists trading_record;
drop table if exists own;
drop table if exists stock;
drop table if exists company_account;
drop table if exists individual_account;

/*==============================================================*/
/* Table: individual_account                                    */
/*==============================================================*/
create table individual_account
(
   ID                   varchar(10) not null,
   name                 varchar(20) not null,
   pwHash               varchar(32) not null,
   gender               varchar(6) not null,
   asset                double not null,
   country              varchar(20) not null,
   phone                varchar(20),
   e_mail               varchar(30),
   primary key (ID)
);

/*==============================================================*/
/* Table: company_account                                       */
/*==============================================================*/
create table company_account
(
   ID                   varchar(10) not null,
   company_name         varchar(20) not null,
   pwHash               varchar(32) not null,
   country              varchar(20) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: stock                                                 */
/*==============================================================*/
create table stock
(
   ID                   varchar(10) not null,
   name                 varchar(20) not null,
   issue_time           date not null,
   issue_circulation    int not null,
   issue_price          double not null,
   remain               int not null,
   type                 varchar(20) not null,
   primary key (ID)
);

alter table stock add constraint FK_Reference_6 foreign key (ID)
      references company_account (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: own                                                   */
/*==============================================================*/
create table own
(
   acc_ID               varchar(10) not null,
   sto_ID               varchar(10) not null,
   number               int not null,
   primary key (acc_ID, sto_ID)
);

alter table own add constraint FK_Reference_2 foreign key (acc_ID)
      references individual_account (ID) on delete restrict on update restrict;

alter table own add constraint FK_Reference_3 foreign key (sto_ID)
      references stock (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: trading_record                                        */
/*==============================================================*/
create table trading_record
(
   acc_ID               varchar(10) not null,
   sto_ID               varchar(10) not null,
   date                 date not null,
   transcation          boolean not null,
   price                double not null,
   number               int not null,
   primary key (acc_ID, sto_ID, date)
);

alter table trading_record add constraint FK_Reference_4 foreign key (acc_ID)
      references individual_account (ID) on delete restrict on update restrict;

alter table trading_record add constraint FK_Reference_5 foreign key (sto_ID)
      references stock (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: daily_info                                            */
/*==============================================================*/
create table daily_info
(
   ID                   varchar(10) not null,
   date                 date not null,
   open_price           double not null,
   closing_price        double not null,
   max_price            double not null,
   min_price            double not null,
   turnover             int not null,
   now_price            double not null,
   upsanddowns          double not null,
   primary key (ID, date)
);

alter table daily_info add constraint FK_Reference_1 foreign key (ID)
      references stock (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: for_trading                                           */
/*==============================================================*/
create table for_trading
(
   sto_ID               varchar(10) not null,
   acc_ID               varchar(10) not null,
   date                 date not null,
   transcation          varchar(4) not null,
   price                double,
   number               int,
   primary key (sto_ID, acc_ID, date)
);

alter table for_trading add constraint FK_Reference_7 foreign key (sto_ID)
      references stock (ID) on delete restrict on update restrict;

alter table for_trading add constraint FK_Reference_8 foreign key (acc_ID)
      references individual_account (ID) on delete restrict on update restrict;
