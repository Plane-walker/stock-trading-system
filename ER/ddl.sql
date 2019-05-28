drop table if exists for_trading;
drop table if exists pre_trading;
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
   ID                   varchar(20) not null,
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
   ID                   varchar(20) not null,
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
   ID                   varchar(20) not null,
   name                 varchar(20) not null,
   issue_time           datetime not null,
   issue_circulation    int not null,
   issue_price          double not null,
   type                 varchar(20) not null,
   now_price            double not null,
   turnover             int not null,
   upsanddowns          double not null,
   primary key (ID)
);

alter table stock add constraint FK_Reference_6 foreign key (ID)
      references company_account (ID) on delete restrict on update restrict;


/*==============================================================*/
/* Table: own                                                   */
/*==============================================================*/
create table own
(
   acc_ID               varchar(20) not null,
   sto_ID               varchar(20) not null,
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
   acc_ID               varchar(20) not null,
   sto_ID               varchar(20) not null,
   datetime             datetime not null,
   transcation          varchar(4) not null,
   price                double not null,
   number               int not null,
   primary key (acc_ID, sto_ID, datetime)
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
   ID                   varchar(20) not null,
   date                 date not null,
   open_price           double not null,
   closing_price        double not null,
   max_price            double not null,
   min_price            double not null,
   primary key (ID, date)
);

