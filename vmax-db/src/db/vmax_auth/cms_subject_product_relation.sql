drop table if exists cms_prefrence_area;

/*==============================================================*/
/* Table: cms_prefrence_area                                    */
/*==============================================================*/
create table cms_prefrence_area
(
   id                   bigint not null auto_increment,
   name                 varchar(255),
   sub_title            varchar(255),
   pic                  varbinary(500) comment '展示图片',
   sort                 int,
   show_status          int(1),
   primary key (id)
);

alter table cms_prefrence_area comment '优选专区';