alter table daily_info add constraint FK_Reference_1 foreign key (ID)
      references stock (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: for_trading                                           */
/*==============================================================*/
create table for_trading
(
   sto_ID               varchar(20) not null,
   acc_ID               varchar(20) not null,
   datetime             datetime not null,
   transcation          varchar(4) not null,
   price                double,
   number               int,
   primary key (sto_ID, acc_ID, datetime)
);

alter table for_trading add constraint FK_Reference_7 foreign key (sto_ID)
      references stock (ID) on delete restrict on update restrict;

alter table for_trading add constraint FK_Reference_8 foreign key (acc_ID)
      references individual_account (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: pre_trading                                           */
/*==============================================================*/
create table pre_trading
(
   acc_ID               varchar(20) not null,
   sto_ID               varchar(20) not null,
   datetime                 datetime not null,
   transcation          varchar(4) not null,
   price                double,
   number               int,
   primary key (datetime, acc_ID, sto_ID)
);

alter table pre_trading add constraint FK_Reference_10 foreign key (sto_ID)
      references stock (ID) on delete restrict on update restrict;

alter table pre_trading add constraint FK_Reference_9 foreign key (acc_ID)
      references individual_account (ID) on delete restrict on update restrict;
drop procedure if exists purchase;
DELIMITER !!
create procedure purchase(i_ID varchar(20),stock_ID varchar(20),num int,offer_price double)
begin
insert into pre_trading(sto_ID,acc_ID,datetime,transcation,price,number)
value(stock_ID,i_ID,now(),"buy",offer_price,num);
end
!!
DELIMITER ;
drop procedure if exists sell;
DELIMITER !!
create procedure sell(i_ID varchar(20),stock_ID varchar(20),sell_num int,offer_price double)
begin
insert into pre_trading(sto_ID,acc_ID,datetime,transcation,price,number)
value(stock_ID,i_ID,now(),"sell",offer_price,sell_num);
end
!!
DELIMITER ;
drop trigger if exists deal;
DELIMITER !!
create trigger deal before insert on pre_trading for each row
begin
if new.transcation="buy" then
begin
declare sell_price double;
declare sell_num double;
declare sell_ID varchar(20);
declare sell_date datetime;
declare own_num int;
select acc_ID,price,number,datetime into sell_ID,sell_price,sell_num,sell_date from for_trading
where sto_ID=new.sto_ID and transcation="sell" and price<=new.price order by price desc limit 1;
while new.number>0 and sell_price is not null
do
if sell_num<=new.number then
begin
delete from for_trading
where sto_ID=new.sto_ID and acc_ID=sell_ID and transcation="sell" and datetime=sell_date;
set new.number=new.number-sell.num;
end;
else
begin
update for_trading
set number=number-new.number
where sto_ID=new.sto_ID and acc_ID=sell_ID and transcation="sell" and datetime=sell_date;
set sell_num=new.number;
set new.number=0;
end;
end if;
update individual_account
set asset=asset-sell_num*sell_price
where ID=new.acc_ID;
if sell_ID<>"admin" then
begin
update individual_account
set asset=asset+sell_num*sell_price
where ID=sell_ID;
end;
end if;
insert into trading_record(acc_ID,sto_ID,datetime,transcation,price,number)
value(new.acc_ID,new.sto_ID,now(),"buy",sell_price,sell_num);
insert into trading_record(acc_ID,sto_ID,datetime,transcation,price,number)
value(sell_ID,new.sto_ID,now(),"sell",sell_price,sell_num);
select number into own_num from own
where acc_ID=new.acc_ID and sto_ID=new.sto_ID;
update stock
set upsanddowns=(sell_price-now_price)/now_price
where ID=new.sto_ID;
update stock
set now_price=sell_price
where ID=new.sto_ID;
if own_num is not null then
begin
update own
set number=number+sell_num
where acc_ID=new.acc_ID and sto_ID=new.sto_ID;
end;
else
begin
insert into own(acc_ID,sto_ID,number)
value(new.acc_ID,new.sto_ID,sell_num);
end;
end if;
select acc_ID,price,number,datetime into sell_ID,sell_price,sell_num,sell_date from for_trading
where sto_ID=new.sto_ID and transcation="sell" and price<=new.price order by price desc limit 1;
end while;
if new.number>0 then
begin
update individual_account
set asset=asset-new.number*new.price
where ID=new.acc_ID;
insert into for_trading(sto_ID,acc_ID,datetime,transcation,price,number)
value(new.sto_ID,new.acc_ID,now(),"buy",new.price,new.number);
end;
end if;
end;
else
begin
declare buy_price double;
declare buy_num double;
declare buy_ID varchar(20);
declare buy_date datetime;
declare own_num int;
select acc_ID,price,number,datetime into buy_ID,buy_price,buy_num,buy_date from for_trading
where sto_ID=new.sto_ID and transcation="buy" and price>=new.price order by price desc limit 1;
while new.number>0 and buy_price is not null
do
if buy_num<=new.number then
begin
delete from for_trading
where sto_ID=new.sto_ID and acc_ID=buy_ID and transcation="buy" and datetime=buy_date;
set new.number=new.number-buy.num;
end;
else
begin
update for_trading
set number=number-new.number
where sto_ID=new.sto_ID and acc_ID=buy_ID and transcation="buy" and datetime=buy_date;
set buy_num=new.number;
set new.number=0;
end;
end if;
update individual_account
set asset=asset+buy_num*buy_price
where ID=new.acc_ID;
insert into trading_record(acc_ID,sto_ID,datetime,transcation,price,number)
value(new.acc_ID,new.sto_ID,now(),"sell",buy_price,buy_number);
insert into trading_record(acc_ID,sto_ID,datetime,transcation,price,number)
value(buy_ID,new.sto_ID,now(),"buy",buy_price,buy_number);
select number into own_num from own
where acc_ID=new.acc_ID and sto_ID=new.sto_ID;
if own_num>buy_num then
begin
update own
set number=number-buy_num
where acc_ID=new.acc_ID and sto_ID=new.sto_ID;
end;
else
begin
delete from own
where acc_ID=new.acc_ID and sto_ID=new.sto_ID;
end;
end if;
select acc_ID,price,number,datetime into buy_ID,buy_price,buy_num,buy_date from for_trading
where sto_ID=new.sto_ID and transcation="sell" and price<=new.price order by price desc limit 1;
end while;
if new.number>0 then
begin
insert into for_trading(sto_ID,acc_ID,datetime,transcation,price,number)
value(new.sto_ID,new.acc_ID,now(),"sell",new.price,new.number);
end;
end if;
end;
end if;
end
!!
DELIMITER ;